package main;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class Dibujable {
	
	private Color color;
	private List<Ubicacion> zonaDeDibujo;
	
	public Dibujable(Serpiente serpiente) {
		this.color = serpiente.obtenerColor();
		this.zonaDeDibujo = serpiente.getUbicaciones();
	}
	
	public Dibujable(Comestible comestible) {
		this.color = comestible.obtenerColor();
		this.zonaDeDibujo = Collections.singletonList(comestible.getUbicacion());
	}

	public Color obtenerColor() {
		return color;
	}

	public List<Ubicacion> obtenerZonaDeDibujo() {
		return zonaDeDibujo;
	}
	
	
}
