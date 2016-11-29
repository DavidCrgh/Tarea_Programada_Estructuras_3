package aplicacion.cliente.interfaz;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 24-Nov-16 Tiempo: 5:02 PM
 */
public class Utilitario {
    //Fondos
    public Image FONDOCAFE = new Image(getClass().getResource("recursos\\FONDOCAFE.png").toExternalForm());
    public Image FONDOVERDE = new Image(getClass().getResource("recursos\\FONDOVERDE.png").toExternalForm());
    public Image FONDOROJO = new Image(getClass().getResource("recursos\\FONDOROJO.png").toExternalForm());
    public Image FONDONEGRO = new Image(getClass().getResource("recursos\\FONDONEGRO.png").toExternalForm());

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
    public Image ARMERIAL = new Image(getClass().getResource("recursos\\ARMERIAL.png").toExternalForm());
    public Image ARMERIAR = new Image(getClass().getResource("recursos\\ARMERIAR.png").toExternalForm());
    //2x1
    public Image ARMERIAU = new Image(getClass().getResource("recursos\\ARMERIAU.png").toExternalForm());
    public Image ARMERIAD = new Image(getClass().getResource("recursos\\ARMERIAD.png").toExternalForm());

    //Mina
    //1x2
    public Image MINAL = new Image(getClass().getResource("recursos\\MINAL.png").toExternalForm());
    public Image MINAR = new Image(getClass().getResource("recursos\\MINAR.png").toExternalForm());
    //2x1
    public Image MINAU = new Image(getClass().getResource("recursos\\MINAU.png").toExternalForm());
    public Image MINAD = new Image(getClass().getResource("recursos\\MINAD.png").toExternalForm());

    //Conector;
    public Image CONECTOR = new Image(getClass().getResource("recursos\\CONECTOR.png").toExternalForm());
    //Hoyo Negro
    public Image HOYONEGRO = new Image(getClass().getResource("recursos\\HOYONEGRO.png").toExternalForm());

    //Templo
    public Image TEMPLOL = new Image(getClass().getResource("recursos\\TEMPLOL.png").toExternalForm());
    public Image TEMPLOR = new Image(getClass().getResource("recursos\\TEMPLOR.png").toExternalForm());

    public Map<Image, String> tablaValores;


    public Utilitario() {
        tablaValores = new HashMap<>();
        tablaValores.put(FONDOCAFE, "0");
        tablaValores.put(FONDONEGRO, "1");
        tablaValores.put(MUNDOSI, "2");
        tablaValores.put(MUNDOSD, "3");
        tablaValores.put(MUNDOII, "4");
        tablaValores.put(MUNDOID, "5");
        tablaValores.put(MERCADOL, "6");
        tablaValores.put(MERCADOR, "7");
        tablaValores.put(MERCADOU, "8");
        tablaValores.put(MERCADOD, "9");
        tablaValores.put(ARMERIAL, "10");
        tablaValores.put(ARMERIAR, "11");
        tablaValores.put(ARMERIAU, "12");
        tablaValores.put(ARMERIAD, "13");
        tablaValores.put(MINAL, "14");
        tablaValores.put(MINAR, "15");
        tablaValores.put(MINAU, "16");
        tablaValores.put(MINAD, "17");
        tablaValores.put(CONECTOR, "18");
        tablaValores.put(TEMPLOL, "19");
        tablaValores.put(TEMPLOL, "20");
    }

    public Image obtenerKey(String valor) {
        for (Map.Entry entry : tablaValores.entrySet()) {
            if (entry.getValue().equals(valor)) {
                Image imagen = (Image) entry.getKey();
                return imagen;
            }
        }
        return null;
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
