/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Interfaces.IReceptor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Abraham Coronel
 */
public class Receptor {

    private BlockingQueue<byte[]> cola;
    private final IReceptor notificador;

    public Receptor(IReceptor notificador) {
        this.cola = new LinkedBlockingQueue<>();
        this.notificador = notificador;
        iniciarProcesamiento();
    }

    public void encolar(byte[] datosBinarios) {
        cola.add(datosBinarios);
    }

    private void iniciarProcesamiento() {
        new Thread(() -> {
            while (true) {
                try {
                    byte[] mensaje = cola.take();
                    if (notificador != null) {
                        notificador.update(mensaje);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

}
