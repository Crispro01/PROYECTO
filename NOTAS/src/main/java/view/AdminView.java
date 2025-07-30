package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTablaUsuarios;
    private JButton btnAgregarUsuario, btnEditarUsuario, btnEliminarUsuario, btnActualizarUsuarios;
    private JTable tablaAsignaciones;
    private DefaultTableModel modeloTablaAsignaciones;
    private JComboBox<String> cbProfesores, cbMaterias, cbPeriodos;
    private JButton btnAsignarMateria, btnEliminarAsignacion, btnActualizarAsignaciones;
    private JTable tablaEstudiantesMaterias;
    private DefaultTableModel modeloTablaEstudiantesMaterias;
    private JComboBox<String> cbEstudiantes, cbMateriasEstudiantes, cbPeriodosEstudiantes;
    private JButton btnAsignarEstudianteMateria, btnEliminarAsignacionEstudiante, btnActualizarAsignacionesEstudiantes;
    private JTextField txtBuscar;
    private JButton btnCerrarSesion;

    public AdminView() {
        setTitle("Sistema de Notas - Administrador");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel con pestañas
        tabbedPane = new JTabbedPane();

        // Panel de gestión de usuarios
        tabbedPane.addTab("Usuarios", crearPanelUsuarios());

        // Panel de asignación de materias a profesores
        tabbedPane.addTab("Asignación Profesores", crearPanelAsignaciones());

        // Panel de asignación de materias a estudiantes
        tabbedPane.addTab("Asignación Estudiantes", crearPanelAsignacionEstudiantes());

        add(tabbedPane);
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAgregarUsuario = new JButton("Agregar Usuario");
        btnEditarUsuario = new JButton("Editar");
        btnEliminarUsuario = new JButton("Eliminar");
        btnActualizarUsuarios = new JButton("Actualizar");

        panelBotones.add(btnAgregarUsuario);
        panelBotones.add(btnEditarUsuario);
        panelBotones.add(btnEliminarUsuario);
        panelBotones.add(btnActualizarUsuarios);

        // Tabla de usuarios
        modeloTablaUsuarios = new DefaultTableModel();
        modeloTablaUsuarios.addColumn("ID");
        modeloTablaUsuarios.addColumn("Usuario");
        modeloTablaUsuarios.addColumn("Rol");
        modeloTablaUsuarios.addColumn("Nombre Completo");

        tablaUsuarios = new JTable(modeloTablaUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);

        // Agregar componentes al panel principal
        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollUsuarios, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelAsignaciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de selección
        JPanel panelSeleccion = new JPanel(new GridLayout(4, 2, 5, 5));
        panelSeleccion.add(new JLabel("Profesor:"));
        cbProfesores = new JComboBox<>();
        panelSeleccion.add(cbProfesores);

        panelSeleccion.add(new JLabel("Materia:"));
        cbMaterias = new JComboBox<>();
        panelSeleccion.add(cbMaterias);

        panelSeleccion.add(new JLabel("Período:"));
        cbPeriodos = new JComboBox<>(new String[]{"1Q", "2Q", "3Q", "4Q"});
        panelSeleccion.add(cbPeriodos);

        btnAsignarMateria = new JButton("Asignar Materia");
        panelSeleccion.add(new JLabel());
        panelSeleccion.add(btnAsignarMateria);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnEliminarAsignacion = new JButton("Eliminar Asignación");
        btnActualizarAsignaciones = new JButton("Actualizar");
        btnCerrarSesion = new JButton("Cerrar Sesión");
        panelBotones.add(btnCerrarSesion);

        panelBotones.add(btnEliminarAsignacion);
        panelBotones.add(btnActualizarAsignaciones);

        modeloTablaAsignaciones = new DefaultTableModel();
        modeloTablaAsignaciones.addColumn("ID Profesor");
        modeloTablaAsignaciones.addColumn("Profesor");
        modeloTablaAsignaciones.addColumn("Materia");
        modeloTablaAsignaciones.addColumn("Período");

        tablaAsignaciones = new JTable(modeloTablaAsignaciones);
        JScrollPane scrollAsignaciones = new JScrollPane(tablaAsignaciones);

        // Agregar componentes al panel principal
        panel.add(panelSeleccion, BorderLayout.NORTH);
        panel.add(scrollAsignaciones, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelAsignacionEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de selección
        JPanel panelSeleccion = new JPanel(new GridLayout(4, 2, 5, 5));
        panelSeleccion.add(new JLabel("Estudiante:"));
        cbEstudiantes = new JComboBox<>();
        panelSeleccion.add(cbEstudiantes);

        panelSeleccion.add(new JLabel("Materia:"));
        cbMateriasEstudiantes = new JComboBox<>();
        panelSeleccion.add(cbMateriasEstudiantes);

        panelSeleccion.add(new JLabel("Período:"));
        cbPeriodosEstudiantes = new JComboBox<>(new String[]{"1Q", "2Q", "3Q", "4Q"});
        panelSeleccion.add(cbPeriodosEstudiantes);

        btnAsignarEstudianteMateria = new JButton("Asignar Materia");
        panelSeleccion.add(new JLabel());
        panelSeleccion.add(btnAsignarEstudianteMateria);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnEliminarAsignacionEstudiante = new JButton("Eliminar Asignación");
        btnActualizarAsignacionesEstudiantes = new JButton("Actualizar");

        panelBotones.add(btnEliminarAsignacionEstudiante);
        panelBotones.add(btnActualizarAsignacionesEstudiantes);

        // Tabla de asignaciones estudiantes-materias
        modeloTablaEstudiantesMaterias = new DefaultTableModel();
        modeloTablaEstudiantesMaterias.addColumn("ID Estudiante");
        modeloTablaEstudiantesMaterias.addColumn("Estudiante");
        modeloTablaEstudiantesMaterias.addColumn("Materia");
        modeloTablaEstudiantesMaterias.addColumn("Período");

        tablaEstudiantesMaterias = new JTable(modeloTablaEstudiantesMaterias);
        JScrollPane scrollAsignacionesEstudiantes = new JScrollPane(tablaEstudiantesMaterias);

        // Agregar componentes al panel principal
        panel.add(panelSeleccion, BorderLayout.NORTH);
        panel.add(scrollAsignacionesEstudiantes, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    // Métodos para la gestión de usuarios
    public void limpiarTablaUsuarios() {
        modeloTablaUsuarios.setRowCount(0);
    }

    public void agregarFilaUsuario(Object[] fila) {
        modeloTablaUsuarios.addRow(fila);
    }

    public int obtenerUsuarioSeleccionado() {
        return tablaUsuarios.getSelectedRow();
    }

    // Métodos para la gestión de asignaciones profesores-materias
    public void limpiarTablaAsignaciones() {
        modeloTablaAsignaciones.setRowCount(0);
    }

    public void agregarFilaAsignacion(Object[] fila) {
        modeloTablaAsignaciones.addRow(fila);
    }

    public int obtenerAsignacionSeleccionada() {
        return tablaAsignaciones.getSelectedRow();
    }

    // Métodos para la gestión de asignaciones estudiantes-materias
    public void limpiarTablaEstudiantesMaterias() {
        modeloTablaEstudiantesMaterias.setRowCount(0);
    }

    public void agregarFilaAsignacionEstudiante(Object[] fila) {
        modeloTablaEstudiantesMaterias.addRow(fila);
    }

    public int obtenerAsignacionEstudianteSeleccionada() {
        return tablaEstudiantesMaterias.getSelectedRow();
    }

    // Métodos para obtener selecciones
    public int getIdProfesorSeleccionado() {
        return cbProfesores.getSelectedIndex() >= 0 ?
                Integer.parseInt(cbProfesores.getSelectedItem().toString().split(" - ")[0]) : -1;
    }

    public int getIdMateriaSeleccionada() {
        return cbMaterias.getSelectedIndex() >= 0 ?
                Integer.parseInt(cbMaterias.getSelectedItem().toString().split(" - ")[0]) : -1;
    }

    public String getPeriodoSeleccionado() {
        return (String) cbPeriodos.getSelectedItem();
    }

    public int getIdEstudianteSeleccionado() {
        return cbEstudiantes.getSelectedIndex() >= 0 ?
                Integer.parseInt(cbEstudiantes.getSelectedItem().toString().split(" - ")[0]) : -1;
    }

    public int getIdMateriaEstudianteSeleccionada() {
        return cbMateriasEstudiantes.getSelectedIndex() >= 0 ?
                Integer.parseInt(cbMateriasEstudiantes.getSelectedItem().toString().split(" - ")[0]) : -1;
    }

    public String getPeriodoEstudianteSeleccionado() {
        return (String) cbPeriodosEstudiantes.getSelectedItem();
    }

    // Métodos para cargar combobox
    public void cargarProfesores(String[] profesores) {
        cbProfesores.removeAllItems();
        for (String profesor : profesores) {
            cbProfesores.addItem(profesor);
        }
    }

    public void cargarMaterias(String[] materias) {
        cbMaterias.removeAllItems();
        for (String materia : materias) {
            cbMaterias.addItem(materia);
        }
    }

    public void cargarEstudiantes(String[] estudiantes) {
        cbEstudiantes.removeAllItems();
        for (String estudiante : estudiantes) {
            cbEstudiantes.addItem(estudiante);
        }
    }

    public void cargarMateriasEstudiantes(String[] materias) {
        cbMateriasEstudiantes.removeAllItems();
        for (String materia : materias) {
            cbMateriasEstudiantes.addItem(materia);
        }
    }

    // Métodos para listeners
    public void addAgregarUsuarioListener(ActionListener listener) {
        btnAgregarUsuario.addActionListener(listener);
    }

    public void addEditarUsuarioListener(ActionListener listener) {
        btnEditarUsuario.addActionListener(listener);
    }

    public void addEliminarUsuarioListener(ActionListener listener) {
        btnEliminarUsuario.addActionListener(listener);
    }

    public void addActualizarUsuariosListener(ActionListener listener) {
        btnActualizarUsuarios.addActionListener(listener);
    }

    public void addAsignarMateriaListener(ActionListener listener) {
        btnAsignarMateria.addActionListener(listener);
    }

    public void addEliminarAsignacionListener(ActionListener listener) {
        btnEliminarAsignacion.addActionListener(listener);
    }

    public void addActualizarAsignacionesListener(ActionListener listener) {
        btnActualizarAsignaciones.addActionListener(listener);
    }

    public void addAsignarEstudianteMateriaListener(ActionListener listener) {
        btnAsignarEstudianteMateria.addActionListener(listener);
    }

    public void addEliminarAsignacionEstudianteListener(ActionListener listener) {
        btnEliminarAsignacionEstudiante.addActionListener(listener);
    }

    public void addActualizarAsignacionesEstudiantesListener(ActionListener listener) {
        btnActualizarAsignacionesEstudiantes.addActionListener(listener);
    }

    // Diálogos
    public Object[] mostrarDialogoUsuario(boolean esEdicion, Object[] datosActuales) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JComboBox<String> cbRol = new JComboBox<>(new String[]{"Administrador", "Profesor", "Estudiante"});
        JTextField txtNombreCompleto = new JTextField();

        if (esEdicion && datosActuales != null) {
            txtUsername.setText(datosActuales[0].toString());
            cbRol.setSelectedItem(datosActuales[1].toString());
            txtNombreCompleto.setText(datosActuales[2].toString());
        }

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPassword);
        panel.add(new JLabel("Rol:"));
        panel.add(cbRol);
        panel.add(new JLabel("Nombre Completo:"));
        panel.add(txtNombreCompleto);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                esEdicion ? "Editar Usuario" : "Agregar Usuario",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            return new Object[]{
                    txtUsername.getText().trim(),
                    new String(txtPassword.getPassword()).trim(),
                    cbRol.getSelectedItem().toString(),
                    txtNombreCompleto.getText().trim()
            };
        }
        return null;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public DefaultTableModel getModeloTablaAsignaciones() {
        return modeloTablaAsignaciones;
    }

    public DefaultTableModel getModeloTablaUsuarios() {
        return modeloTablaUsuarios;
    }

    public DefaultTableModel getModeloTablaEstudiantesMaterias() {
        return modeloTablaEstudiantesMaterias;
    }

    public String getTextoBusqueda() {
        return txtBuscar.getText().trim();
    }

    public void addCerrarSesionListener(ActionListener listener) {
        btnCerrarSesion.addActionListener(listener);
    }
}