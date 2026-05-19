/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.RespuestaFinalizacionDTO;
import DTOs.ResultadoFinalizacionDTO;
import DTOs.ConfiguracionPartidaDTO;
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
public interface ISubDominio {

    public void prepararJuego(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException;
    
    public void configurarPartida(ConfiguracionPartidaDTO configuracionDTO);

    void solicitarUnion(JugadorResumenDTO jugadorSolicitante);

    void aceptarSolicitudUnion(int idJugadorSolicitante);

    void rechazarSolicitudUnion(int idJugadorSolicitante);

    //Intenta jugar una carta de la mano del jugador actual hacia el descarte
    public void jugarCarta(int idJugador, CartaDTO cartaAJugarDTO) throws ValidarManoException, ValidarTurnoException, JugadaValidaException, MazoVacioException;

    //Hace que el jugador actual robe una carta del mazo
    public void robarCarta(int idJugador) throws MazoVacioException, ValidarTurnoException;

    //Define el nuevo color en juego cuando un jugador tira un comodín
    public void elegirColorComodin(TipoColor nuevoColor) throws MazoVacioException;

    //Gira la ruleta y devuelve la acción que debe realizarse (cuando se tira una carta Spin)
    public AccionesPosibles tirarRuleta();

    //Permite a un jugador protegerse gritando "¡UNO!"
    public void gritarUno(int idJugador);

    //Pasa el turno al siguiente jugador según el sentido actual del juego
    public void terminarTurno();

    // Devuelve quién es el jugador que debe tirar en este momento
    public JugadorResumenDTO obtenerJugadorActual();

    // Devuelve la carta que está visible en el tope del descarte
    public CartaDTO obtenerCartaEnTope();

    // Devuelve el color que está activo en el juego (útil si hay un comodín en el tope)
    public TipoColor obtenerColorActual();

    public List<CartaDTO> obtenerManoJugador(int idJugador);

    public EstadoPartidaDTO obtenerEstadoPartida();

    //finalizar partida
    void solicitarFinalizacion(JugadorResumenDTO jugador);

    void responderFinalizacion(RespuestaFinalizacionDTO respuesta);

    RespuestaFinalizacionDTO registrarRespuestaFinalizacion(RespuestaFinalizacionDTO respuestaDTO);

    ResultadoFinalizacionDTO evaluarFinalizacion();

    // METODOS PARA LA SALA
    public boolean confirmarInicioPartida(JugadorResumenDTO jugadorDTO);

    public List<JugadorResumenDTO> obtenerJugadoresConfirmados();

    public boolean puedeIniciarPartida();

    //Metodos configuarar partida
    
}
