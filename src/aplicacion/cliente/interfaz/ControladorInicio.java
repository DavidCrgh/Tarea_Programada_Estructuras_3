package aplicacion.cliente.interfaz;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
            String nickname = cuadroNickname.getText();
            System.out.println(nickname);
        });
    }
}
