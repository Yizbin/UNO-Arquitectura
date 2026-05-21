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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Partida {

    private static final int MINIMO_JUGADORES_PARA_INICIAR = 2;
    private static final int MAXIMO_JUGADORES_PARTIDA = 4;
    private static final int ID_ANFITRION = 1;

    /**
     * Lista de los jugadores registrados en la partida
     */
    private List<Jugador> jugadores;
    /**
     * Clase que gestiona los turnos de lo sjugadores
     */
    private Turno turno;
    /**
     * Mazo de la partida (contiene la lista de cartas),
     */
    private Mazo mazo;
    /**
     * Descarte de la partida, cuenta con validaciones de la jugada
     */
    private Descarte descarte;
    /**
     * Ruleta con la que cuenta la partida
     */
    private Ruleta ruleta;
    /**
     * Configuracion con la que contara la partida
     */
    private ConfiguracionPartida configuracion;
    /**
     *
     */
    private boolean disponible;
    /**
     *
     */
    private EstadoFinalizacion estadoFinalizacion;
    /**
     *
     */
    private ResultadoFinalizacionDTO resultadoFinalizacion;
    /**
     *
     */
    private TablaPosicionesDTO tablaPosiciones;
    /**
     *
     */
    private final Map<Integer, RespuestaFinalizacionDTO> respuestasFinalizacion;
    private final Map<Integer, Jugador> jugadoresRegistrados;

    /**
     *
     */
    private final JugadorMapper jugadorMapper;

    /**
     *
     */
    public Partida() {
        this.jugadores = List.of();
        this.turno = new Turno(0, this.jugadores);
        this.descarte = new Descarte();
        this.ruleta = new Ruleta();
        this.configuracion = null;
        this.disponible = false;
        this.estadoFinalizacion = EstadoFinalizacion.SIN_SOLICITUD;
        this.respuestasFinalizacion = new HashMap<>();
        this.jugadoresRegistrados = new HashMap<>();
        this.jugadorMapper = new JugadorMapper();

    }

    /**
     *
     * @param idJugadorSolicitante
     */
    public void solicitarUnion(int idJugadorSolicitante) {
        if (estaIniciada()) {
            throw new IllegalStateException("No se puede solicitar unirse a una partida iniciada.");
        }

        if (jugadores.size() >= 4) {
            throw new IllegalStateException("La partida ya alcanzo el numero maximo de jugadores.");
        }

        if (jugadorYaEstaUnido(idJugadorSolicitante)) {
            throw new IllegalArgumentException("El jugador ya esta unido a la partida.");
        }

        Jugador jugadorSolicitante = jugadoresRegistrados.get(idJugadorSolicitante);

        if (jugadorSolicitante == null) {
            throw new IllegalArgumentException("Jugador no registrado con ID: " + idJugadorSolicitante);
        }

        jugadorSolicitante.setAceptado(false);
        jugadorSolicitante.setEstadoSala(EstadoJugadorSala.ESPERANDO);

        List<Jugador> jugadoresActualizados = new ArrayList<>(jugadores);
        jugadoresActualizados.add(jugadorSolicitante);

        this.jugadores = List.copyOf(jugadoresActualizados);
        this.turno.setJugadores(this.jugadores);
    }

    /**
     *
     * @param idJugadorSolicitante
     */
    public void aceptarSolicitudUnion(int idJugadorSolicitante) {
        if (estaIniciada()) {
            throw new IllegalStateException("No se puede aceptar jugadores en una partida iniciada.");
        }

        Jugador jugador = obtenerJugadorPorId(idJugadorSolicitante);

        if (jugador.isAceptado()) {
            throw new IllegalArgumentException("El jugador ya fue aceptado.");
        }

        jugador.setAceptado(true);
        jugador.setEstadoSala(EstadoJugadorSala.ESPERANDO);
    }

    /**
     *
     * @param idJugadorSolicitante
     */
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
        this.turno.setJugadores(this.jugadores);
    }

    public void cargarJugadoresPartida(List<JugadorResumenDTO> jugadoresDTO) {
        if (jugadoresDTO == null) {
            return;
        }

        List<Jugador> jugadoresActualizados = new ArrayList<>(this.jugadores);

        for (JugadorResumenDTO jugadorDTO : jugadoresDTO) {
            if (jugadorDTO == null) {
                continue;
            }

            Jugador jugador = jugadorMapper.toEntity(jugadorDTO);
            jugador.setAceptado(jugadorDTO.isAceptado());

            reemplazarOAgregarJugador(jugadoresActualizados, jugador);
            jugadoresRegistrados.put(jugador.getId(), jugador);
        }

        this.jugadores = List.copyOf(jugadoresActualizados);
        this.turno.setJugadores(this.jugadores);
    }

    private void reemplazarOAgregarJugador(List<Jugador> jugadoresActualizados, Jugador jugador) {
        for (int i = 0; i < jugadoresActualizados.size(); i++) {
            if (jugadoresActualizados.get(i).getId() == jugador.getId()) {
                jugadoresActualizados.set(i, jugador);
                return;
            }
        }

        jugadoresActualizados.add(jugador);
    }

    /**
     *
     * @param idJugador
     * @return
     */
    private boolean jugadorYaEstaUnido(int idJugador) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == idJugador) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @return
     */
    private boolean estaIniciada() {
        return mazo != null;
    }

    /**
     *
     * @param jugadoresDTO
     */
    public void cargarJugadoresDesdeDTO(List<JugadorResumenDTO> jugadoresDTO) {
        this.jugadores = List.copyOf(new JugadorMapper().toEntityList(jugadoresDTO));
        this.turno = new Turno(0, this.jugadores);
    }

    public void cargarPartidaDesdeDTO(EstadoPartidaDTO estadoDTO) {
        if (estadoDTO == null) {
            throw new IllegalArgumentException("El estado de partida no puede ser nulo.");
        }

        if (estadoDTO.getJugadores() != null) {
            this.jugadores = List.copyOf(jugadorMapper.toEntityList(estadoDTO.getJugadores()));
            this.turno.setJugadores(this.jugadores);
            sincronizarTurnoConEstado(estadoDTO.getIdJugador());
        }

        if (estadoDTO.getEstadosJugadoresSala() != null) {
            for (JugadorEstadoSalaDTO estadoJugador : estadoDTO.getEstadosJugadoresSala()) {
                actualizarEstadoJugadorSala(estadoJugador);
            }
        }

        if (estadoDTO.getCartaEnDescarte() != null) {
            this.descarte.setTope(new CartaMapper().toEntity(estadoDTO.getCartaEnDescarte()));
        }

        if (estadoDTO.getMazo() != null) {
            Stack<Carta> cartasMazo = new Stack<>();
            cartasMazo.addAll(new CartaMapper().toEntityList(estadoDTO.getMazo()));
            this.mazo = new Mazo();
            this.mazo.setCartas(cartasMazo);
        }

        this.estadoFinalizacion = estadoDTO.getEstadoFinalizacion();
        this.resultadoFinalizacion = estadoDTO.getResultadoFinalizacion();
        this.respuestasFinalizacion.clear();
        if (estadoDTO.getRespuestasFinalizacion() != null) {
            for (RespuestaFinalizacionDTO respuesta : estadoDTO.getRespuestasFinalizacion()) {
                if (respuesta != null && respuesta.getJugador() != null) {
                    this.respuestasFinalizacion.put(respuesta.getJugador().getId(), respuesta);
                }
            }
        }
    }

    private void sincronizarTurnoConEstado(int idJugadorEnTurno) {
        if (idJugadorEnTurno <= 0 || jugadores == null || jugadores.isEmpty()) {
            return;
        }

        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getId() == idJugadorEnTurno) {
                this.turno.setIndiceTurnoActual(i);
                return;
            }
        }
    }

    /**
     *
     * @throws MazoVacioException
     */
    public void iniciarPartida() throws MazoVacioException {
        iniciarPartida(1);
    }

    public void iniciarPartida(int idJugadorSolicitante) throws MazoVacioException {
        if (idJugadorSolicitante != ID_ANFITRION) {
            throw new IllegalStateException("Solo el anfitrion puede iniciar la partida.");
        }

        if (estaIniciada()) {
            throw new IllegalStateException("La partida ya fue iniciada.");
        }

        if (!puedeIniciarPartida()) {
            throw new IllegalStateException("La partida aun no cumple las condiciones para iniciar.");
        }

        crearMazo();
        inicializarTurno();
        repartirCartasIniciales();
        colocarPrimeraCarta();
    }

    private void crearMazo() {
        this.mazo = Mazo.crear();
    }

    private void inicializarTurno() {
        this.turno = new Turno(0, this.jugadores);
    }

    private void repartirCartasIniciales() throws MazoVacioException {
        for (Jugador jugador : jugadores) {
            jugador.setMano(mazo.obtenerMano());
        }
    }

    private void colocarPrimeraCarta() throws MazoVacioException {
        Carta primeraCarta = mazo.sacarCarta();
        descarte.apilarCarta(primeraCarta);
    }

    /**
     *
     * @param jugador
     * @param cartaAJugar
     * @throws ValidarManoException
     * @throws ValidarTurnoException
     * @throws JugadaValidaException
     * @throws MazoVacioException
     */
    private void aplicarJugadaDeCarta(Jugador jugador, Carta cartaAJugar)
            throws ValidarManoException, ValidarTurnoException, JugadaValidaException, MazoVacioException {
        validarTurno(jugador);

        if (!descarte.puedeApilar(cartaAJugar)) {
            throw new JugadaValidaException("Jugada invalida. La carta no coincide en color o simbolo.");
        }

        Carta cartaJugada = this.getJugadorActual().jugarCarta(cartaAJugar);
        this.getJugadorActual().sumarPuntos(cartaJugada.getPuntuacion());
        this.descarte.apilarCarta(cartaJugada);

        if (cartaJugada instanceof CartaNumero) {
            avanzarTurno();
            return;
        }

        if (cartaJugada instanceof CartaAccion cartaAccion) {
            aplicarEfectoAccion(cartaAccion);
        }
    }

    /**
     *
     * @param idJugador
     * @param cartaAJugarDTO
     * @throws ValidarManoException
     * @throws ValidarTurnoException
     * @throws JugadaValidaException
     * @throws MazoVacioException
     */
    public void procesarJugadaCarta(int idJugador, CartaDTO cartaAJugarDTO)
            throws ValidarManoException, ValidarTurnoException, JugadaValidaException, MazoVacioException {
        Jugador jugador = obtenerJugadorPorId(idJugador);
        Carta cartaAJugar = new CartaMapper().toEntity(cartaAJugarDTO);

        if (cartaAJugar == null) {
            throw new IllegalArgumentException("La carta a jugar no puede ser nula.");
        }

        aplicarJugadaDeCarta(jugador, cartaAJugar);
    }

    /**
     * Regresa el jugador en turno en una dto
     *
     * @return
     */
    public JugadorResumenDTO obtenerJugadorActualDTO() {
        Jugador jugadorActual = getJugadorActual();
        JugadorResumenDTO dto = jugadorMapper.toDTO(jugadorActual);
        dto.setEnTurno(true);
        return dto;
    }

    /**
     *
     * @return
     */
    public CartaDTO obtenerCartaEnTopeDTO() {
        return new CartaMapper().toDTO(descarte.getTope());
    }

    /**
     *
     * @param idJugador
     * @return
     */
    public List<CartaDTO> obtenerManoJugadorDTO(int idJugador) {
        return new CartaMapper().toDTOList(obtenerJugadorPorId(idJugador).getMano());
    }

    /**
     *
     * @return
     */
    public EstadoPartidaDTO obtenerEstadoPartidaDTO() {
        EstadoPartidaDTO estadoDTO = new EstadoPartidaDTO();

        List<JugadorResumenDTO> jugadoresDTO
                = jugadores != null ? jugadorMapper.toDTOList(jugadores) : List.of();

        if (jugadores != null && !jugadores.isEmpty()) {
            int idJugadorActual = getJugadorActual().getId();

            for (JugadorResumenDTO jugadorDTO : jugadoresDTO) {
                jugadorDTO.setEnTurno(jugadorDTO.getId() == idJugadorActual);
                jugadorDTO.setMano(obtenerManoJugadorDTO(jugadorDTO.getId()));
            }

            estadoDTO.setIdJugador(idJugadorActual);
        }

        estadoDTO.setJugadores(jugadoresDTO);

        estadoDTO.setEstadosJugadoresSala(obtenerEstadosJugadoresSala());
        if (descarte != null && descarte.getTope() != null) {
            estadoDTO.setCartaEnDescarte(obtenerCartaEnTopeDTO());
        }
        if (mazo != null) {
            estadoDTO.setMazo(new CartaMapper().toDTOList(mazo.getCartas()));
        }

        estadoDTO.setRuletaActiva(false);
        estadoDTO.setInicioPermitido(puedeIniciarPartida());
        estadoDTO.setEstadosJugadoresSala(obtenerEstadosJugadoresSala());

        estadoDTO.setEstadoFinalizacion(estadoFinalizacion);
        estadoDTO.setResultadoFinalizacion(resultadoFinalizacion);
        estadoDTO.setRespuestasFinalizacion(new ArrayList<>(respuestasFinalizacion.values()));

        return estadoDTO;
    }

    /**
     *
     * @param jugador
     * @return
     */
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

    /**
     *
     * @param respuestaDTO
     * @return
     */
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

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    private EstadoFinalizacion determinarEstadoFinalizacion() {
        if (!todosAceptaronFinalizacion()) {
            return EstadoFinalizacion.CANCELADA;
        }

        if (todosRespondieronFinalizacion()) {
            return EstadoFinalizacion.FINALIZADA;
        }

        return EstadoFinalizacion.EN_ESPERA_RESPUESTAS;
    }

    /**
     *
     * @return
     */
    private boolean todosRespondieronFinalizacion() {
        int totalJugadores = jugadores != null ? jugadores.size() : 0;
        return totalJugadores > 0 && respuestasFinalizacion.size() >= totalJugadores;
    }

    /**
     *
     * @return
     */
    private boolean todosAceptaronFinalizacion() {
        for (RespuestaFinalizacionDTO respuesta : respuestasFinalizacion.values()) {
            if (!Boolean.TRUE.equals(respuesta.getAcepta())) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return
     */
    private TablaPosicionesDTO calcularTablaPosiciones() {
        List<JugadorResumenDTO> posiciones = new JugadorMapper().toDTOList(jugadores);
        posiciones.sort(
                Comparator.comparingInt(JugadorResumenDTO::getPuntos).reversed()
                        .thenComparingInt(JugadorResumenDTO::getCantidadDeCartas)
                        .thenComparing(JugadorResumenDTO::getNombreUsuario, Comparator.nullsLast(String::compareToIgnoreCase))
        );
        return new TablaPosicionesDTO(posiciones);
    }

    /**
     *
     * @param jugador
     * @return
     */
    private String obtenerNombreJugador(JugadorResumenDTO jugador) {
        return jugador.getNombreUsuario() != null && !jugador.getNombreUsuario().isBlank()
                ? jugador.getNombreUsuario()
                : "Jugador " + jugador.getId();
    }

    /**
     *
     * @param jugador
     * @throws MazoVacioException
     * @throws ValidarTurnoException
     */
    public void robarCarta(Jugador jugador) throws MazoVacioException, ValidarTurnoException {
        validarTurno(jugador);
        this.getJugadorActual().robarCarta(obtenerCartaDelMazo());
    }

    /**
     *
     * @param idJugador
     * @throws MazoVacioException
     * @throws ValidarTurnoException
     */
    public void robarCarta(int idJugador) throws MazoVacioException, ValidarTurnoException {
        robarCarta(obtenerJugadorPorId(idJugador));
    }

    /**
     *
     * @param jugador
     */
    public void gritarUno(Jugador jugador) {
        for (Jugador j : jugadores) {
            if (j.equals(jugador)) {
                j.gritarUno();
                break;
            }
        }
    }

    /**
     *
     * @param idJugador
     */
    public void gritarUno(int idJugador) {
        gritarUno(obtenerJugadorPorId(idJugador));
    }

    /**
     *
     * @return
     */
    public Jugador getJugadorActual() {
        return turno.obtenerJugadorActual();
    }

    /**
     *
     */
    public void avanzarTurno() {
        turno.avanzar();
    }

    /**
     *
     */
    public void invertirSentido() {
        turno.invertirSentido();
    }

    /**
     *
     * @param jugador
     * @throws MazoVacioException
     */
    public void penalizarJugador(Jugador jugador) throws MazoVacioException {
        if (jugador.esVulnerableAlCastigo()) {
            jugador.robarCarta(obtenerCartaDelMazo());
            jugador.robarCarta(obtenerCartaDelMazo());
        }
    }

    /**
     *
     * @param jugador
     * @param cantidad
     * @throws MazoVacioException
     */
    public void aplicarCastigo(Jugador jugador, int cantidad) throws MazoVacioException {
        for (int i = 0; i < cantidad; i++) {
            jugador.robarCarta(obtenerCartaDelMazo());
        }
    }

    /**
     *
     * @param nuevoColor
     * @throws MazoVacioException
     */
    public void procesarColorComodin(TipoColor nuevoColor) throws MazoVacioException {
        this.descarte.elegirColor(nuevoColor);

        Carta tope = this.descarte.getTope();
        if (tope instanceof CartaComodin cartaComodin && cartaComodin.getTipoComodin() == Comodines.TOMA_CUATRO) {
            avanzarTurno();
            aplicarCastigo(getJugadorActual(), 4);
        }
        avanzarTurno();
    }

    /**
     *
     * @param jugador
     * @throws ValidarTurnoException
     */
    private void validarTurno(Jugador jugador) throws ValidarTurnoException {
        turno.validarTurno(jugador);
    }

    /**
     *
     * @param idJugador
     * @return
     */
    private Jugador obtenerJugadorPorId(int idJugador) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == idJugador) {
                return jugador;
            }
        }
        throw new IllegalArgumentException("Jugador no encontrado con ID: " + idJugador);
    }

    /**
     *
     * @return @throws MazoVacioException
     */
    private Carta obtenerCartaDelMazo() throws MazoVacioException {
        if (mazo == null) {
            throw new IllegalStateException("La partida no tiene mazo inicializado.");
        }
        if (mazo.estaVacio()) {
            mazo.rellenar(descarte.vaciarParaRellenarMazo());
        }
        return mazo.sacarCarta();
    }

    /**
     *
     * @param carta
     * @throws MazoVacioException
     */
    private void aplicarEfectoAccion(CartaAccion carta) throws MazoVacioException {
        switch (carta.getTipoAccion()) {
            case REVERSA ->
                aplicarReversa();
            case SALTA ->
                aplicarSalto();
            case TOMA_DOS ->
                aplicarTomaDos();
        }
    }

    private void aplicarReversa() {
        turno.aplicarReversa();
    }

    private void aplicarSalto() {
        turno.aplicarSalto();
    }

    private void aplicarTomaDos() throws MazoVacioException {
        Jugador jugadorCastigado = turno.avanzarYObtenerJugadorActual();
        aplicarCastigo(jugadorCastigado, 2);
        turno.avanzar();
    }

    /**
     *
     * @param jugadorDTO
     * @return
     */
    public boolean confirmarInicioPartida(JugadorResumenDTO jugadorDTO) {
        if (jugadorDTO == null) {
            return false;
        }

        Jugador jugador = obtenerJugadorPorId(jugadorDTO.getId());
        jugador.confirmarInicioPartida();
        return puedeIniciarPartida();
    }

    /**
     *
     * @return
     */
    public List<JugadorResumenDTO> obtenerJugadoresConfirmados() {
        List<JugadorResumenDTO> jugadoresConfirmados = new ArrayList<>();
        JugadorMapper mapper = new JugadorMapper();

        for (Jugador jugador : jugadores) {
            if (jugador.estaConfirmadoParaIniciar()) {
                jugadoresConfirmados.add(mapper.toDTO(jugador));
            }
        }
        return jugadoresConfirmados;
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

    /**
     *
     * @return
     */
    public boolean puedeIniciarPartida() {
        int totalJugadores = jugadores.size();

        if (totalJugadores < MINIMO_JUGADORES_PARA_INICIAR) {
            return false;
        }

        if (totalJugadores == MAXIMO_JUGADORES_PARTIDA) {
            return true;
        }

        if (totalJugadores < MAXIMO_JUGADORES_PARTIDA) {
            return todosLosJugadoresConfirmados();
        }

        return false;
    }

    /**
     *
     * @return
     */
    private boolean todosLosJugadoresConfirmados() {
        for (Jugador jugador : jugadores) {
            if (!jugador.estaConfirmadoParaIniciar()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param configuracion
     * @return
     */
    public static Partida crearConConfiguracion(ConfiguracionPartida configuracion) {
        Partida partida = new Partida();
        partida.configurarPartida(configuracion);
        return partida;
    }

    /**
     *
     * @param configuracion
     */
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

    /**
     *
     * @return
     */
    public List<Jugador> getJugadores() {
        return List.copyOf(jugadores);
    }

    /**
     *
     * @param jugadores
     */
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = List.copyOf(jugadores);
        this.turno = new Turno(0, this.jugadores);
    }

    /**
     *
     * @return
     */
    public Mazo getMazo() {
        return mazo;
    }

    /**
     *
     * @param mazo
     */
    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    /**
     *
     * @return
     */
    public Descarte getDescarte() {
        return descarte;
    }

    /**
     *
     * @param descarte
     */
    public void setDescarte(Descarte descarte) {
        this.descarte = descarte != null ? descarte : new Descarte();
    }

    /**
     *
     * @return
     */
    public Ruleta getRuleta() {
        return ruleta;
    }

    /**
     *
     * @param ruleta
     */
    public void setRuleta(Ruleta ruleta) {
        this.ruleta = ruleta;
    }

    public TipoColor getColorActual() {
        return descarte.getColorActual();
    }

    /**
     *
     * @return
     */
    public ConfiguracionPartida getConfiguracion() {
        return configuracion;
    }

    /**
     *
     * @return
     */
    public boolean isDisponible() {
        return disponible;
    }

    public void registrarJugador(JugadorResumenDTO jugadorDTO) {
        if (jugadorDTO == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }

        if (estaIniciada()) {
            throw new IllegalStateException("No se pueden registrar jugadores cuando inicia el juego");
        }

        if (jugadorDTO.getId() <= 0) {
            jugadorDTO.setId(generarSiguienteIdJugador());
        }

        Jugador jugadorExistente = jugadoresRegistrados.get(jugadorDTO.getId());

        if (jugadorExistente != null) {
            jugadorExistente.actualizarPerfil(
                    jugadorDTO.getNombreUsuario(),
                    jugadorDTO.getRutaAvatar()
            );

            actualizarJugadorEnSala(jugadorExistente);
            return;
        }

        Jugador nuevoJugador = this.jugadorMapper.toEntity(jugadorDTO);
        boolean esAnfitrion = nuevoJugador.getId() == ID_ANFITRION;
        nuevoJugador.setAceptado(esAnfitrion);
        nuevoJugador.setEstadoSala(EstadoJugadorSala.ESPERANDO);

        jugadoresRegistrados.put(nuevoJugador.getId(), nuevoJugador);

        if (esAnfitrion && jugadores.isEmpty()) {
            List<Jugador> jugadoresActualizados = new ArrayList<>(jugadores);
            jugadoresActualizados.add(nuevoJugador);

            this.jugadores = List.copyOf(jugadoresActualizados);
            this.turno.setJugadores(this.jugadores);
        }
    }

    private int generarSiguienteIdJugador() {
        for (int id = 2; id <= 4; id++) {
            if (!jugadoresRegistrados.containsKey(id)) {
                return id;
            }
        }

        throw new IllegalStateException("La partida ya alcanzo el numero maximo de jugadores.");
    }

    private void actualizarJugadorEnSala(Jugador jugadorActualizado) {
        if (!jugadorYaEstaUnido(jugadorActualizado.getId())) {
            return;
        }

        List<Jugador> jugadoresActualizados = new ArrayList<>(jugadores);

        for (int i = 0; i < jugadoresActualizados.size(); i++) {
            if (jugadoresActualizados.get(i).getId() == jugadorActualizado.getId()) {
                jugadoresActualizados.set(i, jugadorActualizado);
                break;
            }
        }

        this.jugadores = List.copyOf(jugadoresActualizados);
        this.turno.setJugadores(this.jugadores);
    }

}
