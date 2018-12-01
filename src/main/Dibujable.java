package main;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

/**
 * Clase que representa un elemento dibujable por el campo
 * @author gonrodriguez
 *
 */
public class Dibujable {
	
	private Color color;
	private List<Ubicacion> zonaDeDibujo;
	private String nombreJugador;
	private int puntaje;

	public Dibujable(Serpiente serpiente) {
		this.color = serpiente.obtenerColor();
		this.zonaDeDibujo = serpiente.getUbicaciones();
		this.nombreJugador = serpiente.getNombre();
		this.puntaje = serpiente.getPuntaje();
	}

	public Dibujable(Comestible comestible) {
		this.color = comestible.obtenerColor();
		this.zonaDeDibujo = Collections.singletonList(comestible.getUbicacion());
	}

}
