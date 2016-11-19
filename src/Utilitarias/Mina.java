package Utilitarias;

/**
 * Created by Bryan on 11/19/2016.
 */
public class Mina extends Thread{
    private int tamanno;
    private int costo;
    private long tiempo;
    private int cantidadXCiclo;
    public int cantidadGenerada;

    public Mina(int _costo, int _tamanno){
        tamanno=_tamanno;
        costo=_costo;
        cantidadGenerada=0;
        cantidadXCiclo=50;
        tiempo=50;
    }

    public void setTiempo(long _tiempo){
        this.tiempo=_tiempo;
    }

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

}
