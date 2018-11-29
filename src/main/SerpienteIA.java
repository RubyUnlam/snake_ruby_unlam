package main;

import java.awt.Color;

public class SerpienteIA extends Serpiente {
	
	private int dificultad; //por ciento
	
	public SerpienteIA(int dificultad, Color color, String nombre) {
		super(color, nombre);
		this.dificultad = dificultad;
		this.direccion = Direccion.IZQUIERDA;
	}

	public void cambiarMirada(Comestible comestible) {
		
		if(estaMuerto()) {
			return;
		}
		
		int probabilidadCaminoOptimo = (int) Math.floor(Math.random() * 101);
		
		if( probabilidadCaminoOptimo <= dificultad ) {
			caminoOptimoIA(comestible);
		}
		else {
			caminoRandomIA();
		}
	}
	
	private void caminoRandomIA() {
		
		int probabilidadDelCamino = (int) Math.floor(Math.random() * 101);
		
		if(probabilidadDelCamino >= 85) {
			mirar(Direccion.ABAJO.name());
		}else if( probabilidadDelCamino >= 70) {
			mirar(Direccion.ARRIBA.name());
		}else if( probabilidadDelCamino >= 55) {
			mirar(Direccion.DERECHA.name());
		}else if( probabilidadDelCamino >= 40) {
			mirar(Direccion.IZQUIERDA.name());
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
			if( direccionEnX == 1 ) {
				mirar(Direccion.IZQUIERDA.name());
			} else {
				mirar(Direccion.DERECHA.name());;
			}
			return;
		}
		
		// X < Y  o  X == Y (decido ir a por Y)
		if(direccionEnY == 1) {
			mirar(Direccion.ARRIBA.name());
		}
		else {
			mirar(Direccion.ABAJO.name());
		}
	}
	
	public void mirar(String mirarA) {
		this.direccion = direccion.cambiarDireccion(mirarA, this);
	}
	
}
