package main;

public abstract class Comestible {
	
	private Ubicacion ubicacion;
	private boolean comida = false;
	
	Comestible(){
		this.ubicacion = new Ubicacion();
	}
	
	Comestible(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
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
	
}