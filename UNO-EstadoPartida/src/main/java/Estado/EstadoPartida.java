/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estado;

import DTOs.EstadoPartidaDTO;
import DTOs.PeticionJugadaDTO;
import Plantilla.ContextoPipeline;
import Interfaces.IFiltro;

/**
 *
 * @author Abraham Coronel
 */
public class EstadoPartida implements IFiltro<PeticionJugadaDTO, EstadoPartidaDTO> {

    @Override
    public ContextoPipeline<EstadoPartidaDTO> procesar(ContextoPipeline<PeticionJugadaDTO> contexto) throws Exception {

        PeticionJugadaDTO peticion = contexto.getMensaje();

        if (peticion == null) {
            return new ContextoPipeline<>(null);
        }

        EstadoPartidaDTO nuevoEstado = actualizarLogicaJuego(peticion);
        return new ContextoPipeline<>(nuevoEstado);
    }

    private EstadoPartidaDTO actualizarLogicaJuego(PeticionJugadaDTO peticion) {

        EstadoPartidaDTO estado = new EstadoPartidaDTO();

        estado.setIdJugadorEnTurno(peticion.getIdJugador());

        return estado;
    }
}
