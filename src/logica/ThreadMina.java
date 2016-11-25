package logica;

import aplicacion.cliente.interfaz.ControladorPrincipalJuego;
import javafx.application.Platform;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 23-Nov-16 Tiempo: 1:53 PM
 */
public class ThreadMina extends Thread {
    public ControladorPrincipalJuego controladorJuego;
    public Mina mina;
    public boolean stop;


    public ThreadMina(ControladorPrincipalJuego pControladorJuego) {
        controladorJuego = pControladorJuego;
        mina = new Mina();
        mina.cantidadXCiclo = 50;
        mina.tiempo = 60;
    }

    public void run() {
        while (!stop) {
            try {
                sleep(mina.tiempo * 1000);
                controladorJuego.acero += mina.cantidadXCiclo;
                Platform.runLater(() -> {
                    controladorJuego.cantidadAcero.setText("" + controladorJuego.acero);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
