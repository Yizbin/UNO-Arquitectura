/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import java.io.DataInputStream;
import java.net.Socket;

/**
 *
 * @author Abraham Coronel
 */
public class ServidorTCP implements Runnable {

    private ColaEntrada cola;
    private DataInputStream in;

    public ServidorTCP(Socket socket, ColaEntrada cola) {
        this.cola = cola;
        try {
            this.in = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                int size = in.readInt();
                byte[] datos = new byte[size];
                in.readFully(datos);
                cola.encolar(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
