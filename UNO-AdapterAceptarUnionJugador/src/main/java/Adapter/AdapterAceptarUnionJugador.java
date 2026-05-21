/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Interfaces.ISink;
import MVC_Sala.ModeloSala;
import Plantilla.ContextoPipeline;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class AdapterAceptarUnionJugador implements ISink<EstadoPartidaDTO> {

    private final ModeloSala modeloSala;

    public AdapterAceptarUnionJugador(ModeloSala modeloSala) {
        this.modeloSala = modeloSala;
    }

    public AdapterAceptarUnionJugador(ModeloSala modeloSala, int idJugadorLocal) {
        this(modeloSala);
    }

    @Override
    public void enviar(ContextoPipeline<EstadoPartidaDTO> contexto) throws Exception {
        if (contexto == null || contexto.estaDetenido()) {
            return;
        }

        EstadoPartidaDTO estado = contexto.getMensaje();

        if (estado == null) {
            return;
        }

        aceptarUniones(estado.getJugadores());
    }

    private void aceptarUniones(List<JugadorResumenDTO> jugadores) {
        if (jugadores == null) {
            return;
        }

        for (JugadorResumenDTO jugador : jugadores) {
            if (jugador != null && jugador.isAceptado()) {
                aceptarUnion(jugador);
            }
        }
    }

    public void aceptarUnion(JugadorResumenDTO jugador) {
        modeloSala.registrarUnionAceptada(jugador);
    }
}
