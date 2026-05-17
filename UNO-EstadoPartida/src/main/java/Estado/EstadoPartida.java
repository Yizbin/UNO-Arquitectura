/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estado;

import DTOs.EstadoPartidaDTO;
import Plantilla.ContextoPipeline;
import Interfaces.IFiltro;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class EstadoPartida implements IFiltro<EstadoPartidaDTO, EstadoPartidaDTO> {

    @Override
    public ContextoPipeline<EstadoPartidaDTO> procesar(ContextoPipeline<EstadoPartidaDTO> contexto) {

        if (contexto == null || contexto.estaDetenido()) {
            return contexto;
        }

        EstadoPartidaDTO estado = contexto.getMensaje();

        if (estado == null) {
            return new ContextoPipeline<>(null);
        }

        actualizarEstado(estado);
        return new ContextoPipeline<>(estado);
    }

    private void actualizarEstado(EstadoPartidaDTO estado) {
        if (estado.getJugadores() == null) {
            estado.setJugadores(List.of());
        }
        if (estado.getManoJugadorActual() == null) {
            estado.setManoJugadorActual(List.of());
        }
        if (estado.getMensajeEstado() == null) {
            estado.setMensajeEstado("");
        }
    }
}
