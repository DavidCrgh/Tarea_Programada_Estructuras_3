package aplicacion.cliente.interfaz;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 24-Nov-16 Tiempo: 5:02 PM
 */
public class Utilitario {
    //Fondos
    public Image FONDOCAFE = new Image(getClass().getResource("recursos\\FONDOCAFE.png").toExternalForm());
    public Image FONDOVERDE = new Image(getClass().getResource("recursos\\FONDOVERDE.png").toExternalForm());
    public Image FONDOROJO = new Image(getClass().getResource("recursos\\FONDOROJO.png").toExternalForm());
    public Image FONDONEGRO;

    //Mundo
    public Image MUNDOSI = new Image(getClass().getResource("recursos\\MUNDOSI.png").toExternalForm());
    public Image MUNDOSD = new Image(getClass().getResource("recursos\\MUNDOSD.png").toExternalForm());
    public Image MUNDOII = new Image(getClass().getResource("recursos\\MUNDOII.png").toExternalForm());
    public Image MUNDOID = new Image(getClass().getResource("recursos\\MUNDOID.png").toExternalForm());

    //Mercado
    //1x2
    public Image MERCADOL = new Image(getClass().getResource("recursos\\MERCADOL.png").toExternalForm());
    public Image MERCADOR = new Image(getClass().getResource("recursos\\MERCADOR.png").toExternalForm());
    //2x1
    public Image MERCADOU = new Image(getClass().getResource("recursos\\MERCADOU.png").toExternalForm());
    public Image MERCADOD = new Image(getClass().getResource("recursos\\MERCADOD.png").toExternalForm());

    //Armeria
    //1x2
    public Image ARMERIAL;
    public Image ARMERIAR;
    //2x1
    public Image ARMERIAU;
    public Image ARMERIAD;

    //Mina
    //1x2
    public Image MINAL;
    public Image MINAR;
    //2x1
    public Image MINAU;
    public Image MINAD;

    //Conector;
    public Image CONECTOR;
    //Hoyo Negro
    public Image HOYONEGRO = new Image(getClass().getResource("recursos\\HOYONEGRO.png").toExternalForm());

    public Utilitario() {

    }

    public static int[] determinarCoordenadas(int pos) {
        int coordenadas[] = new int[2];
        int i = 0;
        int j = 0;
        while (true) {
            if (i % 15 == 0) {
                coordenadas[0] += 1;
                j = 0;
            }
            if (i == pos) {
                break;
            }
            i++;
            j++;
        }
        coordenadas[0]--;
        coordenadas[1] = j;
        return coordenadas;
    }
}
