package main;

public interface Estado {

	Estado moverse();
	Estado chequearColision(Serpiente serpiente);
	Estado chequearColision(Comestible comestible);
	Estado morir();
	
}
