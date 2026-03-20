/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import Interfaces.IDispatcher;
import Interfaces.ISerializador;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Abraham Coronel
 */
public class Dispatcher implements IDispatcher {

    private final Queue<PaqueteSalida> cola;
    private final Cliente clienteRed;
    private final ISerializador serializador;

    public Dispatcher(ISerializador serializador) {
        this.cola = new ConcurrentLinkedQueue<>();
        this.clienteRed = new Cliente();
        this.serializador = serializador;
        iniciarProcesamiento();
    }

    @Override
    public void enviar(Object mensaje, String ip, int puerto) {
        cola.add(new PaqueteSalida(mensaje, ip, puerto));
    }

    private void iniciarProcesamiento() {
        new Thread(() -> {
            while (true) {
                PaqueteSalida paquete = cola.poll();
                if (paquete != null) {
                    try {
                        byte[] datosBinarios = serializador.serializar(paquete.getMensaje());
                        clienteRed.enviarPorSocket(datosBinarios, paquete.getIp(), paquete.getPuerto());
                    } catch (Exception e) {
                        System.err.println("Error procesando envío: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

}
