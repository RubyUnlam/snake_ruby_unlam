package juego.serpiente;

public class Inmortal extends Normal{
	@Override
	public Estado morir(Serpiente serpiente) {
		return this;
	}
}
