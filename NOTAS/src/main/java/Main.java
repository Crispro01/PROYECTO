import controller.AuthController;
import dao.Conexion;
import dao.UsuarioDAO;
import view.AuthView;
import view.RegistroView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        if (!dao.Conexion.testConnection()) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar a la base de datos. Verifique la configuración.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            AuthView authView = new AuthView();
            new AuthController(authView);
            authView.setVisible(true);
        });
    }
}