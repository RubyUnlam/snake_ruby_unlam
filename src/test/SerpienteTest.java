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
		
		int tamañoInicial = serpiente.getUbicaciones().size();
		serpiente.moverse();
		
		Assert.assertEquals(4, tamañoInicial);
		Assert.assertEquals(4, serpiente.getUbicaciones().size());
	}
	
	@Test
	public void testComerManzana() {
		Serpiente serpiente = new Serpiente();
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		Comestible manzana = new Manzana(new Ubicacion(cabeza.getX() - 20, cabeza.getY()));
		
		int tamañoInicial = serpiente.getUbicaciones().size();
		serpiente.moverse();
		serpiente.checkearColision(manzana);
		
		Assert.assertEquals(4, tamañoInicial);
		Assert.assertEquals(5, serpiente.getUbicaciones().size());
	}
	
	@Test
	public void testComerManzanaSinEstarEnPosicion() {
		Serpiente serpiente = new Serpiente();
		Ubicacion cabeza = serpiente.getUbicaciones().get(0);
		Comestible manzana = new Manzana(new Ubicacion(cabeza.getX() - 20, cabeza.getY()));
		
		int tamañoInicial = serpiente.getUbicaciones().size();
		serpiente.checkearColision(manzana);
		
		Assert.assertEquals(4, tamañoInicial);
		Assert.assertEquals(4, serpiente.getUbicaciones().size());
	}
	
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
	}
	
	@Test
	public void testChocarContraOtraSerpienteMata() {
		Serpiente serpienteUno = new Serpiente(new Ubicacion(200, 60));
		Serpiente serpienteDos = new Serpiente(new Ubicacion(120, 60));
		
		
		int tamañoInicial = serpienteUno.getUbicaciones().size();
		serpienteUno.moverse();
		serpienteUno.checkearColision(serpienteDos);
		
		Assert.assertEquals(4, tamañoInicial);
		Assert.assertTrue(serpienteUno.getUbicaciones().isEmpty());
		Assert.assertEquals(4, serpienteDos.getUbicaciones().size());
	}
	
	@Test
	public void testChocarContraOtraCabezaMuerenAmbas() {
		Serpiente serpienteUno = new Serpiente(new Ubicacion(120, 40));
		Serpiente serpienteDos = new Serpiente(new Ubicacion(120, 60));
		
		
		int tamañoInicialUno = serpienteUno.getUbicaciones().size();
		int tamañoInicialDos = serpienteDos.getUbicaciones().size();
		
		serpienteUno.mirarAbajo();
		serpienteUno.moverse();
		serpienteUno.checkearColision(serpienteDos);
		
		Assert.assertEquals(4, tamañoInicialUno);
		Assert.assertEquals(4, tamañoInicialDos);
		Assert.assertTrue(serpienteUno.getUbicaciones().isEmpty());
		Assert.assertTrue(serpienteDos.getUbicaciones().isEmpty());
	}
	
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
		int tamañoAntesDeMorir = serpiente.getUbicaciones().size();
		serpiente.checkearColision(serpiente);
		
		Assert.assertEquals(5, tamañoAntesDeMorir);
		Assert.assertTrue(serpiente.getUbicaciones().isEmpty());
	}
	
	@Test
	public void testFueraDeRangoIzquierda() {
		Serpiente serpiente = new Serpiente(new Ubicacion(0, 50));
		
		serpiente.moverse();
		
		Assert.assertEquals(new Ubicacion(ANCHO_VENTANA, 50), serpiente.getUbicaciones().get(0));
	}

}
