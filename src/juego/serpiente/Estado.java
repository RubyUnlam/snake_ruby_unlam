package juego.serpiente;

public interface Estado {

	public Estado moverse(Serpiente serpiente);

	public Estado morir(Serpiente serpiente);
	
}
