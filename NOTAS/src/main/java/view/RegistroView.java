package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegistroView extends JFrame {
    private JComboBox<String> cbTipo;
    private JTextField txtCedula, txtNombres, txtApellidos, txtEmail, txtTelefono;
    private JTextField txtUsername, txtDatoEspecifico;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegistrar;
    private JLabel lblDatoEspecifico;

    public RegistroView() {
        setTitle("Registro de Usuario");
        setSize(500, 550); // Aumentamos un poco el tamaño
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con borde y márgenes
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel para los datos personales
        JPanel panelDatosPersonales = new JPanel();
        panelDatosPersonales.setLayout(new BoxLayout(panelDatosPersonales, BoxLayout.Y_AXIS));
        panelDatosPersonales.setBorder(BorderFactory.createTitledBorder("Datos Personales"));

        // Panel para credenciales
        JPanel panelCredenciales = new JPanel();
        panelCredenciales.setLayout(new BoxLayout(panelCredenciales, BoxLayout.Y_AXIS));
        panelCredenciales.setBorder(BorderFactory.createTitledBorder("Credenciales de Acceso"));

        // Panel para el botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Componentes de datos personales
        addFormField(panelDatosPersonales, "Tipo de usuario:", cbTipo = new JComboBox<>(new String[]{"Estudiante", "Profesor", "Administrador"}));
        addFormField(panelDatosPersonales, "Cédula:", txtCedula = new JTextField());
        addFormField(panelDatosPersonales, "Nombres:", txtNombres = new JTextField());
        addFormField(panelDatosPersonales, "Apellidos:", txtApellidos = new JTextField());
        addFormField(panelDatosPersonales, "Email:", txtEmail = new JTextField());
        addFormField(panelDatosPersonales, "Teléfono:", txtTelefono = new JTextField());

        // Campo específico con label dinámico
        JPanel panelDatoEspecifico = new JPanel(new BorderLayout(5, 5));
        lblDatoEspecifico = new JLabel("Matrícula:");
        txtDatoEspecifico = new JTextField();
        panelDatoEspecifico.add(lblDatoEspecifico, BorderLayout.WEST);
        panelDatoEspecifico.add(txtDatoEspecifico, BorderLayout.CENTER);
        panelDatosPersonales.add(panelDatoEspecifico);
        panelDatosPersonales.add(Box.createVerticalStrut(5));

        // Componentes de credenciales
        addFormField(panelCredenciales, "Nombre de usuario:", txtUsername = new JTextField());
        addFormField(panelCredenciales, "Contraseña:", txtPassword = new JPasswordField());
        addFormField(panelCredenciales, "Confirmar contraseña:", txtConfirmPassword = new JPasswordField());

        // Botón de registro
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setPreferredSize(new Dimension(120, 30));
        panelBoton.add(btnRegistrar);

        // Agregar paneles al principal
        panelPrincipal.add(panelDatosPersonales, BorderLayout.NORTH);
        panelPrincipal.add(panelCredenciales, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Listener para cambiar el label del dato específico
        cbTipo.addActionListener(e -> actualizarDatoEspecifico());
    }

    private void addFormField(JPanel panel, String labelText, JComponent field) {
        JPanel fieldPanel = new JPanel(new BorderLayout(5, 5));
        fieldPanel.add(new JLabel(labelText), BorderLayout.WEST);
        fieldPanel.add(field, BorderLayout.CENTER);

        // Establecer tamaño preferido para campos de texto
        if (field instanceof JTextField) {
            field.setPreferredSize(new Dimension(200, 25));
        }

        panel.add(fieldPanel);
        panel.add(Box.createVerticalStrut(5)); // Espacio entre campos
    }

    private void actualizarDatoEspecifico() {
        String tipo = (String) cbTipo.getSelectedItem();
        switch (tipo) {
            case "Estudiante":
                lblDatoEspecifico.setText("Matrícula:");
                break;
            case "Profesor":
                lblDatoEspecifico.setText("Especialidad:");
                break;
            case "Administrador":
                lblDatoEspecifico.setText("Código admin:");
                break;
        }
        lblDatoEspecifico.revalidate();
        lblDatoEspecifico.repaint();
    }

    // Getters para los datos (se mantienen igual)
    public String getTipoUsuario() {
        return (String) cbTipo.getSelectedItem();
    }

    public String getCedula() {
        return txtCedula.getText().trim();
    }

    public String getNombres() {
        return txtNombres.getText().trim();
    }

    public String getApellidos() {
        return txtApellidos.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getTelefono() {
        return txtTelefono.getText().trim();
    }

    public String getDatoEspecifico() {
        return txtDatoEspecifico.getText().trim();
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public String getConfirmPassword() {
        return new String(txtConfirmPassword.getPassword());
    }

    // Métodos para control (se mantienen igual)
    public void addRegistrarListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCedula.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtDatoEspecifico.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
    }
}