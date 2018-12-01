package juego.serpiente;

import static utilidades.Constantes.*;
import static java.util.Objects.nonNull;

/**
 * Representa las direcciones a las que se puede mirar
 */
public enum Direccion {

    ARRIBA("arriba", "abajo", NO_MIRAR, MIRAR_ARRIBA),
    ABAJO("abajo", "arriba", NO_MIRAR, MIRAR_ABAJO),
    IZQUIERDA("izquierda", "derecha", MIRAR_IZQUIERDA, NO_MIRAR),
    DERECHA("derecha", "izquierda", MIRAR_DERECHA, NO_MIRAR);

    private String id;
    private String idOpuesto;
    private int mirandoX;
    private int mirandoY;

    Direccion(String id, String idOpuesto, int mirandoX, int mirandoY) {
        this.id = id;
        this.idOpuesto = idOpuesto;
        this.mirandoX = mirandoX;
        this.mirandoY = mirandoY;
    }

    /**
     * Intenta cambiar la direccion por la pedida. Si no es posible cambiar de
     * direccion devuelve la direccion actual
     *
     * @param mirar
     * @param aDireccionar
     * @return
     */
    public Direccion cambiarDireccion(String mirar, Serpiente aDireccionar) {
        Direccion direccion = encontrarDireccion(mirar);
        if (esDireccionValida(direccion) && esGiroValido(direccion, aDireccionar)) {
            return direccion;
        }
        return this;
    }

    /**
     * Es una direccion valida si fue encontrada entre los elementos
     * del enum (no es null) y no es opuesta a la direccion actual
     *
     * @param direccion
     * @return
     */
    private boolean esDireccionValida(Direccion direccion) {
        return nonNull(direccion) && direccion.idOpuesto != this.id;
    }

    /**
     * Es un giro valido si no esta haciendo un giro de 180 grados en la direccion
     * que quiere mirar
     *
     * @param direccion
     * @param aDireccionar
     * @return
     */
    private boolean esGiroValido(Direccion direccion, Serpiente aDireccionar) {
        return (quiereMirarEnX(direccion) && noEs180GradosEnX(direccion, aDireccionar)) || (quiereMirarEnY(direccion) && noEs180GradosEnY(direccion, aDireccionar));
    }

    /**
     * Quiere mirar en la direccion X si la direccion pedida
     * es IZQUIERDA o DERECHA
     *
     * @param direccion
     * @param
     * @return
     */
    private boolean quiereMirarEnX(Direccion direccion) {
        return IZQUIERDA.equals(direccion) || DERECHA.equals(direccion);
    }

    /**
     * Quiere mirar en la direccion Y si la direccion pedida
     * es ARRIBA o ABAJO
     *
     * @param direccion
     * @param
     * @return
     */
    private boolean quiereMirarEnY(Direccion direccion) {
        return ARRIBA.equals(direccion) || ABAJO.equals(direccion);
    }

    /**
     * Se chequea si la direccion a la que se quiere mirar intenta hacer
     * un giro de 180 grados en X
     *
     * @param direccion
     * @param aDireccionar
     * @return
     */
    private boolean noEs180GradosEnX(Direccion direccion, Serpiente aDireccionar) {
        int cabeza = aDireccionar.getUbicaciones().get(0).getX();
        int cuello = aDireccionar.getUbicaciones().get(1).getX();
        return ((cabeza + (direccion.mirandoX * VELOCIDAD)) != cuello);
    }

    /**
     * Se chequea si la direccion a la que se quiere mirar intenta hacer
     * un giro de 180 grados en Y
     *
     * @param direccion
     * @param aDireccionar
     * @return
     */
    private boolean noEs180GradosEnY(Direccion direccion, Serpiente aDireccionar) {
        int cabeza = aDireccionar.getUbicaciones().get(0).getY();
        int cuello = aDireccionar.getUbicaciones().get(1).getY();
        return ((cabeza + (direccion.mirandoY * VELOCIDAD)) != cuello);
    }

    /**
     * Busca entre los valores del Enum el que tenga el id correspondiente
     * a la direccion pedida
     *
     * @param direccion
     * @return
     */
    private static Direccion encontrarDireccion(String direccion) {
        for (Direccion actual : values()) {
            if (actual.id.equalsIgnoreCase(direccion)) {
                return actual;
            }
        }
        return null;
    }

    public static String encontrarIdOpuesto(String aMirar) {
        Direccion direccion = encontrarDireccion(aMirar);
        if (nonNull(direccion)) {
            return direccion.idOpuesto;
        }
        return aMirar;
    }

    public int getMirandoX() {
        return mirandoX;
    }

    public int getMirandoY() {
        return mirandoY;
    }

}
