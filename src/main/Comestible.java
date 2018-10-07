package main;

public abstract class Comestible {
	
	private Ubicacion ubicacion;
	
	Comestible(){
		this.ubicacion = generarUbicacionAleatoria();
	}
	
	private Ubicacion generarUbicacionAleatoria() {
		int posicionAleatoriaX = (int) (10 + Math.random() * 760);
		int posicionAleatoriaY = (int) (10 + Math.random() * 550);
		Ubicacion ubicacion = new Ubicacion(posicionAleatoriaX, posicionAleatoriaY);
		return ubicacion;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	
}
