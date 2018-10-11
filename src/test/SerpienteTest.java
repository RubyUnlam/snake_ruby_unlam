package test;

import org.junit.Assert;
import org.junit.Test;

import main.Comestible;
import main.Manzana;
import main.Serpiente;
import main.Ubicacion;
import static utilidades.Constantes.*;

public class SerpienteTest {
	
	@Test
	public void testMoverseNoCrece() {
		Serpiente serpiente = new Serpiente();
		
		int tamanioInicial = serpiente.getUbicaciones().size(); 
		serpiente.moverse();
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertEquals(4, serpiente.getUbicaciones().size());
	}//Se verifica que el método moverse no incrementa el tamaño de la serpiente
	
	@Test
	public void testComerManzana() {
		Serpiente serpiente = new Serpiente();
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		Comestible manzana = new Manzana(new Ubicacion(cabeza.getX() - 20, cabeza.getY())); //spawneo una manzana 20 píxeles delante de la serpiente
		
		int tamanioInicial = serpiente.getUbicaciones().size();
		serpiente.moverse();
		serpiente.checkearColision(manzana);
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertEquals(5, serpiente.getUbicaciones().size());
	}//Se verifica que comer manzana incremente el tamaño de la serpiente
	
	@Test
	public void testComerManzanaSinEstarEnPosicion() {
		Serpiente serpiente = new Serpiente();
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		Comestible manzana = new Manzana(new Ubicacion(cabeza.getX() - 20, cabeza.getY()));
		
		int tamanioInicial = serpiente.getUbicaciones().size();
		serpiente.checkearColision(manzana);
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertEquals(4, serpiente.getUbicaciones().size());
	}//Se verifica que si la cabeza de la serpiente no está en la posición de la manzana, esta no sea comida ni crezca.
	
	@Test
	public void testMoverseSeMueve() {
		Serpiente serpiente = new Serpiente();
		
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		Ubicacion hombro = serpiente.getUbicaciones().get(1);
		Ubicacion rodilla = serpiente.getUbicaciones().get(2);
		
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(cabeza.getX() -20, cabeza.getY()), serpiente.getUbicaciones().get(0));
		Assert.assertEquals(cabeza, serpiente.getUbicaciones().get(1));
		Assert.assertEquals(hombro, serpiente.getUbicaciones().get(2));
		Assert.assertEquals(rodilla, serpiente.getUbicaciones().get(3));
	}//Guardo las posiciones de la serpiente, la muevo, y verifico que todas estén desplazadas
	
	@Test
	public void testChocarContraOtraSerpienteMata() {
		Serpiente serpienteUno = new Serpiente(new Ubicacion(200, 60));
		Serpiente serpienteDos = new Serpiente(new Ubicacion(120, 60)); //Creo una serpiente a 4 posiciones de distancia
		
		
		int tamanioInicial = serpienteUno.getUbicaciones().size();
		serpienteUno.moverse(); //Se mueve la serpiente hacia la derecha
		serpienteUno.checkearColision(serpienteDos);
		
		Assert.assertEquals(4, tamanioInicial);
		Assert.assertTrue(serpienteUno.getUbicaciones().isEmpty());
		Assert.assertEquals(4, serpienteDos.getUbicaciones().size());
	}//Verifico que la colisión entre serpientes resulte en una muerte
	
	@Test
	public void testChocarContraOtraCabezaMuerenAmbas() {
		Serpiente serpienteUno = new Serpiente(new Ubicacion(120, 40)); 
		Serpiente serpienteDos = new Serpiente(new Ubicacion(120, 60)); //Spawneo 2 serpientes a 1 posición de distancia
		
		
		int tamanioInicialUno = serpienteUno.getUbicaciones().size();
		int tamanioInicialDos = serpienteDos.getUbicaciones().size();
		
		serpienteUno.mirarAbajo();
		serpienteUno.moverse();
		serpienteUno.checkearColision(serpienteDos); //Las acomodo para que se miren, muevo una y chequeo colisión
		
		Assert.assertEquals(4, tamanioInicialUno);
		Assert.assertEquals(4, tamanioInicialDos);
		Assert.assertTrue(serpienteUno.getUbicaciones().isEmpty());
		Assert.assertTrue(serpienteDos.getUbicaciones().isEmpty());
	}//Choque de cabezas resulta en la muerte de ambas serpientes
	
	@Test
	public void testChocarConsigoMisma() {
		Ubicacion cabeza = new Ubicacion(140, 140);
		Serpiente serpiente = new Serpiente(cabeza);
		
		serpiente.checkearColision(new Manzana(cabeza));
		serpiente.mirarAbajo();
		serpiente.moverse();
		serpiente.mirarDerecha();
		serpiente.moverse();
		serpiente.mirarArriba();
		serpiente.moverse();
		int tamanioAntesDeMorir = serpiente.getUbicaciones().size();
		serpiente.checkearColision(serpiente);
		
		Assert.assertEquals(5, tamanioAntesDeMorir);
		Assert.assertTrue(serpiente.getUbicaciones().isEmpty());
	}//Se crea una serpiente de 5 posiciones y se la mueve en círculos hasta que choque consigo misma
	
	@Test
	public void testFueraDeRangoIzquierda() {
		Serpiente serpiente = new Serpiente(new Ubicacion(0, 50));
		
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(ANCHO_VENTANA, 50), serpiente.getUbicaciones().get(0));
	}
	
	@Test
	public void testFueraDeRangoDerecha() {
		Serpiente serpiente = new Serpiente(new Ubicacion(ANCHO_VENTANA, 50));
		serpiente.mirarArriba();
		serpiente.moverse();
		serpiente.mirarDerecha();
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(0, 30), serpiente.getUbicaciones().get(0));
	}
	
	@Test
	public void testFueraDeRangoArriba() {
		Serpiente serpiente = new Serpiente(new Ubicacion(0, 0));
		serpiente.mirarArriba();
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(0, ALTURA_VENTANA), serpiente.getUbicaciones().get(0));
	}
	@Test
	public void testFueraDeRangoAbajo() {
		Serpiente serpiente = new Serpiente(new Ubicacion(0, ALTURA_VENTANA));
		serpiente.mirarAbajo();
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(0, 0), serpiente.getUbicaciones().get(0));
	}
	//En todos últimos 4 test se verifica que si una serpiente desaparece por un borde, que aparezca en el opuesto.

}
