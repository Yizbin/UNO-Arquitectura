package Interfaces;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Entidades.Jugador;
import Entidades.Partida;
import Enums.AccionesPosibles;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import Mappers.CartaMapper;
import Mappers.JugadorMapper;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class SubDominioConcreto implements ISubDominio {

    private Partida partida;
    private final CartaMapper cartaMapper;
    private final JugadorMapper jugadorMapper;

    public SubDominioConcreto() {
        this.cartaMapper = new CartaMapper();
        this.jugadorMapper = new JugadorMapper();
    }

    @Override
    public void prepararJuego(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException {
        List<Jugador> jugadores = jugadorMapper.toEntityList(jugadoresDTO);

        this.partida = new Partida(jugadores);
        this.partida.iniciarPartida();
    }

    @Override
    public void unirJugador(JugadorResumenDTO jugadorDTO) {
        if (this.partida == null) {
            this.partida = partida.desdeJugadoresDTO(List.of());
        }
        
        this.partida.unirJugador(jugadorDTO);
    }

    @Override
    public boolean confirmarInicioPartida(JugadorResumenDTO jugadorDTO) {
        if (this.partida == null || jugadorDTO == null) {
            return false;
        }

        return this.partida.confirmarInicioPartida(jugadorDTO);
    }

    @Override
    public List<JugadorResumenDTO> obtenerJugadoresConfirmados() {
        if (this.partida == null) {
            return List.of();
        }

        return this.partida.obtenerJugadoresConfirmados();
    }

    @Override
    public boolean puedeIniciarPartida() {
        return this.partida != null && this.partida.puedeIniciarPartida();
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
}
