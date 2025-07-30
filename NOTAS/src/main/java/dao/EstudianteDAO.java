package dao;

import model.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    public boolean registrarEstudiante(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO Estudiante (id_estudiante, matricula) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estudiante.getIdEstudiante());
            stmt.setString(2, estudiante.getMatricula());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarEstudiante(int idEstudiante) throws SQLException {
        String sql = "DELETE FROM Estudiante WHERE id_estudiante = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Estudiante> listarTodos() throws SQLException {
        String sql = "SELECT e.id_estudiante, e.matricula, p.nombres, p.apellidos, p.email " +
                "FROM Estudiante e JOIN Persona p ON e.id_estudiante = p.id_persona";

        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                estudiantes.add(new Estudiante(
                        rs.getInt("id_estudiante"),
                        rs.getString("matricula"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                ));
            }
        }
        return estudiantes;
    }

    public List<Estudiante> listarEstudiantesPorMateria(int idMateria) throws SQLException {
        String sql = "SELECT e.id_estudiante, e.matricula, p.nombres, p.apellidos, p.email, " +
                "n.nota_parcial1, n.nota_parcial2, n.nota_final " +
                "FROM Estudiante e " +
                "JOIN Persona p ON e.id_estudiante = p.id_persona " +
                "JOIN EstudianteMateria em ON e.id_estudiante = em.id_estudiante " +
                "LEFT JOIN Nota n ON e.id_estudiante = n.id_estudiante AND em.id_materia = n.id_materia " +
                "WHERE em.id_materia = ?";

        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                        rs.getInt("id_estudiante"),
                        rs.getString("matricula"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                );

                // Asignar notas si existen
                estudiante.setNotaParcial1(rs.getDouble("nota_parcial1"));
                if (rs.wasNull()) estudiante.setNotaParcial1(null);

                estudiante.setNotaParcial2(rs.getDouble("nota_parcial2"));
                if (rs.wasNull()) estudiante.setNotaParcial2(null);

                estudiante.setNotaFinal(rs.getDouble("nota_final"));
                if (rs.wasNull()) estudiante.setNotaFinal(null);

                estudiantes.add(estudiante);
            }
        }
        return estudiantes;
    }

    public Estudiante buscarPorId(int idEstudiante) throws SQLException {
        String sql = "SELECT e.id_estudiante, e.matricula, p.nombres, p.apellidos, p.email " +
                "FROM Estudiante e JOIN Persona p ON e.id_estudiante = p.id_persona " +
                "WHERE e.id_estudiante = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estudiante(
                        rs.getInt("id_estudiante"),
                        rs.getString("matricula"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                );
            }
            return null;
        }
    }

    public boolean actualizarNotas(int idEstudiante, int idMateria, int idProfesor,
                                   String periodo, Double notaParcial1,
                                   Double notaParcial2, Double notaFinal) throws SQLException {
        // Verificar si ya existe un registro de notas para este estudiante en esta materia y periodo
        String sqlCheck = "SELECT 1 FROM Nota WHERE id_estudiante = ? AND id_materia = ? AND periodo = ?";
        String sqlInsert = "INSERT INTO Nota (id_estudiante, id_materia, id_profesor, periodo, " +
                "nota_parcial1, nota_parcial2, nota_final) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE Nota SET nota_parcial1 = ?, nota_parcial2 = ?, nota_final = ?, " +
                "id_profesor = ? WHERE id_estudiante = ? AND id_materia = ? AND periodo = ?";

        try (Connection conn = Conexion.getConnection()) {
            // Verificar si existe registro previo
            boolean existeRegistro = false;
            try (PreparedStatement stmt = conn.prepareStatement(sqlCheck)) {
                stmt.setInt(1, idEstudiante);
                stmt.setInt(2, idMateria);
                stmt.setString(3, periodo);
                try (ResultSet rs = stmt.executeQuery()) {
                    existeRegistro = rs.next();
                }
            }

            // Insertar o actualizar según corresponda
            if (existeRegistro) {
                try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
                    stmt.setDouble(1, notaParcial1);
                    stmt.setDouble(2, notaParcial2);
                    stmt.setDouble(3, notaFinal);
                    stmt.setInt(4, idProfesor);
                    stmt.setInt(5, idEstudiante);
                    stmt.setInt(6, idMateria);
                    stmt.setString(7, periodo);
                    return stmt.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                    stmt.setInt(1, idEstudiante);
                    stmt.setInt(2, idMateria);
                    stmt.setInt(3, idProfesor);
                    stmt.setString(4, periodo);
                    stmt.setDouble(5, notaParcial1);
                    stmt.setDouble(6, notaParcial2);
                    stmt.setDouble(7, notaFinal);
                    return stmt.executeUpdate() > 0;
                }
            }
        }
    }

    public boolean matricularEstudiante(int idEstudiante, int idMateria, String periodo) throws SQLException {
        String sql = "INSERT INTO EstudianteMateria (id_estudiante, id_materia, periodo_academico) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idMateria);
            stmt.setString(3, periodo);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean desmatricularEstudiante(int idEstudiante, int idMateria, String periodo) throws SQLException {
        String sql = "DELETE FROM EstudianteMateria WHERE id_estudiante = ? AND id_materia = ? AND periodo_academico = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idMateria);
            stmt.setString(3, periodo);

            return stmt.executeUpdate() > 0;
        }
    }
    public List<Estudiante> obtenerMateriasConNotas(int idEstudiante) throws SQLException {
        String sql = "SELECT m.id_materia, m.nombre, m.creditos, " +
                "n.nota_parcial1, n.nota_parcial2, n.nota_final " +
                "FROM Materia m " +
                "JOIN EstudianteMateria em ON m.id_materia = em.id_materia " +
                "LEFT JOIN Nota n ON m.id_materia = n.id_materia AND em.id_estudiante = n.id_estudiante " +
                "WHERE em.id_estudiante = ?";

        List<Estudiante> materias = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                        idEstudiante,
                        "", // Matrícula no necesaria aquí
                        "", // Nombres no necesarios
                        "", // Apellidos no necesarios
                        rs.getDouble("nota_parcial1"),
                        rs.getDouble("nota_parcial2"),
                        rs.getDouble("nota_final")
                );

                // Configurar notas (manejar NULL)
                estudiante.setNotaParcial1(rs.getObject("nota_parcial1") != null ? rs.getDouble("nota_parcial1") : null);
                estudiante.setNotaParcial2(rs.getObject("nota_parcial2") != null ? rs.getDouble("nota_parcial2") : null);
                estudiante.setNotaFinal(rs.getObject("nota_final") != null ? rs.getDouble("nota_final") : null);

                materias.add(estudiante);
            }
        }
        return materias;
    }
    public Estudiante obtenerNotasEstudianteMateria(int idEstudiante, int idMateria) throws SQLException {
        String sql = "SELECT nota_parcial1, nota_parcial2, nota_final " +
                "FROM Nota " +
                "WHERE id_estudiante = ? AND id_materia = ?";

        Estudiante estudiante = new Estudiante(idEstudiante, "");

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idMateria);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                estudiante.setNotaParcial1(rs.getObject("nota_parcial1") != null ? rs.getDouble("nota_parcial1") : null);
                estudiante.setNotaParcial2(rs.getObject("nota_parcial2") != null ? rs.getDouble("nota_parcial2") : null);
                estudiante.setNotaFinal(rs.getObject("nota_final") != null ? rs.getDouble("nota_final") : null);
            }
        }

        return estudiante;
    }
}