package main;

import static utilidades.Constantes.ALTURA_VENTANA;
import static utilidades.Constantes.ANCHO_VENTANA;
import static utilidades.Constantes.VELOCIDAD;

import java.util.ArrayList;

public class Estado {

	public Estado moverse(Serpiente serpiente) {
		if( serpiente.estaMuerto() )
			return this;
		
		for(int i = serpiente.getUbicaciones().size() - 1 ; i > 0 ; i--) {
			serpiente.getUbicaciones().set(i, serpiente.getUbicaciones().get(i-1));
		}
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		int x = cabeza.getX() + (serpiente.direccion.getMirandoX() * VELOCIDAD);
		int y = cabeza.getY() + (serpiente.direccion.getMirandoY() * VELOCIDAD);
		
		if (x == ANCHO_VENTANA) {
			x = 0;
		} else if (x < 0) {
			x = ANCHO_VENTANA - 20;
		}
		
		if (y == ALTURA_VENTANA) {
			y = 0;
		} else if (y < 0) {
			y = ALTURA_VENTANA - 20;
		}
				
		serpiente.getUbicaciones().set(0, new Ubicacion(x,y));
		return this;
	}

	public Estado morir(Serpiente serpiente) {
		serpiente.ubicaciones = new ArrayList<>();
		return this;
	}
	
}
