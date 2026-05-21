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
public class DominioFiltro implements IFiltro<PeticionJugadaDTO, PeticionJugadaDTO> {

    private final ISubDominio subDominio;

    public DominioFiltro(ISubDominio subDominio) {
        this.subDominio = subDominio;
    }

    @Override
    public ContextoPipeline<PeticionJugadaDTO> procesar(ContextoPipeline<PeticionJugadaDTO> contexto) throws Exception {

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
                EstadoPartidaDTO estado = peticion.getEstadoPartida();
                if (estado == null) {
                    throw new IllegalStateException("La solicitud de union debe incluir el id del jugador.");
                }
                subDominio.solicitarUnion(estado.getIdJugador());
            }

            case ACEPTAR_SOLICITUD_UNION -> {
                EstadoPartidaDTO estado = peticion.getEstadoPartida();
                subDominio.aceptarSolicitudUnion(estado.getIdJugador());
            }

            case RECHAZAR_SOLICITUD_UNION -> {
                EstadoPartidaDTO estado = peticion.getEstadoPartida();
                subDominio.rechazarSolicitudUnion(estado.getIdJugador());
            }
            case CAMBIAR_INICIO_PARTIDA -> {
                EstadoPartidaDTO estado = peticion.getEstadoPartida();
                if (estado == null || estado.getEstadosJugadoresSala() == null || estado.getEstadosJugadoresSala().isEmpty()) {
                    throw new IllegalStateException("La peticion de cambio de inicio debe incluir el estado del jugador.");
                }
                subDominio.actualizarEstadoJugadorSala(estado.getEstadosJugadoresSala().get(0));
            }
            case INICIAR_PARTIDA -> {
                EstadoPartidaDTO estado = peticion.getEstadoPartida();
                if (estado == null || estado.getJugadores() == null) {
                    throw new IllegalStateException("La peticion de inicio debe incluir los jugadores de la partida.");
                }
                subDominio.prepararJuego(estado, peticion.getJugadorActualizar());
            }
            case CARGAR_PARTIDA -> {
                EstadoPartidaDTO estado = peticion.getEstadoPartida();
                if (estado == null) {
                    throw new IllegalStateException("La peticion de carga debe incluir el estado de la partida.");
                }
                subDominio.cargarPartida(estado);
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
            case REGISTRAR_JUGADOR -> {
                if (peticion.getJugadorLocal() == null) {
                    throw new IllegalStateException("El jugador no puede ser nulo");
                }
                subDominio.registrarJugador(peticion.getJugadorLocal());
            }
            case TERMINAR_TURNO -> {
                subDominio.terminarTurno();
            }
            case SOLICITAR_FINALIZACION -> {
                subDominio.solicitarFinalizacion(obtenerJugadorPeticion(peticion));
            }
            case ACEPTAR_FINALIZACION -> {
                subDominio.responderFinalizacion(crearRespuestaFinalizacion(peticion, true));
            }
            case RECHAZAR_FINALIZACION -> {
                subDominio.responderFinalizacion(crearRespuestaFinalizacion(peticion, false));
            }
            case JUGADOR_REGISTRADO, SOLICITUD_UNION_RECIBIDA, SOLICITUD_UNION_ACEPTADA, SOLICITUD_UNION_RECHAZADA -> {
                EstadoPartidaDTO estado = peticion.getEstadoPartida();

                if (estado == null || estado.getJugadores() == null) {
                    throw new IllegalStateException(
                            "La accion de sincronizacion debe incluir los jugadores de la partida."
                    );
                }
                subDominio.cargarJugadoresPartida(estado.getJugadores());
            }

            default ->
                throw new UnsupportedOperationException("Tipo de accion no reconocido: " + tipoAccion);
        }

        return new ContextoPipeline<>(crearRespuesta(peticion));
    }

    private PeticionJugadaDTO crearRespuesta(PeticionJugadaDTO peticion) {
        PeticionJugadaDTO respuesta = new PeticionJugadaDTO();

        respuesta.setAccion(obtenerAccionRespuesta(peticion.getAccion()));
        respuesta.setJugadorActualizar(peticion.getJugadorActualizar());
        respuesta.setConfiguracionPartida(peticion.getConfiguracionPartida());
        respuesta.setEstadoPartida(subDominio.obtenerEstadoPartida());

        if (peticion.getAccion() == TipoAccionPartida.REGISTRAR_JUGADOR
                && peticion.getJugadorLocal() != null
                && respuesta.getEstadoPartida() != null) {
            respuesta.getEstadoPartida().setIdJugador(peticion.getJugadorLocal().getId());
        }

        return respuesta;
    }

    private TipoAccionPartida obtenerAccionRespuesta(TipoAccionPartida accionOriginal) {
        return switch (accionOriginal) {
            case REGISTRAR_JUGADOR ->
                TipoAccionPartida.JUGADOR_REGISTRADO;
            case SOLICITAR_UNIRSE_PARTIDA ->
                TipoAccionPartida.SOLICITUD_UNION_RECIBIDA;
            case ACEPTAR_SOLICITUD_UNION ->
                TipoAccionPartida.SOLICITUD_UNION_ACEPTADA;
            case RECHAZAR_SOLICITUD_UNION ->
                TipoAccionPartida.SOLICITUD_UNION_RECHAZADA;
            default ->
                esAccionDeJuego(accionOriginal)
                ? TipoAccionPartida.CARGAR_PARTIDA
                : accionOriginal;
        };
    }

    private boolean esAccionDeJuego(TipoAccionPartida accion) {
        return accion == TipoAccionPartida.INICIAR_PARTIDA
                || accion == TipoAccionPartida.JUGAR_CARTA
                || accion == TipoAccionPartida.ROBAR_CARTA
                || accion == TipoAccionPartida.ELEGIR_COLOR
                || accion == TipoAccionPartida.TIRAR_RULETA
                || accion == TipoAccionPartida.GRITAR_UNO
                || accion == TipoAccionPartida.TERMINAR_TURNO
                || accion == TipoAccionPartida.SOLICITAR_FINALIZACION
                || accion == TipoAccionPartida.ACEPTAR_FINALIZACION
                || accion == TipoAccionPartida.RECHAZAR_FINALIZACION;
    }

    private RespuestaFinalizacionDTO crearRespuestaFinalizacion(PeticionJugadaDTO peticion, boolean acepta) {
        RespuestaFinalizacionDTO respuesta = new RespuestaFinalizacionDTO();
        respuesta.setJugador(obtenerJugadorPeticion(peticion));
        respuesta.setAcepta(acepta);
        return respuesta;
    }

    private DTOs.JugadorResumenDTO obtenerJugadorPeticion(PeticionJugadaDTO peticion) {
        return peticion.getJugadorLocal() != null
                ? peticion.getJugadorLocal()
                : peticion.getJugadorActualizar();
    }

}
