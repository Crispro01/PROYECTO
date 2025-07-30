package model;

public class EstudianteMateria {
    private int idEstudiante;
    private int idMateria;
    private String periodoAcademico;

    public EstudianteMateria(int idEstudiante, int idMateria, String periodoAcademico) {
        this.idEstudiante = idEstudiante;
        this.idMateria = idMateria;
        this.periodoAcademico = periodoAcademico;
    }

    public int getIdEstudiante() { return idEstudiante; }
    public int getIdMateria() { return idMateria; }
    public String getPeriodoAcademico() { return periodoAcademico; }
}

