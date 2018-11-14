package main;

public class Inmortal extends Normal{
	@Override
	public Estado morir(Serpiente serpiente) {
		return this;
	}
}
