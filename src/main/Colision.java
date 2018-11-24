package main;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

public class Colision{
	
	public void comprobarColision(Serpiente serpiente, Comestible comestible) {
		if(nonNull(comestible) && !serpiente.getUbicaciones().isEmpty() && serpiente.getUbicacionCabeza().equals(comestible.getUbicacion())) {
			serpiente.crecer();
			comestible.setComida(true);
		}
	}
	
//	public void comprobarColision(Serpiente jugador, Serpiente serpiente) { //TODO colisiones entre mas de 2 serpientes.
//		List<Ubicacion> cuerpo = serpiente.getUbicaciones();
//		if(!serpiente.getUbicaciones().isEmpty()) {
//			Ubicacion cabeza = jugador.getUbicacionCabeza();
//			if(cabeza.equals(serpiente.getUbicaciones().get(0)) && !jugador.equals(serpiente)) {
//				serpiente.morir();
//				jugador.estado.morir(jugador);
//			} //verifico si no chocaron sus cabezas
//			
//			for (int i = 1; i < cuerpo.size(); i++) { // cuerpo de la otra serpiente, ya sea otra o si misma
//				Ubicacion actual = cuerpo.get(i);
//				if (cabeza.equals(actual)) { 
//					jugador.estado.morir(jugador); 
//				}
//			} //si choca contra algo, muere			
//		}
//	}
	
	public void comprobarColisiones(List<Serpiente> jugadores, List<SerpienteIA> serpientesIA) {
		List<Serpiente> jugadoresParaEliminar = new ArrayList<Serpiente>();;
		List<Serpiente> jugadoresTotales = new ArrayList<Serpiente>();
		
		jugadoresTotales.addAll(jugadores);
		jugadoresTotales.addAll(serpientesIA);
		
		for (int i = 0; i < jugadoresTotales.size(); i++) {
			Serpiente jugador1 = jugadoresTotales.get(i);
			
			if( jugador1.estaMuerto() )
				continue;
			
			Ubicacion cabezaJugador1 = jugador1.getUbicacionCabeza();
			
			for (int j = 0; j < jugadoresTotales.size(); j++) {
				Serpiente jugador2 = jugadoresTotales.get(j);
				
				if( jugador2.estaMuerto() )
					continue;
				
				Ubicacion cabezaJugador2 = jugador2.getUbicacionCabeza();	
				
				//VERFICO SI CHOCAN DOS CABEZAS - CASO CONTRARIO ANALIZO CONTRA UN CUERPO
				if( cabezaJugador1.equals(cabezaJugador2) && i != j) {
					jugadoresParaEliminar.add(jugador1);
					jugadoresParaEliminar.add(jugador2);
					
				}else {
					List<Ubicacion> cuerpoJugador2 = jugador2.getUbicaciones();
					
					for (int k = 1; k < cuerpoJugador2.size(); k++) {
						Ubicacion actual = cuerpoJugador2.get(k);
						if (cabezaJugador1.equals(actual)) { 
							jugadoresParaEliminar.add(jugador1);
						}	
					}
				}
			}
		}
		eliminarJugadores(jugadoresParaEliminar);
	}
	
	public void eliminarJugadores(List<Serpiente> jugadoresParaEliminar) {
		for (Serpiente jugador : jugadoresParaEliminar) {
			jugador.morir();
		}
	}

}
