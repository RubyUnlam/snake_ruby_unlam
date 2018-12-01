package test;

import org.junit.Assert;
import org.junit.Test;

import juego.comestible.Comestible;
import juego.comestible.Manzana;
import juego.serpiente.Serpiente;
import juego.serpiente.SerpienteIA;
import juego.Ubicacion;
import juego.serpiente.Colision;
import static utilidades.Constantes.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SerpienteTest {
	
	/**
	 * Se verifica que el m�todo moverse no incrementa el tama�o de la serpiente
	 */
	@Test
	public void testMoverseNoCrece() {
		Serpiente serpiente = new Serpiente(Color.BLUE, "sarasa");
		
		int tamanioInicial = serpiente.getUbicaciones().size(); 
		serpiente.moverse();
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertEquals(4, serpiente.getUbicaciones().size());
	}
	
	/**
	 * Se verifica que comer manzana incremente el tama�o de la serpiente
	 */
	@Test
	public void testComerManzana() {
		Colision colision = new Colision();
		
		Serpiente serpiente = new Serpiente(Color.BLUE, "sarasa");

		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		
		Queue<Comestible> comestibles = new ConcurrentLinkedQueue<Comestible>();
		Comestible manzana = new Manzana(new Ubicacion(cabeza.getX() - 20, cabeza.getY())); //spawneo una manzana 20 p�xeles delante de la serpiente
		comestibles.add(manzana);
		
		int tamanioInicial = serpiente.getUbicaciones().size();
		serpiente.moverse();
		
		colision.comprobarColisionComestible(serpiente, comestibles);
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertEquals(5, serpiente.getUbicaciones().size());
	}
	
	/**
	 * Se verifica que si la cabeza de la serpiente no est� en la posici�n de la manzana, esta no sea comida ni crezca.
	 */
	@Test
	public void testComerManzanaSinEstarEnPosicion() {
		Colision colision = new Colision();
		
		Serpiente serpiente = new Serpiente(Color.BLUE, "sarasa");

		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		
		Queue<Comestible> comestibles = new ConcurrentLinkedQueue<Comestible>();
		Comestible manzana = new Manzana(new Ubicacion(cabeza.getX() - 20, cabeza.getY()));
		comestibles.add(manzana);
		
		int tamanioInicial = serpiente.getUbicaciones().size();

		colision.comprobarColisionComestible(serpiente, comestibles);
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertEquals(4, serpiente.getUbicaciones().size());
	}
	
	/**
	 * Guardo las posiciones de la serpiente, la muevo, y verifico que todas est�n desplazadas
	 */
	@Test
	public void testMoverseSeMueve() {
		Serpiente serpiente = new Serpiente(new Ubicacion(100, 50));
		
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		Ubicacion hombro = serpiente.getUbicaciones().get(1);
		Ubicacion rodilla = serpiente.getUbicaciones().get(2);
		
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(cabeza.getX() - 20, cabeza.getY()), serpiente.getUbicaciones().get(0));
		Assert.assertEquals(cabeza, serpiente.getUbicaciones().get(1));
		Assert.assertEquals(hombro, serpiente.getUbicaciones().get(2));
		Assert.assertEquals(rodilla, serpiente.getUbicaciones().get(3));
	}
	
	/**
	 * Se verifica que el choque de una serpiente contra otra (siempre que el choque no sea en la cabeza)
	 * provoca la muerte de la serpiente que choc�.
	 * */
	@Test
	public void testChocarContraOtraSerpienteMata() {
		Colision colision = new Colision();
		
		List<Serpiente> serpientes = new ArrayList<>();
		List<SerpienteIA> serpientesIA = new ArrayList<>();
		
		Queue<Comestible> comestibles = new ConcurrentLinkedQueue<Comestible>();
		
		Serpiente serpienteUno = new Serpiente(new Ubicacion(200, 60));
		Serpiente serpienteDos = new Serpiente(new Ubicacion(160, 60));
		
		serpientes.add(serpienteUno);
		serpientes.add(serpienteDos);
		
		int tamanioInicial = serpienteUno.getUbicaciones().size();
		
		serpienteUno.moverse();
		
		colision.comprobarColisiones(serpientes, serpientesIA, comestibles);
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertTrue(serpienteUno.getUbicaciones().isEmpty());
		Assert.assertEquals(4, serpienteDos.getUbicaciones().size());
	}
	
	/**
	 * Se verifica que el choque entre 2 cabezas de serpientes termine en la muerte de ambas.
	 */
	@Test
	public void testChocarContraOtraCabezaMuerenAmbas() {
		Colision colision = new Colision();
		
		List<Serpiente> serpientes = new ArrayList<>();
		List<SerpienteIA> serpientesIA = new ArrayList<>();
		
		Queue<Comestible> comestibles = new ConcurrentLinkedQueue<Comestible>();
		
		Serpiente serpienteUno = new Serpiente(new Ubicacion(120, 40)); 
		Serpiente serpienteDos = new Serpiente(new Ubicacion(120, 60));
		
		serpientes.add(serpienteUno);
		serpientes.add(serpienteDos);
		
		int tamanioInicialUno = serpienteUno.getUbicaciones().size();
		int tamanioInicialDos = serpienteDos.getUbicaciones().size();
		
		serpienteUno.mirar("abajo");
		
		serpienteUno.moverse();
		
		colision.comprobarColisiones(serpientes, serpientesIA, comestibles);
		
		Assert.assertEquals(4, tamanioInicialUno);
		Assert.assertEquals(4, tamanioInicialDos);
		Assert.assertTrue(serpienteUno.getUbicaciones().isEmpty());
		Assert.assertTrue(serpienteDos.getUbicaciones().isEmpty());
	}
	
	/**
	 * Se crea una serpiente de 5 posiciones y se la mueve en c�rculos hasta que choque consigo misma
	 */
	@Test
	public void testChocarConsigoMisma() {
		Colision colision = new Colision();
		
		List<Serpiente> serpientes = new ArrayList<>();
		List<SerpienteIA> serpientesIA = new ArrayList<>();
		
		Queue<Comestible> comestibles = new ConcurrentLinkedQueue<Comestible>();
		
		Ubicacion cabeza = new Ubicacion(140, 140);
		Serpiente serpiente = new Serpiente(cabeza);
		
		Comestible manzana = new Manzana(cabeza);
		
		serpientes.add(serpiente);
		
		comestibles.add(manzana);
		
		colision.comprobarColisiones(serpientes, serpientesIA, comestibles);
		
		serpiente.mirar("abajo");
		serpiente.moverse();
		serpiente.mirar("derecha");
		serpiente.moverse();
		serpiente.mirar("arriba");
		serpiente.moverse();
		int tamanioAntesDeMorir = serpiente.getUbicaciones().size();
		
		colision.comprobarColisiones(serpientes, serpientesIA, comestibles);
		
		Assert.assertEquals(5, tamanioAntesDeMorir);
		Assert.assertTrue(serpiente.getUbicaciones().isEmpty());
	}
	
	/**
	 * Se verifica que si la serpiente desaparece por la izquierda, reaparezca por la derecha.
	 */
	@Test
	public void testFueraDeRangoIzquierda() {
		Serpiente serpiente = new Serpiente(new Ubicacion(0, 50));
		
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(ANCHO_VENTANA-20, 50), serpiente.getUbicaciones().get(0));
	}
	
	/**
	 * Se verifica que si la serpiente desaparece por la derecha, reaparezca por la izquierda.
	 */
	@Test
	public void testFueraDeRangoDerecha() {
		Serpiente serpiente = new Serpiente(new Ubicacion(ANCHO_VENTANA-20, 50));
		serpiente.mirar("arriba");
		serpiente.moverse();
		serpiente.mirar("derecha");
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(0, 30), serpiente.getUbicaciones().get(0));
	}
	
	/**
	 * Se verifica que si la serpiente desaparece por arriba, reaparezca abajo.
	 */
	@Test
	public void testFueraDeRangoArriba() {
		Serpiente serpiente = new Serpiente(new Ubicacion(0, 0));
		serpiente.mirar("arriba");
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(0, ALTURA_VENTANA-20), serpiente.getUbicaciones().get(0));
	}
	/**
	 * Se verifica que si la serpiente desaparece por abajo, reaparezca por arriba.
	 */
	@Test
	public void testFueraDeRangoAbajo() {
		Serpiente serpiente = new Serpiente(new Ubicacion(0, ALTURA_VENTANA-20));
		serpiente.mirar("abajo");
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(0, 0), serpiente.getUbicaciones().get(0));
	}

}
