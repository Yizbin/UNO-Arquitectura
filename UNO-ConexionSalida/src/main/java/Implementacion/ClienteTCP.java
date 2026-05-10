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

    private ColaSalida cola;
    private DataOutputStream out;

    public ClienteTCP(String ip, int puerto, ColaSalida cola) {
        this.cola = cola;
        this.cola.setObservador(this);
        try {
            Socket socket = new Socket(ip, puerto);
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nuevoMensaje() {
        try {
            byte[] datos = cola.desencolar();
            if (datos != null) {

                if (out != null) {
                    out.writeInt(datos.length);
                    out.write(datos);
                    out.flush();
                } else {
                    System.err.println("Mensaje ignorado: No hay conexión con el servidor.");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
