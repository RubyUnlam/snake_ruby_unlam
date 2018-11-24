package main;

import servidor.Servidor;

public class Main {

    public static void main(String[] args) {
        new Servidor(12000).start();
    }
}
