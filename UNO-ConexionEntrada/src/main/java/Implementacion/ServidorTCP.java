/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import Interfaces.IObserverConexion;
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
    private IObserverConexion observador; 

    public ServidorTCP(int puerto, ColaEntrada cola, IObserverConexion observador) throws IOException {
        this.cola = cola;
        this.observador = observador;
        this.serverSocket = new ServerSocket(puerto);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();

                String ipCliente = socket.getInetAddress().getHostAddress();

                if (observador != null) {
                    observador.notificarNuevaConexion(ipCliente);
                }

                new Thread(() -> {
                    try {
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        while (true) {
                            int size = in.readInt();
                            byte[] datos = new byte[size];
                            in.readFully(datos);
                            cola.encolar(datos);
                        }
                    } catch (Exception e) {
                        System.err.println("Cliente desconectado: " + ipCliente);
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
