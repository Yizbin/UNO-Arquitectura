/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import Implementacion.Dispatcher;
import InterfacesConexion.IDispatcher;

/**
 *
 * @author Abraham Coronel
 */
public class DispatcherFactory {

    public static IDispatcher crearDispatcher(String ip, int puerto) {
        return new Dispatcher(ip, puerto);
    }
}
