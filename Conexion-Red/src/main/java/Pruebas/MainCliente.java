/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import Cliente.DispatcherFactory;
import Interfaces.IDispatcher;

/**
 *
 * @author Abraham Coronel
 */
public class MainCliente {

    public static void main(String[] args) {
        System.out.println("CLIENTE 1");

        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();

        String mensaje = "Hola chat soy el cliente";

        System.out.println("[CLIENTE] -> Empacando a binario y enviando...");

        dispatcher.enviar(mensaje, "127.0.0.1", 5000);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        System.out.println("[CLIENTE] -> Mensaje enviado. Cerrando instancia del cliente.");
        System.exit(0);
    }

}
