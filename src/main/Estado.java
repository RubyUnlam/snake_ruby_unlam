package main;

public interface Estado {

	Estado moverse();
	Estado checkearColision(Serpiente serpiente);
	Estado checkearColision(Comestible comestible);
	
}
