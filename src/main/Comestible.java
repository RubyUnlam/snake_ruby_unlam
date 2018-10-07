package main;

public abstract class Comestible {
	
	private Ubicacion ubicacion;
	
	Comestible(){
		this.ubicacion = generarUbicacionAleatoria();
	}
	
	private Ubicacion generarUbicacionAleatoria() {
		double randomX = Math.random() * 770;
		double randomY = Math.random() * 550;
		int posicionAleatoriaX = (int) (10 + (randomX - (randomX % 20)));
		int posicionAleatoriaY = (int) (10 + (randomY - (randomY % 20)));
		Ubicacion ubicacion = new Ubicacion(posicionAleatoriaX, posicionAleatoriaY);
		return ubicacion;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	
}
