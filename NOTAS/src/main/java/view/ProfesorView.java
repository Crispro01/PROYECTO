package view;

import model.Estudiante;
import model.Materia;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ProfesorView extends JFrame {
    private JComboBox<Materia> cbMaterias;
    private JTable tablaEstudiantes;
    private JButton btnGenerarReporte;
    private JButton btnAsignarNotas;
    private JTextField txtBuscarEstudiante;
    private JButton btnEliminar;
    private JButton btnCerrarSesion;

    public ProfesorView() {
        setTitle("Sistema Académico - Profesor");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior con combobox de materias
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Materia:"));
        cbMaterias = new JComboBox<>();
        cbMaterias.setPreferredSize(new Dimension(300, 25));
        panelSuperior.add(cbMaterias);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar estudiante:"));
        txtBuscarEstudiante = new JTextField(20);
        panelBusqueda.add(txtBuscarEstudiante);


        // Panel norte combinado
        JPanel panelNorte = new JPanel(new GridLayout(2, 1));
        panelNorte.add(panelSuperior);
        panelNorte.add(panelBusqueda);

        // Tabla de estudiantes
        tablaEstudiantes = new JTable();
        tablaEstudiantes.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Matrícula", "Nombres", "Apellidos", "Nota Parcial 1", "Nota Parcial 2", "Nota Final"}
        ));
        JScrollPane scrollEstudiantes = new JScrollPane(tablaEstudiantes);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGenerarReporte = new JButton("Generar Reporte");
        btnAsignarNotas = new JButton("Asignar Notas");
        panelBotones.add(btnGenerarReporte);
        panelBotones.add(btnAsignarNotas);
        btnEliminar = new JButton("Eliminar Estudiante");
        panelBotones.add(btnEliminar);
        btnCerrarSesion = new JButton("Cerrar Sesión");
        panelBotones.add(btnCerrarSesion);

        // Agregar componentes al frame
        add(panelNorte, BorderLayout.NORTH);
        add(scrollEstudiantes, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Configuración adicional de la tabla
        configurarTablaEstudiantes();
    }

    private void configurarTablaEstudiantes() {
        tablaEstudiantes.setAutoCreateRowSorter(true);
        tablaEstudiantes.setFillsViewportHeight(true);
        tablaEstudiantes.getTableHeader().setReorderingAllowed(false);

        // Ajustar ancho de columnas
        tablaEstudiantes.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tablaEstudiantes.getColumnModel().getColumn(1).setPreferredWidth(100); // Matrícula
        tablaEstudiantes.getColumnModel().getColumn(2).setPreferredWidth(150); // Nombres
        tablaEstudiantes.getColumnModel().getColumn(3).setPreferredWidth(150); // Apellidos
        tablaEstudiantes.getColumnModel().getColumn(4).setPreferredWidth(100); // Nota Parcial 1
        tablaEstudiantes.getColumnModel().getColumn(5).setPreferredWidth(100); // Nota Parcial 2
        tablaEstudiantes.getColumnModel().getColumn(6).setPreferredWidth(100); // Nota Final
    }

    // Métodos para materias
    public void cargarMaterias(List<Materia> materias) {
        cbMaterias.removeAllItems();
        for (Materia materia : materias) {
            cbMaterias.addItem(materia);
        }
    }

    public Materia getMateriaSeleccionada() {
        return (Materia) cbMaterias.getSelectedItem();
    }

    // Métodos para estudiantes
    public void cargarEstudiantes(List<Estudiante> estudiantes) {
        DefaultTableModel modelo = (DefaultTableModel) tablaEstudiantes.getModel();
        modelo.setRowCount(0);

        for (Estudiante estudiante : estudiantes) {
            modelo.addRow(new Object[]{
                    estudiante.getIdEstudiante(),
                    estudiante.getMatricula(),
                    estudiante.getNombres(),
                    estudiante.getApellidos(),
                    estudiante.getNotaParcial1(),
                    estudiante.getNotaParcial2(),
                    estudiante.getNotaFinal()
            });
        }
    }

    public Estudiante getEstudianteSeleccionado() {
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            return null;
        }

        DefaultTableModel modelo = (DefaultTableModel) tablaEstudiantes.getModel();
        return new Estudiante(
                (int) modelo.getValueAt(filaSeleccionada, 0),
                (String) modelo.getValueAt(filaSeleccionada, 1),
                (String) modelo.getValueAt(filaSeleccionada, 2),
                (String) modelo.getValueAt(filaSeleccionada, 3),
                (Double) modelo.getValueAt(filaSeleccionada, 4),
                (Double) modelo.getValueAt(filaSeleccionada, 5),
                (Double) modelo.getValueAt(filaSeleccionada, 6)
        );
    }

    public void mostrarDialogoAsignarNotas(Estudiante estudiante, Materia materia) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        JTextField txtParcial1 = new JTextField(5);
        JTextField txtParcial2 = new JTextField(5);
        JTextField txtFinal = new JTextField(5);

        if (estudiante.getNotaParcial1() != null) {
            txtParcial1.setText(String.valueOf(estudiante.getNotaParcial1()));
        }
        if (estudiante.getNotaParcial2() != null) {
            txtParcial2.setText(String.valueOf(estudiante.getNotaParcial2()));
        }
        if (estudiante.getNotaFinal() != null) {
            txtFinal.setText(String.valueOf(estudiante.getNotaFinal()));
        }

        panel.add(new JLabel("Estudiante:"));
        panel.add(new JLabel(estudiante.getNombres() + " " + estudiante.getApellidos()));
        panel.add(new JLabel("Materia:"));
        panel.add(new JLabel(materia.getNombre()));
        panel.add(new JLabel("Nota Parcial 1:"));
        panel.add(txtParcial1);
        panel.add(new JLabel("Nota Parcial 2:"));
        panel.add(txtParcial2);
        panel.add(new JLabel("Nota Final:"));
        panel.add(txtFinal);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Asignar Notas",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                estudiante.setNotaParcial1(Double.parseDouble(txtParcial1.getText()));
                estudiante.setNotaParcial2(Double.parseDouble(txtParcial2.getText()));
                estudiante.setNotaFinal(Double.parseDouble(txtFinal.getText()));
            } catch (NumberFormatException e) {
                mostrarMensaje("Error: Las notas deben ser valores numéricos");
                return;
            }
        }
    }

    public void mostrarReporte(String contenido) {
        JTextArea textArea = new JTextArea(contenido);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Reporte de Estudiantes", JOptionPane.INFORMATION_MESSAGE);
    }
    public void addEliminarListener(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }

    public void addMateriaSelectionListener(ActionListener listener) {
        cbMaterias.addActionListener(listener);
    }

    public void addGenerarReporteListener(ActionListener listener) {
        btnGenerarReporte.addActionListener(listener);
    }

    public void addAsignarNotasListener(ActionListener listener) {
        btnAsignarNotas.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public String getTextoBusqueda() {
        return txtBuscarEstudiante.getText().trim();
    }
    public void addCerrarSesionListener(ActionListener listener) {
        btnCerrarSesion.addActionListener(listener);
    }
}