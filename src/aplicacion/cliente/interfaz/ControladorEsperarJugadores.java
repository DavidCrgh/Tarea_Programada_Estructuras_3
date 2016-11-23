package aplicacion.cliente.interfaz;

import conexiones.client.Client;
import conexiones.client.ThreadClient;
import conexiones.client.ThreadEsperarJugadores;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 4:30 PM
 */
public class ControladorEsperarJugadores implements Initializable {
    @FXML
    public Label labelJugadoresConectados;
    @FXML
    public Button botonComenzar;
    @FXML
    public ListView listaJugadores;

    public ThreadEsperarJugadores hiloEspera;
    public Client cliente;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        botonComenzar.setOnAction(event -> {

            hiloEspera.pause = false;
            hiloEspera.stop = true;


        });



    }

    public void conectarServer(String nombreJugador) {
        cliente = new Client(nombreJugador, this);
        cliente.abrirConexion();
        hiloEspera = new ThreadEsperarJugadores(this);
        hiloEspera.start();
    }
}
