package main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIOS")
public class Usuario {

	@Id
	@Column(name = "Nombre_Usuario")
	private String nombreUsuario;

	@Column(name = "Password")
	private String contrasenia;
	
	public Usuario() {

	}

	public Usuario(String nombreUsuario, String contrasenia) {
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

}
