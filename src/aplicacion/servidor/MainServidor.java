package aplicacion.servidor;

import conexiones.server.Server;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 2:38 PM
 */
public class MainServidor {
    public static void main(String[] args) {
        Server servidor = new Server();
        servidor.runServer();
    }
}
