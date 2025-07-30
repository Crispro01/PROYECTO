package dao;

import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, p.tipo as rol FROM Usuario u JOIN Persona p ON u.id_persona = p.id_persona";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getInt("id_persona"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("rol")));
            }
        }
        return usuarios;
    }

    public boolean crearUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (id_persona, username, password) VALUES (?, ?, SHA2(?, 256))";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getIdPersona());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getPassword());

            return stmt.executeUpdate() > 0;
        }
    }

    public Usuario buscarPorId(int idUsuario) throws SQLException {
        String sql = "SELECT u.*, p.tipo as rol FROM Usuario u JOIN Persona p ON u.id_persona = p.id_persona WHERE u.id_usuario = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getInt("id_persona"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("rol"));
                }
            }
        }
        return null;
    }

    public Usuario buscarPorIdPersona(int idPersona) throws SQLException {
        String sql = "SELECT u.*, p.tipo as rol FROM Usuario u JOIN Persona p ON u.id_persona = p.id_persona WHERE u.id_persona = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getInt("id_persona"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("rol"));
                }
            }
        }
        return null;
    }

    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE Usuario SET username = ?, password = CASE WHEN ? = '' THEN password ELSE SHA2(?, 256) END WHERE id_usuario = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getPassword());
            stmt.setInt(4, usuario.getIdUsuario());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (id_persona, username, password) VALUES (?, ?, SHA2(?, 256))";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, usuario.getIdPersona());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getPassword());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setIdUsuario(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public Usuario autenticarUsuario(String username, String password) throws SQLException {
        String sql = "SELECT u.*, p.tipo as rol FROM Usuario u " +
                "JOIN Persona p ON u.id_persona = p.id_persona " +
                "WHERE u.username = ? AND u.password = SHA2(?, 256)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getInt("id_persona"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("rol")
                    );
                }
            }
        }
        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, p.tipo as rol FROM Usuario u JOIN Persona p ON u.id_persona = p.id_persona";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getInt("id_persona"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("rol")
                ));
            }
        }
        return usuarios;
    }
}