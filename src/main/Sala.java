package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Sala {

	private String nombreSala;

	private String contrasenia;

	private int cantidadJugadores;

	private int cantidadIA;

	private String nombreCreador;

	private int dificultadIA;

	private int cantidadDeListos;

	private ActualizacionDelJuego actualizacionDelJuego = new ActualizacionDelJuego(false, Collections.EMPTY_LIST, "");

	private List<Jugador> jugadores = new ArrayList<>();

	private transient CountDownLatch cuentaRegresiva;

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

	public void agregarJugador(Jugador jugador) {
		jugadores.add(jugador);
		for (int i = 0; i < jugadores.size() -1; i++) {
			jugadores.get(i).notificarActualizacionDeSala(this);
		}
	}

	public void iniciar() {
		System.out.println("listos: " + cantidadDeListos + " jugadores: " + jugadores.size());
		if (cantidadDeListos == jugadores.size()) {
			generarJuego();
		}
	}

	public void darListo() {
		cantidadDeListos++;
	}

	public void removerJugador(Jugador jugador) {
		jugadores.remove(jugador);
		cantidadDeListos--;
		for (int i = 0; i < jugadores.size(); i++) {
			jugadores.get(i).notificarActualizacionDeSala(this);
		}
		cuentaRegresiva.countDown();
		crearCuentaRegresiva();
	}

	public void generarJuego() {
		new Thread(){
			@Override
			public void run() {
				for (int i = 0; i < jugadores.size(); i++) {
					jugadores.get(i).cerrarActualizacionDeSala();
				}
				for (int i = 0; i < jugadores.size(); i++) {
					jugadores.get(i).cerrarActualizacionDeSala();
				}
				Juego.iniciar(Sala.this, cuentaRegresiva);
			}
		}.start();
	}

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public CountDownLatch obtenerCuentaRegresiva() {
		return cuentaRegresiva;
	}

	public void crearCuentaRegresiva() {
		this.cuentaRegresiva = new CountDownLatch(1);
	}

	public int getJugadoresEnSala(){
		return jugadores.size();
	}

	public void reiniciarSala() {
		cantidadDeListos = 0;
	}

	public void setActualizacionDelJuego(ActualizacionDelJuego actualizacionDelJuego) {
		this.actualizacionDelJuego = actualizacionDelJuego;
	}

	// TODO: Cuando haya distintos tipos de mapa y tiempo, agregar lo siguiente
	// private int mapa;
	//
	// private int tiempo;

}
