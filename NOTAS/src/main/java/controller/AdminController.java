package controller;

import dao.*;
import model.*;
import view.AdminView;
import view.AuthView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private AdminView view;
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private PersonaDAO personaDAO;
    private ProfesorDAO profesorDAO;
    private MateriaDAO materiaDAO;
    private ProfesorMateriaDAO profesorMateriaDAO;
    private EstudianteDAO estudianteDAO;
    private EstudianteMateriaDAO estudianteMateriaDAO;


    public AdminController(AdminView view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;
        this.usuarioDAO = new UsuarioDAO();
        this.personaDAO = new PersonaDAO();
        this.profesorDAO = new ProfesorDAO();
        this.materiaDAO = new MateriaDAO();
        this.profesorMateriaDAO = new ProfesorMateriaDAO();
        this.estudianteDAO = new EstudianteDAO();
        this.estudianteMateriaDAO = new EstudianteMateriaDAO();

        configurarVista();
        configurarListeners();
        cargarDatosIniciales();
    }

    private void configurarVista() {
        view.setTitle("Administrador: " + usuario.getUsername());
    }

    private void configurarListeners() {
        view.addAgregarUsuarioListener(this::agregarUsuario);
        view.addEditarUsuarioListener(this::editarUsuario);
        view.addEliminarUsuarioListener(this::eliminarUsuario);
        view.addActualizarUsuariosListener(e -> cargarUsuarios());

        view.addAsignarMateriaListener(this::asignarMateria);
        view.addEliminarAsignacionListener(this::eliminarAsignacion);
        view.addActualizarAsignacionesListener(e -> cargarAsignaciones());
        view.addAsignarEstudianteMateriaListener(this::asignarEstudianteMateria);
        view.addEliminarAsignacionEstudianteListener(this::eliminarAsignacionEstudiante);
        view.addActualizarAsignacionesEstudiantesListener(e -> cargarAsignacionesEstudiantes());
        view.addCerrarSesionListener(this::cerrarSesion);
    }

    private void cargarDatosIniciales() {
        cargarUsuarios();
        cargarProfesoresYMaterias();
        cargarAsignaciones();
        cargarEstudiantesYMaterias();
        cargarAsignacionesEstudiantes();
    }

    private void cargarUsuarios() {
        try {
            view.limpiarTablaUsuarios();
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            for (Usuario usuario : usuarios) {
                Persona persona = personaDAO.obtenerPorId(usuario.getIdPersona());
                view.agregarFilaUsuario(new Object[]{
                        usuario.getIdUsuario(),
                        usuario.getUsername(),
                        persona.getTipo(),
                        persona.getNombres() + " " + persona.getApellidos()
                });
            }
        } catch (SQLException e) {
            view.mostrarMensaje("Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void cargarProfesoresYMaterias() {
        try {
            // Cargar profesores en formato "ID - Nombre"
            List<Profesor> profesores = profesorDAO.listarTodos();
            String[] profesoresArray = profesores.stream()
                    .map(p -> p.getIdProfesor() + " - " + p.getNombres() + " " + p.getApellidos())
                    .toArray(String[]::new);
            view.cargarProfesores(profesoresArray);

            // Cargar materias en formato "ID - Nombre"
            List<Materia> materias = materiaDAO.listarTodas();
            String[] materiasArray = materias.stream()
                    .map(m -> m.getIdMateria() + " - " + m.getNombre())
                    .toArray(String[]::new);
            view.cargarMaterias(materiasArray);
        } catch (SQLException e) {
            view.mostrarMensaje("Error al cargar datos: " + e.getMessage());
        }
    }

    private void cargarAsignaciones() {
        try {
            view.limpiarTablaAsignaciones();
            List<ProfesorMateria> asignaciones = profesorMateriaDAO.listarTodas();
            for (ProfesorMateria pm : asignaciones) {
                Profesor profesor = profesorDAO.obtenerPorId(pm.getIdProfesor());
                Materia materia = materiaDAO.obtenerPorId(pm.getIdMateria());
                view.agregarFilaAsignacion(new Object[]{
                        profesor.getIdPersona(),
                        profesor.getNombres() + " " + profesor.getApellidos(),
                        materia.getNombre(),
                        pm.getPeriodoAcademico()
                });
            }
        } catch (SQLException e) {
            view.mostrarMensaje("Error al cargar asignaciones: " + e.getMessage());
        }
    }

    private void cargarEstudiantesYMaterias() {
        try {
            // Cargar estudiantes en formato "ID - Nombre"
            List<Estudiante> estudiantes = estudianteDAO.listarTodos();
            String[] estudiantesArray = estudiantes.stream()
                    .map(e -> e.getIdEstudiante() + " - " + e.getNombres() + " " + e.getApellidos())
                    .toArray(String[]::new);
            view.cargarEstudiantes(estudiantesArray);

            // Cargar materias para estudiantes
            List<Materia> materias = materiaDAO.listarTodas();
            String[] materiasArray = materias.stream()
                    .map(m -> m.getIdMateria() + " - " + m.getNombre())
                    .toArray(String[]::new);
            view.cargarMateriasEstudiantes(materiasArray);
        } catch (SQLException e) {
            view.mostrarMensaje("Error al cargar datos: " + e.getMessage());
        }
    }

    private void cargarAsignacionesEstudiantes() {
        try {
            view.limpiarTablaEstudiantesMaterias();
            List<EstudianteMateria> asignaciones = estudianteMateriaDAO.listarTodas();
            for (EstudianteMateria em : asignaciones) {
                Estudiante estudiante = estudianteDAO.buscarPorId(em.getIdEstudiante());
                Materia materia = materiaDAO.obtenerPorId(em.getIdMateria());
                view.agregarFilaAsignacionEstudiante(new Object[]{
                        estudiante.getIdEstudiante(),
                        estudiante.getNombres() + " " + estudiante.getApellidos(),
                        materia.getNombre(),
                        em.getPeriodoAcademico()
                });
            }
        } catch (SQLException e) {
            view.mostrarMensaje("Error al cargar asignaciones: " + e.getMessage());
        }
    }

    private void asignarEstudianteMateria(ActionEvent e) {
        int idEstudiante = view.getIdEstudianteSeleccionado();
        int idMateria = view.getIdMateriaEstudianteSeleccionada();
        String periodo = view.getPeriodoEstudianteSeleccionado();

        if (idEstudiante == -1 || idMateria == -1) {
            view.mostrarMensaje("Seleccione estudiante y materia");
            return;
        }

        try {
            if (estudianteMateriaDAO.matricularEstudiante(idEstudiante, idMateria, periodo)) {
                view.mostrarMensaje("Asignación exitosa");
                cargarAsignacionesEstudiantes();
            } else {
                view.mostrarMensaje("Error en la asignación");
            }
        } catch (SQLException ex) {
            view.mostrarMensaje("Error de base de datos: " + ex.getMessage());
        }
    }

    private void eliminarAsignacionEstudiante(ActionEvent e) {
        int fila = view.obtenerAsignacionEstudianteSeleccionada();
        if (fila == -1) {
            view.mostrarMensaje("Seleccione una asignación");
            return;
        }

        int idEstudiante = (int) view.getModeloTablaEstudiantesMaterias().getValueAt(fila, 0);
        String materia = (String) view.getModeloTablaEstudiantesMaterias().getValueAt(fila, 2);
        String periodo = (String) view.getModeloTablaEstudiantesMaterias().getValueAt(fila, 3);

        int confirmacion = JOptionPane.showConfirmDialog(
                view,
                "¿Está seguro de eliminar esta asignación?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                Materia materiaObj = materiaDAO.obtenerPorNombre(materia);
                if (materiaObj == null) {
                    view.mostrarMensaje("No se encontró la materia");
                    return;
                }

                if (estudianteMateriaDAO.desmatricularEstudiante(idEstudiante, materiaObj.getIdMateria(), periodo)) {
                    view.mostrarMensaje("Asignación eliminada exitosamente");
                    cargarAsignacionesEstudiantes();
                } else {
                    view.mostrarMensaje("Error al eliminar asignación");
                }
            } catch (SQLException ex) {
                view.mostrarMensaje("Error de base de datos: " + ex.getMessage());
            }
        }
    }

    private void agregarUsuario(ActionEvent e) {
        Object[] datos = view.mostrarDialogoUsuario(false, null);
        if (datos != null) {
            try {
                // Validar datos
                if (((String)datos[0]).isEmpty() || ((String)datos[1]).isEmpty()) {
                    view.mostrarMensaje("Usuario y contraseña son requeridos");
                    return;
                }

                Persona persona = new Persona(
                        0, //
                        "",
                        ((String)datos[3]).split(" ")[0], // Primer nombre
                        ((String)datos[3]).split(" ").length > 1 ?
                                ((String)datos[3]).split(" ")[1] : "", // Apellido
                        "",
                        "",
                        (String)datos[2] // Rol
                );

                int idPersona = personaDAO.registrarPersona(persona);

                // Registro usuario
                Usuario usuario = new Usuario(
                        0,
                        idPersona,
                        (String)datos[0],
                        (String)datos[1],
                        (String)datos[2]
                );

                if (usuarioDAO.registrarUsuario(usuario)) {
                    view.mostrarMensaje("Usuario registrado exitosamente");
                    cargarUsuarios();
                } else {
                    personaDAO.eliminarPersona(idPersona);
                    view.mostrarMensaje("Error al registrar usuario");
                }
            } catch (SQLException ex) {
                view.mostrarMensaje("Error de base de datos: " + ex.getMessage());
            }
        }
    }

    private void editarUsuario(ActionEvent e) {
        int fila = view.obtenerUsuarioSeleccionado();
        if (fila == -1) {
            view.mostrarMensaje("Seleccione un usuario");
            return;
        }

        // Obtener datos actuales
        int idUsuario = (int) view.getModeloTablaUsuarios().getValueAt(fila, 0);
        Object[] datosActuales = new Object[]{
                view.getModeloTablaUsuarios().getValueAt(fila, 1),
                view.getModeloTablaUsuarios().getValueAt(fila, 2),
                view.getModeloTablaUsuarios().getValueAt(fila, 3)
        };

        Object[] nuevosDatos = view.mostrarDialogoUsuario(true, datosActuales);
        if (nuevosDatos != null) {
            try {
                // Actualizar usuario
                Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
                usuario.setUsername((String)nuevosDatos[0]);
                // Solo actualizar contraseña si no está vacía
                if (!((String)nuevosDatos[1]).isEmpty()) {
                    usuario.setPassword((String)nuevosDatos[1]);
                }

                if (usuarioDAO.actualizarUsuario(usuario)) {
                    // Actualizar persona (nombre)
                    Persona persona = personaDAO.obtenerPorId(usuario.getIdPersona());
                    persona.setNombres(((String)nuevosDatos[3]).split(" ")[0]);
                    persona.setApellidos(((String)nuevosDatos[3]).split(" ").length > 1 ?
                            ((String)nuevosDatos[3]).split(" ")[1] : "");
                    persona.setTipo((String)nuevosDatos[2]);

                    personaDAO.actualizarPersona(persona);

                    view.mostrarMensaje("Usuario actualizado exitosamente");
                    cargarUsuarios();
                } else {
                    view.mostrarMensaje("Error al actualizar usuario");
                }
            } catch (SQLException ex) {
                view.mostrarMensaje("Error de base de datos: " + ex.getMessage());
            }
        }
    }

    private void eliminarUsuario(ActionEvent e) {
        int fila = view.obtenerUsuarioSeleccionado();
        if (fila == -1) {
            view.mostrarMensaje("Seleccione un usuario");
            return;
        }

        int idUsuario = (int) view.getModeloTablaUsuarios().getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(
                view,
                "¿Está seguro de eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (usuarioDAO.eliminarUsuario(idUsuario)) {
                    view.mostrarMensaje("Usuario eliminado exitosamente");
                    cargarUsuarios();
                } else {
                    view.mostrarMensaje("Error al eliminar usuario");
                }
            } catch (SQLException ex) {
                view.mostrarMensaje("Error de base de datos: " + ex.getMessage());
            }
        }
    }

    private void asignarMateria(ActionEvent e) {
        int idProfesor = view.getIdProfesorSeleccionado();
        int idMateria = view.getIdMateriaSeleccionada();
        String periodo = view.getPeriodoSeleccionado();

        if (idProfesor == -1 || idMateria == -1) {
            view.mostrarMensaje("Seleccione profesor y materia");
            return;
        }

        try {
            if (profesorMateriaDAO.asignarMateriaAProfesor(idProfesor, idMateria, periodo)) {
                view.mostrarMensaje("Asignación exitosa");
                cargarAsignaciones();
            } else {
                view.mostrarMensaje("Error en la asignación");
            }
        } catch (SQLException ex) {
            view.mostrarMensaje("Error de base de datos: " + ex.getMessage());
        }
    }

    private void eliminarAsignacion(ActionEvent e) {
        int fila = view.obtenerAsignacionSeleccionada();
        if (fila == -1) {
            view.mostrarMensaje("Seleccione una asignación");
            return;
        }

        int idProfesor = (int) view.getModeloTablaAsignaciones().getValueAt(fila, 0);
        String materia = (String) view.getModeloTablaAsignaciones().getValueAt(fila, 2);
        String periodo = (String) view.getModeloTablaAsignaciones().getValueAt(fila, 3);

        int confirmacion = JOptionPane.showConfirmDialog(
                view,
                "¿Está seguro de eliminar esta asignación?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Obtener el idMateria basado en el nombre
                Materia materiaObj = materiaDAO.obtenerPorNombre(materia);
                if (materiaObj == null) {
                    view.mostrarMensaje("No se encontró la materia");
                    return;
                }

                if (profesorMateriaDAO.desasignarMateriaDeProfesor(idProfesor, materiaObj.getIdMateria(), periodo)) {
                    view.mostrarMensaje("Asignación eliminada exitosamente");
                    cargarAsignaciones();
                } else {
                    view.mostrarMensaje("Error al eliminar asignación");
                }
            } catch (SQLException ex) {
                view.mostrarMensaje("Error de base de datos: " + ex.getMessage());
            }
        }
    }

    private void cerrarSesion(ActionEvent e) {
        view.dispose();

        AuthView authView = new AuthView();
        new AuthController(authView);
        authView.setVisible(true);
    }

    public void mostrarVista() {
        view.setVisible(true);
    }
}