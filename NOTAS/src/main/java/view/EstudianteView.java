package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class EstudianteView extends JFrame {
    private JTable tablaMaterias;
    private JLabel lblNombre, lblMatricula, lblPromedio;
    private JButton btnActualizar;
    private JButton btnCerrarSesion;

    public EstudianteView() {
        setTitle("Sistema Académico - Estudiante");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new GridLayout(3, 1, 5, 5));
        lblNombre = new JLabel("Nombre: ");
        lblMatricula = new JLabel("Matrícula: ");
        lblPromedio = new JLabel("Promedio General: ");
        panelSuperior.add(lblNombre);
        panelSuperior.add(lblMatricula);
        panelSuperior.add(lblPromedio);

        tablaMaterias = new JTable();
        tablaMaterias.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Materia", "Créditos", "Parcial 1", "Parcial 2", "Final", "Promedio", "Estado"}
        ));
        JScrollPane scrollMaterias = new JScrollPane(tablaMaterias);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnActualizar = new JButton("Actualizar");
        btnCerrarSesion = new JButton("Cerrar Sesión");
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrarSesion);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollMaterias, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        configurarTabla();
    }

    private void configurarTabla() {
        tablaMaterias.setAutoCreateRowSorter(true);
        tablaMaterias.setFillsViewportHeight(true);
        tablaMaterias.getTableHeader().setReorderingAllowed(false);

        tablaMaterias.getColumnModel().getColumn(0).setPreferredWidth(200); // Materia
        tablaMaterias.getColumnModel().getColumn(1).setPreferredWidth(80);  // Créditos
        tablaMaterias.getColumnModel().getColumn(2).setPreferredWidth(80);  // Parcial 1
        tablaMaterias.getColumnModel().getColumn(3).setPreferredWidth(80);  // Parcial 2
        tablaMaterias.getColumnModel().getColumn(4).setPreferredWidth(80);  // Final
        tablaMaterias.getColumnModel().getColumn(5).setPreferredWidth(80);  // Promedio
        tablaMaterias.getColumnModel().getColumn(6).setPreferredWidth(100); // Estado
    }

    public void mostrarDatosEstudiante(String nombre, String matricula, double promedio) {
        lblNombre.setText("Nombre: " + nombre);
        lblMatricula.setText("Matrícula: " + matricula);
        lblPromedio.setText("Promedio General: " + String.format("%.2f", promedio));
    }

    public void cargarMaterias(Object[][] datos) {
        DefaultTableModel modelo = (DefaultTableModel) tablaMaterias.getModel();
        modelo.setRowCount(0);

        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
    }

    public void addActualizarListener(ActionListener listener) {
        btnActualizar.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void addCerrarSesionListener(ActionListener listener) {
        btnCerrarSesion.addActionListener(listener);
    }
}