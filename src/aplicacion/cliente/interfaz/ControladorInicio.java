package aplicacion.cliente.interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 2:41 PM
 */
public class ControladorInicio implements Initializable {
    @FXML
    public Button botonInicio;
    @FXML
    public TextField cuadroNickname;
    @FXML
    public ImageView imagenFondo;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Image image = new Image(getClass().getResource("recursos\\FondoInicio.jpg").toExternalForm());
        imagenFondo.setImage(image);

        botonInicio.setOnAction(event -> {
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent raiz = null;
            try {
                raiz = loader.load(getClass().getResource("EsperarJugadores.fxml").openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ControladorEsperarJugadores controlador = (ControladorEsperarJugadores) loader.getController();
            escenario.setTitle("");
            escenario.setScene(new Scene(raiz, 265, 342));
            escenario.show();

            String nickname = cuadroNickname.getText();
            controlador.conectarServer(nickname);

            Stage temp = (Stage) botonInicio.getScene().getWindow();
            temp.close();
        });
    }
}
