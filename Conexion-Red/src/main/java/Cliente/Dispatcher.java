/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import Interfaces.IDispatcher;
import Interfaces.ISerializador;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Abraham Coronel
 */
public class Dispatcher implements IDispatcher {

    private final BlockingQueue<PaqueteSalida> cola;
    private final Cliente clienteRed;
    private final ISerializador serializador;

    public Dispatcher(ISerializador serializador, Cliente clienteRed) {
        this.cola = new LinkedBlockingQueue<>();
        this.clienteRed = clienteRed;
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
                try {
                    PaqueteSalida paquete = cola.take();
                    byte[] datosBinarios = serializador.serializar(paquete.getMensaje());
                    clienteRed.enviarPorSocket(datosBinarios, paquete.getIp(), paquete.getPuerto());

                } catch (InterruptedException e) {
                    System.err.println("El hilo del Dispatcher fue interrumpido.");
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.err.println("Error procesando envio: " + e.getMessage());
                }
            }
        }).start();
    }

}
