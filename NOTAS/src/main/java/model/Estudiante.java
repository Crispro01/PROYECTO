package model;

public class Estudiante {
    private int idEstudiante;
    private String matricula;
    private String nombres;
    private String apellidos;
    private String email;
    private Double notaParcial1;
    private Double notaParcial2;
    private Double notaFinal;


    public Estudiante(int idEstudiante, String matricula, String nombres, String apellidos, String email) {
        this.idEstudiante = idEstudiante;
        this.matricula = matricula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.notaParcial1 = null;
        this.notaParcial2 = null;
        this.notaFinal = null;
    }
    public Estudiante(int idEstudiante, String matricula, String nombres, String apellidos,
                      String email, Double notaParcial1, Double notaParcial2, Double notaFinal) {
        this.idEstudiante = idEstudiante;
        this.matricula = matricula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.notaParcial1 = notaParcial1;
        this.notaParcial2 = notaParcial2;
        this.notaFinal = notaFinal;
    }

    public Estudiante(int idEstudiante, String matricula, String nombres, String apellidos,
                      Double notaParcial1, Double notaParcial2, Double notaFinal) {
        this(idEstudiante, matricula, nombres, apellidos, null, notaParcial1, notaParcial2, notaFinal);
    }

    public Estudiante(int idEstudiante, String matricula) {
        this(idEstudiante, matricula, "", "", "", null, null, null);
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Métodos para notas
    public Double getNotaParcial1() {
        return notaParcial1;
    }

    public void setNotaParcial1(Double notaParcial1) {
        this.notaParcial1 = notaParcial1;
    }

    public Double getNotaParcial2() {
        return notaParcial2;
    }

    public void setNotaParcial2(Double notaParcial2) {
        this.notaParcial2 = notaParcial2;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public Double calcularPromedio() {
        if (notaParcial1 != null && notaParcial2 != null && notaFinal != null) {
            return (notaParcial1 * 0.3) + (notaParcial2 * 0.3) + (notaFinal * 0.4);
        }
        return null;
    }

    public Boolean aprobo() {
        Double promedio = calcularPromedio();
        return promedio != null && promedio >= 7.0;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "idEstudiante=" + idEstudiante +
                ", matricula='" + matricula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", notaParcial1=" + notaParcial1 +
                ", notaParcial2=" + notaParcial2 +
                ", notaFinal=" + notaFinal +
                '}';
    }
}