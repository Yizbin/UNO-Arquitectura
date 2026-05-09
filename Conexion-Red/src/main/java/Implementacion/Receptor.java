/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import InterfacesConexion.IObserverCola;
import InterfacesConexion.IReceptor;

/**
 *
 * @author Abraham Coronel
 */
public class Receptor implements IObserverCola {

    private ColaEntrada cola;
    private IReceptor aplicacionPrincipal;

    public Receptor(ColaEntrada cola, IReceptor aplicacion) {
        this.cola = cola;
        this.aplicacionPrincipal = aplicacion;
        this.cola.setObservador(this);
    }

    @Override
    public void nuevoMensaje() {
        byte[] datos = cola.desencolar();
        if (datos != null && aplicacionPrincipal != null) {
            aplicacionPrincipal.recibir(datos); 
        }
    }
}
