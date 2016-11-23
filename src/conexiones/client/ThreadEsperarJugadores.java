package conexiones.client;

import aplicacion.cliente.interfaz.ControladorEsperarJugadores;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 23-Nov-16 Tiempo: 4:23 PM
 */
public class ThreadEsperarJugadores extends Thread {
    ControladorEsperarJugadores controladorEspera;
    public boolean stop;
    public boolean pause;


    public ThreadEsperarJugadores(ControladorEsperarJugadores pControlador) {
        controladorEspera = pControlador;
    }

    public void run() {
        while (!stop) {
            while (pause) {
                synchronized (this) {
                    try {
                        controladorEspera.cliente.salidaDatos.writeInt(4);
                        sleep(5000);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

            synchronized (this) {
                while (controladorEspera.botonComenzar.isDisable()) {
                    try {
                        controladorEspera.cliente.salidaDatos.writeInt(2);
                        sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pause = true;
            }
        }
        System.out.println("Se murio el hilo de un jugadorsh");
    }
}
