/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import Interfaces.IReceptor;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Abraham Coronel
 */
public class Receptor {

    private Queue<byte[]> cola;
    private final IReceptor notificador;

    public Receptor(IReceptor notificador) {
        this.cola = new ConcurrentLinkedQueue<>();
        this.notificador = notificador;
        iniciarProcesamiento();
    }

    public void encolar(byte[] datosBinarios) {
        cola.add(datosBinarios);
    }

    private void iniciarProcesamiento() {
        new Thread(() -> {
            while (true) {
                byte[] mensaje = cola.poll();

                if (mensaje != null && notificador != null) {
                    notificador.update(mensaje);
                }
            }
        }).start();
    }

}
