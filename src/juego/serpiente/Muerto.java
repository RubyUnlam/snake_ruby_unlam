package juego.serpiente;

public class Muerto implements Estado {

    public Estado moverse(Serpiente serpiente) {
        return this;
    }

    public Estado morir(Serpiente serpiente) {
        return this;
    }

    public Direccion mirarA(Serpiente serpiente, String mirarA) {
        return serpiente.direccion;
    }

}
