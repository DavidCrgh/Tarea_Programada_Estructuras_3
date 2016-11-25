package aplicacion.cliente.interfaz;

import logica.Matriz;
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
import logica.TiposConstrucciones;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

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

    public int codigoImagenUnidad;
    public int codigoUnidadActual;
    public TiposConstrucciones tipoUnidadActual;
    public int izquierda;
    public int abajo;
    public int diagonal;

    public Utilitario imagenes;

    public String contenidoChat;

    public Matriz matrizPropia;
    //public ArrayList<ArrayList<ImageView>> matrizImagenesPropia;
    //public ArrayList<ArrayList<ImageView>> matrizImagenesEnemiga;

    public ArrayList<String> enemigos;

    public Client cliente;

    public ThreadMina minaHilo;

    public int acero;
    public int dinero;

    public int indiceEnemigo;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        imagenes = new Utilitario();

        matrizPropia = new Matriz();

        dinero = 4000;
        cantidadDinero.setText("" + dinero);

        modoConstruccion = true;
        modoInicial = true;
        codigoImagenUnidad = 1;
        codigoUnidadActual = 1;
        tipoUnidadActual = TiposConstrucciones.MUNDO;
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

        precargarTableros();

        int limite = tableroPropio.getChildren().size();
        for (int i = 0; i < limite; i++) {
            ImageView imagenActual = (ImageView) tableroPropio.getChildren().get(i);
            final int j = i;

            imagenActual.setOnMouseClicked(event -> {
                if (modoInicial) {
                    pintarUnidad(imagenActual, j, codigoImagenUnidad);
                    int[] coordenadas = Utilitario.determinarCoordenadas(j);
                    matrizPropia.posicionarObjeto(tipoUnidadActual, coordenadas[0], coordenadas[1], 0);
                    modoInicial = false;
                    izquierda = 1;
                    abajo = 0;
                    diagonal = 0;
                    codigoImagenUnidad = 2;
                    codigoUnidadActual = 3;
                    tipoUnidadActual = TiposConstrucciones.FABRICA1x2;
                } else {
                    pintarUnidad(imagenActual, j, codigoImagenUnidad);
                    int[] coordenadas = Utilitario.determinarCoordenadas(j);
                    matrizPropia.posicionarObjeto(tipoUnidadActual, coordenadas[0], coordenadas[1], codigoUnidadActual);
                    modoConstruccion = false;
                }
            });

            imagenActual.setOnMouseEntered(event -> {
                if (modoConstruccion && modoInicial) {
                    if (areaDisponible(imagenActual, j)) {
                        pintarArea(imagenActual, imagenes.FONDOVERDE, j);
                    }
                } else if (modoConstruccion) {
                    if (areaDisponible(imagenActual, j)) {
                        pintarArea(imagenActual, imagenes.FONDOVERDE, j);
                    }
                }
            });
            imagenActual.setOnMouseExited(event -> {
                if (modoConstruccion && modoInicial) {
                    if (areaDisponible(imagenActual, j)) {
                        pintarArea(imagenActual, imagenes.FONDOCAFE, j);
                    }
                } else if (modoConstruccion) {
                    if (areaDisponible(imagenActual, j)) {
                        pintarArea(imagenActual, imagenes.FONDOCAFE, j);
                    }
                }
            });
        }
    }

    public void precargarTableros() {
        ArrayList<ArrayList<ImageView>> matrizImagenesPropia = new ArrayList<>();
        ArrayList<ArrayList<ImageView>> matrizImagenesEnemiga = new ArrayList<>();
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

        construirTablero(tableroPropio, matrizImagenesPropia);
        construirTablero(tableroEnemigo, matrizImagenesEnemiga);

        int x = ThreadLocalRandom.current().nextInt(0, 15);
        int y = ThreadLocalRandom.current().nextInt(0, 15);
        matrizPropia.tablero[x][y] = 7;
        ImageView viewHoyo = (ImageView) tableroPropio.getChildren().get((x * 15) + y);
        viewHoyo.setImage(imagenes.HOYONEGRO);

        while (true) {
            x = ThreadLocalRandom.current().nextInt(0, 15);
            y = ThreadLocalRandom.current().nextInt(0, 15);
            if (matrizPropia.tablero[x][y] == 0) {
                matrizPropia.tablero[x][y] = 7;
                viewHoyo = (ImageView) tableroPropio.getChildren().get((x * 15) + y);
                viewHoyo.setImage(imagenes.HOYONEGRO);
                break;
            }
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
