package conexiones.server;

import logica.Armas.Armas;
import logica.Coordenadas;
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
    public Matriz matrizDisconexa;
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
                        boolean todosListos = true;
                        for (int i = 0; i < enemigos.size(); i++) {
                            todosListos = todosListos & enemigos.get(i).listo;
                        }
                        if (todosListos) {
                            int randomNum = ThreadLocalRandom.current().nextInt(0, server.hilos.size());
                            pasarTurno(server.hilos.get(randomNum).nombreJugador);
                            for (int i = 0; i < server.hilos.size(); i++) {
                                server.hilos.get(i).salidaDatos.writeInt(8);
                            }
                            for (int i = 0; i < server.hilos.size(); i++) {
                                server.hilos.get(i).salidaDatos.writeInt(12);
                            }
                        }
                        break;
                    case 7:
                        pasarTurno(nombreJugador);
                        break;
                    case 8:
                        Matriz matriz = (Matriz) entradaObjetos.readObject();
                        matrizDisconexa = matriz;
                        break;
                    case 9:
                        ArrayList<Matriz> matrices = new ArrayList<>();
                        for (int i = 0; i < enemigos.size(); i++) {
                            matrices.add(enemigos.get(i).matrizDisconexa);
                        }
                        salidaDatos.writeInt(11);
                        salidaObjetos.writeObject(matrices);
                        break;
                    case 10:
                        Coordenadas coordenadas = (Coordenadas) entradaObjetos.readObject();
                        String nombreEnemigo = entradaDatos.readUTF();
                        Armas tipoArma = (Armas) entradaObjetos.readObject();

                        for (int i = 0; i < enemigos.size(); i++) {
                            if (enemigos.get(i).nombreJugador.equals(nombreEnemigo)) {
                                ThreadServer hilo = enemigos.get(i);
                                hilo.salidaDatos.writeInt(13);
                                hilo.salidaObjetos.writeObject(coordenadas);
                                hilo.salidaObjetos.writeObject(tipoArma);
                                break;
                            }
                        }
                        break;
                    case 11:
                        String message = "Jugador protegido por comodin";
                        for (ThreadServer enemigoJugador : enemigos) {
                            enemigoJugador.salidaDatos.writeInt(2);
                            enemigoJugador.salidaDatos.writeUTF(nombreJugador);
                            enemigoJugador.salidaDatos.writeUTF(message);
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
