package Adapter;

import DTOs.EstadoPartidaDTO;
import Interfaces.ISink;
import MVC_JugarTurno.IControlModelo;
import Plantilla.ContextoPipeline;

/**
 *
 * @author saula
 */
public class AdapterCargarPartida implements ISink<EstadoPartidaDTO> {

    private final IControlModelo modeloJuego;

    public AdapterCargarPartida(IControlModelo modeloJuego) {
        if (modeloJuego == null) {
            throw new IllegalArgumentException("El modelo de juego no puede ser nulo.");
        }
        this.modeloJuego = modeloJuego;
    }

    @Override
    public void enviar(ContextoPipeline<EstadoPartidaDTO> contexto) {
        if (contexto == null || contexto.estaDetenido()) {
            return;
        }

        modeloJuego.cargarPartida(contexto.getMensaje());
    }
}
