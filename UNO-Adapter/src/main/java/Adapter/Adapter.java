/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import Interfaces.IConexionSalida;
import DTOs.PaqueteRedDTO;
import Interfaces.ISink;
import Plantilla.ContextoPipeline;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class Adapter implements ISink<List<PaqueteRedDTO>> {

    private final IConexionSalida conexionSalida;

    public Adapter(IConexionSalida conexionSalida) {
        this.conexionSalida = conexionSalida;
    }

    @Override
    public void enviar(ContextoPipeline<List<PaqueteRedDTO>> contexto) throws Exception {
        List<PaqueteRedDTO> listaPaquetes = contexto.getMensaje();

        if (listaPaquetes != null && !listaPaquetes.isEmpty()) {
            for (PaqueteRedDTO paquete : listaPaquetes) {
                conexionSalida.enviarMensaje(
                        paquete.getIp(),
                        paquete.getPuerto(),
                        paquete.getPayload()
                );
            }
        }
    }

}
