package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sistema_notas";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public static boolean testConnection() {
        Connection conn = null;
        try {
            // Configurar propiedades de conexión
            Properties props = new Properties();
            props.setProperty("user", DB_USER);
            props.setProperty("password", DB_PASSWORD);
            props.setProperty("useSSL", "false");
            props.setProperty("serverTimezone", "UTC");
            props.setProperty("allowPublicKeyRetrieval", "true");

            // Intentar establecer conexión
            conn = DriverManager.getConnection(DB_URL, props);

            return conn.isValid(2);
        } catch (SQLException e) {
            System.err.println("Error al probar la conexión:");
            System.err.println("URL: " + DB_URL);
            System.err.println("Usuario: " + DB_USER);
            System.err.println("Mensaje de error: " + e.getMessage());
            return false;
        } finally {
            //
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión de prueba: " + e.getMessage());
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);
        props.setProperty("useSSL", "false");
        props.setProperty("serverTimezone", "UTC");
        props.setProperty("allowPublicKeyRetrieval", "true");
        return DriverManager.getConnection(DB_URL, props);
    }
}