package main;

import static java.util.Objects.nonNull;
import static utilidades.Constantes.ALTURA_VENTANA;
import static utilidades.Constantes.ANCHO_VENTANA;
import static utilidades.Constantes.MIRAR_ABAJO;
import static utilidades.Constantes.MIRAR_ARRIBA;
import static utilidades.Constantes.MIRAR_DERECHA;
import static utilidades.Constantes.MIRAR_IZQUIERDA;
import static utilidades.Constantes.NO_MIRAR;

import java.util.ArrayList;
import java.util.List;

public class Serpiente {

	private Estado estado;
	
	private List<Ubicacion> ubicaciones = new ArrayList<>();
	private int mirandoX;
	private int mirandoY;
	
	// TODO: analizar si realmente es necesario que la serpiente tenga esto.
	private Puntaje puntaje;

	private int velocidad = 20;
	
	public Serpiente(){
		estado = new Normal();
		Ubicacion cabeza = new Ubicacion();
		ubicaciones.add(cabeza);
		ubicaciones.add(new Ubicacion(cabeza.getX() + velocidad, cabeza.getY()));
		crecer();
		crecer();
		mirarIzquierda();
	}
	
	// Constructor para recibir el usuario del login e inicializar el puntaje
	public Serpiente(String usuario){
		estado = new Normal();
		Ubicacion cabeza = new Ubicacion();
		ubicaciones.add(cabeza);
		ubicaciones.add(new Ubicacion(cabeza.getX() + velocidad, cabeza.getY()));
		crecer();
		crecer();
		mirarIzquierda();
		puntaje = new Puntaje(0, usuario, 0, 0);
	}
	
	public Serpiente(Ubicacion cabeza){
		estado = new Normal();
		ubicaciones.add(cabeza);
		ubicaciones.add(new Ubicacion(cabeza.getX() + velocidad, cabeza.getY()));
		crecer();
		crecer();
		mirarIzquierda();
	}

	private void morir() {
		estado = estado.morir();
	}
	
	public void moverse() {
		estado = this.estado.moverse();
	}

	private void crecer() {
		int cola = ubicaciones.size() - 1;
		Ubicacion uCola = ubicaciones.get(cola);
		Ubicacion uAnteUltimo = ubicaciones.get(cola - 1);
		int nuevoX = calcularPosicionAnterior(uCola.getX(), uAnteUltimo.getX());
		int nuevoY = calcularPosicionAnterior(uCola.getY() ,uAnteUltimo.getY());
		ubicaciones.add(new Ubicacion(nuevoX, nuevoY));
	}
	
	private int calcularPosicionAnterior(int ultimaPos, int anteUltimaPos) {
		return ultimaPos + (ultimaPos - anteUltimaPos);
	}
	
	public void checkearColision(Comestible comestible) {
		estado = estado.checkearColision(comestible);
	}
	
	public void checkearColision(Serpiente serpiente) { //TODO ESTADOS; MUERTO NO HACE NADA
		estado = estado.checkearColision(serpiente);
	}
	
	public void mirarDerecha() { 
		if (mirandoX != MIRAR_IZQUIERDA && noEs180GradosEnX(MIRAR_DERECHA)) {
			mirandoX = MIRAR_DERECHA;
			mirandoY = NO_MIRAR;
		}
	}
	
	public void mirarIzquierda() {
		if (mirandoX != MIRAR_DERECHA && noEs180GradosEnX(MIRAR_IZQUIERDA)) {
			mirandoX = MIRAR_IZQUIERDA;
			mirandoY = NO_MIRAR;			
		}
	}
	
	public void mirarArriba() {
		if (mirandoY != MIRAR_ABAJO && noEs180GradosEnY(MIRAR_ARRIBA)) {
			mirandoX = NO_MIRAR;
			mirandoY = MIRAR_ARRIBA;			
		}
	}
	
	public void mirarAbajo() {
		if (mirandoY != MIRAR_ARRIBA && noEs180GradosEnY(MIRAR_ABAJO)) {
			mirandoX = NO_MIRAR;
			mirandoY = MIRAR_ABAJO;			
		}
	}
	
	private boolean noEs180GradosEnX(int direccion) {
		if (!ubicaciones.isEmpty()) {
			int cabeza = ubicaciones.get(0).getX();
			int cuello = ubicaciones.get(1).getX();
			return ((cabeza + (direccion * velocidad)) != cuello);
		}
		return false;
	}
	
	private boolean noEs180GradosEnY(int direccion) {
		if (!ubicaciones.isEmpty()) {
			int cabeza = ubicaciones.get(0).getY();
			int cuello = ubicaciones.get(1).getY();
			return ((cabeza + (direccion * velocidad)) != cuello);
		}
		return false;
	}
	
	public Puntaje getPuntaje() {
		return puntaje;
	}

	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}
	
	class Normal implements Estado {

		@Override
		public Estado moverse() {
			for(int i = ubicaciones.size() - 1 ; i > 0 ; i--) {
					ubicaciones.set(i, ubicaciones.get(i-1));
			}
			Ubicacion cabeza = ubicaciones.get(0);
			int x = cabeza.getX() + (mirandoX * velocidad);
			int y = cabeza.getY() + (mirandoY * velocidad);
			
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
					
			ubicaciones.set(0, new Ubicacion(x,y));
			return this;
		}	
		


		@Override
		public Estado checkearColision(Serpiente serpiente) { //TODO colisiones entre mas de 2 serpientes.
			Ubicacion cabeza = ubicaciones.get(0);
			List<Ubicacion> cuerpo = serpiente.getUbicaciones();
			if(cabeza.equals(serpiente.getUbicaciones().get(0)) && !Serpiente.this.equals(serpiente)) {
				serpiente.morir();
				return morir();
			} //verifico si no chocaron sus cabezas
			
			for (int i = 1; i < cuerpo.size(); i++) {
				Ubicacion actual = cuerpo.get(i);
				if (cabeza.equals(actual)) {
					return morir(); 
				}
			} //si choca contra algo, muere			
			return this;
		}

		@Override
		public Estado checkearColision(Comestible comestible) {
			if(nonNull(comestible) && !ubicaciones.isEmpty() && ubicaciones.get(0).equals(comestible.getUbicacion())) {
				crecer();
				comestible.setComida(true);
			}
			return this;
		}


		@Override
		public Estado morir() {
			// Esto definitivamente no deberia estar aca.
			Sesion sesion = new Sesion(puntaje);
			sesion.persistirPuntaje();
			//
			ubicaciones = new ArrayList<>();
			return new Muerto();
		}
		
	}
	
	class Muerto implements Estado {

		@Override
		public Estado moverse() {
			return this;
		}

		@Override
		public Estado checkearColision(Serpiente serpiente) {
			return this;
		}

		@Override
		public Estado checkearColision(Comestible comestible) {
			return this;
		}
		
		@Override
		public Estado morir() {
			return this;
		}
	}
	
	
}
