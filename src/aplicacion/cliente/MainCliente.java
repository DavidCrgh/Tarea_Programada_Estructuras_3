package aplicacion.cliente;/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 3:07 PM
 */

import aplicacion.cliente.interfaz.Utilitario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logica.Conector;
import logica.Mundo;
import logica.NodoGrafo;

public class MainCliente extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("interfaz\\Inicio.fxml"));
        primaryStage.setTitle("Guerra de los Mundos");
        primaryStage.setScene(new Scene(root, 580, 320));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
