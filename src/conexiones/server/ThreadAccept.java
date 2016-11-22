package conexiones.server;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 12:41 PM
 */
public class ThreadAccept extends Thread {
    public boolean stop;
    public Server servidor;

    public ThreadAccept(Server _servidor) {
        stop = false;
        this.servidor = _servidor;
    }

    public void run() {
        while (!stop) {

        }
    }
}
