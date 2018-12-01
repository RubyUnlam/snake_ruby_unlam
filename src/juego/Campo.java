package juego;

import juego.comestible.Coco;
import juego.comestible.Comestible;
import juego.comestible.Manzana;
import juego.serpiente.Colision;
import juego.serpiente.Serpiente;
import juego.serpiente.SerpienteIA;
import main.ActualizacionDelJuego;
import main.Observado;
import main.Observador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import javax.swing.Timer;

import static utilidades.Constantes.CICLO_DE_JUEGO;


public class Campo implements ActionListener, Observado {

	private static final String PUNTAJE = "Puntaje";
	private static final String SUPERVIVENCIA = "Supervivencia";

	private Timer timer;
	private CountDownLatch finDelJuego;
	private int tiempoDeJuego;

	private List<Serpiente> serpientes;
	private List<SerpienteIA> serpientesIA;
	private Queue<Comestible> comestibles;
	private Colision colisionador;

	private int ciclos;
	private ActualizacionDelJuego actualizacionDelJuego;
	private String modoDeJuego;
	private int puntajeAAlcanzar;
	private GeneradoDeComestibles generador;

	private Observador observador;

	Campo(List<Serpiente> jugadores, List<SerpienteIA> serpientesIA, CountDownLatch finDelJuego, int tiempoDeJuego, int puntajeAAlcanzar, String modoDeJuego) {
		this.serpientes = jugadores;
		this.serpientesIA = serpientesIA;
		this.comestibles = new ConcurrentLinkedQueue<Comestible>();
		this.colisionador = new Colision();
        this.finDelJuego = finDelJuego;
		timer = new Timer(CICLO_DE_JUEGO, this);
		this.tiempoDeJuego = tiempoDeJuego * 100;
		this.puntajeAAlcanzar = puntajeAAlcanzar;
		this.modoDeJuego = modoDeJuego;
		this.generador = new GeneradoDeComestibles(comestibles, jugadores.size() + serpientesIA.size());
	}

	public void comenzarJuego() {
		timer.start();
		generador.iniciar();
	}

	public void terminarJuego() {
	    timer.stop();
	    generador.parar();
    }


	public void notificarDibujables(ActualizacionDelJuego actualizacion) {
		observador.dibujar(actualizacion);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (comestibles.isEmpty()) {
			comestibles.add(new Manzana());
		}

		for (Serpiente jugador : serpientes){
		    if (jugador.salir()) {
		        jugador.morir();
		        observador.removerJugador(jugador.getNombre());
            }
        }

        for (Serpiente jugador : serpientes) {
            jugador.moverse();
        }
        for (SerpienteIA jugadorIA : serpientesIA) {
        	jugadorIA.cambiarMirada(comestibles.peek());
        	jugadorIA.moverse();
        }

        colisionador.comprobarColisiones(serpientes, serpientesIA, comestibles);

        prepararActualizacionDelJuego();

        ciclos+=10;
        notificarDibujables(actualizacionDelJuego);
    }

    private void prepararActualizacionDelJuego() {
        List<Dibujable> dibujables = prepararDibujables();
        if (partidaFinalizada()) {
            terminarJuego();
            actualizacionDelJuego = new ActualizacionDelJuego(true, dibujables, obtenerSerpienteGanadora());
            this.finDelJuego.countDown();
        } else {
            actualizacionDelJuego = new ActualizacionDelJuego(dibujables);
        }
    }

    private boolean partidaFinalizada(){
		if(SUPERVIVENCIA.equals(modoDeJuego)){
			return terminoElTiempo() || !haySerpientesVivas() || hayUnaSerpienteViva();
		} else {
			return !haySerpientesVivas() || puntajeMaximoAlcanzado();
		}
	}

	/**
	 * Obtiene el nombre de la serpiente ganadora.
	 * En caso de que no haya serpientes vivas, devuelve empate.
	 * @return
	 */
	private String obtenerSerpienteGanadora() { //TODO CONTEMPLAR DIFERENTES CASOS DE GANADOR. MODIFICAR CUANDO SE MODIFIQUEN LAS COLISIONES.

		if (SUPERVIVENCIA.equals(modoDeJuego)) {
			return obtenerGanadoraSupervivencia();
		} else {
			return obtenerGanadoraPuntaje();
		}
	}

	/**
	 * Devuelve el nombre de la serpiente ganadora del modo supervivencia
	 * @return
	 */
	private String obtenerGanadoraSupervivencia() { //TODO HACER QUE DEVUELVA UNA LISTA CON LOS HOMBRES DE LOS GANADORES
		for(Serpiente serpiente : serpientes){
			if(!serpiente.estaMuerto()){
				return serpiente.getNombre();
			}
		}

		for(Serpiente serpiente : serpientesIA){
			if(!serpiente.estaMuerto()){
				return serpiente.getNombre();
			}
		}
		return "Empate";
	}

	/**
	 * Se busca la serpiente con mayor puntaje y se devuelve el nombre de la misma.
	 * Este metodo sirve tanto para cuando una serpiente alcanza el puntaje maximo
	 * como cuando ya no quedan serpientes en el campo.
	 * @return
	 */
	private String obtenerGanadoraPuntaje(){
		int puntaje = 0;
		String nombreMayorPuntaje = "nadie. Nadie consiguio puntos.";
		for(Serpiente serpiente : serpientes){
			if(serpiente.getPuntaje() > puntaje){
				puntaje = serpiente.getPuntaje();
				nombreMayorPuntaje = serpiente.getNombre();
			}
		}

		for(SerpienteIA serpiente : serpientesIA)
			if(serpiente.getPuntaje() > puntaje){
				puntaje = serpiente.getPuntaje();
				nombreMayorPuntaje = serpiente.getNombre();
			}

		return nombreMayorPuntaje;
	}

	/**
     * Genera un dibujable por cada serpiente y comestible en el campo
     * @return una lista de dibujables
     */
    private List<Dibujable> prepararDibujables() {
        List<Dibujable> dibujables = new ArrayList<>();
        for(Serpiente serpientes : serpientes){
            dibujables.add(new Dibujable(serpientes));
        }

        for(Serpiente serpientesIA : serpientesIA){
            dibujables.add(new Dibujable(serpientesIA));
        }

        for (Comestible comestible : comestibles) {
            dibujables.add(new Dibujable(comestible));
        }
        return dibujables;
    }

	/**
	 * verifica si termino el tiempo de juego. True si termino.
	 * @return
	 */
	private boolean	terminoElTiempo(){
    	return ciclos > tiempoDeJuego;
	}

	/**
	 * Verifica si quedan serpientes vivas. Devuelve true si hay 1 o más, y false en otro caso
	 * @return
	 */
	private boolean haySerpientesVivas() {
		return obtenerCantidadSerpientesVivas() > 0;
	}

	/**
	 * Verifica si queda solo una serpiente viva.
	 *
	 * @return
	 */
	private boolean hayUnaSerpienteViva(){
		return obtenerCantidadSerpientesVivas() == 1;
	}

	/**
	 * Devuelve la cantidad de serpientes vivas, tanto IA como humanas.
	 * @return
	 */
	private int obtenerCantidadSerpientesVivas(){
		int cantidadVivas = 0;
		for(Serpiente serpiente : serpientes){
			if(!serpiente.estaMuerto())
				cantidadVivas++;
		}

		for(SerpienteIA serpiente : serpientesIA){
			if(!serpiente.estaMuerto()){
				cantidadVivas++;
			}
		}
		return cantidadVivas;
	}
	/**
	 *  Verifica si alguna serpiente (tanto humana como IA) alcanzo el puntaje
	 *  necesario para ganar la partida.
	 * @return
	 */

	private boolean puntajeMaximoAlcanzado(){
		for(Serpiente serpiente : serpientes){
			if(serpiente.getPuntaje() >= puntajeAAlcanzar){return true;}
		}

		for(SerpienteIA serpiente : serpientesIA){
			if(serpiente.getPuntaje() >= puntajeAAlcanzar){return true;}
		}

		return false;
	}

	/**
	 * Metodo para agregar un observador a la lista.
	 * Cada jugador deberia ser un observador
	 */
	@Override
	public void agregarObservador(Observador observador) {
		this.observador = observador;
	}
}
