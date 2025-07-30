package controller;

import dao.*;
import model.*;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AuthController {
    private AuthView authView;
    private RegistroView registroView;
    private PersonaDAO personaDAO;
    private UsuarioDAO usuarioDAO;
    private EstudianteDAO estudianteDAO;
    private ProfesorDAO profesorDAO;

    public AuthController(AuthView authView) {
        this.authView = authView;
        this.personaDAO = new PersonaDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.estudianteDAO = new EstudianteDAO();
        this.profesorDAO = new ProfesorDAO();

        configurarListeners();
    }

    private void configurarListeners() {
        authView.addLoginListener(this::login);
        authView.addRegistroListener(e -> mostrarRegistro());
    }

    private void mostrarRegistro() {
        registroView = new RegistroView();
        registroView.addRegistrarListener(this::registrarUsuario);
        registroView.setVisible(true);
    }

    private void registrarUsuario(ActionEvent e) {
        // Validaciones básicas
        if (!validarCamposRegistro()) {
            return;
        }

        try {
            // 1. Registrar Persona
            Persona persona = new Persona(
                    0,
                    registroView.getCedula(),
                    registroView.getNombres(),
                    registroView.getApellidos(),
                    registroView.getEmail(),
                    registroView.getTelefono(),
                    registroView.getTipoUsuario()
            );

            int idPersona = personaDAO.registrarPersona(persona);
            if (idPersona == 0) {
                registroView.mostrarMensaje("Error al registrar persona");
                return;
            }

            // 2. Registrar Usuario
            String rol = registroView.getTipoUsuario();
            Usuario usuario = new Usuario(
                    0,
                    idPersona,
                    registroView.getUsername(),
                    registroView.getPassword(),
                    rol);

            if (!usuarioDAO.registrarUsuario(usuario)) {
                personaDAO.eliminarPersona(idPersona);
                registroView.mostrarMensaje("Error al registrar usuario");
                return;
            }

            // 3. Registrar según tipo
            String tipo = registroView.getTipoUsuario();
            boolean exito = false;

            if (tipo.equals("Estudiante")) {
                Estudiante estudiante = new Estudiante(idPersona, registroView.getDatoEspecifico());
                exito = estudianteDAO.registrarEstudiante(estudiante);
            } else if (tipo.equals("Profesor")) {
                Profesor profesor = new Profesor(idPersona, registroView.getDatoEspecifico());
                exito = profesorDAO.registrarProfesor(profesor);
            } else {
                exito = true;
            }

            if (exito) {
                registroView.mostrarMensaje("Registro exitoso!");
                registroView.limpiarCampos();
                registroView.dispose();
            } else {
                usuarioDAO.eliminarUsuario(usuario.getIdUsuario());
                personaDAO.eliminarPersona(idPersona);
                registroView.mostrarMensaje("Error al registrar datos específicos");
            }

        } catch (SQLException ex) {
            registroView.mostrarMensaje("Error de base de datos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean validarCamposRegistro() {
        // Validar que todos los campos estén completos
        if (registroView.getCedula().isEmpty() ||
                registroView.getNombres().isEmpty() ||
                registroView.getApellidos().isEmpty() ||
                registroView.getEmail().isEmpty() ||
                registroView.getUsername().isEmpty() ||
                registroView.getPassword().isEmpty()) {
            registroView.mostrarMensaje("Todos los campos son obligatorios");
            return false;
        }

        // Validar contraseñas coincidan
        if (!registroView.getPassword().equals(registroView.getConfirmPassword())) {
            registroView.mostrarMensaje("Las contraseñas no coinciden");
            return false;
        }

        // Validar dato específico según tipo
        String tipo = registroView.getTipoUsuario();
        if (!tipo.equals("Administrador") && registroView.getDatoEspecifico().isEmpty()) {
            registroView.mostrarMensaje(tipo.equals("Estudiante") ?
                    "La matrícula es obligatoria" : "La especialidad es obligatoria");
            return false;
        }

        return true;
    }

    private void login(ActionEvent e) {
        String username = authView.getUsername();
        String password = authView.getPassword();

        try {
            Usuario usuario = usuarioDAO.autenticarUsuario(username, password);
            if (usuario != null) {
                Persona persona = personaDAO.obtenerPorId(usuario.getIdPersona());
                abrirVistaSegunRol(persona);
                authView.limpiarCampos();
            } else {
                authView.mostrarMensaje("Credenciales incorrectas");
            }
        } catch (SQLException ex) {
            authView.mostrarMensaje("Error al conectar con la base de datos");
            ex.printStackTrace();
        }
    }

    private void abrirVistaSegunRol(Persona persona) {
        authView.dispose();

        try {
            Usuario usuario = usuarioDAO.buscarPorIdPersona(persona.getIdPersona());

            if (usuario == null) {
                throw new SQLException("Usuario no encontrado");
            }

            switch (persona.getTipo()) {
                case "Administrador":
                    new AdminController(new AdminView(), usuario).mostrarVista();
                    break;
                case "Profesor":
                    new ProfesorController(new ProfesorView(), usuario).mostrarVista();
                    break;
                case "Estudiante":
                    Estudiante estudiante = estudianteDAO.buscarPorId(persona.getIdPersona());
                    estudiante.setNombres(persona.getNombres());
                    estudiante.setApellidos(persona.getApellidos());
                    estudiante.setEmail(persona.getEmail());
                    new EstudianteController(new EstudianteView(), estudiante).mostrarVista();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Rol no reconocido", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la vista: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}