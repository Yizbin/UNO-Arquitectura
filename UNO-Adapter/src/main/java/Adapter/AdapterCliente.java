/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import Interfaces.IConexionSalida;
import Interfaces.ISink;
import Plantilla.ContextoPipeline;

/**
 *
 * @author Abraham Coronel
 */
public class AdapterCliente implements ISink<byte[]> {

    private final String ip;
    private final int puerto;
    private final IConexionSalida conexionSalida;

    public AdapterCliente(String ip, int puerto, IConexionSalida conexionSalida) {
        this.ip = ip;
        this.puerto = puerto;
        this.conexionSalida = conexionSalida;
    }

    @Override
    public void enviar(ContextoPipeline<byte[]> contexto) throws Exception {
        byte[] payload = contexto.getMensaje();

        if (payload != null && payload.length > 0) {
            conexionSalida.enviarMensaje(ip, puerto, payload);
        }
    }
}
