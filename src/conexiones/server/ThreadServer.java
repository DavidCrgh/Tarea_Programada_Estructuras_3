package conexiones.server;

import conexiones.client.Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 12:37 PM
 */
public class ThreadServer extends Thread {
    public Socket socketClient;
    //Objetos de IO
    public DataInputStream entradaDatos;
    public ObjectInputStream entradaObjetos;
    public DataOutputStream salidaDatos;
    public ObjectOutputStream salidaObjetos;
    public Server server;

    public Client client;
    public ArrayList<ThreadServer> enemigos; //.size() siempre 1 >= y <=3
    public int numeroJugador; //id del jugador al que este thread atiende

    public boolean stop;

    public ThreadServer(Socket _socketClient, Server _server, int _numeroJugador) {
        this.socketClient = _socketClient;
        this.server = _server;
        this.numeroJugador = _numeroJugador;
        enemigos = new ArrayList<>();
        stop = false;
    }

    public void run() {
        try {
            entradaDatos = new DataInputStream(client.client.getInputStream());
            entradaObjetos = new ObjectInputStream(client.client.getInputStream());
            salidaDatos = new DataOutputStream(client.client.getOutputStream());
            salidaObjetos = new ObjectOutputStream(client.client.getOutputStream());
            client = (Client) entradaObjetos.readObject();
            actualizarEnemigos();
            for (ThreadServer hilo : server.hilos) {
                hilo.salidaDatos.writeInt(-1);//TODO cambiar -1 por el codigo para hacer que los clientes reciban al nuevo enemigo
                //hilo.salidaObjetos.writeObject(enemigos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int opcion = 0;
        int numeroJugadores = 0;

        while (!stop) {
            try {
                sleep(100);
                opcion = entradaDatos.readInt();
                switch (opcion) {
                    default://TODO agregar los casos verdaderos
                        System.out.println("Hilo servidor de " + client.nombre + " corriendo.");
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void actualizarEnemigos() {
        for (ThreadServer enemigo : server.hilos) {
            if (enemigo != this & !enemigos.contains(enemigo)) {
                enemigos.add(enemigo);
                enemigo.actualizarEnemigos();
            }
        }
    }
}
