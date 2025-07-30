package model;

public class Profesor {
    private int idProfesor;
    private String especialidad;
    private String nombres;
    private String apellidos;
    private int idPersona;

    public Profesor(int idProfesor, String especialidad) {
        this.idProfesor = idProfesor;
        this.especialidad = especialidad;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }
}