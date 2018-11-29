package test;

import org.junit.Assert;
import org.junit.Test;

import main.Comestible;
import main.Manzana;
import main.Serpiente;
import main.Ubicacion;
import static utilidades.Constantes.*;

import java.awt.Color;

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
		Serpiente serpiente = new Serpiente(Color.BLUE, "sarasa");
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		Comestible manzana = new Manzana(new Ubicacion(cabeza.getX() - 20, cabeza.getY())); //spawneo una manzana 20 p�xeles delante de la serpiente
		
		int tamanioInicial = serpiente.getUbicaciones().size();
		serpiente.moverse();
		serpiente.checkearColision(manzana);
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertEquals(5, serpiente.getUbicaciones().size());
	}
	
	/**
	 * Se verifica que si la cabeza de la serpiente no est� en la posici�n de la manzana, esta no sea comida ni crezca.
	 */
	@Test
	public void testComerManzanaSinEstarEnPosicion() {
		Serpiente serpiente = new Serpiente(Color.BLUE, "sarasa");
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		Comestible manzana = new Manzana(new Ubicacion(cabeza.getX() - 20, cabeza.getY()));
		
		int tamanioInicial = serpiente.getUbicaciones().size();
		serpiente.checkearColision(manzana);
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertEquals(4, serpiente.getUbicaciones().size());
	}
	
	/**
	 * Guardo las posiciones de la serpiente, la muevo, y verifico que todas est�n desplazadas
	 */
	@Test
	public void testMoverseSeMueve() {
		Serpiente serpiente = new Serpiente(Color.BLUE, "sarasa");
		
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		Ubicacion hombro = serpiente.getUbicaciones().get(1);
		Ubicacion rodilla = serpiente.getUbicaciones().get(2);
		
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(cabeza.getX() -20, cabeza.getY()), serpiente.getUbicaciones().get(0));
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
		Serpiente serpienteUno = new Serpiente(new Ubicacion(200, 60), Color.BLUE);
		Serpiente serpienteDos = new Serpiente(new Ubicacion(120, 60), Color.BLUE);
		
		
		int tamanioInicial = serpienteUno.getUbicaciones().size();
		serpienteUno.moverse();
		serpienteUno.checkearColision(serpienteDos);
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertTrue(serpienteUno.getUbicaciones().isEmpty());
		Assert.assertEquals(4, serpienteDos.getUbicaciones().size());
	}
	
	/**
	 * Se verifica que el choque entre 2 cabezas de serpientes termine en la muerte de ambas.
	 */
	@Test
	public void testChocarContraOtraCabezaMuerenAmbas() {
		Serpiente serpienteUno = new Serpiente(new Ubicacion(120, 40), Color.BLUE); 
		Serpiente serpienteDos = new Serpiente(new Ubicacion(120, 60), Color.BLUE);
		
		
		int tamanioInicialUno = serpienteUno.getUbicaciones().size();
		int tamanioInicialDos = serpienteDos.getUbicaciones().size();
		
		serpienteUno.mirar("abajo");
		serpienteUno.moverse();
		serpienteUno.checkearColision(serpienteDos);
		
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
		Ubicacion cabeza = new Ubicacion(140, 140);
		Serpiente serpiente = new Serpiente(cabeza, Color.BLUE);
		
		serpiente.checkearColision(new Manzana(cabeza));
		serpiente.mirar("abajo");
		serpiente.moverse();
		serpiente.mirar("derecha");
		serpiente.moverse();
		serpiente.mirar("arriba");
		serpiente.moverse();
		int tamanioAntesDeMorir = serpiente.getUbicaciones().size();
		serpiente.checkearColision(serpiente);
		
		Assert.assertEquals(5, tamanioAntesDeMorir);
		Assert.assertTrue(serpiente.getUbicaciones().isEmpty());
	}
	
	/**
	 * Se verifica que si la serpiente desaparece por la izquierda, reaparezca por la derecha.
	 */
	@Test
	public void testFueraDeRangoIzquierda() {
		Serpiente serpiente = new Serpiente(new Ubicacion(0, 50), Color.BLUE);
		
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(ANCHO_VENTANA, 50), serpiente.getUbicaciones().get(0));
	}
	
	/**
	 * Se verifica que si la serpiente desaparece por la derecha, reaparezca por la izquierda.
	 */
	@Test
	public void testFueraDeRangoDerecha() {
		Serpiente serpiente = new Serpiente(new Ubicacion(ANCHO_VENTANA, 50), Color.BLUE);
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
		Serpiente serpiente = new Serpiente(new Ubicacion(0, 0), Color.BLUE);
		serpiente.mirar("arriba");
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(0, ALTURA_VENTANA), serpiente.getUbicaciones().get(0));
	}
	/**
	 * Se verifica que si la serpiente desaparece por abajo, reaparezca por arriba.
	 */
	@Test
	public void testFueraDeRangoAbajo() {
		Serpiente serpiente = new Serpiente(new Ubicacion(0, ALTURA_VENTANA), Color.BLUE);
		serpiente.mirar("abajo");
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(0, 0), serpiente.getUbicaciones().get(0));
	}

}
