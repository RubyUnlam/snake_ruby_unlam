package main;

import juego.Jugador;
import org.apache.log4j.Logger;
import servidor.ManejadorES;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class MenuPrincipal {

    public static final String SALIR = "salir";
    public static final String VER_SALAS = "ver_salas";
    public static final String CREAR_SALA = "crear_sala";
    public static final String UNIRSE_A_SALA = "unirse_a_sala";
    public static final String SALIR_DE_SALA = "salir_de_sala";
    public static final String JUGAR = "jugar";
    public static final String CAMBIAR_COLOR = "cambiar_color";
    public static final String PUNTAJE = "Puntaje";

    private Jugador jugador;
    private Sala salaActual;
    private final Logger logger = Logger.getLogger(MenuPrincipal.class);
    private boolean saleDelPartido = false;
    private SincronizadorDeSalas sincronizadorDeSalas;
    private ManejadorES manejadorES;

    public MenuPrincipal(Usuario usuario, ManejadorES manejadorES, SincronizadorDeSalas sincronizadorDeSalas) {
        this.jugador = new Jugador(usuario.getNombreUsuario(), manejadorES);
        this.manejadorES = manejadorES;
        this.sincronizadorDeSalas = sincronizadorDeSalas;
    }

    public void jugar() {
        try {
            String opcion = manejadorES.escuchar(String.class);

            while (!SALIR.equals(opcion)) {
                switch (opcion) {
                    case VER_SALAS:
                        verSalas();
                        break;
                    case CREAR_SALA:
                        crearSala();
                        break;
                    case UNIRSE_A_SALA:
                        unirseASala();
                        break;
                    case SALIR_DE_SALA:
                        salirDeSala();
                        break;
                    case JUGAR:
                        iniciarJuego();
                        break;
                    case CAMBIAR_COLOR:
                        cambiarColor();
                        break;
                    default:
                        logger.error(opcion);
                        break;
                }
                opcion = manejadorES.escuchar(String.class);
            }
        } catch(IOException e) {
            logger.error(e.getMessage());
        }

        if(nonNull(salaActual)){
            salaActual.removerJugador(jugador);
        }

    }

    /**
     * Se setea en el jugador el color enviado por el cliente
     * @throws IOException
     */
    private void cambiarColor() throws IOException {
        jugador.setColor(manejadorES.escuchar(Color.class));
    }

    /**
     * Le envia al cliente las salas disponibles en el sincronizador
     * @throws IOException
     */
    private void verSalas() throws IOException {
        List<Sala> salasAAgregar = new ArrayList<Sala>();
        for(Sala sala : sincronizadorDeSalas.obtenerSalas()){
            if(!sala.estaInactiva()){
                salasAAgregar.add(sala);
            }
        }
        manejadorES.enviar(new RespuestaAccionConSala(true, salasAAgregar));
    }

    /**
     * Recibe una sala a crear del cliente, llama al metodo {@link #crearSala(Sala)} y devuelve al cliente
     * la respuesta
     * @throws IOException
     */
    private void crearSala() throws IOException {
        Sala salaACrear = manejadorES.escuchar(Sala.class);
        RespuestaAccionConSala respuesta = crearSala(salaACrear);
        manejadorES.enviar(respuesta);
    }

    /**
     * Recibe la sala a la cual quiere unirse el cliente, llama al metodo {@link #unirseASala(Sala)}
     * y devuelve al cliente la respuesta
     * @throws IOException
     */
    private void unirseASala() throws IOException {
        Sala salaAUnirse = manejadorES.escuchar(Sala.class);
        RespuestaAccionConSala respuestaAccionConSala = unirseASala(salaAUnirse);
        manejadorES.enviar(respuestaAccionConSala);
    }

    /**
     * Remueve al jugador de la sala, limpia la sala actual y si no acaba de salir
     * de un partido le envia un mensaje vacio para darle de baja las actualizaciones de salas
     * @throws IOException
     */
    private void salirDeSala() throws IOException {
        salaActual.removerJugador(jugador);
        //manejadorES.enviar(salaActual);
        if (!saleDelPartido) {
            jugador.cerrarActualizacionDeSala();
            for (Jugador jugadorEnLista : salaActual.getJugadores()) {
                jugadorEnLista.notificarActualizacionDeSala(salaActual);
            }
        }
        salaActual = null;
        saleDelPartido = false;
    }

    /**
     * Si el cliente se encuentra en una sala, genera un {@link CountDownLatch}
     * que se setea en el Jugador para identificar cuando este dejo de escuchar los
     * mensajes del teclado desde el cliente. El jugador da listo e intenta iniciar el juego.
     * Se espera hasta que termine el partido. Luego se espera hasta que se dejen de escuchar teclas y
     * se elimina la sala una vez terminado el juego por completo.
     */
    private void iniciarJuego() {
        if (nonNull(salaActual)) {
            try {
                CountDownLatch escuchandoTeclas = new CountDownLatch(1);
                jugador.setEscuchandoTeclas(escuchandoTeclas);
                salaActual.darListo(jugador);
                salaActual.intentarIniciarElJuego();
                salaActual.cambiarEstado(true);
                escuchandoTeclas.await();
                saleDelPartido = true;
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Dada una sala a unirse busca esta entre las salas existentes. Si la encuentra y la
     * contraseña concuerda une al jugador y responde de manera correcta. En otro caso
     * devuelve el mensaje de error correspondiente.
     *
     * @param salaAUnirse
     * @return
     */
    private RespuestaAccionConSala unirseASala(Sala salaAUnirse) {
        for (Sala sala : this.sincronizadorDeSalas.obtenerSalas()) {
            if (sala.getNombreSala().equals(salaAUnirse.getNombreSala())) {
                if (isNull(sala.getContrasenia()) || sala.getContrasenia().equals(salaAUnirse.getContrasenia())) {
                    if (hayEspacio(sala)) {
                        if(!sala.estaInactiva()){
                            sala.agregarJugador(jugador);
                            this.salaActual = sala;
                        } else {
                            return new RespuestaAccionConSala(false, "Sala con juego iniciado");
                        }
                        return new RespuestaAccionConSala(true, this.sincronizadorDeSalas.obtenerSalas());
                    } else {
                        return generarRespuestaDeError("Sala llena");
                    }
                } else {
                    return generarRespuestaDeError("Contraseña invalida");
                }
            }
        }
        return generarRespuestaDeError("Sala no encontrada");
    }

    /**
     * Dado un mensaje de error, devuelve una respuesta con ese mensaje y con el campo accion
     * valida en false.
     *
     * @param mensaje
     * @return
     */
    private RespuestaAccionConSala generarRespuestaDeError(String mensaje) {
        return new RespuestaAccionConSala(false, mensaje);
    }

    /**
     * Dada una sala, verifica si todavía hay espacio para meter a otro jugador     *
     * @param sala
     * @return
     */
    private boolean hayEspacio(Sala sala) {
        return sala.getCantidadJugadores() - sala.getJugadoresEnSala() > 0;
    }

    /**
     * Dada una sala, devuelve si se puede crear
     *
     * @param salaACrear
     * @return
     */
    private RespuestaAccionConSala crearSala(Sala salaACrear) {
        if (salaACrear.getNombreSala().isEmpty()) {
            return new RespuestaAccionConSala(false, "El nombre no puede estar vacío");
        }

        if (!cantidadJugadoresValida(salaACrear)) {
            return new RespuestaAccionConSala(false, "La cantidad total de jugadores debe ser a lo sumo 4");
        }

        if (salaACrear.getTiempo() < 0){
            return new RespuestaAccionConSala(false, "El tiempo ingresado no es valido");
        }

        if (nombreUsado(salaACrear.getNombreSala())) {
            return new RespuestaAccionConSala(false, "Ya existe una sala con ese nombre");
        }

        if (!condicionesDeVictoriaValidas(salaACrear)){
            return new RespuestaAccionConSala(false, "Los parametros de victoria ingresados son invalidos");
        }

        this.sincronizadorDeSalas.agregarSala(salaACrear);
        this.salaActual = salaACrear;
        salaACrear.agregarJugador(jugador);
        return new RespuestaAccionConSala(true, this.sincronizadorDeSalas.obtenerSalas());
    }

    private boolean condicionesDeVictoriaValidas(Sala sala){
        return PUNTAJE.equals(sala.getModoDeJuego()) ? esPuntajeValido(sala) : esTiempoValido(sala);
    } //TODO CAMBIAR POR UN SWITCH SI AGREGAMOS MÁS MODOS DE JUEGO

    /**
     * Verfica que el puntaje ingresado sea un numero mayor a 0.
     * @param sala
     * @return
     */
    private boolean esPuntajeValido(Sala sala){
        return sala.getPuntajeAAlcanzar() > 0;
    }

    /**
     * Verifica que el tiempo ingresado sea un numero mayor a 0.
     * @return
     */
    private boolean esTiempoValido(Sala sala) {
       return sala.getTiempo() > 0;
    }

    /**
     * Devuelve false si la cantidad de jugadores no es válida
     *
     * @param salaACrear
     * @return
     */
    private boolean cantidadJugadoresValida(Sala salaACrear) {
        return salaACrear.getCantidadJugadores() + salaACrear.getCantidadIA() <= 4;
    }

    /**
     * Devuelve false si el nombre de la sala ya existe en la lista
     *
     * @param nombreSala
     * @return
     */
    private boolean nombreUsado(String nombreSala) {
        for (Sala sala : this.sincronizadorDeSalas.obtenerSalas()) {
            if (sala.getNombreSala().equals(nombreSala)) {
                return true;
            }
        }
        return false;
    }
}
