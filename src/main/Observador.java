package main;

import java.net.Socket;

public interface Observador {

	public void dibujar(UbicacionesDTO campo);
	public void agregarJugador(Socket jugador);
	
}
