package conexiones.client;

import aplicacion.cliente.interfaz.ControladorEsperarJugadores;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 12:32 PM
 */
public class Client implements Serializable {
    public static String IP_SERVER = "localhost";
    public ControladorEsperarJugadores ventanaPreJuego;
    //TODO aqui va controlador de la ventanaPrincipal
    public ObjectInputStream entradaObjetos;
    public ObjectOutputStream salidaObjetos;
    public Socket client;
    public String nombre;

    public Client(String _nombre/*ref a ventana*/) {
        this.nombre = _nombre;
    }

    public void abrirConexion() {
        try {
            client = new Socket(IP_SERVER, 8080);
            obtenerFlujos();
            salidaObjetos.writeUTF(nombre);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error en la conexion del cliente al servidor.");
        }
        new ThreadClient(this).start();
    }

    public void obtenerFlujos() {
        try {
            salidaObjetos = new ObjectOutputStream(client.getOutputStream());
            salidaObjetos.flush();
            entradaObjetos = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
