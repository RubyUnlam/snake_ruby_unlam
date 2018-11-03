package main;

import java.util.Random;

public class SerpienteIA extends Serpiente{
	
	private int dificultad; //por ciento
	
	public SerpienteIA(int dificultad) {
		super();
		this.dificultad = dificultad;
	}

	public void cambiarMirada(Comestible comestible) {
		
		if( comprobarEstado() == "Muerto") {
			return;
		}
		
		Random random = new Random();
		int probabilidadCaminoOptimo = random.nextInt()*100;
		
		if( probabilidadCaminoOptimo <= dificultad ) {
			caminoOptimoIA(comestible);
		}
		else {
			caminoRandomIA();
		}
	}
	
	private void caminoRandomIA() {
		Random random = new Random();
		int probabilidadDelCamino = random.nextInt()*100;
		
		if(probabilidadDelCamino >= 85) {
			mirarAbajo();
		}else if( probabilidadDelCamino >= 70) {
			mirarArriba();
		}else if( probabilidadDelCamino >= 55) {
			mirarDerecha();
		}else if( probabilidadDelCamino >= 40) {
			mirarIzquierda();
		}
		return;
	}

	private void caminoOptimoIA(Comestible comestible) {
		Ubicacion ubicacionManzana = comestible.getUbicacion();
		Ubicacion ubicacionCabeza = getUbicacionCabeza();
				
		int distanciaEnX = ubicacionCabeza.getX() - ubicacionManzana.getX();
		int distanciaEnY = ubicacionCabeza.getY() - ubicacionManzana.getY();
		int direccionEnX = 0, direccionEnY = 0;
		
		if(distanciaEnX != 0) {
			direccionEnX = distanciaEnX / Math.abs(distanciaEnX);			
		}
		if(distanciaEnY != 0) {
			direccionEnY = distanciaEnY / Math.abs(distanciaEnY);
		}
		
		int diferenciaDeEjes = Math.abs(distanciaEnX) - Math.abs(distanciaEnY);
		
		if( diferenciaDeEjes > 0 ) {
			// X > Y
			if( direccionEnX == 1 )  mirarIzquierda();
			else mirarDerecha();
			return;
		}
		
		// X < Y  o  X == Y (decido ir a por Y)
		if(direccionEnY == 1) {
			mirarArriba();
		}
		else {
			mirarAbajo();
		}
	}
}
