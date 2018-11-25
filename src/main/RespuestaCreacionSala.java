package main;

import java.util.List;

public class RespuestaCreacionSala {

    private boolean fueCreada;
    private List<Sala> listaSalas;
    private String mensaje;

    public RespuestaCreacionSala(boolean rta, List<Sala> salas) {
        fueCreada = rta;
        listaSalas = salas;
    }


    public RespuestaCreacionSala(boolean rta, String mensaje) {
        fueCreada = rta;
        this.mensaje = mensaje;
    }
}
