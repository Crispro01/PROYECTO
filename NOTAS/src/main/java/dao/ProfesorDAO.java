package dao;

import model.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {
    public boolean registrarProfesor(Profesor profesor) throws SQLException {
        String sql = "INSERT INTO Profesor (id_profesor, especialidad) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, profesor.getIdProfesor());
            stmt.setString(2, profesor.getEspecialidad());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarProfesor(int idProfesor) throws SQLException {
        String sql = "DELETE FROM Profesor WHERE id_profesor = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfesor);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Profesor> listarTodos() throws SQLException {
        String sql = "SELECT p.id_profesor, p.especialidad, per.nombres, per.apellidos " +
                "FROM Profesor p " +
                "JOIN Persona per ON p.id_profesor = per.id_persona";

        List<Profesor> profesores = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Profesor profesor = new Profesor(
                        rs.getInt("id_profesor"),
                        rs.getString("especialidad")
                );
                profesor.setNombres(rs.getString("nombres"));
                profesor.setApellidos(rs.getString("apellidos"));
                profesores.add(profesor);
            }
        }
        return profesores;
    }

    public Profesor obtenerPorId(int idProfesor) throws SQLException {
        String sql = "SELECT p.especialidad, per.nombres, per.apellidos " +
                "FROM Profesor p " +
                "JOIN Persona per ON p.id_profesor = per.id_persona " +
                "WHERE p.id_profesor = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfesor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Profesor profesor = new Profesor(
                        idProfesor,
                        rs.getString("especialidad")
                );
                profesor.setNombres(rs.getString("nombres"));
                profesor.setApellidos(rs.getString("apellidos"));
                return profesor;
            }
        }
        return null;
    }

    public Profesor obtenerPorIdPersona(int idPersona) throws SQLException {
        String sql = "SELECT p.id_profesor, p.especialidad, per.nombres, per.apellidos " +
                "FROM Profesor p " +
                "JOIN Persona per ON p.id_profesor = per.id_persona " +
                "WHERE p.id_profesor = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Profesor profesor = new Profesor(
                        rs.getInt("id_profesor"),
                        rs.getString("especialidad")
                );
                profesor.setNombres(rs.getString("nombres"));
                profesor.setApellidos(rs.getString("apellidos"));
                return profesor;
            }
        }
        return null;
    }
}