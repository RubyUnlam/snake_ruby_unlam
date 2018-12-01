package juego.serpiente;

public class Confundido extends Normal {

    @Override
    public Direccion mirarA(Serpiente serpiente, String mirarA) {
        String nuevaDireccion = Direccion.encontrarIdOpuesto(mirarA);
        return serpiente.direccion.cambiarDireccion(nuevaDireccion, serpiente);
    }
}
