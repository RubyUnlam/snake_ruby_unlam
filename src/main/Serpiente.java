package main;

import static java.util.Objects.nonNull;
import static utilidades.Constantes.ALTURA_VENTANA;
import static utilidades.Constantes.ANCHO_VENTANA;
import static utilidades.Constantes.VELOCIDAD;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Serpiente {

	private Estado estado;
	private Color color;
	private List<Ubicacion> ubicaciones = new ArrayList<>();
	protected Direccion direccion;
	private int puntaje = 0;
	private String nombreDeSerpiente;
	
	public Serpiente(Color color, String nombre){
		this.color = color;
		estado = new Normal();
		Ubicacion cabeza = new Ubicacion();
		ubicaciones.add(cabeza);
		ubicaciones.add(new Ubicacion(cabeza.getX() + VELOCIDAD, cabeza.getY()));
		crecer();
		crecer();
		this.direccion = Direccion.IZQUIERDA;
		this.nombreDeSerpiente = nombre;
	}
	
	public Serpiente(Ubicacion cabeza, Color color){
		this.color = color;
		estado = new Normal();
		ubicaciones.add(cabeza);
		ubicaciones.add(new Ubicacion(cabeza.getX() + VELOCIDAD, cabeza.getY()));
		crecer();
		crecer();
		this.direccion = Direccion.IZQUIERDA;
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
	
	public void checkearColision(Serpiente serpiente) {
		estado = estado.checkearColision(serpiente);
	}
	
	public void mirar(String mirarA) {
		this.direccion = direccion.cambiarDireccion(mirarA, this);
	}	
	
	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}
	
	public Color obtenerColor() {
		return this.color;
	}
	
	protected Ubicacion getUbicacionCabeza() {
		return  ubicaciones.get(0);
	}
	
	protected boolean estaMuerto() {
		return ubicaciones.isEmpty();
	}

	public Estado getEstado() {
		return estado;
	}

	public String getNombre(){ return nombreDeSerpiente;}

	public int getPuntaje(){ return puntaje;}
	
	class Normal implements Estado {

		@Override
		public Estado moverse() {
			for(int i = ubicaciones.size() - 1 ; i > 0 ; i--) {
					ubicaciones.set(i, ubicaciones.get(i-1));
			}
			Ubicacion cabeza = ubicaciones.get(0);
			int x = cabeza.getX() + (direccion.getMirandoX() * VELOCIDAD);
			int y = cabeza.getY() + (direccion.getMirandoY() * VELOCIDAD);
			
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
			Ubicacion cabeza = getUbicacionCabeza();
			List<Ubicacion> cuerpo = serpiente.getUbicaciones();
			if(!serpiente.getUbicaciones().isEmpty()) {
				if(cabeza.equals(serpiente.getUbicaciones().get(0)) && !Serpiente.this.equals(serpiente)) {
					serpiente.morir();
					return morir();
				} //verifico si no chocaron sus cabezas
				
				for (int i = 1; i < cuerpo.size(); i++) { // cuerpo de la otra serpiente, ya sea otra o si misma
					Ubicacion actual = cuerpo.get(i);
					if (cabeza.equals(actual)) { 
						return morir(); 
					}
				} //si choca contra algo, muere			
			}
			return this;
		}

		@Override
		public Estado checkearColision(Comestible comestible) {
			if(nonNull(comestible) && !ubicaciones.isEmpty() && getUbicacionCabeza().equals(comestible.getUbicacion())) {
				crecer();
				comestible.setComida(true);
				puntaje+=10;
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
