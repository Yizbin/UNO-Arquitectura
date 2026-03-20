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

                    //Sacamos el numero entero que nos dice el tamanio del paquete
                    int tamaño = in.readInt();

                    if (tamaño > 0) {
                        //Se creaun arreglo del tamanio del mensaje
                        byte[] datos = new byte[tamaño];
                        //Bloquea la lectura hasta que el 100% de los bytes hallan llegado, para evitar paquetes corruptos o medias
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
