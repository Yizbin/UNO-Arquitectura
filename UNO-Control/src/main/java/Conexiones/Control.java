/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexiones;

import DTOs.ConexionJugadorDTO;
import DTOs.PaqueteRedDTO;
import Plantilla.ContextoPipeline;
import Interfaces.IFiltro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class Control implements IFiltro<byte[], List<PaqueteRedDTO>> {

    private final List<ConexionJugadorDTO> listaJugadores;

    public Control() {
        this.listaJugadores = new ArrayList<>();
    }

    public void registrarJugador(ConexionJugadorDTO conexion) {
        this.listaJugadores.add(conexion);
    }

    @Override
    public ContextoPipeline<List<PaqueteRedDTO>> procesar(ContextoPipeline<byte[]> contexto) throws Exception {
        byte[] payloadBytes = contexto.getMensaje();
        
        if (payloadBytes == null) {
            ContextoPipeline<List<PaqueteRedDTO>> ctxError = new ContextoPipeline<>(null);
            ctxError.detenerConError("El mensaje llegó nulo al filtro de Control.");
            return ctxError;
        }

        List<PaqueteRedDTO> paquetesDeSalida = new ArrayList<>();

        for (ConexionJugadorDTO jugador : listaJugadores) {
            PaqueteRedDTO paquete = new PaqueteRedDTO(jugador.getIp(), jugador.getPuerto());
            paquete.setPayload(payloadBytes);
            
            paquetesDeSalida.add(paquete);
        }

        return new ContextoPipeline<>(paquetesDeSalida);
    }
}
