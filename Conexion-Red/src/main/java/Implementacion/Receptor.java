/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import Interfaces.ContextoPipeline;
import Interfaces.IObserverCola;
import Interfaces.IPump;
import Interfaces.ISink;

/**
 *
 * @author Abraham Coronel
 */
public class Receptor implements IObserverCola, IPump<byte[]>{

    private ColaEntrada cola;
    private ISink<byte[]> destinoTuberias; 

    public Receptor(ColaEntrada cola) {
        this.cola = cola;
        this.cola.setObservador(this); 
    }

    @Override
    public void conectarDestino(ISink<byte[]> destino) {
        this.destinoTuberias = destino;
    }

    @Override
    public void nuevoMensaje() {
        byte[] datos = cola.desencolar();
        
        if (datos != null && destinoTuberias != null) {
            try {
                ContextoPipeline<byte[]> contexto = new ContextoPipeline<>(datos);
                
                destinoTuberias.enviar(contexto); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
