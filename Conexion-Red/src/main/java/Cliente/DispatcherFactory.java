/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import Interfaces.IDispatcher;
import Serializador.Serializador;

/**
 *
 * @author Abraham Coronel
 */
public class DispatcherFactory {

    public static IDispatcher crearDispatcher() {
        Serializador serializadorBinario = new Serializador();
        Cliente clienteDeRed = new Cliente();

        return new Dispatcher(serializadorBinario, clienteDeRed);
    }
}
