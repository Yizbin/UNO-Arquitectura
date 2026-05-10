/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import Interfaces.IObserverCola;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author Abraham Coronel
 */
public class ClienteTCP implements IObserverCola {

    private final String ip;
    private final int puerto;
    private ColaSalida cola;
    private DataOutputStream out;

    public ClienteTCP(String ip, int puerto, ColaSalida cola) {
        this.ip = ip;
        this.puerto = puerto;
        this.cola = cola;
        this.cola.setObservador(this);
        conectar();
    }

    private boolean conectar() {
        try {
            Socket socket = new Socket(ip, puerto);
            this.out = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (Exception e) {
            System.err.println("No se pudo conectar a " + ip + ":" + puerto + " — " + e.getMessage());
            return false;
        }
    }

    @Override
    public void nuevoMensaje() {
        try {
            byte[] datos = cola.desencolar();
            if (datos == null) {
                return;
            }

            if (out == null && !conectar()) {
                System.err.println("Mensaje descartado: cliente " + ip + ":" + puerto + " no disponible.");
                return;
            }

            out.writeInt(datos.length);
            out.write(datos);
            out.flush();

        } catch (Exception e) {
            System.err.println("Error al enviar a " + ip + ":" + puerto + " — reseteando conexión.");
            out = null;
        }
    }

}
