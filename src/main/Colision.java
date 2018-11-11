package main;

import static java.util.Objects.nonNull;

import java.util.List;

public class Colision{
	
	public void comprobarColision(Serpiente serpiente, Comestible comestible) {
		if(nonNull(comestible) && !serpiente.getUbicaciones().isEmpty() && serpiente.getUbicacionCabeza().equals(comestible.getUbicacion())) {
			serpiente.crecer();
			comestible.setComida(true);
		}
	}
	
	public void comprobarColision(Serpiente jugador, Serpiente serpiente) { //TODO colisiones entre mas de 2 serpientes.
		Ubicacion cabeza = jugador.getUbicacionCabeza();
		List<Ubicacion> cuerpo = serpiente.getUbicaciones();
		if(!serpiente.getUbicaciones().isEmpty()) {
			if(cabeza.equals(serpiente.getUbicaciones().get(0)) && !jugador.equals(serpiente)) {
				serpiente.morir();
				jugador.estado.morir(jugador);
			} //verifico si no chocaron sus cabezas
			
			for (int i = 1; i < cuerpo.size(); i++) { // cuerpo de la otra serpiente, ya sea otra o si misma
				Ubicacion actual = cuerpo.get(i);
				if (cabeza.equals(actual)) { 
					jugador.estado.morir(jugador); 
				}
			} //si choca contra algo, muere			
		}
	}

}
