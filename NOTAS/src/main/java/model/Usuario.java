package model;

public class Usuario {
    private int idUsuario;
    private int idPersona;
    private String username;
    private String password;
    private String rol;

    public Usuario(int idUsuario, int idPersona, String username, String password, String rol) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public void setIdUsuario(int idUsuario) {
        if (idUsuario < 0) {
            throw new IllegalArgumentException("ID de usuario no puede ser negativo");
        }
        this.idUsuario = idUsuario;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username no puede estar vacío");
        }
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password no puede estar vacío");
        }
        this.password = password;
    }

    public void setRol(String rol) {
        if (rol == null || !(rol.equals("Administrador") || rol.equals("Profesor") || rol.equals("Estudiante"))) {
            throw new IllegalArgumentException("Rol no válido");
        }
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return idUsuario == usuario.idUsuario;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idUsuario);
    }
}