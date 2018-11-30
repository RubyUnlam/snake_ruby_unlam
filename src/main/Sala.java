package main;

import java.util.ArrayList;
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

	private int tiempo;

	private List<Jugador> jugadores = new ArrayList<>();

	private String modoDeJuego;

	private int puntajeAAlcanzar;

	private transient CountDownLatch partidoTerminado; //TODO CAMBIARLE EL NOMBRE POR ALGO TAL VEZ MAS DESCRIPTIVO

	public Sala(String nombreSala, String contrasenia, int cantidadJugadores, int cantidadIA, String nombreCreador,
			int dificultadIA, String modoDeJuego, int tiempo, int puntaje) {
		this.nombreSala = nombreSala;
		this.contrasenia = contrasenia;
		this.cantidadJugadores = cantidadJugadores;
		this.cantidadIA = cantidadIA;
		this.nombreCreador = nombreCreador;
		this.dificultadIA = dificultadIA;
		this.modoDeJuego = modoDeJuego;
		this.tiempo = tiempo;
		this.puntajeAAlcanzar = puntaje;
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

	public int getDificultadIA() {
		return dificultadIA;
	}

	/**
	 * Agrega al jugador a la sala y se notifica la actualizacion
	 * @param jugador
	 */
	public void agregarJugador(Jugador jugador) {
		jugadores.add(jugador);
		notificarActualizacionAJugadores(jugadores.size() - 1);
	}

	/**
	 * Se notifica a los jugadores el estado actual de la sala
	 * @param cantidadDeJugadoresANotificar
	 */
	private void notificarActualizacionAJugadores(int cantidadDeJugadoresANotificar) {
		for (int i = 0; i < cantidadDeJugadoresANotificar; i++) {
			jugadores.get(i).notificarActualizacionDeSala(this);
		}
	}

	/**
	 * Chequea si la cantidad de jugadores listos es igual a los jugadores de la sala.
	 * Si es asi genera el juego.
	 */
	private boolean esValidoIniciarElJuego() {
		return cantidadDeListos == getJugadoresEnSala();
	}

	/**
	 * Si el jugador no le dio listo aun, aumenta la cantidad de listos y marca al jugador
	 * que le dio listo.
	 * @param jugador
	 */
	public void darListo(Jugador jugador) {
		if (!jugador.getEstaListo()) {
			cantidadDeListos++;
			jugador.setEstaListo(true);
		}
	}

	/**
	 * Remueve al jugador en cuestion, si este estaba listo remueve el visto
 	 * @param jugador
	 */
	public void removerJugador(Jugador jugador) {
		jugadores.remove(jugador);

		if (jugador.getEstaListo()) {
			cantidadDeListos--;
			jugador.setEstaListo(false);
		}
		intentarIniciarElJuego();

	}

	/**
	 * Si es valido iniciar el juego lo hace, sino comunica las actualizaciones a los jugadores
	 */
	public void intentarIniciarElJuego() {
		if (esValidoIniciarElJuego()) {
			generarJuego();
		} else {
			notificarActualizacionAJugadores(getCantidadJugadores());
		}
	}

	/**
	 * Les indica a los jugadores que deben dejar de escuchar acutalizaciones de sala y comienza el juego
	 */
	private void generarJuego() {
		new Thread(){
			@Override
			public void run() {
				for (int i = 0; i < jugadores.size(); i++) {
					jugadores.get(i).cerrarActualizacionDeSala();
				}
				Juego.iniciar(Sala.this, partidoTerminado);
			}
		}.start();
	}

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public CountDownLatch obtenerPartidoTerminado() {
		return partidoTerminado;
	}

	/**
	 * Inicializa un {@link CountDownLatch} que servira para marcar a los jugadores
	 * cuando el partido ha terminado
	 */
	public void crearPartidoTerminado() {
		this.partidoTerminado = new CountDownLatch(1);
	}

	/**
	 * Devuelve la cantidad de jugadores en la sala
	 * @return
	 */
	public int getJugadoresEnSala(){
		return jugadores.size();
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

	public int getTiempo() {
		return tiempo;
	}

	public String getModoDeJuego() {
		return modoDeJuego;
	}

	public int getPuntajeAAlcanzar() {
		return puntajeAAlcanzar;
	}
}

