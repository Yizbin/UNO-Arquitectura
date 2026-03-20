/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author Abraham Coronel
 */
public class Cliente {

    public void enviarPorSocket(byte[] datos, String ip, int puerto) {
        try (Socket socket = new Socket(ip, puerto); DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            out.writeInt(datos.length); //Enviamos el tamanio del paquete
            out.write(datos);           //Enviamos los bytes 
            out.flush();

        } catch (Exception e) {
            System.err.println("Error enviando datos al servidor: " + e.getMessage());
        }
    }
}
