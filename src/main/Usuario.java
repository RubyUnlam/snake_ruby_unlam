package main;

import javax.persistence.*;

@Entity
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @Column(name = "Nombre_Usuario")
    private String nombreUsuario;

    @Column(name = "Password")
    private String contrasenia;

    @Column(name = "Email")
    private String email;

    public Usuario() {

    }

    public Usuario(String nombreUsuario, String contrasenia, String email) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.email = email;
    }

    public Usuario(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
