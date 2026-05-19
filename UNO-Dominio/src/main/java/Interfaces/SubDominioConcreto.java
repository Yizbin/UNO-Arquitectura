package Interfaces;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.RespuestaFinalizacionDTO;
import DTOs.ResultadoFinalizacionDTO;
import Entidades.Partida;
import Enums.AccionesPosibles;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class SubDominioConcreto implements ISubDominio {

    private Partida partida;

    public SubDominioConcreto(Partida partida) {
        this.partida = partida;
    }

    @Override
    public void prepararJuego(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException {
        partida.cargarJugadoresDesdeDTO(jugadoresDTO);
        partida.iniciarPartida();
    }

    @Override
    public void solicitarUnion(JugadorResumenDTO jugadorSolicitante) {
        this.partida.solicitarUnion(jugadorSolicitante);
    }

    @Override
    public void aceptarSolicitudUnion(int idJugadorSolicitante) {
        this.partida.aceptarSolicitudUnion(idJugadorSolicitante);
    }

    @Override
    public void rechazarSolicitudUnion(int idJugadorSolicitante) {
        this.partida.rechazarSolicitudUnion(idJugadorSolicitante);
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
        return this.partida != null ? this.partida.obtenerEstadoPartidaDTO() : new EstadoPartidaDTO();
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
}
