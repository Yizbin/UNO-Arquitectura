/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import Interfaces.IConexionSalida;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Abraham Coronel
 */
public class Dispatcher implements IConexionSalida {

    private final Map<String, ClienteTCP> poolClientes;
    private final Map<String, ColaSalida> poolColas;

    public Dispatcher() {
        this.poolClientes = new HashMap<>();
        this.poolColas = new HashMap<>();
    }

    @Override
    public void enviarMensaje(String ip, int puerto, byte[] payload) {
        String llave = ip + ":" + puerto;

        if (!poolClientes.containsKey(llave)) {
            ColaSalida nuevaCola = new ColaSalida();
            ClienteTCP nuevoCliente = new ClienteTCP(ip, puerto, nuevaCola);

            poolColas.put(llave, nuevaCola);
            poolClientes.put(llave, nuevoCliente);
        }

        ColaSalida colaDestino = poolColas.get(llave);
        if (colaDestino != null && payload != null) {
            colaDestino.encolar(payload);
        }
    }

    @Override
    public void preConectar(String ip, int puerto) {
        String llave = ip + ":" + puerto;

        if (!poolClientes.containsKey(llave)) {
            ColaSalida nuevaCola = new ColaSalida();
            
            ClienteTCP nuevoCliente = new ClienteTCP(ip, puerto, nuevaCola);

            poolColas.put(llave, nuevaCola);
            poolClientes.put(llave, nuevoCliente);
        }
    }
}
