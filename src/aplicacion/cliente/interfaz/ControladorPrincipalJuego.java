package aplicacion.cliente.interfaz;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 8:20 PM
 */
public class ControladorPrincipalJuego implements Initializable {
    @FXML
    private TextArea mensajesChat;
    @FXML
    private TextField entradaChat;
    @FXML
    private GridPane tableroPropio;
    @FXML
    private GridPane tableroEnemigo;

    public String contenidoChat;
    public ArrayList<ArrayList<ImageView>> matrizImagenesPropia;
    public ArrayList<ArrayList<ImageView>> matrizImagenesEnemiga;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        contenidoChat = "";
        mensajesChat.setEditable(false);
        mensajesChat.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                mensajesChat.setScrollTop(Double.MAX_VALUE);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Codigo ejemplo para cargar las matrices de ImageViews, no es final, hay que encapsularlo en una funcion
        matrizImagenesPropia = new ArrayList<>();
        matrizImagenesEnemiga = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ArrayList<ImageView> filaActualPropia = new ArrayList<>();
            ArrayList<ImageView> filaActualEnemiga = new ArrayList<>();
            matrizImagenesPropia.add(filaActualPropia);
            matrizImagenesEnemiga.add(filaActualEnemiga);
            for (int j = 0; j < 15; j++) {
                ImageView cuadroImagenPropia = new ImageView();
                ImageView cuadroImagenEnemiga = new ImageView();
                Image imagen = new Image(getClass().getResource("recursos\\fondoTierra.png").toExternalForm());
                cuadroImagenPropia.setImage(imagen);
                cuadroImagenEnemiga.setImage(imagen);
                filaActualPropia.add(cuadroImagenPropia);
                filaActualEnemiga.add(cuadroImagenEnemiga);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        construirTablero(tableroPropio, matrizImagenesPropia);
        construirTablero(tableroEnemigo, matrizImagenesEnemiga);

        entradaChat.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    contenidoChat += "David: " + entradaChat.getText() + "\n";
                    mensajesChat.setText(contenidoChat);
                    mensajesChat.appendText("");
                    entradaChat.clear();
                }
            }
        });

        int limite = tableroPropio.getChildren().size();
        for (int i = 0; i < limite; i++) {
            int j = i;
            tableroPropio.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(j);
                }
            });
        }
    }

    public void construirTablero(GridPane tableroCargado, ArrayList<ArrayList<ImageView>> imagenes) {
        tableroCargado.getChildren().clear();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                tableroCargado.add(imagenes.get(i).get(j), j, i);
            }
        }
    }
}
