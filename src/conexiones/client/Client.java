package conexiones.client;

import aplicacion.cliente.interfaz.ControladorEsperarJugadores;
import aplicacion.cliente.interfaz.ControladorPrincipalJuego;

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
    public ControladorPrincipalJuego ventanaPrincipalJuego;
    public DataInputStream entradaDatos;
    public DataOutputStream salidaDatos;
    public ObjectInputStream entradaObjetos;
    public ObjectOutputStream salidaObjetos;
    public Socket client;
    public String nombre;

    public Client(String _nombre, ControladorEsperarJugadores controladorEspera) {
        this.nombre = _nombre;
        ventanaPreJuego = controladorEspera;
        ventanaPrincipalJuego = null;
    }

    public void abrirConexion() {
        try {
            client = new Socket(IP_SERVER, 8080);
            obtenerFlujos();
            salidaDatos.writeUTF(nombre);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error en la conexion del cliente al servidor.");
        }
        new ThreadClient(this).start();
    }

    public void obtenerFlujos() {
        try {
            salidaObjetos = new ObjectOutputStream(client.getOutputStream());
            salidaDatos = new DataOutputStream(client.getOutputStream());
            salidaObjetos.flush();
            salidaDatos.flush();
            entradaObjetos = new ObjectInputStream(client.getInputStream());
            entradaDatos = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
