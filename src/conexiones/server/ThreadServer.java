package conexiones.server;

import logica.Matriz;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
    public boolean listo;

    public boolean stop;

    public ThreadServer(Socket _socketClient, Server _server, int _numeroJugador) {
        this.socketClient = _socketClient;
        this.server = _server;
        this.numeroJugador = _numeroJugador;
        enemigos = new ArrayList<>();
        stop = false;
        listo = false;
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
            for (ThreadServer hilo : server.hilos) {
                actualizarEnemigos();
                //hilo.salidaObjetos.writeInt(9);//TODO cambiar -1 por el código para hacer que los clientes reciban al nuevo enemigo
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
                    case 2:
                        salidaDatos.writeInt(7);
                        salidaDatos.writeInt(server.hilos.size());
                        for (int i = 0; i < server.hilos.size(); i++) {
                            salidaDatos.writeUTF(server.hilos.get(i).nombreJugador);
                        }
                        break;
                    case 3:
                        salidaDatos.writeInt(1);
                        salidaDatos.writeInt(enemigos.size());
                        for (int i = 0; i < enemigos.size(); i++) {
                            salidaDatos.writeUTF(enemigos.get(i).nombreJugador);
                        }
                        break;
                    case 4:
                        salidaDatos.writeInt(4);
                        salidaDatos.writeInt(server.hilos.size());
                        for (int i = 0; i < server.hilos.size(); i++) {
                            salidaDatos.writeUTF(server.hilos.get(i).nombreJugador);
                        }
                        break;
                    case 5:
                        String chat = entradaDatos.readUTF();
                        for (ThreadServer enemigoJugador : enemigos) {
                            enemigoJugador.salidaDatos.writeInt(2);
                            enemigoJugador.salidaDatos.writeUTF(nombreJugador);
                            enemigoJugador.salidaDatos.writeUTF(chat);
                        }
                        break;
                    case 6:
                        listo = true;
                        boolean aux = true;
                        for (int i = 0; i < enemigos.size(); i++) {
                            aux = aux & enemigos.get(i).listo;
                        }
                        if (aux) {
                            for (int i = 0; i < server.hilos.size(); i++) {
                                server.hilos.get(i).salidaDatos.writeInt(8);
                            }
                            int randomNum = ThreadLocalRandom.current().nextInt(0, server.hilos.size());
                            pasarTurno(server.hilos.get(randomNum).nombreJugador);
                        }
                        break;
                    case 7:
                        pasarTurno(nombreJugador);
                        break;
                    case 8:
                        Matriz matriz = (Matriz) entradaObjetos.readObject();
                        for (ThreadServer hilo : enemigos) {
                            //hilo.salidaDatos.writeInt();
                            hilo.salidaObjetos.writeObject(matriz);
                        }
                        break;
                }
            } catch (Exception e) {
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
        ArrayList<ThreadServer> auxThreads = (ArrayList<ThreadServer>)server.hilos.clone();
        auxThreads.remove(this);
        this.enemigos=auxThreads;
        System.out.println("Enemigos de: "+this.nombreJugador);
        for (ThreadServer enemigo : this.enemigos) {
            System.out.println(enemigo.nombreJugador+"\n");
        }
    }

    public String[] obtenerNombres() {

        String arregloNombres[] = new String[4];
        int i = 0;
        for (ThreadServer hilos : server.hilos) {

            arregloNombres[i] = hilos.nombreJugador;
            i++;

        }
        return arregloNombres;
    }

    public void pasarTurno(String actual) {
        int j = 0;
        for (int i = 0; i < server.hilos.size(); i++) {
            if (server.hilos.get(i).nombreJugador.equals(actual)) {
                j = i;
                break;
            }
        }

        try {
            if ((j + 1) >= server.hilos.size()) {
                ThreadServer hilo = server.hilos.get(0);
                hilo.salidaDatos.writeInt(9);
                for (ThreadServer hiloActual : hilo.enemigos) {
                    hiloActual.salidaDatos.writeInt(10);
                    hiloActual.salidaDatos.writeUTF(hilo.nombreJugador);
                }
            } else {
                ThreadServer hilo = server.hilos.get(j + 1);
                hilo.salidaDatos.writeInt(9);
                for (ThreadServer hiloActual : hilo.enemigos) {
                    hiloActual.salidaDatos.writeInt(10);
                    hiloActual.salidaDatos.writeUTF(hilo.nombreJugador);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
