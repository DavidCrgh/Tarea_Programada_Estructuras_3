package logica;

import aplicacion.cliente.interfaz.ControladorPrincipalJuego;

import java.util.Random;

/**
 * Created by Bryan on 11/29/2016.
 */
public class ThreadTemplo extends Thread{
    public ControladorPrincipalJuego controladorJuego;
    private boolean stop;

    public ThreadTemplo(ControladorPrincipalJuego controlador){
        this.controladorJuego=controlador;
        stop=false;
    }

    public void run(){
        while(!stop){
            try{
                sleep(30000);
                controladorJuego.enviarMensaje();
                controladorJuego.juego.comodin=true;
                Random random = new Random();
                controladorJuego.juego.disparosRecibidos=0;
                while(controladorJuego.juego.disparosRecibidos<random.nextInt()*12){
                    sleep(1);
                }
                controladorJuego.juego.comodin=false;
                controladorJuego.juego.disparosRecibidos=0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
