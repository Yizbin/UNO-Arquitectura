/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_Sala;

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

    void registrarJugador(JugadorResumenDTO datos, Map<TipoColor, TipoColor> misColores);

    void establecerJugadorLocal(JugadorResumenDTO datos);

    JugadorResumenDTO getJugadorLocal();

    boolean actualizarEstadoJugadorSala();

    EstadoJugadorSala obtenerEstadoJugador(int idJugador);

    void validarCondicionInicio(EstadoPartidaDTO estadoPartidaDTO);
}
