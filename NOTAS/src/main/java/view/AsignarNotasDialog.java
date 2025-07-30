package view;

import javax.swing.*;
import java.awt.*;
import model.Estudiante;
import model.Materia;

public class AsignarNotasDialog extends JDialog {
    private JTextField txtParcial1;
    private JTextField txtParcial2;
    private JTextField txtFinal;
    private boolean aceptado = false;

    public AsignarNotasDialog(JFrame parent, Estudiante estudiante, Materia materia) {
        super(parent, "Asignar Notas", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Información del estudiante y materia
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Estudiante:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(estudiante.getNombres() + " " + estudiante.getApellidos()), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(estudiante.getMatricula()), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Materia:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(materia.getNombre()), gbc);

        // Campos para notas
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Nota Parcial 1:"), gbc);
        gbc.gridx = 1;
        txtParcial1 = new JTextField(5);
        if (estudiante.getNotaParcial1() != null) {
            txtParcial1.setText(String.valueOf(estudiante.getNotaParcial1()));
        }
        panel.add(txtParcial1, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Nota Parcial 2:"), gbc);
        gbc.gridx = 1;
        txtParcial2 = new JTextField(5);
        if (estudiante.getNotaParcial2() != null) {
            txtParcial2.setText(String.valueOf(estudiante.getNotaParcial2()));
        }
        panel.add(txtParcial2, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Nota Final:"), gbc);
        gbc.gridx = 1;
        txtFinal = new JTextField(5);
        if (estudiante.getNotaFinal() != null) {
            txtFinal.setText(String.valueOf(estudiante.getNotaFinal()));
        }
        panel.add(txtFinal, gbc);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> {
            aceptado = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public Double getNotaParcial1() {
        try {
            return Double.parseDouble(txtParcial1.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Double getNotaParcial2() {
        try {
            return Double.parseDouble(txtParcial2.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Double getNotaFinal() {
        try {
            return Double.parseDouble(txtFinal.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}