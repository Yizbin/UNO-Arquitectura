/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import InterfacesConexion.IObserverCola;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Abraham Coronel
 */
public class ColaSalida {

    private Queue<byte[]> cola = new LinkedList<>();
    private IObserverCola observador;

    public void setObservador(IObserverCola observador) {
        this.observador = observador;
    }

    public synchronized void encolar(byte[] datos) {
        cola.add(datos);
        if (observador != null) {
            observador.nuevoMensaje();
        }
    }

    public synchronized byte[] desencolar() {
        return cola.poll();
    }
}
