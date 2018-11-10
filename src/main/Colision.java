package main;

import static java.util.Objects.nonNull;

import java.util.List;

public class Colision extends Serpiente{
	
	public void checkearColision(Comestible comestible) {
		if(nonNull(comestible) && !getUbicaciones().isEmpty() && getUbicacionCabeza().equals(comestible.getUbicacion())) {
			crecer();
			comestible.setComida(true);
		}
	}
	
	public void checkearColision(Serpiente serpiente) { //TODO colisiones entre mas de 2 serpientes.
		Ubicacion cabeza = getUbicacionCabeza();
		List<Ubicacion> cuerpo = serpiente.getUbicaciones();
		if(!serpiente.getUbicaciones().isEmpty()) {
			if(cabeza.equals(serpiente.getUbicaciones().get(0)) && !this.equals(serpiente)) {
				serpiente.morir();
				this.estado.morir();
			} //verifico si no chocaron sus cabezas
			
			for (int i = 1; i < cuerpo.size(); i++) { // cuerpo de la otra serpiente, ya sea otra o si misma
				Ubicacion actual = cuerpo.get(i);
				if (cabeza.equals(actual)) { 
					this.estado.morir(); 
				}
			} //si choca contra algo, muere			
		}
	}

}
