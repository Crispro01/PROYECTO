package model;

public class ProfesorMateria {
    private int idProfesor;
    private int idMateria;
    private String periodoAcademico;

    public ProfesorMateria(int idProfesor, int idMateria, String periodoAcademico) {
        this.idProfesor = idProfesor;
        this.idMateria = idMateria;
        this.periodoAcademico = periodoAcademico;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public String getPeriodoAcademico() {
        return periodoAcademico;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public void setPeriodoAcademico(String periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }
}