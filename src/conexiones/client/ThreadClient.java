package conexiones.client;

import java.io.IOException;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 1:08 PM
 */
public class ThreadClient extends Thread {
    public boolean stop;
    public Client client;
    //TODO ref a ventana va aqui

    public ThreadClient(Client _client /*_ref a ventana*/) {
        this.client = _client;
        //ref a ventana = _ref a ventana
    }

    public void run() {
        int opcion = 0;

        while (!stop) {
            try {
                sleep(100);
                opcion = client.entradaDatos.readInt();
                switch (opcion) {
                    default:
                        System.out.println("ThreaClient de " + client.nombre + " corriendo.");//TODO cambiar por los casos de verdad
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Servidor desconectado.");
    }
}
