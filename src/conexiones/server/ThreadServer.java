package conexiones.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 12:37 PM
 */
public class ThreadServer extends Thread implements Serializable {
    public Socket socketClient;
    //Objetos de IO
    public DataInputStream entradaDatos;
    public DataOutputStream salidaDatos;
    public ObjectInputStream entradaObjetos;
    public ObjectOutputStream salidaObjetos;
    public Server server;

    public ArrayList<ThreadServer> enemigos; //.size() siempre 1 >= y <=3
    public int numeroJugador; //id del jugador al que este thread atiende
    public String nombreJugador;

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
            salidaObjetos = new ObjectOutputStream(socketClient.getOutputStream());
            salidaDatos = new DataOutputStream(socketClient.getOutputStream());
            salidaObjetos.flush();
            salidaDatos.flush();
            entradaObjetos = new ObjectInputStream(socketClient.getInputStream());
            entradaDatos = new DataInputStream(socketClient.getInputStream());
            nombreJugador = entradaDatos.readUTF();
            System.out.println(nombreJugador);
            actualizarEnemigos();
            for (ThreadServer hilo : server.hilos) {
                hilo.salidaObjetos.writeInt(-1);//TODO cambiar -1 por el código para hacer que los clientes reciban al nuevo enemigo
                //hilo.salidaObjetos.writeObject(enemigos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int opcion = 0;
        int numeroJugadores = 0;

        while (!stop) {
            try {
                sleep(100);
                opcion = entradaDatos.readInt();

                switch (opcion) {
                    case 1:
                        salidaObjetos.writeObject(new ArrayList<String>());
                        break;
                    default://TODO agregar los casos verdaderos
                        System.out.println("Hilo servidor de " + " corriendo.");
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
