package dao;

import model.EstudianteMateria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteMateriaDAO {

    public List<EstudianteMateria> listarPorEstudiante(int idEstudiante) throws SQLException {
        String sql = "SELECT id_materia, periodo_academico FROM EstudianteMateria WHERE id_estudiante = ?";
        List<EstudianteMateria> materias = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                materias.add(new EstudianteMateria(
                        idEstudiante,
                        rs.getInt("id_materia"),
                        rs.getString("periodo_academico")
                ));
            }
        }
        return materias;
    }
    public boolean matricularEstudiante(int idEstudiante, int idMateria, String periodoAcademico) throws SQLException {
        String sql = "INSERT INTO EstudianteMateria (id_estudiante, id_materia, periodo_academico) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idMateria);
            stmt.setString(3, periodoAcademico);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean desmatricularEstudiante(int idEstudiante, int idMateria, String periodoAcademico) throws SQLException {
        String sql = "DELETE FROM EstudianteMateria WHERE id_estudiante = ? AND id_materia = ? AND periodo_academico = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idMateria);
            stmt.setString(3, periodoAcademico);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean estaMatriculado(int idEstudiante, int idMateria, String periodoAcademico) throws SQLException {
        String sql = "SELECT 1 FROM EstudianteMateria WHERE id_estudiante = ? AND id_materia = ? AND periodo_academico = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idMateria);
            stmt.setString(3, periodoAcademico);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<EstudianteMateria> listarTodas() throws SQLException {
        String sql = "SELECT id_estudiante, id_materia, periodo_academico FROM EstudianteMateria";
        List<EstudianteMateria> asignaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                asignaciones.add(new EstudianteMateria(
                        rs.getInt("id_estudiante"),
                        rs.getInt("id_materia"),
                        rs.getString("periodo_academico")
                ));
            }
        }
        return asignaciones;
    }

    public List<EstudianteMateria> listarMateriasPorEstudiante(int idEstudiante) throws SQLException {
        String sql = "SELECT id_materia, periodo_academico FROM EstudianteMateria WHERE id_estudiante = ?";
        List<EstudianteMateria> materias = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                materias.add(new EstudianteMateria(
                        idEstudiante,
                        rs.getInt("id_materia"),
                        rs.getString("periodo_academico")
                ));
            }
        }
        return materias;
    }

    public List<EstudianteMateria> listarEstudiantesPorMateria(int idMateria) throws SQLException {
        String sql = "SELECT id_estudiante, periodo_academico FROM EstudianteMateria WHERE id_materia = ?";
        List<EstudianteMateria> estudiantes = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                estudiantes.add(new EstudianteMateria(
                        rs.getInt("id_estudiante"),
                        idMateria,
                        rs.getString("periodo_academico")
                ));
            }
        }
        return estudiantes;
    }

    public List<EstudianteMateria> listarPorPeriodo(String periodoAcademico) throws SQLException {
        String sql = "SELECT id_estudiante, id_materia FROM EstudianteMateria WHERE periodo_academico = ?";
        List<EstudianteMateria> asignaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, periodoAcademico);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                asignaciones.add(new EstudianteMateria(
                        rs.getInt("id_estudiante"),
                        rs.getInt("id_materia"),
                        periodoAcademico
                ));
            }
        }
        return asignaciones;
    }
}