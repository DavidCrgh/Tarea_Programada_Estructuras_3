package aplicacion.cliente.interfaz;

import conexiones.client.Client;
import conexiones.client.ThreadEsperarJugadores;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

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
            try {
                hiloEspera.pause = false;
                hiloEspera.stop = true;
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("PrincipalJuego.fxml").openStream());
                ControladorPrincipalJuego controlador = loader.getController();
                cliente.ventanaPrincipalJuego = controlador;
                controlador.cliente = cliente;
                cliente.salidaDatos.writeInt(3);
                stage.setScene(new Scene(root, 1243, 813));
                stage.setTitle("Guerra de los Mundos");
                stage.show();
                Stage temp = (Stage) botonComenzar.getScene().getWindow();
                temp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void conectarServer(String nombreJugador) {
        cliente = new Client(nombreJugador, this);
        cliente.abrirConexion();
        hiloEspera = new ThreadEsperarJugadores(this);
        hiloEspera.start();
    }
}
