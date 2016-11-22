package conexiones.client;

import java.io.*;
import java.net.Socket;

/**
 * Creado por David Valverde Garro - 2016034774
 * Fecha: 22-Nov-16 Tiempo: 12:32 PM
 */
public class Client implements Serializable {
    public static String IP_SERVER = "localhost";
    //TODO Ref a ventana va aqui (controlador FXML)
    public DataInputStream entradaDatos;
    public DataOutputStream salidaDatos;
    public ObjectInputStream entradaObjetos;
    public ObjectOutputStream salidaObjectos;
    public Socket client;
    public String nombre;

    public Client(/*ref a ventana*/) {

    }

    public void abrirConexion() {
        try {
            client = new Socket(IP_SERVER, 8080);
            entradaDatos = new DataInputStream(client.getInputStream());
            salidaDatos = new DataOutputStream(client.getOutputStream());
            entradaObjetos = new ObjectInputStream(client.getInputStream());
            salidaObjectos = new ObjectOutputStream(client.getOutputStream());
            salidaObjectos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error en la conexion del cliente al servidor.");
        }
        //TODO new ThreadClient().start();
    }
}
