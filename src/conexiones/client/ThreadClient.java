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

    public ThreadClient(Client _client) {
        this.client = _client;
    }

    public void run() {
        int opcion;

        while (!stop) {
            try {
                sleep(100);
                opcion = client.entradaDatos.readInt();
                switch (opcion) {
                    case 1:
                        ArrayList<String> enemigos = new ArrayList<>();
                        int cantidadEnemigos = client.entradaDatos.readInt();
                        for (int i = 0; i < cantidadEnemigos; i++) {
                            enemigos.add(client.entradaDatos.readUTF());
                        }
                        client.ventanaPrincipalJuego.precargarInterfaz(enemigos);
                        break;
                    case 2:
                        String nombreJugador = client.entradaDatos.readUTF();
                        String mensaje = client.entradaDatos.readUTF();
                        client.ventanaPrincipalJuego.mensajesChat.appendText(nombreJugador + ": " + mensaje + "\n");
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
                    case 8:
                        Platform.runLater(() -> {
                            client.ventanaPrincipalJuego.empezarJuego();
                        });
                        break;
                    case 9:
                        Platform.runLater(() -> {
                            client.ventanaPrincipalJuego.empezarTurno();
                        });
                        break;
                    case 10:
                        String nombre = client.entradaDatos.readUTF();
                        Platform.runLater(() -> {
                            client.ventanaPrincipalJuego.terminarTurno(nombre);
                        });
                        break;
                    case 11:

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
