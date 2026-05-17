package Interfaces;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
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

    private final Partida partida;

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
        return this.partida.obtenerEstadoPartidaDTO();
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
    public void actualizarPerfilJugador(JugadorResumenDTO jugadorDTO) {
        this.partida.actualizarPerfilJugador(jugadorDTO);
    }
}
