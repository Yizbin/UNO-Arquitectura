/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Abraham Coronel
 */
public class Servidor {

    private ServerSocket serverSocket;
    private final Receptor receptor;

    public Servidor(int puerto, Receptor receptor) {
        this.receptor = receptor;
        try {
            this.serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor escuchando en el puerto: " + puerto);
            iniciarEscucha();
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private void iniciarEscucha() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket socketCliente = serverSocket.accept();
                    DataInputStream in = new DataInputStream(socketCliente.getInputStream());

                    int tamaño = in.readInt();

                    if (tamaño > 0) {
                        byte[] datos = new byte[tamaño];
                        in.readFully(datos);
                        receptor.encolar(datos);
                    }

                    socketCliente.close();

                } catch (IOException e) {
                    System.err.println("Error leyendo cliente: " + e.getMessage());
                }
            }
        }).start();
    }
}
