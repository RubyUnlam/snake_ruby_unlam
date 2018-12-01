package juego.serpiente;

import juego.Ubicacion;
import juego.comestible.Comestible;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Colision {

	public void comprobarColisionComestible(Serpiente serpiente, Queue<Comestible> comestibles) {
		Ubicacion cabezaSerpiente = serpiente.getUbicacionCabeza();

		for (Comestible comestible : comestibles) {
			if( cabezaSerpiente.equals(comestible.getUbicacion()) ) {
				serpiente.comer(comestible);
				comestible.setComida(true);
			}

			if (comestible.fueComida()) {
                comestibles.remove(comestible);
            }
		}
	}

	public void comprobarColisiones(List<Serpiente> jugadores, List<SerpienteIA> serpientesIA, Queue<Comestible> comestibles) {
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
			if (!jugadoresParaEliminar.contains(jugador1)) {
				comprobarColisionComestible(jugador1, comestibles);
			}
		}
		eliminarJugadores(jugadoresParaEliminar);
	}

	private void eliminarJugadores(List<Serpiente> jugadoresParaEliminar) {
		for (Serpiente jugador : jugadoresParaEliminar) {
			jugador.morir();
		}
	}

}
