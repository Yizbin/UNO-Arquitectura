package Entidades;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Enums.Comodines;
import Enums.EstadoJugadorSala;
import Enums.TipoColor;
import Excepciones.JugadaValidaException;
import Excepciones.MazoVacioException;
import Excepciones.ValidarManoException;
import Excepciones.ValidarTurnoException;
import Mappers.CartaMapper;
import Mappers.JugadorMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Abraham Coronel
 */
public class Partida {

    private List<Jugador> jugadores;
    private Mazo mazo;
    private Descarte descarte;
    private Ruleta ruleta;
    private int indiceTurnoActual;
    private boolean sentidoHorario;
    private TipoColor colorActual;
    private boolean esperandoColor;
    
    private ConfiguracionPartida configuracion;
    private boolean disponible;

    

    public Partida(List<Jugador> jugadores, Mazo mazo) {
        this.jugadores = List.copyOf(jugadores);
        this.mazo = mazo;
        this.descarte = new Descarte();
        this.ruleta = new Ruleta();
        this.indiceTurnoActual = 0;
        this.sentidoHorario = true;
        this.colorActual = TipoColor.NINGUNO;
        this.esperandoColor = false;
    }

    public static Partida desdeJugadoresDTO(List<JugadorResumenDTO> jugadoresDTO, Mazo mazo) {
        List<Jugador> jugadores = new JugadorMapper().toEntityList(jugadoresDTO);
        return new Partida(jugadores, mazo);
    }

    public void unirJugador(JugadorResumenDTO jugadorDTO) {
        Jugador jugador = new JugadorMapper().toEntity(jugadorDTO);

        if (jugador == null) {
            throw new IllegalArgumentException("El jugador a unir no puede ser nulo.");
        }

        if (jugadores.contains(jugador)) {
            throw new IllegalArgumentException("El jugador ya esta unido a la partida.");
        }

        List<Jugador> jugadoresActualizados = new ArrayList<>(jugadores);
        jugadoresActualizados.add(jugador);
        this.jugadores = List.copyOf(jugadoresActualizados);
    }

    public void iniciarPartida() throws MazoVacioException {
        for (int i = 0; i < 7; i++) {
            for (Jugador jugador : jugadores) {
                jugador.robarCarta(mazo.sacarCarta());
            }
        }

        Carta primeraCarta = mazo.sacarCarta();
        descarte.apilarCarta(primeraCarta);

        switch (primeraCarta) {
            case CartaNumero cartaNumero ->
                this.colorActual = cartaNumero.getColor();
            case CartaAccion cartaAccion ->
                this.colorActual = cartaAccion.getColor();
            default ->
                this.colorActual = TipoColor.NINGUNO;
        }
    }

    private void aplicarJugadaDeCarta(Jugador jugador, Carta cartaAJugar)
            throws ValidarManoException, ValidarTurnoException, JugadaValidaException, MazoVacioException {
        validarTurno(jugador);

        Carta cartaEnTope = this.descarte.getTope();
        if (!cartaAJugar.esJugableSobre(cartaEnTope, this.colorActual)) {
            throw new JugadaValidaException("Jugada invalida. La carta no coincide en color o simbolo.");
        }

        Carta cartaJugada = this.getJugadorActual().jugarCarta(cartaAJugar);
        this.descarte.apilarCarta(cartaJugada);

        if (cartaJugada instanceof CartaNumero cartaNumero) {
            this.colorActual = cartaNumero.getColor();
            avanzarTurno();
            return;
        }

        if (cartaJugada instanceof CartaAccion cartaAccion) {
            this.colorActual = cartaAccion.getColor();
            aplicarEfectoAccion(cartaAccion);
            return;
        }

        if (cartaJugada instanceof CartaComodin) {
            this.esperandoColor = true;
        }
    }

    public void procesarJugadaCarta(int idJugador, CartaDTO cartaAJugarDTO)
            throws ValidarManoException, ValidarTurnoException, JugadaValidaException, MazoVacioException {
        Jugador jugador = obtenerJugadorPorId(idJugador);
        Carta cartaAJugar = new CartaMapper().toEntity(cartaAJugarDTO);

        if (cartaAJugar == null) {
            throw new IllegalArgumentException("La carta a jugar no puede ser nula.");
        }

        aplicarJugadaDeCarta(jugador, cartaAJugar);
    }

    public JugadorResumenDTO obtenerJugadorActualDTO() {
        JugadorResumenDTO dto = new JugadorMapper().toDTO(getJugadorActual());
        dto.setEnTurno(true);
        return dto;
    }

    public CartaDTO obtenerCartaEnTopeDTO() {
        return new CartaMapper().toDTO(descarte.getTope());
    }

    public List<CartaDTO> obtenerManoJugadorDTO(int idJugador) {
        return new CartaMapper().toDTOList(obtenerJugadorPorId(idJugador).getMano());
    }

//    public EstadoPartidaDTO obtenerEstadoPartidaDTO() {
//        EstadoPartidaDTO estadoDTO = new EstadoPartidaDTO();
//        List<JugadorResumenDTO> jugadoresDTO = new JugadorMapper().toDTOList(jugadores);
//        int idJugadorActual = getJugadorActual().getId();
//
//        for (JugadorResumenDTO jugadorDTO : jugadoresDTO) {
//            jugadorDTO.setEnTurno(jugadorDTO.getId() == idJugadorActual);
//        }
//
//        estadoDTO.setJugadores(jugadoresDTO);
//        estadoDTO.setEsperandoColor(esperandoColor);
//        estadoDTO.setCartaEnDescarte(obtenerCartaEnTopeDTO());
//        estadoDTO.setRuletaActiva(false);
//        estadoDTO.setIdJugadorEnTurno(idJugadorActual);
//        estadoDTO.setPartidaListaParaIniciar(puedeIniciarPartida());
//
//        return estadoDTO;
//    }
    
    public EstadoPartidaDTO obtenerEstadoPartidaDTO() {
        EstadoPartidaDTO estadoDTO = new EstadoPartidaDTO();
        List<JugadorResumenDTO> jugadoresDTO = new JugadorMapper().toDTOList(jugadores);

        int idJugadorActual = -1;

        if (jugadores!=null && !jugadores.isEmpty() ) {
            idJugadorActual = getJugadorActual().getId();

            for (JugadorResumenDTO jugadorDTO : jugadoresDTO) {
                jugadorDTO.setEnTurno(jugadorDTO.getId() == idJugadorActual);
            }

            estadoDTO.setIdJugadorEnTurno(idJugadorActual);
        }

        estadoDTO.setJugadores(jugadoresDTO);
        estadoDTO.setEsperandoColor(esperandoColor);
        estadoDTO.setRuletaActiva(false);
        estadoDTO.setPartidaListaParaIniciar(puedeIniciarPartida());
        

        if (descarte != null && descarte.getTope() != null) {
            estadoDTO.setCartaEnDescarte(obtenerCartaEnTopeDTO());
        }       
        return estadoDTO;
    }
    
    public static Partida crearConConfiguracion(ConfiguracionPartida configuracion) {
        Partida partida = new Partida(List.of(), null);
        partida.configurarPartida(configuracion);
        return partida;
    }

    public void robarCarta(Jugador jugador) throws MazoVacioException, ValidarTurnoException {
        validarTurno(jugador);
        this.getJugadorActual().robarCarta(obtenerCartaDelMazo());
    }

    public void robarCarta(int idJugador) throws MazoVacioException, ValidarTurnoException {
        robarCarta(obtenerJugadorPorId(idJugador));
    }

    public void gritarUno(Jugador jugador) {
        for (Jugador j : jugadores) {
            if (j.equals(jugador)) {
                j.gritarUno();
                break;
            }
        }
    }

    public void gritarUno(int idJugador) {
        gritarUno(obtenerJugadorPorId(idJugador));
    }

    public Jugador getJugadorActual() {
        return jugadores.get(indiceTurnoActual);
    }

    public void avanzarTurno() {
        if (sentidoHorario) {
            indiceTurnoActual = (indiceTurnoActual + 1) % jugadores.size();
        } else {
            indiceTurnoActual = (indiceTurnoActual - 1 + jugadores.size()) % jugadores.size();
        }
    }

    public void invertirSentido() {
        this.sentidoHorario = !this.sentidoHorario;
    }

    public void penalizarJugador(Jugador jugador) throws MazoVacioException {
        if (jugador.esVulnerableAlCastigo()) {
            jugador.robarCarta(obtenerCartaDelMazo());
            jugador.robarCarta(obtenerCartaDelMazo());
        }
    }

    public void aplicarCastigo(Jugador jugador, int cantidad) throws MazoVacioException {
        for (int i = 0; i < cantidad; i++) {
            jugador.robarCarta(obtenerCartaDelMazo());
        }
    }

    public void procesarColorComodin(TipoColor nuevoColor) throws MazoVacioException {
        this.colorActual = nuevoColor;
        this.esperandoColor = false;

        Carta tope = this.descarte.getTope();
        if (tope instanceof CartaComodin cartaComodin && cartaComodin.getTipoComodin() == Comodines.TOMA_CUATRO) {
            avanzarTurno();
            aplicarCastigo(getJugadorActual(), 4);
        }
        avanzarTurno();
    }

    private void validarTurno(Jugador jugador) throws ValidarTurnoException {
        if (!jugador.equals(this.getJugadorActual())) {
            throw new ValidarTurnoException("No es el turno de este jugador.");
        }
    }

    private Jugador obtenerJugadorPorId(int idJugador) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == idJugador) {
                return jugador;
            }
        }
        throw new IllegalArgumentException("Jugador no encontrado con ID: " + idJugador);
    }

    private Carta obtenerCartaDelMazo() throws MazoVacioException {
        if (mazo.estaVacio()) {
            mazo.rellenar(descarte.vaciarParaRellenarMazo());
        }
        return mazo.sacarCarta();
    }

    private void aplicarEfectoAccion(CartaAccion carta) throws MazoVacioException {
        switch (carta.getTipoAccion()) {
            case REVERSA -> {
                invertirSentido();
                avanzarTurno();
            }
            case SALTA -> {
                avanzarTurno();
                avanzarTurno();
            }
            case TOMA_DOS -> {
                avanzarTurno();
                aplicarCastigo(getJugadorActual(), 2);
                avanzarTurno();
            }
        }
    }

    // METODOS PARA LA SALA
    public void solicitarInicioPartida(JugadorResumenDTO jugadorDTO) {
        confirmarInicioPartida(jugadorDTO);
    }

    public boolean confirmarInicioPartida(JugadorResumenDTO jugadorDTO) {
        if (jugadorDTO == null) {
            return false;
        }

        for (Jugador jugador : jugadores) {
            boolean mismoId = jugador.getId() == jugadorDTO.getId();
            boolean mismoNombre = Objects.equals(jugador.getUsuario(), jugadorDTO.getNombreUsuario());

            if (mismoId || mismoNombre) {
                jugador.setEstadoSala(EstadoJugadorSala.CONFIRMADO);
                return puedeIniciarPartida();
            }
        }

        return false;
    }

    public List<JugadorResumenDTO> obtenerJugadoresConfirmados() {
        List<JugadorResumenDTO> jugadoresConfirmados = new ArrayList<>();
        JugadorMapper mapper = new JugadorMapper();

        for (Jugador jugador : jugadores) {
            if (jugador.getEstadoSala() == EstadoJugadorSala.CONFIRMADO) {
                jugadoresConfirmados.add(mapper.toDTO(jugador));
            }
        }

        return jugadoresConfirmados;
    }

    public boolean puedeIniciarPartida() {
        int totalJugadores = jugadores.size();

        if (totalJugadores == 4) {
            return true;
        }

        if (totalJugadores >= 2 && totalJugadores <= 3) {
            return todosLosJugadoresConfirmados();
        }

        return false;
    }

    private boolean todosLosJugadoresConfirmados() {
        for (Jugador jugador : jugadores) {
            if (jugador.getEstadoSala() != EstadoJugadorSala.CONFIRMADO) {
                return false;
            }
        }

        return true;
    }
    
    //ConfiguracionPartida
    public void configurarPartida(ConfiguracionPartida configuracion) {
        if (configuracion == null) {
            throw new IllegalArgumentException("La configuración no puede ser nula.");
        }
        configuracion.validarConfiguracion();

        this.configuracion = configuracion;
    }
    
    public void establecerDisponible() {
        if (this.configuracion == null) {
            throw new IllegalStateException("No se puede establecer disponible una partida sin configuración.");
        }
        this.disponible = true;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public ConfiguracionPartida getConfiguracion() {
        return configuracion;
    }
    public List<Jugador> getJugadores() {
        return List.copyOf(jugadores);
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = List.copyOf(jugadores);
    }

    public Mazo getMazo() {
        return mazo;
    }

    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    public Descarte getDescarte() {
        return descarte;
    }

    public void setDescarte(Descarte descarte) {
        this.descarte = descarte;
    }

    public Ruleta getRuleta() {
        return ruleta;
    }

    public void setRuleta(Ruleta ruleta) {
        this.ruleta = ruleta;
    }

    public int getIndiceTurnoActual() {
        return indiceTurnoActual;
    }

    public void setIndiceTurnoActual(int indiceTurnoActual) {
        this.indiceTurnoActual = indiceTurnoActual;
    }

    public boolean isSentidoHorario() {
        return sentidoHorario;
    }

    public void setSentidoHorario(boolean sentidoHorario) {
        this.sentidoHorario = sentidoHorario;
    }

    public TipoColor getColorActual() {
        return colorActual;
    }

    public void setColorActual(TipoColor colorActual) {
        this.colorActual = colorActual;
        this.esperandoColor = false;
    }

    public boolean isEsperandoColor() {
        return esperandoColor;
    }

    public void setEsperandoColor(boolean esperandoColor) {
        this.esperandoColor = esperandoColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jugadores, indiceTurnoActual, sentidoHorario, colorActual, esperandoColor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Partida other = (Partida) obj;
        return indiceTurnoActual == other.indiceTurnoActual
                && sentidoHorario == other.sentidoHorario
                && esperandoColor == other.esperandoColor
                && Objects.equals(jugadores, other.jugadores)
                && Objects.equals(colorActual, other.colorActual);
    }
}
