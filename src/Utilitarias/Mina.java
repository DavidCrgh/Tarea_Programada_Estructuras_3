package Utilitarias;

/**
 * Created by Bryan on 11/19/2016.
 */
public class Mina extends Thread{
    private int tamanno;
    private int costo;
    public long tiempo;
    public int cantidadXCiclo;
    //public int cantidadGenerada;

    public Mina() {
        tamanno = 0;
        costo = 0;
        //cantidadGenerada=0;
        cantidadXCiclo=50;
        tiempo=50;
    }

    public void setTiempo(long _tiempo){
        this.tiempo=_tiempo;
    }
/*
    public void run(){
        while(true){
            try {
                sleep(this.tiempo*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cantidadGenerada+=cantidadXCiclo;
        }
    }
    */

}
