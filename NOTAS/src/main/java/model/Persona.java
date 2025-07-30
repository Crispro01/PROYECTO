package model;

public class Persona {
    private int idPersona;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String tipo;

    public Persona(int idPersona, String cedula, String nombres, String apellidos,
                   String email, String telefono, String tipo) {
        this.idPersona = idPersona;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public int getIdPersona() { return idPersona; }
    public String getCedula() { return cedula; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getTipo() { return tipo; }

    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public void setNombres(String s) {
    }

    public void setApellidos(String s) {
    }

    public void setTipo(String nuevosDato) {
    }
}