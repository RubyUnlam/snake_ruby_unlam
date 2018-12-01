package juego;

public enum Cuadrante {

    PRIMERO(0, 1, 0),
    SEGUNDO(1, 0, 0),
    TERCER(2, 0, 1),
    CUARTO(3, 1, 1);

    private int numeroDeCuadrante;
    private int x;
    private int y;

    Cuadrante(int numeroDeCuadrante, int x, int y) {
        this.numeroDeCuadrante = numeroDeCuadrante;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Cuadrante obtenerCuadrante(int nroDeCuadrante) {
        for(Cuadrante cuadrante : values()) {
            if (cuadrante.numeroDeCuadrante == nroDeCuadrante) {
                return cuadrante;
            }
        }
        return PRIMERO;
    }
}
