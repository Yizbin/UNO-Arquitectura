package Adapter;

import DTOs.EstadoPartidaDTO;
import Interfaces.ISink;
import MVC_Sala.IControlModeloSala;
import Plantilla.ContextoPipeline;

/**
 *
 * @author saula
 */
public class AdapterIniciarPartida implements ISink<EstadoPartidaDTO> {

    private final IControlModeloSala modeloSala;

    public AdapterIniciarPartida(IControlModeloSala modeloSala) {
        if (modeloSala == null) {
            throw new IllegalArgumentException("El modelo de sala no puede ser nulo.");
        }
        this.modeloSala = modeloSala;
    }

    @Override
    public void enviar(ContextoPipeline<EstadoPartidaDTO> contexto) {
        if (contexto == null || contexto.estaDetenido()) {
            return;
        }

        modeloSala.validarCondicionInicio(contexto.getMensaje());
    }
}
