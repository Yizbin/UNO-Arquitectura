package Adapter;

import DTOs.PeticionJugadaDTO;
import Enums.TipoAccionPartida;
import Interfaces.ISink;
import MVC_JugarTurno.IControlModelo;
import MVC_Sala.IControlModeloSala;
import Plantilla.ContextoPipeline;

public class AdapterEntradaPartida implements ISink<PeticionJugadaDTO> {

    private final IControlModeloSala modeloSala;
    private final IControlModelo modeloJuego;

    public AdapterEntradaPartida(IControlModeloSala modeloSala, IControlModelo modeloJuego) {
        if (modeloSala == null) {
            throw new IllegalArgumentException("El modelo de sala no puede ser nulo.");
        }
        if (modeloJuego == null) {
            throw new IllegalArgumentException("El modelo de juego no puede ser nulo.");
        }

        this.modeloSala = modeloSala;
        this.modeloJuego = modeloJuego;
    }

    @Override
    public void enviar(ContextoPipeline<PeticionJugadaDTO> contexto) {
        if (contexto == null || contexto.estaDetenido()) {
            return;
        }

        PeticionJugadaDTO peticion = contexto.getMensaje();
        if (peticion == null || peticion.getAccion() == null) {
            return;
        }

        if (peticion.getAccion() == TipoAccionPartida.CARGAR_PARTIDA) {
            modeloJuego.cargarPartida(peticion.getEstadoPartida());
            return;
        }

        if (esAccionDeSala(peticion.getAccion())) {
            modeloSala.validarCondicionInicio(peticion.getEstadoPartida());
        }
    }

    private boolean esAccionDeSala(TipoAccionPartida accion) {
        return accion == TipoAccionPartida.SOLICITAR_UNIRSE_PARTIDA
                || accion == TipoAccionPartida.ACEPTAR_SOLICITUD_UNION
                || accion == TipoAccionPartida.RECHAZAR_SOLICITUD_UNION
                || accion == TipoAccionPartida.CAMBIAR_INICIO_PARTIDA
                || accion == TipoAccionPartida.CONFIGURAR_PARTIDA
                || accion == TipoAccionPartida.REGISTRAR_JUGADOR;
    }
}
