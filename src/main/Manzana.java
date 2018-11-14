package main;

import java.awt.Color;

public class Manzana extends Comestible {
	
	private final static Color colorDeManzana = Color.RED;
	
	public Manzana(){
		super(colorDeManzana);
	}
	
	public Manzana(Ubicacion ubicacion) {
		super(ubicacion, colorDeManzana);
	}
	
}
