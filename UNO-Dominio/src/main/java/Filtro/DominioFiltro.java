/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Filtro;

import DTOs.EstadoPartidaDTO;
import DTOs.PeticionJugadaDTO;
import DTOs.RespuestaFinalizacionDTO;
import Enums.TipoAccionPartida;
import static Enums.TipoAccionPartida.CONFIGURAR_PARTIDA;
import Interfaces.IFiltro;
import Interfaces.ISubDominio;
import Plantilla.ContextoPipeline;

/**
 *
 * @author Abraham Coronel
 */
public class DominioFiltro implements IFiltro<PeticionJugadaDTO, EstadoPartidaDTO> {

    private final ISubDominio subDominio;

    public DominioFiltro(ISubDominio subDominio) {
        this.subDominio = subDominio;
    }

    @Override
    public ContextoPipeline<EstadoPartidaDTO> procesar(ContextoPipeline<PeticionJugadaDTO> contexto) throws Exception {

        PeticionJugadaDTO peticion = contexto.getMensaje();

        if (peticion == null) {
            throw new IllegalStateException("La petición en el contexto es nula.");
        }

        TipoAccionPartida tipoAccion = peticion.getAccion();

        if (tipoAccion == null) {
            throw new IllegalStateException("El tipo de accion no ha sido definido en la peticion.");
        }

        switch (tipoAccion) {

            case CONFIGURAR_PARTIDA -> {
                subDominio.configurarPartida(peticion.getConfiguracionPartida());
            }
            case SOLICITAR_UNIRSE_PARTIDA -> {
                subDominio.solicitarUnion(peticion.getJugadorActualizar());
            }

            case ACEPTAR_SOLICITUD_UNION -> {
                EstadoPartidaDTO estado = peticion.getEstadoPartida();
                subDominio.aceptarSolicitudUnion(estado.getIdJugador());
            }

            case RECHAZAR_SOLICITUD_UNION -> {
                EstadoPartidaDTO estado = peticion.getEstadoPartida();
                subDominio.rechazarSolicitudUnion(estado.getIdJugador());
            }
            case INICIAR_PARTIDA -> {
                EstadoPartidaDTO estado = peticion.getEstadoPartida();
                if (estado == null || estado.getJugadores() == null) {
                    throw new IllegalStateException("La peticion de inicio debe incluir los jugadores de la partida.");
                }
                subDominio.prepararJuego(estado.getJugadores());
            }

            case JUGAR_CARTA -> {
                subDominio.jugarCarta(peticion.getEstadoPartida().getIdJugador(), peticion.getEstadoPartida().getCartaEnDescarte());
            }
            case ROBAR_CARTA -> {
                subDominio.robarCarta(peticion.getEstadoPartida().getIdJugador());
            }
            case ELEGIR_COLOR -> {
                subDominio.elegirColorComodin(peticion.getEstadoPartida().getColorSeleccionado());
            }
            case TIRAR_RULETA -> {
                subDominio.tirarRuleta();
            }
            case GRITAR_UNO -> {
                subDominio.gritarUno(peticion.getEstadoPartida().getIdJugador());
            }
            case TERMINAR_TURNO -> {
                subDominio.terminarTurno();
            }
            case SOLICITAR_FINALIZACION -> {
                subDominio.solicitarFinalizacion(peticion.getJugadorActualizar());
            }
            case ACEPTAR_FINALIZACION -> {
                subDominio.responderFinalizacion(crearRespuestaFinalizacion(peticion, true));
            }
            case RECHAZAR_FINALIZACION -> {
                subDominio.responderFinalizacion(crearRespuestaFinalizacion(peticion, false));
            }

            default ->
                throw new UnsupportedOperationException("Tipo de accion no reconocido: " + tipoAccion);
        }

        EstadoPartidaDTO estado = subDominio.obtenerEstadoPartida();
        return new ContextoPipeline<>(estado);
    }

    private RespuestaFinalizacionDTO crearRespuestaFinalizacion(PeticionJugadaDTO peticion, boolean acepta) {
        RespuestaFinalizacionDTO respuesta = new RespuestaFinalizacionDTO();
        respuesta.setJugador(peticion.getJugadorActualizar());
        respuesta.setAcepta(acepta);
        return respuesta;
    }

}
