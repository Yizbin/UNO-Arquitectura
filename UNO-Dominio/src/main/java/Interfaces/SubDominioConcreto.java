package Interfaces;

import DTOs.CartaDTO;
import DTOs.ConfiguracionPartidaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.RespuestaFinalizacionDTO;
import DTOs.ResultadoFinalizacionDTO;
import DTOs.ConfiguracionPartidaDTO;
import DTOs.JugadorEstadoSalaDTO;
import Entidades.ConfiguracionPartida;
import Entidades.Partida;
import Enums.AccionesPosibles;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import Mappers.ConfiguracionMapper;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class SubDominioConcreto implements ISubDominio {

    private final Partida partida;
    private final ConfiguracionMapper configuracionMapper;

    public SubDominioConcreto() {
        this(new Partida());
    }

    public SubDominioConcreto(Partida partida) {
        this.partida = partida != null ? partida : new Partida();
        this.configuracionMapper = new ConfiguracionMapper();
    }

    @Override
    public void prepararJuego(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException {
        partida.cargarJugadoresDesdeDTO(jugadoresDTO);
        partida.iniciarPartida();
    }

    @Override
    public void solicitarUnion(JugadorResumenDTO jugadorSolicitante) {
        partida.solicitarUnion(jugadorSolicitante);
    }

    @Override
    public void aceptarSolicitudUnion(int idJugadorSolicitante) {
        partida.aceptarSolicitudUnion(idJugadorSolicitante);
    }

    @Override
    public void rechazarSolicitudUnion(int idJugadorSolicitante) {
        partida.rechazarSolicitudUnion(idJugadorSolicitante);
    }

    public boolean confirmarInicioPartida(JugadorResumenDTO jugadorDTO) {
        return partida.confirmarInicioPartida(jugadorDTO);
    }

    public List<JugadorResumenDTO> obtenerJugadoresConfirmados() {
        return partida.obtenerJugadoresConfirmados();
    }

    @Override
    public boolean puedeIniciarPartida() {
        return partida.puedeIniciarPartida();
    }

    @Override
    public void elegirColorComodin(TipoColor nuevoColor) throws MazoVacioException {
        partida.procesarColorComodin(nuevoColor);
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
        return partida.getColorActual();
    }

    @Override
    public List<CartaDTO> obtenerManoJugador(int idJugador) {
        return partida.obtenerManoJugadorDTO(idJugador);
    }

    @Override
    public EstadoPartidaDTO obtenerEstadoPartida() {
        return partida.obtenerEstadoPartidaDTO();
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

    @Override
    public void solicitarFinalizacion(JugadorResumenDTO jugador) {
        partida.solicitarFinalizacion(jugador);
    }

    @Override
    public void responderFinalizacion(RespuestaFinalizacionDTO respuesta) {
        registrarRespuestaFinalizacion(respuesta);
        evaluarFinalizacion();
    }

    @Override
    public RespuestaFinalizacionDTO registrarRespuestaFinalizacion(RespuestaFinalizacionDTO respuestaDTO) {
        return partida.registrarRespuestaFinalizacion(respuestaDTO);
    }

    @Override
    public ResultadoFinalizacionDTO evaluarFinalizacion() {
        return partida.evaluarFinalizacion();
    }

    @Override
    public void configurarPartida(ConfiguracionPartidaDTO configuracionDTO) {
        ConfiguracionPartida configuracionPartida = configuracionMapper.toEntity(configuracionDTO);
        partida.configurarPartida(configuracionPartida);
        partida.establecerDisponible();

        System.out.println("[CONFIG] Partida creada: " + (partida != null));
        System.out.println("[CONFIG] Configuración asignada: " + (partida.getConfiguracion() != null));
        System.out.println("[CONFIG] Partida disponible: " + partida.isDisponible());
        System.out.println("[CONFIG] Mazo creado en configurar partida: " + (partida.getMazo() != null));

    }

    @Override
    public boolean actualizarEstadoJugadorSala(JugadorEstadoSalaDTO jugadorEstadoDTO) {
        return this.partida.actualizarEstadoJugadorSala(jugadorEstadoDTO);
    }

    @Override
    public List<JugadorEstadoSalaDTO> obtenerEstadosJugadoresSala() {
        return this.partida.obtenerEstadosJugadoresSala();
    }
}
