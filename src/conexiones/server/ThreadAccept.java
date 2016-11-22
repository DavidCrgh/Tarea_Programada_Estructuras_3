package conexiones.server;

import java.io.IOException;
import java.net.Socket;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 12:41 PM
 */
public class ThreadAccept extends Thread {
    public boolean stop;
    public int numeroJugadores;
    public Server servidor;

    public ThreadAccept(Server _servidor) {
        stop = false;
        numeroJugadores = 0;
        this.servidor = _servidor;
    }

    public void run() {
        Socket socketTemporal;
        ThreadServer hiloTemporal;
        while (!stop) {
            try {
                socketTemporal = servidor.server.accept();
                hiloTemporal = new ThreadServer(socketTemporal, servidor, numeroJugadores + 1);
                servidor.clientes.add(socketTemporal);
                servidor.hilos.add(hiloTemporal);
                hiloTemporal.start();
                numeroJugadores++;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (numeroJugadores == 4) {
                stop = true;
            }
        }
    }
}
