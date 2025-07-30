package controller;

import dao.*;
import model.*;
import view.AuthView;
import view.EstudianteView;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteController {
    private EstudianteView view;
    private Estudiante estudiante;
    private EstudianteDAO estudianteDAO;
    private MateriaDAO materiaDAO;
    private EstudianteMateriaDAO estudianteMateriaDAO;

    public EstudianteController(EstudianteView view, Estudiante estudiante) {
        this.view = view;
        this.estudiante = estudiante;
        this.estudianteDAO = new EstudianteDAO();
        this.materiaDAO = new MateriaDAO();
        this.estudianteMateriaDAO = new EstudianteMateriaDAO();

        configurarVista();
        configurarListeners();
        cargarDatos();
    }

    private void configurarVista() {
        view.setTitle("Estudiante: " + estudiante.getNombreCompleto());
    }

    private void configurarListeners() {
        view.addActualizarListener(e -> cargarDatos());
        view.addCerrarSesionListener(this::cerrarSesion);
    }

    private void cargarDatos() {
        try {
            // 1. Obtener todas las materias matriculadas por el estudiante
            List<EstudianteMateria> matriculas = estudianteMateriaDAO.listarPorEstudiante(estudiante.getIdEstudiante());

            // 2. Preparar datos para la tabla
            List<Object[]> datosMaterias = new ArrayList<>();
            double sumaPromedios = 0;
            int materiasConNotas = 0;

            for (EstudianteMateria matricula : matriculas) {
                // Obtener información de la materia
                Materia materia = materiaDAO.obtenerPorId(matricula.getIdMateria());

                // Obtener notas para esta materia
                Estudiante datosNotas = estudianteDAO.obtenerNotasEstudianteMateria(
                        estudiante.getIdEstudiante(),
                        matricula.getIdMateria()
                );

                // Calcular promedio y estado
                Double promedio = datosNotas.calcularPromedio();
                String estado = "Sin calificar";

                if (promedio != null) {
                    estado = promedio >= 7.0 ? "Aprobado" : "Reprobado";
                    sumaPromedios += promedio;
                    materiasConNotas++;
                }

                // Agregar fila a los datos de la tabla
                datosMaterias.add(new Object[]{
                        materia.getNombre(),
                        materia.getCreditos(),
                        datosNotas.getNotaParcial1() != null ? String.format("%.2f", datosNotas.getNotaParcial1()) : "-",
                        datosNotas.getNotaParcial2() != null ? String.format("%.2f", datosNotas.getNotaParcial2()) : "-",
                        datosNotas.getNotaFinal() != null ? String.format("%.2f", datosNotas.getNotaFinal()) : "-",
                        promedio != null ? String.format("%.2f", promedio) : "-",
                        estado
                });
            }

            // 3. Calcular promedio general
            double promedioGeneral = materiasConNotas > 0 ? sumaPromedios / materiasConNotas : 0;

            // 4. Actualizar vista
            view.mostrarDatosEstudiante(
                    estudiante.getNombreCompleto(),
                    estudiante.getMatricula(),
                    promedioGeneral
            );

            view.cargarMaterias(datosMaterias.toArray(new Object[0][]));

        } catch (SQLException e) {
            view.mostrarMensaje("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cerrarSesion(ActionEvent e) {
        view.dispose(); // Cierra la vista del profesor

        // Mostrar nuevamente la vista de autenticación
        AuthView authView = new AuthView();
        new AuthController(authView);
        authView.setVisible(true);
    }

    public void mostrarVista() {
        view.setVisible(true);
    }
}