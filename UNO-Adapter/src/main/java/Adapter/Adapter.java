/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import Interfaces.IConexionSalida;
import DTOs.PaqueteRedDTO;
import Interfaces.ISink;
import Plantilla.ContextoPipeline;

/**
 *
 * @author Abraham Coronel
 * @param <T>
 */
public class Adapter<T> implements ISink<T> {

    private final IConexionSalida conexionSalida;

    public Adapter(IConexionSalida conexionSalida) {
        this.conexionSalida = conexionSalida;
    }

    @Override
    public void enviar(ContextoPipeline<T> contexto) throws Exception {

        T mensaje = contexto.getMensaje();

        if (mensaje != null) {
            try {
                PaqueteRedDTO paquete = (PaqueteRedDTO) mensaje;

                conexionSalida.enviarMensaje(
                        paquete.getIp(),
                        paquete.getPuerto(),
                        paquete.getPayload()
                );

            } catch (ClassCastException e) {
                System.err.println("Error en el Adapter: El mensaje no es un PaqueteRedDTO. " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error inesperado en el Adapter al enviar el mensaje: " + e.getMessage());
            }
        }
    }

}
