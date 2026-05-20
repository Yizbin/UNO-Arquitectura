package Adapter;

import DTOs.PeticionJugadaDTO;
import Enums.TipoAccionPartida;
import Interfaces.ISink;
import MVC_Sala.IControlModeloSala;
import Plantilla.ContextoPipeline;

/**
 *
 * @author saula
 */
public class AdapterIniciarPartida implements ISink<PeticionJugadaDTO> {

    private final IControlModeloSala modeloSala;

    public AdapterIniciarPartida(IControlModeloSala modeloSala) {
        if (modeloSala == null) {
            throw new IllegalArgumentException("El modelo de sala no puede ser nulo.");
        }
        this.modeloSala = modeloSala;
    }

    @Override
    public void enviar(ContextoPipeline<PeticionJugadaDTO> contexto) {
        if (contexto == null || contexto.estaDetenido()) {
            return;
        }

        PeticionJugadaDTO peticion = contexto.getMensaje();
        if (peticion == null) {
            return;
        }

        if (!esAccionDeSala(peticion.getAccion())) {
            return;
        }

        modeloSala.validarCondicionInicio(peticion.getEstadoPartida());
    }

    private boolean esAccionDeSala(TipoAccionPartida accion) {
        return accion == TipoAccionPartida.SOLICITAR_UNIRSE_PARTIDA
                || accion == TipoAccionPartida.ACEPTAR_SOLICITUD_UNION
                || accion == TipoAccionPartida.RECHAZAR_SOLICITUD_UNION
                || accion == TipoAccionPartida.CAMBIAR_INICIO_PARTIDA
                || accion == TipoAccionPartida.CONFIGURAR_PARTIDA;
    }
}
