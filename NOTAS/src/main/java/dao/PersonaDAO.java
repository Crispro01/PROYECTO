package dao;

import model.Persona;
import java.sql.*;

public class PersonaDAO {
    public int registrarPersona(Persona persona) throws SQLException {
        String sql = "INSERT INTO Persona (cedula, nombres, apellidos, email, telefono, tipo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, persona.getCedula());
            stmt.setString(2, persona.getNombres());
            stmt.setString(3, persona.getApellidos());
            stmt.setString(4, persona.getEmail());
            stmt.setString(5, persona.getTelefono());
            stmt.setString(6, persona.getTipo());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            return 0;
        }
    }

    public Persona obtenerPorId(int idPersona) throws SQLException {
        String sql = "SELECT * FROM Persona WHERE id_persona = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Persona(
                            rs.getInt("id_persona"),
                            rs.getString("cedula"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getString("tipo")
                    );
                }
            }
        }
        return null;
    }

    public boolean eliminarPersona(int idPersona) throws SQLException {
        String sql = "DELETE FROM Persona WHERE id_persona = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            return stmt.executeUpdate() > 0;
        }
    }

    public void actualizarPersona(Persona persona) {
    }
}