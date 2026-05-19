/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import DTOs.JugadorResumenDTO;
import DTOs.PeticionJugadaDTO;
import Interfaces.ISink;
import MVC_Sala.ModeloSala;
import Plantilla.ContextoPipeline;

/**
 *
 * @author Abraham Coronel
 */
public class AdapterSolicitarUnionJugador implements ISink<PeticionJugadaDTO> {

    private final ModeloSala modeloSala;

    public AdapterSolicitarUnionJugador(ModeloSala modeloSala) {
        this.modeloSala = modeloSala;
    }

    @Override
    public void enviar(ContextoPipeline<PeticionJugadaDTO> contexto) throws Exception {
        if (contexto == null || contexto.estaDetenido()) {
            return;
        }

        PeticionJugadaDTO peticion = contexto.getMensaje();

        if (peticion == null || peticion.getJugadorActualizar() == null) {
            return;
        }

        JugadorResumenDTO jugador = peticion.getJugadorActualizar();

        modeloSala.agregarSolicitudUnion(jugador);
    }

}
