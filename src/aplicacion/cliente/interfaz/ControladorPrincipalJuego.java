package aplicacion.cliente.interfaz;

import logica.ThreadMina;
import conexiones.client.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 8:20 PM
 */
public class ControladorPrincipalJuego implements Initializable {
    @FXML
    public TextArea mensajesChat;
    @FXML
    public TextField entradaChat;
    @FXML
    private GridPane tableroPropio;
    @FXML
    private GridPane tableroEnemigo;
    @FXML
    public Label cantidadAcero;
    @FXML
    public Label cantidadDinero;
    @FXML
    public Text enemigoActual;
    @FXML
    public Text nombrePropio;
    @FXML
    public Button botonComprar;
    @FXML
    public Button botonCancelar;
    @FXML
    public Button botonNegociar;
    @FXML
    public Button botonDerecho;
    @FXML
    public Button botonIzquierdo;
    @FXML
    public Button botonListo;

    public boolean modoInicial;
    public boolean modoConstruccion;
    public boolean modoAtaque;
    public int codigoUnidadActual;

    public int izquierda;
    public int abajo;
    public int diagonal;

    public Utilitario imagenes;

    public String contenidoChat;
    public ArrayList<ArrayList<ImageView>> matrizImagenesPropia;
    public ArrayList<ArrayList<ImageView>> matrizImagenesEnemiga;

    public ArrayList<String> enemigos;

    public Client cliente;

    public ThreadMina minaHilo;

    public int acero;
    public int dinero;

    public int indiceEnemigo;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        imagenes = new Utilitario();
        dinero = 4000;
        cantidadDinero.setText("" + dinero);

        modoConstruccion = true;
        modoInicial = true;
        codigoUnidadActual = 1;
        izquierda = 1;
        abajo = 15;
        diagonal = 16;

        botonComprar.setOnAction(event -> {
            //TODO
        });

        botonCancelar.setOnAction(event -> {
            modoConstruccion = false;
        });

        botonNegociar.setOnAction(event -> {
            //TODO
        });

        botonListo.setOnAction(event -> {
            modoConstruccion = true;
            izquierda = 0;
            abajo = 0;
            diagonal = 0;
        });

        botonIzquierdo.setOnAction(event -> {

            if (indiceEnemigo > 0) {
                indiceEnemigo--;
            }
            enemigoActual.setText(enemigos.get(indiceEnemigo));

        });
        botonDerecho.setOnAction(event -> {
            if (indiceEnemigo < enemigos.size() - 1) {

                indiceEnemigo++;
            }
            enemigoActual.setText(enemigos.get(indiceEnemigo));
        });

        //////////REVISAAAR/////////////
        /*botonComprar.setOnAction(event -> {
            minaHilo = new ThreadMina(this);
            minaHilo.start();
        });*/
        ////////////REVISAARAAR///////////

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
                cuadroImagenPropia.setImage(imagenes.FONDOCAFE);
                cuadroImagenEnemiga.setImage(imagenes.FONDOCAFE);
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
                    if (!(entradaChat.getText().equals(""))) {
                        contenidoChat += cliente.nombre + ": " + entradaChat.getText() + "\n";
                        try {
                            cliente.salidaDatos.writeInt(5);
                            cliente.salidaDatos.writeUTF(entradaChat.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mensajesChat.appendText(contenidoChat);
                        entradaChat.clear();
                        contenidoChat = "";

                        tableroPropio.setDisable(true);
                    }
                }
            }
        });

        int limite = tableroPropio.getChildren().size();
        for (int i = 0; i < limite; i++) {
            ImageView imagenActual = (ImageView) tableroPropio.getChildren().get(i);
            final int j = i;

            imagenActual.setOnMouseClicked(event -> {
                if (modoInicial) {
                    pintarUnidad(imagenActual, j, codigoUnidadActual);
                    modoInicial = false;
                    izquierda = 1;
                    abajo = 0;
                    diagonal = 0;
                    codigoUnidadActual = 2;
                } else {
                    pintarUnidad(imagenActual, j, codigoUnidadActual);
                    modoConstruccion = false;
                }
            });

            imagenActual.setOnMouseEntered(event -> {
                if (modoConstruccion && modoInicial) {
                    pintarArea(imagenActual, imagenes.FONDOVERDE, j);
                } else if (modoConstruccion) {
                    if (areaDisponible(imagenActual, j)) {
                        pintarArea(imagenActual, imagenes.FONDOVERDE, j);
                    }
                }
            });
            imagenActual.setOnMouseExited(event -> {
                if (modoConstruccion && modoInicial) {
                    pintarArea(imagenActual, imagenes.FONDOCAFE, j);
                } else if (modoConstruccion) {
                    if (areaDisponible(imagenActual, j)) {
                        pintarArea(imagenActual, imagenes.FONDOCAFE, j);
                    }
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

    public void precargarInterfaz(ArrayList<String> _enemigos) {
        nombrePropio.setText(cliente.nombre);
        enemigos = _enemigos;
        enemigoActual.setText(enemigos.get(0));
    }

    public void pintarArea(ImageView imageView, Image imagen, int j) {
        ImageView imagenIzquierda = (ImageView) tableroPropio.getChildren().get(j + izquierda);
        ImageView imagenAbajo = (ImageView) tableroPropio.getChildren().get(j + abajo);
        ImageView imagenDiagonal = (ImageView) tableroPropio.getChildren().get(j + diagonal);
        imageView.setImage(imagen);
        imagenIzquierda.setImage(imagen);
        imagenAbajo.setImage(imagen);
        imagenDiagonal.setImage(imagen);
    }

    public boolean areaDisponible(ImageView imagenActual, int j) {
        ImageView imagenIzquierda = (ImageView) tableroPropio.getChildren().get(j + izquierda);
        ImageView imagenAbajo = (ImageView) tableroPropio.getChildren().get(j + abajo);
        ImageView imagenDiagonal = (ImageView) tableroPropio.getChildren().get(j + diagonal);
        return (imagenActual.getImage().equals(imagenes.FONDOCAFE) && imagenAbajo.getImage().equals(imagenes.FONDOCAFE) &&
                imagenIzquierda.getImage().equals(imagenes.FONDOCAFE) && imagenDiagonal.getImage().equals(imagenes.FONDOCAFE)) ||
                (imagenActual.getImage().equals(imagenes.FONDOVERDE) && imagenAbajo.getImage().equals(imagenes.FONDOVERDE) &&
                        imagenIzquierda.getImage().equals(imagenes.FONDOVERDE) && imagenDiagonal.getImage().equals(imagenes.FONDOVERDE));
    }

    public void pintarUnidad(ImageView imagenActual, int j, int codigoUnidad) {
        ImageView imagenIzquierda = (ImageView) tableroPropio.getChildren().get(j + izquierda);
        ImageView imagenAbajo = (ImageView) tableroPropio.getChildren().get(j + abajo);
        ImageView imagenDiagonal = (ImageView) tableroPropio.getChildren().get(j + diagonal);

        switch (codigoUnidad) {
            case 1: //Mundo
                imagenActual.setImage(imagenes.MUNDOSI);
                imagenIzquierda.setImage(imagenes.MUNDOSD);
                imagenAbajo.setImage(imagenes.MUNDOII);
                imagenDiagonal.setImage(imagenes.MUNDOID);
                break;
            case 2:
                imagenActual.setImage(imagenes.MERCADOL);
                imagenIzquierda.setImage(imagenes.MERCADOR);
        }
    }
}
