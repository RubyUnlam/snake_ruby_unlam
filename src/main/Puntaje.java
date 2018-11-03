package main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PUNTAJES")
public class Puntaje {

	public Puntaje() {

	}
	
	public Puntaje(int puntuacion, String nombreUsuario, int suicidios, int asesinatos) {
		this.puntuacion = puntuacion;
		this.nombreUsuario = nombreUsuario;
		this.suicidios = suicidios;
		this.asesinatos = asesinatos;
	}

	@Column(name = "Puntuacion")
	private int puntuacion;
	
	@Id
	@Column(name = "Nombre_Usuario")
	private String nombreUsuario;

	@Column(name = "Suicidios")
	private int suicidios;

	@Column(name = "Asesinatos")
	private int asesinatos;
	
	public void incrementarPuntuacion() {
		this.puntuacion += 10;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public int getSuicidios() {
		return suicidios;
	}

	public void setSuicidios(int suicidios) {
		this.suicidios = suicidios;
	}

	public int getAsesinatos() {
		return asesinatos;
	}

	public void setAsesinatos(int asesinatos) {
		this.asesinatos = asesinatos;
	}
	
}
