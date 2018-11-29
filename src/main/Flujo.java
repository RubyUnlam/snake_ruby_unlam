package main;

import com.google.gson.Gson;
import servidor.ManejadorMovimiento;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Flujo extends Thread { //TODO PENSAR EL NOMBRE PARA ESTO

    private Socket conexion;
    private SincronizadorDeSalas sincronizadorDeSalas;
    private Gson gson = new Gson();
    private Jugador jugador;
    private Sala salaActual;
    private boolean saleDelPartido = false;
    private CountDownLatch countDownLatch;

    public Flujo(Socket conexion, SincronizadorDeSalas sincronizadorDeSalas) {
        this.conexion = conexion;
        this.sincronizadorDeSalas = sincronizadorDeSalas;
    }

    @Override
    public void run() {

        Serpiente serpiente = new Serpiente(Color.BLUE);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(conexion.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(conexion.getInputStream());
            RegistroUsuario registroUsuario = null;
            Boolean sesionIniciada = false;

            //------------------------REGISTRO-------------------------------//

            Usuario usuario;
            do {
                usuario = gson.fromJson(dataInputStream.readUTF(), Usuario.class);
                if (isNull(usuario.getEmail())) {
                    boolean registroValido = Sesion.iniciarSesion(usuario);
                    if (!registroValido) {
                        registroUsuario = new RegistroUsuario("login invalido", registroValido);
                    } else {
                        registroUsuario = new RegistroUsuario("", registroValido);
                        sesionIniciada = true;
                    }
                } else {
                    registroUsuario = Sesion.registrarUsuario(usuario.getNombreUsuario(), usuario.getContrasenia(), usuario.getEmail());
                }
                dataOutputStream.writeUTF(gson.toJson(registroUsuario));
            } while (!sesionIniciada);

            //------------------------REGISTRO-------------------------------//

            //------------------------MENU-------------------------------//
            this.jugador = new Jugador(usuario.getNombreUsuario(), conexion);


            String opcion = dataInputStream.readUTF();

            while (!"salir".equals(opcion)) { //TODO CONSTANTE
                switch (opcion) {
                    case "ver_salas":
                        dataOutputStream.writeUTF(gson.toJson(new RespuestaAccionConSala(true, sincronizadorDeSalas.obtenerSalas())));
                        break;
                    case "crear_sala":
                        String json = dataInputStream.readUTF();
                        System.out.println(json);
                        Sala salaACrear = gson.fromJson(json, Sala.class);
                        RespuestaAccionConSala respuesta = crearSala(salaACrear);
                        dataOutputStream.writeUTF(gson.toJson(respuesta));
                        break;
                    case "unirse_a_sala":
                        String json1 = dataInputStream.readUTF();
                        System.out.println(json1);
                        Sala salaAUnirse = gson.fromJson(json1, Sala.class);
                        RespuestaAccionConSala respuestaAccionConSala = unirseASala(salaAUnirse);
                        dataOutputStream.writeUTF(gson.toJson(respuestaAccionConSala));
                        break;
                    case "salir_de_sala":
                        salaActual.removerJugador(jugador);
                        salaActual = null;
                        if (!saleDelPartido) {
                            dataOutputStream.writeUTF("");
                        } else {
                            System.out.println("acaba de salir de un partido");
                        }
                        saleDelPartido = false;
                        break;
                    case "jugar":
                        if (nonNull(salaActual)) {
                            try {
                                countDownLatch = new CountDownLatch(1);
                                jugador.setCountDownLatch(countDownLatch);
                                jugador.setEstaListo(true);
                                salaActual.darListo();
                                salaActual.iniciar();
                                salaActual.obtenerCuentaRegresiva().await();
                                System.out.println("Esperando fin de movimientos");
                                countDownLatch.await();
                                sincronizadorDeSalas.eliminarSala(salaActual);
                                saleDelPartido = true;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        System.out.println(opcion);
                        break;
                }
                System.out.println("VOLVI AL MENU");
                opcion = dataInputStream.readUTF();
            }

            System.out.println("SALI FUERA DEL MENU");

            //------------------------MENU-------------------------------//


        } catch (IOException e) {
            e.printStackTrace();
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
                        sala.agregarJugador(jugador);
                        this.salaActual = sala;
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
     * Dada una sala, verifica si todavía hay espacio para meter a otro jugador
     *
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

        if (nombreUsado(salaACrear.getNombreSala())) {
            return new RespuestaAccionConSala(false, "Ya existe una sala con ese nombre");
        }
        this.sincronizadorDeSalas.agregarSala(salaACrear);
        this.salaActual = salaACrear;
        salaACrear.crearCuentaRegresiva();
        salaACrear.agregarJugador(jugador);
        return new RespuestaAccionConSala(true, this.sincronizadorDeSalas.obtenerSalas());
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
