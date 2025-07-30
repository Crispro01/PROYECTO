package dao;

import model.Materia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {
    public List<Materia> listarMateriasPorProfesor(int idProfesor) throws SQLException {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT m.id_materia, m.nombre, m.descripcion, m.creditos " +
                "FROM Materia m JOIN ProfesorMateria pm ON m.id_materia = pm.id_materia " +
                "WHERE pm.id_profesor = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfesor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    materias.add(new Materia(
                            rs.getInt("id_materia"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getInt("creditos")
                    ));
                }
            }
        }
        return materias;
    }

    public List<String> listarNombresMateriasPorProfesor(int idProfesor) throws SQLException {
        List<String> nombresMaterias = new ArrayList<>();
        String sql = "SELECT m.nombre FROM Materia m " +
                "JOIN ProfesorMateria pm ON m.id_materia = pm.id_materia " +
                "WHERE pm.id_profesor = ? " +
                "ORDER BY m.nombre";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfesor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nombresMaterias.add(rs.getString("nombre"));
                }
            }
        }
        return nombresMaterias;
    }

    public int obtenerIdMateriaPorNombre(String nombreMateria) throws SQLException {
        String sql = "SELECT id_materia FROM Materia WHERE nombre = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreMateria);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_materia");
                }
            }
        }
        return -1;
    }

    public Materia obtenerPorNombre(String nombreMateria) throws SQLException {
        String sql = "SELECT id_materia, nombre, descripcion, creditos FROM Materia WHERE nombre = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreMateria);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Materia(
                            rs.getInt("id_materia"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getInt("creditos")
                    );
                }
            }
        }
        return null;
    }

    public List<Materia> listarTodas() throws SQLException {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT id_materia, nombre, descripcion, creditos FROM Materia";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                materias.add(new Materia(
                        rs.getInt("id_materia"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("creditos")
                ));
            }
        }
        return materias;
    }

    public Materia obtenerPorId(int idMateria) throws SQLException {
        String sql = "SELECT nombre, descripcion, creditos FROM Materia WHERE id_materia = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Materia(
                            idMateria,
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getInt("creditos")
                    );
                }
            }
        }
        return null;
    }
}