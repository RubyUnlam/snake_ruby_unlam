package main;

import java.util.ArrayList;

import static java.util.Objects.nonNull;
import java.util.List;
import static utilidades.Constantes.*;

public class Serpiente {
	
	private Integer id = 1;

	private Estado estado;
	
	private List<Ubicacion> ubicaciones = new ArrayList<>();
	private int mirandoX;
	private int mirandoY;

	private int velocidad = 20;
	
	Serpiente(){
		estado = new Normal(); 
		ubicaciones.add(new Ubicacion(50, 90)); //TODO ELIMINAR UBICACIONES DEFAULT
		ubicaciones.add(new Ubicacion(50, 70));
		ubicaciones.add(new Ubicacion(50, 50));
		ubicaciones.add(new Ubicacion(50, 30));
		ubicaciones.add(new Ubicacion(50, 10));
		mirarAbajo();
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
	
	private boolean soyYo(Integer id) {
		return this.id.equals(id);
	}
	
	public void mirarDerecha() { 
		if (mirandoX != MIRAR_IZQUIERDA) {
			mirandoX = MIRAR_DERECHA;
			mirandoY = NO_MIRAR;
		}
	}
	
	public void mirarIzquierda() {
		if (mirandoX != MIRAR_DERECHA) {
			mirandoX = MIRAR_IZQUIERDA;
			mirandoY = NO_MIRAR;			
		}
	}
	
	public void mirarArriba() {
		if (mirandoY != MIRAR_ABAJO) {
			mirandoX = NO_MIRAR;
			mirandoY = MIRAR_ARRIBA;			
		}
	}
	
	public void mirarAbajo() {
		if (mirandoY != MIRAR_ARRIBA) {
			mirandoX = NO_MIRAR;
			mirandoY = MIRAR_ABAJO;			
		}
	}
	
	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}
	

	public Integer getId() {
		return id;
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
					
			ubicaciones.set(0, new Ubicacion(x,y));
			return this;
		}	
		


		@Override
		public Estado checkearColision(Serpiente serpiente) {
			Ubicacion cabeza = ubicaciones.get(0);
			List<Ubicacion> cuerpo = serpiente.getUbicaciones();
			Integer idEnemigo = serpiente.getId();
			if(cabeza.equals(serpiente.getUbicaciones().get(0)) && !soyYo(idEnemigo)) {
				serpiente.morir();
				return morir();
			} //verifico si no chocaron sus cabezas
			
			for (int i = 1; i < cuerpo.size(); i++) {
				Ubicacion actual = cuerpo.get(i);
				if (cabeza.equals(actual)) {
					return morir(); 
				}
			} //si chocó contra algo, muere			
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
