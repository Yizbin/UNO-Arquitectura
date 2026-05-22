/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_Sala;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Enums.EstadoJugadorSala;
import Enums.TipoColor;
import java.util.Map;

/**
 *
 * @author Abraham Coronel
 */
public interface IControlModeloSala {

    boolean solicitarUnirsePartida();
    
    boolean aceptarSolicitudUnion(int idJugadorSolicitante);
    
    boolean rechazarSolicitudUnion(int idJugadorSolicitante);

    boolean iniciarPartida(JugadorResumenDTO jugadorDTO);

    EstadoPartidaDTO crearEstadoInicioPartida();

    void abrirSalaEspera();

    void registrarJugador(JugadorResumenDTO datos);

    void establecerJugadorLocal(JugadorResumenDTO datos);

    boolean actualizarEstadoJugadorSala();

    EstadoJugadorSala obtenerEstadoJugador(int idJugador);

    void validarCondicionInicio(EstadoPartidaDTO estadoPartidaDTO);
    
    Map<TipoColor, TipoColor> getMisColores();
    
    CartaDTO getC1();
    
    CartaDTO getC2();
    
    CartaDTO getC3();

    CartaDTO getC4();
    
    JugadorResumenDTO getJugadorLocal();
}
