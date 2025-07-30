package dao;

import model.ProfesorMateria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorMateriaDAO {
    public boolean asignarMateriaAProfesor(int idProfesor, int idMateria, String periodo) throws SQLException {
        String sql = "INSERT INTO ProfesorMateria (id_profesor, id_materia, periodo_academico) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfesor);
            stmt.setInt(2, idMateria);
            stmt.setString(3, periodo);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean estaProfesorAsignado(int idProfesor, int idMateria) throws SQLException {
        String sql = "SELECT 1 FROM ProfesorMateria WHERE id_profesor = ? AND id_materia = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfesor);
            stmt.setInt(2, idMateria);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean desasignarMateriaDeProfesor(int idProfesor, int idMateria, String periodo) throws SQLException {
        String sql = "DELETE FROM ProfesorMateria WHERE id_profesor = ? AND id_materia = ? AND periodo_academico = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfesor);
            stmt.setInt(2, idMateria);
            stmt.setString(3, periodo);

            return stmt.executeUpdate() > 0;
        }
    }

    public List<ProfesorMateria> listarTodas() throws SQLException {
        String sql = "SELECT id_profesor, id_materia, periodo_academico FROM ProfesorMateria";
        List<ProfesorMateria> asignaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProfesorMateria pm = new ProfesorMateria(
                        rs.getInt("id_profesor"),
                        rs.getInt("id_materia"),
                        rs.getString("periodo_academico")
                );
                asignaciones.add(pm);
            }
        }
        return asignaciones;
    }

    public List<ProfesorMateria> listarPorProfesor(int idProfesor) throws SQLException {
        String sql = "SELECT id_materia, periodo_academico FROM ProfesorMateria WHERE id_profesor = ?";
        List<ProfesorMateria> asignaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfesor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProfesorMateria pm = new ProfesorMateria(
                        idProfesor,
                        rs.getInt("id_materia"),
                        rs.getString("periodo_academico")
                );
                asignaciones.add(pm);
            }
        }
        return asignaciones;
    }

    public List<ProfesorMateria> listarPorMateria(int idMateria) throws SQLException {
        String sql = "SELECT id_profesor, periodo_academico FROM ProfesorMateria WHERE id_materia = ?";
        List<ProfesorMateria> asignaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMateria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProfesorMateria pm = new ProfesorMateria(
                        rs.getInt("id_profesor"),
                        idMateria,
                        rs.getString("periodo_academico")
                );
                asignaciones.add(pm);
            }
        }
        return asignaciones;
    }
}