package Entidades;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorEstadoSalaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.RespuestaFinalizacionDTO;
import DTOs.ResultadoFinalizacionDTO;
import DTOs.TablaPosicionesDTO;
import Enums.Comodines;
import Enums.EstadoFinalizacion;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private EstadoFinalizacion estadoFinalizacion;
    private ResultadoFinalizacionDTO resultadoFinalizacion;
    private TablaPosicionesDTO tablaPosiciones;
    private final Map<Integer, RespuestaFinalizacionDTO> respuestasFinalizacion;

    public Partida() {
        this.jugadores = List.of();
        this.descarte = new Descarte();
        this.ruleta = new Ruleta();
        this.indiceTurnoActual = 0;
        this.sentidoHorario = true;
        this.colorActual = TipoColor.NINGUNO;
        this.esperandoColor = false;
        this.configuracion = null;
        this.disponible = false;
        this.estadoFinalizacion = EstadoFinalizacion.SIN_SOLICITUD;
        this.respuestasFinalizacion = new HashMap<>();

    }

    public void solicitarUnion(JugadorResumenDTO jugadorSolicitanteDTO) {
        if (jugadorSolicitanteDTO == null) {
            throw new IllegalArgumentException("El jugador solicitante no puede ser nulo.");
        }

        if (estaIniciada()) {
            throw new IllegalStateException("No se puede solicitar unirse a una partida iniciada.");
        }

        if (jugadores.size() >= 4) {
            throw new IllegalStateException("La partida ya alcanzo el numero maximo de jugadores.");
        }

        if (jugadorYaEstaUnido(jugadorSolicitanteDTO.getId())) {
            throw new IllegalArgumentException("El jugador ya esta unido a la partida.");
        }

        Jugador jugadorSolicitante = new JugadorMapper().toEntity(jugadorSolicitanteDTO);
        jugadorSolicitante.setAceptado(false);

        List<Jugador> jugadoresActualizados = new ArrayList<>(jugadores);
        jugadoresActualizados.add(jugadorSolicitante);
        this.jugadores = List.copyOf(jugadoresActualizados);
    }

    public void aceptarSolicitudUnion(int idJugadorSolicitante) {
        if (estaIniciada()) {
            throw new IllegalStateException("No se puede aceptar jugadores en una partida iniciada.");
        }

        Jugador jugador = obtenerJugadorPorId(idJugadorSolicitante);

        if (jugador.isAceptado()) {
            throw new IllegalArgumentException("El jugador ya fue aceptado.");
        }

        jugador.setAceptado(true);
    }

    public void rechazarSolicitudUnion(int idJugadorSolicitante) {
        if (estaIniciada()) {
            throw new IllegalStateException("No se puede responder solicitudes en una partida iniciada.");
        }

        Jugador jugador = obtenerJugadorPorId(idJugadorSolicitante);

        if (jugador.isAceptado()) {
            throw new IllegalArgumentException("No se puede rechazar a un jugador ya aceptado.");
        }

        List<Jugador> jugadoresActualizados = new ArrayList<>(jugadores);
        jugadoresActualizados.remove(jugador);
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

    private boolean estaIniciada() {
        return mazo != null;
    }

    public void cargarJugadoresDesdeDTO(List<JugadorResumenDTO> jugadoresDTO) {
        this.jugadores = List.copyOf(new JugadorMapper().toEntityList(jugadoresDTO));
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
        this.getJugadorActual().sumarPuntos(cartaJugada.getPuntuacion());
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
        if (descarte != null && descarte.getTope() != null) {
            estadoDTO.setCartaEnDescarte(obtenerCartaEnTopeDTO());
        }

        estadoDTO.setRuletaActiva(false);

        estadoDTO.setEstadoFinalizacion(estadoFinalizacion);
        estadoDTO.setResultadoFinalizacion(resultadoFinalizacion);

        return estadoDTO;
    }

    public ResultadoFinalizacionDTO solicitarFinalizacion(JugadorResumenDTO jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("La solicitud de finalizacion debe incluir un jugador.");
        }

        this.estadoFinalizacion = EstadoFinalizacion.EN_ESPERA_RESPUESTAS;
        this.resultadoFinalizacion = new ResultadoFinalizacionDTO(
                estadoFinalizacion,
                null,
                "El jugador " + obtenerNombreJugador(jugador) + " solicita finalizar la partida.",
                jugador
        );
        this.tablaPosiciones = null;
        this.respuestasFinalizacion.clear();

        RespuestaFinalizacionDTO respuestaSolicitante = new RespuestaFinalizacionDTO();
        respuestaSolicitante.setJugador(jugador);
        respuestaSolicitante.setAcepta(Boolean.TRUE);
        registrarRespuestaFinalizacion(respuestaSolicitante);

        return resultadoFinalizacion;
    }

    public RespuestaFinalizacionDTO registrarRespuestaFinalizacion(RespuestaFinalizacionDTO respuestaDTO) {
        if (estadoFinalizacion != EstadoFinalizacion.EN_ESPERA_RESPUESTAS) {
            return respuestaDTO;
        }

        if (respuestaDTO == null || respuestaDTO.getJugador() == null) {
            throw new IllegalArgumentException("La respuesta de finalizacion debe incluir un jugador.");
        }

        respuestasFinalizacion.put(respuestaDTO.getJugador().getId(), respuestaDTO);
        return respuestaDTO;
    }

    public ResultadoFinalizacionDTO evaluarFinalizacion() {
        if (estadoFinalizacion != EstadoFinalizacion.EN_ESPERA_RESPUESTAS) {
            return resultadoFinalizacion;
        }

        EstadoFinalizacion estadoDeterminado = determinarEstadoFinalizacion();

        if (estadoDeterminado == EstadoFinalizacion.CANCELADA) {
            estadoFinalizacion = EstadoFinalizacion.CANCELADA;
            resultadoFinalizacion = new ResultadoFinalizacionDTO(
                    estadoFinalizacion,
                    null,
                    "La finalizacion fue cancelada porque un jugador rechazo la solicitud.",
                    resultadoFinalizacion != null ? resultadoFinalizacion.getJugadorSolicitante() : null
            );
            return resultadoFinalizacion;
        }

        if (estadoDeterminado == EstadoFinalizacion.FINALIZADA) {
            estadoFinalizacion = EstadoFinalizacion.FINALIZADA;
            tablaPosiciones = calcularTablaPosiciones();
            resultadoFinalizacion = new ResultadoFinalizacionDTO(
                    estadoFinalizacion,
                    tablaPosiciones,
                    "La partida finalizo por acuerdo de todos los jugadores.",
                    resultadoFinalizacion != null ? resultadoFinalizacion.getJugadorSolicitante() : null
            );
            return resultadoFinalizacion;
        }

        return resultadoFinalizacion;
    }

    private EstadoFinalizacion determinarEstadoFinalizacion() {
        if (!todosAceptaronFinalizacion()) {
            return EstadoFinalizacion.CANCELADA;
        }

        if (todosRespondieronFinalizacion()) {
            return EstadoFinalizacion.FINALIZADA;
        }

        return EstadoFinalizacion.EN_ESPERA_RESPUESTAS;
    }

    private boolean todosRespondieronFinalizacion() {
        int totalJugadores = jugadores != null ? jugadores.size() : 0;
        return totalJugadores > 0 && respuestasFinalizacion.size() >= totalJugadores;
    }

    private boolean todosAceptaronFinalizacion() {
        for (RespuestaFinalizacionDTO respuesta : respuestasFinalizacion.values()) {
            if (!Boolean.TRUE.equals(respuesta.getAcepta())) {
                return false;
            }
        }
        return true;
    }

    private TablaPosicionesDTO calcularTablaPosiciones() {
        List<JugadorResumenDTO> posiciones = new JugadorMapper().toDTOList(jugadores);
        posiciones.sort(
                Comparator.comparingInt(JugadorResumenDTO::getPuntos).reversed()
                        .thenComparingInt(JugadorResumenDTO::getCantidadDeCartas)
                        .thenComparing(JugadorResumenDTO::getNombreUsuario, Comparator.nullsLast(String::compareToIgnoreCase))
        );
        return new TablaPosicionesDTO(posiciones);
    }

    private String obtenerNombreJugador(JugadorResumenDTO jugador) {
        return jugador.getNombreUsuario() != null && !jugador.getNombreUsuario().isBlank()
                ? jugador.getNombreUsuario()
                : "Jugador " + jugador.getId();
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
    public boolean actualizarEstadoJugadorSala(JugadorEstadoSalaDTO jugadorEstadoDTO) {
        if (jugadorEstadoDTO == null) {
            return false;
        }

        for (Jugador jugador : jugadores) {
            if (jugador.getId() == jugadorEstadoDTO.getId()) {
                jugador.setEstadoSala(jugadorEstadoDTO.getEstadoSala());
                return puedeIniciarPartida();
            }
        }

        return false;
    }

    public List<JugadorEstadoSalaDTO> obtenerEstadosJugadoresSala() {
        List<JugadorEstadoSalaDTO> estados = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            estados.add(new JugadorEstadoSalaDTO(
                    jugador.getId(),
                    jugador.getEstadoSala()
            ));
        }
        return estados;
    }

    public boolean puedeIniciarPartida() {
        int totalJugadores = jugadores.size();
        if (totalJugadores == 4) {
            return true;
        }
        if (totalJugadores == 2 || totalJugadores == 3) {
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

    public static Partida crearConConfiguracion(ConfiguracionPartida configuracion) {
        Partida partida = new Partida();
        partida.configurarPartida(configuracion);
        return partida;
    }

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

    public ConfiguracionPartida getConfiguracion() {
        return configuracion;
    }

    public boolean isDisponible() {
        return disponible;
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
