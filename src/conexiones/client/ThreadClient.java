package conexiones.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 1:08 PM
 */
public class ThreadClient extends Thread {
    public boolean stop;
    public Client client;
    ArrayList<String> arregloNombres = new ArrayList<>();
    //TODO ref a ventana va aqui

    public ThreadClient(Client _client /*_ref a ventana*/) {
        this.client = _client;
        //ref a ventana = _ref a ventana
    }

    public void run() {
        int opcion;

        while (!stop) {
            try {
                sleep(100);
                opcion = client.entradaDatos.readInt();
                switch (opcion) {
                    case 7:
                        int jugadores = client.entradaDatos.readInt();
                        arregloNombres.clear();
                        for (int i = 0; i < jugadores; i++) {

                            arregloNombres.add(client.entradaDatos.readUTF());
                        }
                        Platform.runLater(() -> {
                            client.ventanaPreJuego.labelJugadoresConectados.setText(jugadores + "/4");
                            ObservableList<String> items = FXCollections.observableArrayList(arregloNombres);
                            client.ventanaPreJuego.listaJugadores.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
                            client.ventanaPreJuego.listaJugadores.setItems(items);
                        });
                        if (jugadores >= 2) {
                            System.out.println("Hay " + jugadores + " jugadores");
                            client.ventanaPreJuego.botonComenzar.setDisable(false);
                        }
                        break;
                    case 4:
                        int jugadoresAux = client.entradaDatos.readInt();
                        arregloNombres.clear();
                        for (int i = 0; i < jugadoresAux; i++) {
                            arregloNombres.add(client.entradaDatos.readUTF());

                        }
                        Platform.runLater(() -> {
                            client.ventanaPreJuego.labelJugadoresConectados.setText(jugadoresAux + "/4");
                            ObservableList<String> items = FXCollections.observableArrayList(arregloNombres);
                            client.ventanaPreJuego.listaJugadores.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
                            client.ventanaPreJuego.listaJugadores.setItems(items);
                        });
                        break;
                    default:
                        System.out.println("ThreadClient de " + client.nombre + " corriendo.");//TODO cambiar por los casos de verdad
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Servidor desconectado.");
    }
}
