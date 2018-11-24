package main;

public class Sala {

	private String nombreSala;

	private String contrasenia;

	private int cantidadJugadores;

	private int cantidadIA;

	private String nombreCreador;

	private int dificultadIA;

	public Sala() {

	}

	public Sala(String nombreSala, String contrasenia, int cantidadJugadores, int cantidadIA, String nombreCreador,
			int dificultadIA) {
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

	public String getContrasenia() {
		return contrasenia;
	}

	public int getCantidadJugadores() {
		return cantidadJugadores;
	}

	public int getCantidadIA() {
		return cantidadIA;
	}

	public String getNombreCreador() {
		return nombreCreador;
	}

	public int getDificultadIA() {
		return dificultadIA;
	}
	
	// Importante para poder comprobar que no se repitan los nombres de sala
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
	// private int mapa;
	//
	// private int tiempo;

}
