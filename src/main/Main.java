package main;

import servidor.Servidor;

import static main.SesionSingleton.getSessionFactory;

public class Main {

    public static void main(String[] args) {
        // lo primero que hago antes del levantar el servidor es crear el session factory
        // para evitar penalizar al primer jugador en loguear con la espera de creacion de este
        getSessionFactory();
        new Servidor(12000).start();
    }
}
