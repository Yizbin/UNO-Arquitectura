package Adapter;

import DTOs.PeticionJugadaDTO;
import Enums.TipoAccionPartida;
import Interfaces.ISink;
import MVC_JugarTurno.IControlModelo;
import Plantilla.ContextoPipeline;

/**
 *
 * @author saula
 */
public class AdapterCargarPartida implements ISink<PeticionJugadaDTO> {

    private final IControlModelo modeloJuego;

    public AdapterCargarPartida(IControlModelo modeloJuego) {
        if (modeloJuego == null) {
            throw new IllegalArgumentException("El modelo de juego no puede ser nulo.");
        }
        this.modeloJuego = modeloJuego;
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

        if (peticion.getAccion() != TipoAccionPartida.CARGAR_PARTIDA) {
            return;
        }

        modeloJuego.cargarPartida(peticion.getEstadoPartida());
    }
}
