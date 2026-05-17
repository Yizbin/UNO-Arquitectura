package Interfaces;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.RespuestaFinalizacionDTO;
import DTOs.ResultadoFinalizacionDTO;
import DTOs.SolicitudFinalizacionDTO;
import DTOs.TablaPosicionesDTO;
import Entidades.Partida;
import Enums.AccionesPosibles;
import Enums.EstadoFinalizacion;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Abraham Coronel
 */
public class SubDominioConcreto implements ISubDominio {

    private Partida partida;
    private EstadoFinalizacion estadoFinalizacion = EstadoFinalizacion.SIN_SOLICITUD;
    private SolicitudFinalizacionDTO solicitudFinalizacion;
    private ResultadoFinalizacionDTO resultadoFinalizacion;
    private TablaPosicionesDTO tablaPosiciones;
    private final Map<Integer, RespuestaFinalizacionDTO> respuestasFinalizacion = new HashMap<>();

    public SubDominioConcreto(Partida partida) {
        this.partida = partida;
    }

    @Override
    public void prepararJuego(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException {
        partida.cargarJugadoresDesdeDTO(jugadoresDTO);
        partida.iniciarPartida();
    }

    @Override
    public void actualizarPerfilJugador(JugadorResumenDTO datosPerfil) {
        this.partida.actualizarPerfilJugador(datosPerfil);
    }

    @Override
    public void unirJugador(int idJugador) {
        this.partida.unirJugador(idJugador);
    }

    @Override
    public boolean confirmarInicioPartida(JugadorResumenDTO jugadorDTO) {
        return this.partida.confirmarInicioPartida(jugadorDTO);
    }

    @Override
    public List<JugadorResumenDTO> obtenerJugadoresConfirmados() {
        return this.partida.obtenerJugadoresConfirmados();
    }

    @Override
    public boolean puedeIniciarPartida() {
        return this.partida.puedeIniciarPartida();
    }

    @Override
    public void elegirColorComodin(TipoColor nuevoColor) throws MazoVacioException {
        this.partida.procesarColorComodin(nuevoColor);
    }

    @Override
    public AccionesPosibles tirarRuleta() {
        return partida.getRuleta().girar();
    }

    @Override
    public void terminarTurno() {
        partida.avanzarTurno();
    }

    @Override
    public JugadorResumenDTO obtenerJugadorActual() {
        return partida.obtenerJugadorActualDTO();
    }

    @Override
    public CartaDTO obtenerCartaEnTope() {
        return partida.obtenerCartaEnTopeDTO();
    }

    @Override
    public TipoColor obtenerColorActual() {
        return this.partida.getColorActual();
    }

    @Override
    public List<CartaDTO> obtenerManoJugador(int idJugador) {
        return partida.obtenerManoJugadorDTO(idJugador);
    }

    @Override
    public EstadoPartidaDTO obtenerEstadoPartida() {

        EstadoPartidaDTO estado = this.partida != null ? this.partida.obtenerEstadoPartidaDTO() : new EstadoPartidaDTO();
        estado.setEstadoFinalizacion(estadoFinalizacion);
        estado.setSolicitudFinalizacion(solicitudFinalizacion);
        estado.setResultadoFinalizacion(resultadoFinalizacion);
        estado.setTablaPosiciones(tablaPosiciones);
        return estado;
    }

    @Override
    public void jugarCarta(int idJugador, CartaDTO cartaAJugarDTO)
            throws ValidarManoException, ValidarTurnoException, JugadaValidaException, MazoVacioException {
        partida.procesarJugadaCarta(idJugador, cartaAJugarDTO);
    }

    @Override
    public void robarCarta(int idJugador) throws MazoVacioException, ValidarTurnoException {
        partida.robarCarta(idJugador);
    }

    @Override
    public void gritarUno(int idJugador) {
        partida.gritarUno(idJugador);
    }
    
    
    //finalizar partida
    @Override
    public void solicitarFinalizacion(SolicitudFinalizacionDTO solicitud) {
        if (solicitud == null || solicitud.getJugador() == null) {
            throw new IllegalArgumentException("La solicitud de finalizacion debe incluir un jugador.");
        }

        this.solicitudFinalizacion = solicitud;
        this.estadoFinalizacion = EstadoFinalizacion.EN_ESPERA_RESPUESTAS;
        this.resultadoFinalizacion = null;
        this.tablaPosiciones = null;
        this.respuestasFinalizacion.clear();

        RespuestaFinalizacionDTO respuestaSolicitante = new RespuestaFinalizacionDTO();
        respuestaSolicitante.setJugador(solicitud.getJugador());
        respuestaSolicitante.setFecha(new Date());
        respuestaSolicitante.setAcepta(Boolean.TRUE);
        this.respuestasFinalizacion.put(solicitud.getJugador().getId(), respuestaSolicitante);

        evaluarFinalizacion();
    }

    @Override
    public void responderFinalizacion(RespuestaFinalizacionDTO respuesta) {
        if (estadoFinalizacion != EstadoFinalizacion.EN_ESPERA_RESPUESTAS) {
            return;
        }

        if (respuesta == null || respuesta.getJugador() == null) {
            throw new IllegalArgumentException("La respuesta de finalizacion debe incluir un jugador.");
        }

        respuestasFinalizacion.put(respuesta.getJugador().getId(), respuesta);
        evaluarFinalizacion();
    }

    private void evaluarFinalizacion() {
        for (RespuestaFinalizacionDTO respuesta : respuestasFinalizacion.values()) {
            if (!Boolean.TRUE.equals(respuesta.getAcepta())) {
                estadoFinalizacion = EstadoFinalizacion.CANCELADA;
                resultadoFinalizacion = new ResultadoFinalizacionDTO(
                        estadoFinalizacion,
                        null,
                        "La finalizacion fue cancelada porque un jugador rechazo la solicitud."
                );
                return;
            }
        }

        int totalJugadores = obtenerJugadoresActuales().size();
        if (totalJugadores > 0 && respuestasFinalizacion.size() >= totalJugadores) {
            estadoFinalizacion = EstadoFinalizacion.FINALIZADA;
            tablaPosiciones = generarTablaPosiciones();
            resultadoFinalizacion = new ResultadoFinalizacionDTO(
                    estadoFinalizacion,
                    tablaPosiciones,
                    "La partida finalizo por acuerdo de todos los jugadores."
            );
        }
    }

    private TablaPosicionesDTO generarTablaPosiciones() {
        List<JugadorResumenDTO> posiciones = new ArrayList<>(obtenerJugadoresActuales());
        posiciones.sort(
                Comparator.comparingInt(JugadorResumenDTO::getPuntos).reversed()
                        .thenComparingInt(JugadorResumenDTO::getCantidadDeCartas)
                        .thenComparing(JugadorResumenDTO::getNombreUsuario, Comparator.nullsLast(String::compareToIgnoreCase))
        );
        return new TablaPosicionesDTO(posiciones, new Date());
    }

    private List<JugadorResumenDTO> obtenerJugadoresActuales() {
        EstadoPartidaDTO estado = partida != null ? partida.obtenerEstadoPartidaDTO() : new EstadoPartidaDTO();
        return estado.getJugadores() != null ? estado.getJugadores() : List.of();
    }
}
