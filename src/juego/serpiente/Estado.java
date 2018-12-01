package juego.serpiente;

public interface Estado {

    Estado moverse(Serpiente serpiente);

    Estado morir(Serpiente serpiente);

    Direccion mirarA(Serpiente serpiente, String mirarA);

}
