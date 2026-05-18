/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import Plantilla.ContextoPipeline;
import Interfaces.IObserverCola;
import interfaces.IPump;

/**
 *
 * @author Abraham Coronel
 */
public class Receptor implements IObserverCola {

    private ColaEntrada cola;
    private IPump<byte[], ?> pump;

    public Receptor(ColaEntrada cola) {
        this.cola = cola;
        this.cola.setObservador(this);
    }

    public void conectarPump(IPump<byte[], ?> pump) {
        this.pump = pump;
    }

    @Override
    public void nuevoMensaje() {
        byte[] datos = cola.desencolar();

        if (datos != null) {
            try {
                ContextoPipeline<byte[]> contexto = new ContextoPipeline<>(datos);

                pump.procesar(contexto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
