package conexiones.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 12:31 PM
 */
public class Server {
    public ArrayList<Socket> clientes;
    public ArrayList<ThreadServer> hilos;
    public ServerSocket server;

    public Server() {
        clientes = new ArrayList<>();
        hilos = new ArrayList<>();
    }

    public void runServer() {
        try {
            server = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al levantar el servidor.");
        }

        ThreadAccept hiloPeticiones = new ThreadAccept(this);
        hiloPeticiones.start();

        while (true) {

        }
    }
}
