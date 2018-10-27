package main;

import java.util.ArrayList;

import static java.util.Objects.nonNull;
import java.util.List;
import static utilidades.Constantes.*;

public class Serpiente {

	private Estado estado;
	
	private List<Ubicacion> ubicaciones = new ArrayList<>();
	private int mirandoX;
	private int mirandoY;

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

	/**
	 * Genera una nueva ubicacion justo detras de la cola
	 */
	private void crecer() {
		int indiceCola = ubicaciones.size() - 1;
		Ubicacion cola = ubicaciones.get(indiceCola);
		Ubicacion anteUltimo = ubicaciones.get(indiceCola - 1);
		ubicaciones.add(cola.calcularUbicacionAnterior(anteUltimo));
	}
	
	/**
	 * Se verifica si hubo colision con el comestible dado.
	 * La implementacion del metodo se delega a cada uno de los estados.
	 * @param comestible
	 */
	public void checkearColision(Comestible comestible) {
		estado = estado.checkearColision(comestible);
	}
	
	/**
	 * Se verifica si hubo colision contra una serpiente dada.
	 * La implementacion del metodo se delega a cada uno de los estados.
	 * @param comestible
	 */
	public void checkearColision(Serpiente serpiente) {
		estado = estado.checkearColision(serpiente);
	}
	
	public void mirarDerecha() { 
		if (noEs180GradosEnX(MIRAR_DERECHA)) {
			mirandoX = MIRAR_DERECHA;
			mirandoY = NO_MIRAR;
		}
	}
	
	public void mirarIzquierda() {
		if (noEs180GradosEnX(MIRAR_IZQUIERDA)) {
			mirandoX = MIRAR_IZQUIERDA;
			mirandoY = NO_MIRAR;			
		}
	}
	
	public void mirarArriba() {
		if (noEs180GradosEnY(MIRAR_ARRIBA)) {
			mirandoX = NO_MIRAR;
			mirandoY = MIRAR_ARRIBA;			
		}
	}
	
	public void mirarAbajo() {
		if (noEs180GradosEnY(MIRAR_ABAJO)) {
			mirandoX = NO_MIRAR;
			mirandoY = MIRAR_ABAJO;			
		}
	}
	
	/**
	 * Validacion para saber si puede girar en la direccion
	 * especificada en X
	 * @param direccion
	 * @return
	 */
	private boolean noEs180GradosEnX(int direccion) {
		int cabeza = ubicaciones.get(0).getX();
		int cuello = ubicaciones.get(1).getX();
		return ((cabeza + (direccion * velocidad)) != cuello);
	}
	
	/**
	 * Validacion para saber si puede girar en la direccion
	 * especificada en Y
	 * @param direccion
	 * @return
	 */
	private boolean noEs180GradosEnY(int direccion) {
		int cabeza = ubicaciones.get(0).getY();
		int cuello = ubicaciones.get(1).getY();
		return ((cabeza + (direccion * velocidad)) != cuello);
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
			
			if (x > ANCHO_VENTANA) {
				x = 0;
			} else if (x < 0) {
				x = ANCHO_VENTANA;
			}
			
			if (y > ALTURA_VENTANA) {
				y = 0;
			} else if (y < 0) {
				y = ALTURA_VENTANA;
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
