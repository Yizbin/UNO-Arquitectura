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
import factorys.MazoFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Abraham Coronel
 */
public class Partida {

    private List<Jugador> jugadores;
    private int idAnfitrion;
    private List<Integer> solicitudesPendientes; //aqui va el id de los jugadores que solicitan unirse
    private boolean partidaIniciada;

    private Mazo mazo;
    private Descarte descarte;
    private Ruleta ruleta;
    private int indiceTurnoActual;
    private boolean sentidoHorario;
    private TipoColor colorActual;
    private boolean esperandoColor;

    public Partida() {
        this.jugadores = List.of();
        this.solicitudesPendientes = new ArrayList<>();
        this.partidaIniciada = false;
        this.descarte = new Descarte();
        this.ruleta = new Ruleta();
        this.indiceTurnoActual = 0;
        this.sentidoHorario = true;
        this.colorActual = TipoColor.NINGUNO;
        this.esperandoColor = false;
    }

    public Partida(int idAnfitrion) {
        this();
        this.idAnfitrion = idAnfitrion;
    }

    public Partida(int idAnfitrion, List<Jugador> jugadores) {
        this(idAnfitrion);
        this.jugadores = List.copyOf(jugadores);
    }

    public void solicitarUnion(int idJugadorSolicitante) {
        if (partidaIniciada) {
            throw new IllegalStateException("No se puede solicitar unirse a una partida iniciada.");
        }

        if (jugadores.size() >= 4) {
            throw new IllegalStateException("La partida ya alcanzo el numero maximo de jugadores.");
        }

        if (jugadorYaEstaUnido(idJugadorSolicitante)) {
            throw new IllegalArgumentException("El jugador ya esta unido a la partida.");
        }

        if (solicitudesPendientes.contains(idJugadorSolicitante)) {
            throw new IllegalArgumentException("El jugador ya tiene una solicitud pendiente.");
        }

        solicitudesPendientes.add(idJugadorSolicitante);
    }

    public void aceptarSolicitudUnion(int idAnfitrion, int idJugadorSolicitante) {
        validarAnfitrion(idAnfitrion);

        if (!solicitudesPendientes.contains(idJugadorSolicitante)) {
            throw new IllegalArgumentException("No existe una solicitud pendiente para ese jugador.");
        }

        solicitudesPendientes.remove(Integer.valueOf(idJugadorSolicitante));
        agregarJugadorAceptado(idJugadorSolicitante);
    }

    public void rechazarSolicitudUnion(int idAnfitrion, int idJugadorSolicitante) {
        validarAnfitrion(idAnfitrion);

        if (!solicitudesPendientes.contains(idJugadorSolicitante)) {
            throw new IllegalArgumentException("No existe una solicitud pendiente para ese jugador.");
        }

        solicitudesPendientes.remove(Integer.valueOf(idJugadorSolicitante));
    }

    private void agregarJugadorAceptado(int idJugador) {
        if (jugadorYaEstaUnido(idJugador)) {
            throw new IllegalArgumentException("El jugador ya esta unido a la partida.");
        }

        Jugador jugador = new Jugador(idJugador);

        List<Jugador> jugadoresActualizados = new ArrayList<>(jugadores);
        jugadoresActualizados.add(jugador);
        this.jugadores = List.copyOf(jugadoresActualizados);
    }

    private boolean jugadorYaEstaUnido(int idJugador) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == idJugador) {
                return true;
            }
        }

        return false;
    }

    private void validarAnfitrion(int idAnfitrion) {
        if (this.idAnfitrion != idAnfitrion) {
            throw new IllegalArgumentException("Solo el anfitrion puede responder solicitudes.");
        }
    }

    public void cargarJugadoresDesdeDTO(List<JugadorResumenDTO> jugadoresDTO) {
        this.jugadores = List.copyOf(new JugadorMapper().toEntityList(jugadoresDTO));
    }

    public void actualizarPerfilJugador(JugadorResumenDTO jugadorDTO) {
        if (jugadorDTO == null) {
            throw new IllegalArgumentException("Los datos del jugador no pueden ser nulos.");
        }

        JugadorMapper mapper = new JugadorMapper();
        Jugador jugador = mapper.toEntity(jugadorDTO);
        jugador.actualizarPerfil(
                jugadorDTO.getNombreUsuario(),
                jugadorDTO.getRutaAvatar(),
                jugadorDTO.getPreferenciasColor()
        );
    }

    public void iniciarPartida() throws MazoVacioException {

        this.mazo = MazoFactory.crear();

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

    public EstadoPartidaDTO obtenerEstadoPartidaDTO() {
        EstadoPartidaDTO estadoDTO = new EstadoPartidaDTO();
        List<JugadorResumenDTO> jugadoresDTO = new JugadorMapper().toDTOList(jugadores);

        if (!jugadores.isEmpty()) {
            int idJugadorActual = getJugadorActual().getId();

            for (JugadorResumenDTO jugadorDTO : jugadoresDTO) {
                jugadorDTO.setEnTurno(jugadorDTO.getId() == idJugadorActual);
            }

            estadoDTO.setIdJugador(idJugadorActual);
        }

        estadoDTO.setJugadores(jugadoresDTO);
        estadoDTO.setEsperandoColor(esperandoColor);

        if (descarte != null && descarte.getTope() != null) {
            estadoDTO.setCartaEnDescarte(obtenerCartaEnTopeDTO());
        }

        estadoDTO.setRuletaActiva(false);

        estadoDTO.setPartidaListaParaIniciar(puedeIniciarPartida());

        estadoDTO.setIdAnfitrion(idAnfitrion);
        estadoDTO.setPartidaIniciada(partidaIniciada);
        estadoDTO.setSolicitudesPendientes(List.copyOf(solicitudesPendientes));

        return estadoDTO;
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

    public int getIdAnfitrion() {
        return idAnfitrion;
    }

    public void setIdAnfitrion(int idAnfitrion) {
        this.idAnfitrion = idAnfitrion;
    }

    public List<Integer> getSolicitudesPendientes() {
        return List.copyOf(solicitudesPendientes);
    }

    public boolean isPartidaIniciada() {
        return partidaIniciada;
    }

    public void setPartidaIniciada(boolean partidaIniciada) {
        this.partidaIniciada = partidaIniciada;
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
