package juego.serpiente;

import juego.Ubicacion;
import juego.comestible.Comestible;

import static utilidades.Constantes.VELOCIDAD;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Serpiente {

	private Estado estado;
	private Color color;
	private List<Ubicacion> ubicaciones = new ArrayList<>();

	protected Direccion direccion;
	private int puntaje;
	private String nombreDeSerpiente;
	private boolean salir;

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

	public Serpiente(Ubicacion cabeza){
		estado = new Normal();
		ubicaciones.add(cabeza);
		ubicaciones.add(new Ubicacion(cabeza.getX() + VELOCIDAD, cabeza.getY()));
		crecer();
		crecer();
		this.direccion = Direccion.IZQUIERDA;
	}

	public void morir() {
		estado = estado.morir(this);
	}

	public void moverse() {
		estado = estado.moverse(this);
	}

	public void comer(Comestible comestible) {
		puntaje += comestible.obtenerPuntaje();
		crecer();
		powerUp(comestible);
	}

	private void powerUp(Comestible comestible) {
		switch (comestible.getPowerUp()) {
			case "congelado":
				new GestorDeEstado(this, new Congelado(), 1000).start();
				break;
			case "inmortal":
				new GestorDeEstado(this, new Inmortal(), 200).start();
				break;
		}
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

	public void mirar(String mirarA) {
		this.direccion = estado.mirarA(this, mirarA);
	}

	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}

	public void setUbicaciones( ArrayList<Ubicacion> ubicaciones ) {
		this.ubicaciones = ubicaciones;
	}

	public Color obtenerColor() {
		return this.color;
	}

	protected Ubicacion getUbicacionCabeza() {
		return  ubicaciones.get(0);
	}

	public boolean estaMuerto() {
		return ubicaciones.isEmpty();
	}

	public Estado getEstado() {
		return estado;
	}

	public String getNombre(){ return nombreDeSerpiente;}

	public int getPuntaje(){ return puntaje;}

	public void sumarPuntos(int puntos){
		puntaje+=puntos;
	}

    public boolean salir() {
        return salir;
    }

    public Serpiente setSalir(boolean salir) {
        this.salir = salir;
        return this;
    }

	protected void setEstado(Estado estado) {
		this.estado = estado;
	}
}