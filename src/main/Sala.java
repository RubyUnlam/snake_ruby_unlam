package main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SALAS")
public class Sala {
	
	@Id
	@Column(name = "Nombre_Sala")
	private String nombreSala;

	@Column(name = "Password")
	private String contrasenia;

	@Column(name = "Cantidad_Jugadores")
	private int cantidadJugadores;

	@Column(name = "Cantidad_IA")
	private int cantidadIA;
	
	@Column(name = "Nombre_Creador")
	private String nombreCreador;
	
	@Transient
	private int dificultadIA;
	
	public Sala() {
		
	}

	public Sala(String nombreSala, String contrasenia, int cantidadJugadores, int cantidadIA, String nombreCreador, int dificultadIA) {
		this.nombreSala = nombreSala;
		this.contrasenia = contrasenia;
		this.cantidadJugadores = cantidadJugadores;
		this.cantidadIA = cantidadIA;
		this.nombreCreador = nombreCreador;
		this.dificultadIA = dificultadIA;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public int getCantidadJugadores() {
		return cantidadJugadores;
	}

	public void setCantidadJugadores(int cantidadJugadores) {
		this.cantidadJugadores = cantidadJugadores;
	}

	public int getCantidadIA() {
		return cantidadIA;
	}

	public void setCantidadIA(int cantidadIA) {
		this.cantidadIA = cantidadIA;
	}
	
	public void setNombreCreador(String nombreCreador) {
		this.nombreCreador = nombreCreador;
	}
	
	public String getNombreCreador() {
		return nombreCreador;
	}
	
	public int getDificultadIA() {
		return dificultadIA;
	}

	@Override
	public String toString() {
		return "SALA: " + nombreSala + "  |  SERPIENTES: " + cantidadJugadores + "  |  IA: " + cantidadIA + ".";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreSala == null) ? 0 : nombreSala.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sala other = (Sala) obj;
		if (nombreSala == null) {
			if (other.nombreSala != null)
				return false;
		} else if (!nombreSala.equals(other.nombreSala))
			return false;
		return true;
	}

	// TODO: Cuando haya distintos tipos de mapa y tiempo, agregar lo siguiente
	//	@Column(name="Mapa")
	//	private int mapa;
	//	
	//	@Column(name="Tiempo")
	//	private int tiempo;
	
}
