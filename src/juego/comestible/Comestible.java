package juego.comestible;

import juego.Ubicacion;

import java.awt.Color;

public abstract class Comestible {

	private Color color;
	private Ubicacion ubicacion;
	private boolean comida = false;
	
	Comestible(Color color){
		this.ubicacion = new Ubicacion();
		this.color = color;
	}
	
	Comestible(Ubicacion ubicacion, Color color) {
		this.ubicacion = ubicacion;
		this.color = color;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public boolean fueComida() {
		return comida;
	}

	public void setComida(boolean comida) {
		this.comida = comida;
	}
	
	public Color obtenerColor() {
		return this.color;
	}
	
}
