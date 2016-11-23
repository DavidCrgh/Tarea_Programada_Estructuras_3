package aplicacion.cliente;/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 3:07 PM
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainCliente extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //  Parent root = FXMLLoader.load(getClass().getResource("interfaz\\PrincipalJuego.fxml")); //Probar PrincipalJuego
        Parent root = FXMLLoader.load(getClass().getResource("interfaz\\Inicio.fxml"));
        primaryStage.setTitle("Guerra de los Mundos");
        primaryStage.setScene(new Scene(root, 1250, 804)); //Probar PrincipalJuego
        primaryStage.setScene(new Scene(root, 580, 320));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
