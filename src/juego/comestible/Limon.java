package juego.comestible;

import java.awt.*;

public class Limon extends Comestible {

	private final static Color COLOR = Color.YELLOW;
	private final static String POWER_UP = "confundido";
	private final static int PUNTAJE = 20;

	public Limon(){
		super(COLOR, PUNTAJE, POWER_UP);
	}

}
