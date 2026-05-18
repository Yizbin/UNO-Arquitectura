package Interfaces;

import DTOs.CartaDTO;
import DTOs.ConfiguracionPartidaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Entidades.Mazo;
import Entidades.Partida;
import Entidades.ConfiguracionPartida;
import Enums.AccionesPosibles;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import factorys.MazoFactory;
import Mappers.ConfiguracionMapper;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class SubDominioConcreto implements ISubDominio {

    private Partida partida;
    private final MazoFactory mazoFactory;
    private final ConfiguracionMapper configuracionMapper;

    public SubDominioConcreto() {
        this.mazoFactory = new MazoFactory();
        this.configuracionMapper= new ConfiguracionMapper();
    }

    @Override
    public void prepararJuego(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException {
        Mazo mazo = generarMazoCompleto();

        this.partida = Partida.desdeJugadoresDTO(jugadoresDTO, mazo);
        this.partida.iniciarPartida();
    }

    @Override
    public void unirJugador(JugadorResumenDTO jugadorDTO) {
        if (this.partida == null) {
            this.partida = Partida.desdeJugadoresDTO(List.of(), generarMazoCompleto());
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

    private Mazo generarMazoCompleto() {
        return mazoFactory.crear();
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

    @Override
    public void configurarPartida(ConfiguracionPartidaDTO configuracionDTO) {
         ConfiguracionPartida configuracionPartida = configuracionMapper.toEntity(configuracionDTO);
         Mazo mazoConfigurado = mazoFactory.crear(configuracionPartida);

        if (partida == null) {
            partida = Partida.desdeJugadoresDTO(List.of(), mazoConfigurado);
        }

        partida.configurarPartida(configuracionPartida, mazoConfigurado);
        partida.establecerDisponible();
    }
}
