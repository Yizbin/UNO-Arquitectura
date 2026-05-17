/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import DTOs.PaqueteRedDTO;
import Interfaces.IConexionSalida;
import Interfaces.ISink;
import Plantilla.ContextoPipeline;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class AdapterServidor implements ISink<List<PaqueteRedDTO>> {

    private final IConexionSalida conexionSalida;

    public AdapterServidor(IConexionSalida conexionSalida) {
        this.conexionSalida = conexionSalida;
    }

    @Override
    public void enviar(ContextoPipeline<List<PaqueteRedDTO>> contexto) throws Exception {
        List<PaqueteRedDTO> paquetes = contexto.getMensaje();

        if (paquetes == null || paquetes.isEmpty()) {
            return;
        }

        for (PaqueteRedDTO paquete : paquetes) {
            conexionSalida.enviarMensaje(
                    paquete.getIp(),
                    paquete.getPuerto(),
                    paquete.getPayload()
            );
        }
    }
}
