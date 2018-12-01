package juego.comestible;

import java.awt.*;

public class Coco extends Comestible {

	private final static Color COLOR = new Color(80,58,1);
	private final static String POWER_UP = "invertir";
	private final static int PUNTAJE = 15;

	public Coco(){
		super(COLOR, PUNTAJE, POWER_UP);
	}

}
