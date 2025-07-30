package model;

public class Materia {
    private int idMateria;
    private String nombre;
    private String descripcion;
    private int creditos;

    public Materia(int idMateria, String nombre, String descripcion, int creditos) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString() {
        return nombre;
    }
}