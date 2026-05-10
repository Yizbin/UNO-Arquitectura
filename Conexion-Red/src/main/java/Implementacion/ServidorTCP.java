/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Abraham Coronel
 */
public class ServidorTCP implements Runnable {

    private ServerSocket serverSocket;
    private ColaEntrada cola;

    public ServidorTCP(int puerto, ColaEntrada cola) throws IOException {
        this.cola = cola;
        this.serverSocket = new ServerSocket(puerto);
    }

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
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
