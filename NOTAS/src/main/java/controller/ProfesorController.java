package controller;

import dao.*;
import model.*;
import view.AuthView;
import view.ProfesorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.List;

public class ProfesorController {
    private final ProfesorView view;
    private final Usuario usuario;
    private final MateriaDAO materiaDAO;
    private final EstudianteDAO estudianteDAO;
    private int idMateriaActual;
    private List<Estudiante> estudiantesActuales;
    private int idProfesorActual;

    public ProfesorController(ProfesorView view, Usuario usuario) throws SQLException {
        this.view = view;
        this.usuario = usuario;
        this.materiaDAO = new MateriaDAO();
        this.estudianteDAO = new EstudianteDAO();

        configurarVista();
        configurarListeners();
        cargarDatosIniciales();
    }

    private void configurarVista() {
        view.setTitle("Sistema Académico - Profesor: " + usuario.getUsername());
    }

    private void configurarListeners() {
        view.addMateriaSelectionListener(e -> cambiarMateria());
        view.addGenerarReporteListener(this::generarReporte);
        view.addAsignarNotasListener(this::asignarNotas);
        view.addEliminarListener(this::eliminarEstudiante);
        view.addCerrarSesionListener(this::cerrarSesion);
    }

    private void cargarDatosIniciales() throws SQLException {
        idProfesorActual = obtenerIdProfesor();

        if (idProfesorActual > 0) {
            List<Materia> materias = materiaDAO.listarMateriasPorProfesor(idProfesorActual);
            view.cargarMaterias(materias);

            if (!materias.isEmpty()) {
                idMateriaActual = materias.get(0).getIdMateria();
                cargarEstudiantes();
            }
        } else {
            view.mostrarMensaje("No se pudo identificar al profesor");
        }
    }

    private int obtenerIdProfesor() {
        String sql = "SELECT id_profesor FROM Profesor WHERE id_profesor = " +
                "(SELECT id_persona FROM Usuario WHERE username = ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("id_profesor") : -1;
            }
        } catch (SQLException e) {
            view.mostrarMensaje("Error al obtener ID del profesor: " + e.getMessage());
            return -1;
        }
    }

    private void cambiarMateria() {
        Materia materiaSeleccionada = view.getMateriaSeleccionada();
        if (materiaSeleccionada != null) {
            idMateriaActual = materiaSeleccionada.getIdMateria();
            cargarEstudiantes();
        }
    }

    private void cargarEstudiantes() {
        try {
            estudiantesActuales = estudianteDAO.listarEstudiantesPorMateria(idMateriaActual);
            view.cargarEstudiantes(estudiantesActuales);
        } catch (SQLException e) {
            view.mostrarMensaje("Error al cargar estudiantes: " + e.getMessage());
        }
    }

    private void asignarNotas(ActionEvent e) {
        Estudiante estudiante = view.getEstudianteSeleccionado();
        Materia materia = view.getMateriaSeleccionada();

        if (estudiante == null) {
            view.mostrarMensaje("Seleccione un estudiante para asignar notas");
            return;
        }

        if (materia == null) {
            view.mostrarMensaje("Seleccione una materia para asignar notas");
            return;
        }

        view.mostrarDialogoAsignarNotas(estudiante, materia);

        try {
            boolean exito = estudianteDAO.actualizarNotas(
                    estudiante.getIdEstudiante(),
                    materia.getIdMateria(),
                    idProfesorActual,
                    "1Q",
                    estudiante.getNotaParcial1(),
                    estudiante.getNotaParcial2(),
                    estudiante.getNotaFinal()
            );

            if (exito) {
                view.mostrarMensaje("Notas actualizadas correctamente");
                cargarEstudiantes();
            } else {
                view.mostrarMensaje("Error al actualizar las notas");
            }
        } catch (SQLException ex) {
            view.mostrarMensaje("Error al guardar notas: " + ex.getMessage());
        }
    }

    private void generarReporte(ActionEvent e) {
        Materia materia = view.getMateriaSeleccionada();
        if (materia == null) {
            view.mostrarMensaje("Seleccione una materia para generar el reporte");
            return;
        }

        try {
            String reporte = generarContenidoReporte(materia);
            view.mostrarReporte(reporte);
        } catch (SQLException ex) {
            view.mostrarMensaje("Error al generar reporte: " + ex.getMessage());
        }
    }

    private String generarContenidoReporte(Materia materia) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("═".repeat(50)).append("\n");
        sb.append("REPORTE ACADÉMICO - ").append(materia.getNombre()).append("\n");
        sb.append("═".repeat(50)).append("\n\n");
        sb.append(String.format("%-20s %-15s %-15s %-8s %-8s %-8s%n",
                "ESTUDIANTE", "MATRÍCULA", "CORREO", "P1", "P2", "FINAL"));
        sb.append("-".repeat(74)).append("\n");

        List<Estudiante> estudiantes = estudianteDAO.listarEstudiantesPorMateria(materia.getIdMateria());
        for (Estudiante estudiante : estudiantes) {
            sb.append(String.format("%-20s %-15s %-15s %-8s %-8s %-8s%n",
                    estudiante.getNombres() + " " + estudiante.getApellidos(),
                    estudiante.getMatricula(),
                    estudiante.getEmail(),
                    estudiante.getNotaParcial1() != null ? String.format("%.1f", estudiante.getNotaParcial1()) : "-",
                    estudiante.getNotaParcial2() != null ? String.format("%.1f", estudiante.getNotaParcial2()) : "-",
                    estudiante.getNotaFinal() != null ? String.format("%.1f", estudiante.getNotaFinal()) : "-"));
        }

        sb.append("\n").append("Total estudiantes: ").append(estudiantes.size());
        return sb.toString();
    }

    private void cerrarSesion(ActionEvent e) {
        view.dispose();

        AuthView authView = new AuthView();
        new AuthController(authView);
        authView.setVisible(true);
    }
    private void eliminarEstudiante(ActionEvent e) {
        Estudiante estudiante = view.getEstudianteSeleccionado();
        if (estudiante == null) {
            view.mostrarMensaje("Seleccione un estudiante para eliminar");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                view,
                "¿Eliminar al estudiante " + estudiante.getNombres() + " " + estudiante.getApellidos() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Solo elimina al estudiante (sin notas asociadas)
                boolean exito = estudianteDAO.eliminarEstudiante(estudiante.getIdEstudiante());

                if (exito) {
                    view.mostrarMensaje("Estudiante eliminado correctamente");
                    cargarEstudiantes();  // Refrescar la tabla
                } else {
                    view.mostrarMensaje("Error al eliminar el estudiante");
                }
            } catch (SQLException ex) {
                view.mostrarMensaje("Error de base de datos: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    public void mostrarVista() {
        view.setVisible(true);
    }

}