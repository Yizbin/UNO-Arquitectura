/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import InterfacesConexion.IDispatcher;

/**
 *
 * @author Abraham Coronel
 */
public class Dispatcher implements IDispatcher {

    private ColaSalida cola;
    private ClienteTCP cliente;

    public Dispatcher(String ip, int puerto) {
        this.cola = new ColaSalida();
        this.cliente = new ClienteTCP(ip, puerto, cola);
    }

    @Override
    public void despachar(byte[] datos) {
        cola.encolar(datos);
    }

}
