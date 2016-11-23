package aplicacion.cliente.interfaz;

import conexiones.client.Client;
import conexiones.client.ThreadClient;
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
    private Label labelJugadoresConectados;
    @FXML
    private Button botonComenzar;
    @FXML
    private ListView listaJugadores;

    public Client cliente;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

    }

    public void conectarServer(String nombreJugador) {
        cliente = new Client(nombreJugador);
        cliente.abrirConexion();
    }
}
