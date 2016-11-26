package aplicacion.cliente.interfaz;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logica.*;
import conexiones.client.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
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
    @FXML
    public Tab tabUnidades;
    @FXML
    public Tab tabMercado;
    @FXML
    public Tab tabArmeria;
    @FXML
    public TableView tablaUnidades;
    @FXML
    public TableView tablaMercado;
    @FXML
    public TableView tablaArmeria;
    @FXML
    public TableColumn nombreUnidad;
    @FXML
    public TableColumn costoUnidad;
    @FXML
    public TableColumn nombreArma;
    @FXML
    public TableColumn costoArma;

    public Juego juego;

    public Utilitario imagenes;

    public String contenidoChat;

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

        juego = new Juego();

        botonComprar.setOnAction(event -> {
            if (tabUnidades.isSelected()) {
                System.out.println("Tab Unidades seleccionada");
                InfoTiendas info = (InfoTiendas) tablaUnidades.getSelectionModel().getSelectedItem();
                juego.activarModoConstruccion(info);
            } else if (tabArmeria.isSelected()) {
                System.out.println("Tab Armeria seleccionada");
            }
        });

        botonCancelar.setOnAction(event -> {
            juego.desactivarModoConstruccion();
            //juego.activarModoConstruccion();
            //modoConstruccion = false;
        });

        botonNegociar.setOnAction(event -> {
            //TODO
        });

        botonListo.setOnAction(event -> {
            try {
                cliente.salidaDatos.writeInt(6);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*modoConstruccion = true;
            izquierda = 0;
            abajo = 0;
            diagonal = 0;*/
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
                if (juego.modoInicial) {
                    pintarUnidad(imagenActual, j, juego.codigoImagenUnidad);
                    juego.construirUnidad(j);
                    juego.desactivarModoInicial(j);
                    juego.matrizPropia.imprimirMatriz();
                    modificarDinero(2000);
                    tablaUnidades.getSelectionModel().select(2);
                } else if (juego.modoConstruccion) {
                    InfoTiendas info = (InfoTiendas) tablaUnidades.getSelectionModel().getSelectedItem();
                    if (dinero >= info.obtenerCostoInt()) {
                        modificarDinero(0 - info.obtenerCostoInt());
                        pintarUnidad(imagenActual, j, juego.codigoImagenUnidad);
                        juego.construirUnidad(j);
                        juego.desactivarModoConstruccion();
                        juego.matrizPropia.imprimirMatriz();
                    }
                } else if (juego.modoConexion) {
                    int[] coordenadas = Utilitario.determinarCoordenadas(j);
                    juego.conectarUnidades(coordenadas[0], coordenadas[1]);
                    actualizarToolTipConectores();
                    System.out.println("Conexion lograda");
                } else if (imagenActual.getImage().equals(imagenes.CONECTOR)) {
                    int[] coordenadas = Utilitario.determinarCoordenadas(j);
                    juego.coordenadasConector[0] = coordenadas[0];
                    juego.coordenadasConector[1] = coordenadas[1];
                    juego.modoConexion = true;
                    System.out.println("Modo conexion activado");
                }
            });

            imagenActual.setOnMouseEntered(event -> {
                if (juego.modoConstruccion && juego.modoInicial) {
                    if (areaDisponible(imagenActual, j)) {
                        pintarArea(imagenActual, imagenes.FONDOVERDE, j);
                    }
                } else if (juego.modoConstruccion) {
                    if (areaDisponible(imagenActual, j)) {
                        InfoTiendas info = (InfoTiendas) tablaUnidades.getSelectionModel().getSelectedItem();
                        if (dinero >= info.obtenerCostoInt()) {
                            pintarArea(imagenActual, imagenes.FONDOVERDE, j);
                        } else {
                            pintarArea(imagenActual, imagenes.FONDOROJO, j);
                        }
                    }
                }
            });
            imagenActual.setOnMouseExited(event -> {
                if (juego.modoConstruccion && juego.modoInicial) {
                    if (areaDisponible(imagenActual, j)) {
                        pintarArea(imagenActual, imagenes.FONDOCAFE, j);
                    }
                } else if (juego.modoConstruccion) {
                    if (areaDisponible(imagenActual, j)) {
                        pintarArea(imagenActual, imagenes.FONDOCAFE, j);
                    }
                }
            });
        }
        tablaUnidades.setEditable(true);
        configurarTabla(nombreUnidad, costoUnidad);
        configurarTabla(nombreArma, costoArma);
        construirTabla(tablaUnidades, construirTablaUnidades());
    }

    public void empezarJuego() {
        botonListo.setText("Saltar turno");
        System.out.println("Juego iniciado!");
    }

    public void modificarDinero(int cantidad) {
        dinero += cantidad;
        cantidadDinero.setText("" + dinero);
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
        juego.matrizPropia.tablero[x][y] = 7;
        ImageView viewHoyo = (ImageView) tableroPropio.getChildren().get((x * 15) + y);
        viewHoyo.setImage(imagenes.HOYONEGRO);

        while (true) {
            x = ThreadLocalRandom.current().nextInt(0, 15);
            y = ThreadLocalRandom.current().nextInt(0, 15);
            if (juego.matrizPropia.tablero[x][y] == 0) {
                juego.matrizPropia.tablero[x][y] = 7;
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
        ImageView imagenIzquierda = (ImageView) tableroPropio.getChildren().get(j + juego.izquierda);
        ImageView imagenAbajo = (ImageView) tableroPropio.getChildren().get(j + juego.abajo);
        ImageView imagenDiagonal = (ImageView) tableroPropio.getChildren().get(j + juego.diagonal);
        imageView.setImage(imagen);
        imagenIzquierda.setImage(imagen);
        imagenAbajo.setImage(imagen);
        imagenDiagonal.setImage(imagen);
    }

    public boolean areaDisponible(ImageView imagenActual, int j) {
        return areaDisponibleAux(imagenActual, j, imagenes.FONDOCAFE) ||
                areaDisponibleAux(imagenActual, j, imagenes.FONDOVERDE) ||
                areaDisponibleAux(imagenActual, j, imagenes.FONDOROJO) ||
                areaDisponibleAux(imagenActual, j, imagenes.FONDONEGRO);
    }

    public boolean areaDisponibleAux(ImageView imagenActual, int j, Image imagenEvaluada) {
        ImageView imagenIzquierda = (ImageView) tableroPropio.getChildren().get(j + juego.izquierda);
        ImageView imagenAbajo = (ImageView) tableroPropio.getChildren().get(j + juego.abajo);
        ImageView imagenDiagonal = (ImageView) tableroPropio.getChildren().get(j + juego.diagonal);
        return (imagenActual.getImage().equals(imagenEvaluada) &&
                imagenIzquierda.getImage().equals(imagenEvaluada) &&
                imagenAbajo.getImage().equals(imagenEvaluada) &&
                imagenDiagonal.getImage().equals(imagenEvaluada));
    }

    public void configurarTabla(TableColumn nombre, TableColumn costo) {
        nombre.setCellValueFactory(
                new PropertyValueFactory<InfoTiendas, String>("nombre")
        );
        costo.setCellValueFactory(
                new PropertyValueFactory<InfoTiendas, String>("costo")
        );
    }

    public ArrayList<InfoTiendas> construirTablaUnidades() {
        ArrayList<InfoTiendas> info = new ArrayList<>();
        info.add(new InfoTiendas("Mundo", "12000", 1, 1, TiposConstrucciones.MUNDO, 1, 15, 16));
        info.add(new InfoTiendas("Conector", "100", 2, 2, TiposConstrucciones.CONECTOR, 0, 0, 0));
        info.add(new InfoTiendas("Mercado 1x2", "2000", 3, 3, TiposConstrucciones.FABRICA1x2, 1, 0, 0));
        info.add(new InfoTiendas("Mercado 2x1", "2000", 4, 3, TiposConstrucciones.FABRICA2x1, 0, 15, 0));
        info.add(new InfoTiendas("Mina 1x2", "1000", 5, 4, TiposConstrucciones.FABRICA1x2, 1, 0, 0));
        info.add(new InfoTiendas("Mina 2x1", "1000", 6, 4, TiposConstrucciones.FABRICA2x1, 0, 15, 0));
        info.add(new InfoTiendas("Armeria (Misil) 1x2", "1000", 7, 5, TiposConstrucciones.FABRICA1x2, 1, 0, 0));
        info.add(new InfoTiendas("Armeria (Misil) 2x1", "1000", 8, 5, TiposConstrucciones.FABRICA2x1, 0, 15, 0));
        info.add(new InfoTiendas("Armeria (Multi-Shot) 1x2", "1000", 7, 5, TiposConstrucciones.FABRICA1x2, 1, 0, 0));
        info.add(new InfoTiendas("Armeria (Multi-Shot) 2x1", "1000", 8, 5, TiposConstrucciones.FABRICA2x1, 0, 15, 0));
        info.add(new InfoTiendas("Armeria (Bomba) 1x2", "1000", 7, 5, TiposConstrucciones.FABRICA1x2, 1, 0, 0));
        info.add(new InfoTiendas("Armeria (Bomba) 2x1", "1000", 8, 5, TiposConstrucciones.FABRICA2x1, 0, 15, 0));
        info.add(new InfoTiendas("Armeria (Combo-Shot) 1x2", "1000", 7, 5, TiposConstrucciones.FABRICA1x2, 1, 0, 0));
        info.add(new InfoTiendas("Armeria (Combo-Shot) 2x1", "1000", 8, 5, TiposConstrucciones.FABRICA2x1, 0, 15, 0));
        return info;
    }

    public void construirTabla(TableView tabla, ArrayList<InfoTiendas> info) {
        tabla.setItems(FXCollections.observableList(info));
    }

    public void pintarUnidad(ImageView imagenActual, int j, int codigoImagenUnidad) {
        ImageView imagenIzquierda = (ImageView) tableroPropio.getChildren().get(j + juego.izquierda);
        ImageView imagenAbajo = (ImageView) tableroPropio.getChildren().get(j + juego.abajo);
        ImageView imagenDiagonal = (ImageView) tableroPropio.getChildren().get(j + juego.diagonal);

        switch (codigoImagenUnidad) {
            case 1:
                imagenActual.setImage(imagenes.MUNDOSI);
                imagenIzquierda.setImage(imagenes.MUNDOSD);
                imagenAbajo.setImage(imagenes.MUNDOII);
                imagenDiagonal.setImage(imagenes.MUNDOID);
                ponerToolTip(imagenActual, "Mundo", j);
                ponerToolTip(imagenIzquierda, "Mundo", j + juego.izquierda);
                ponerToolTip(imagenAbajo, "Mundo", j + juego.abajo);
                ponerToolTip(imagenDiagonal, "Mundo", j + juego.diagonal);
                break;
            case 2:
                imagenActual.setImage(imagenes.CONECTOR);
                ponerToolTip(imagenActual, "Conector", j);
                break;
            case 3:
                imagenActual.setImage(imagenes.MERCADOL);
                imagenIzquierda.setImage(imagenes.MERCADOR);
                ponerToolTip(imagenActual, "Mercado 1x2", j);
                ponerToolTip(imagenIzquierda, "Mercado 1x2", j + juego.izquierda);
                break;
            case 4:
                imagenActual.setImage(imagenes.MERCADOU);
                imagenAbajo.setImage(imagenes.MERCADOD);
                ponerToolTip(imagenActual, "Mercado 2x1", j);
                ponerToolTip(imagenAbajo, "Mercado", j + juego.abajo);
                break;
            case 5:
                imagenActual.setImage(imagenes.MINAL);
                imagenIzquierda.setImage(imagenes.MINAR);
                ponerToolTip(imagenActual, "Mina 1x2", j);
                ponerToolTip(imagenIzquierda, "Mina 1x2", j + juego.izquierda);
                break;
            case 6:
                imagenActual.setImage(imagenes.MINAU);
                imagenAbajo.setImage(imagenes.MINAD);
                ponerToolTip(imagenActual, "Mina 2x1", j);
                ponerToolTip(imagenAbajo, "Mina 2x1", j + juego.abajo);
                break;
            case 7:
                imagenActual.setImage(imagenes.ARMERIAL);
                imagenIzquierda.setImage(imagenes.ARMERIAR);
                ponerToolTip(imagenActual, "Armeria 1x2", j);
                ponerToolTip(imagenIzquierda, "Armeria 1x2", j + juego.izquierda);
                break;
            case 8:
                imagenActual.setImage(imagenes.ARMERIAU);
                imagenAbajo.setImage(imagenes.ARMERIAD);
                ponerToolTip(imagenActual, "Armeria 2x1", j);
                ponerToolTip(imagenAbajo, "Armeria 1x2", j + juego.abajo);
                break;
        }
    }

    public void ponerToolTip(ImageView imagen, String nombre, int i) {
        if (nombre.equals("Conector")) {
            nombre += "\nHaga click para conectar a una unidad.";
        } else {
            int[] coordenadas = Utilitario.determinarCoordenadas(i);
            nombre += "\n";
            nombre += "X: " + coordenadas[0] + "\n";
            nombre += "Y: " + coordenadas[1];
        }
        Tooltip.install(imagen, new Tooltip(nombre));
    }

    public void actualizarToolTipConectores() {
        int limite = tableroPropio.getChildren().size();
        for (int i = 0; i < limite; i++) {
            ImageView imagenActual = (ImageView) tableroPropio.getChildren().get(i);
            if (imagenActual.getImage().equals(imagenes.CONECTOR)) {
                int[] coordenadas = Utilitario.determinarCoordenadas(i);
                int index = juego.grafoPropio.indexOf(coordenadas[0], coordenadas[1]);
                Conector conector = (Conector) juego.grafoPropio.vertices[index].unidad;
                String mensaje = "Conector\n";
                mensaje += conector.obtenerConexiones();
                Tooltip.install(imagenActual, new Tooltip(mensaje));
            }
        }
    }
}
