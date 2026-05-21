/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import DTOs.JugadorResumenDTO;
import Enums.TipoColor;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Abraham Coronel
 */
public interface IControlModelo {

    public void iniciarJuego(List<JugadorResumenDTO> jugadores, Map<TipoColor, TipoColor> coloresLocales);

    public void iniciarJuego(List<JugadorResumenDTO> jugadores, JugadorResumenDTO jugadorSolicitante);

    public void iniciarJuego(EstadoPartidaDTO estadoPartida, JugadorResumenDTO jugadorSolicitante);

    public void cargarPartida(EstadoPartidaDTO estadoPartidaDTO);

    public void setIdJugadorLocal(int idJugadorLocal);

    public void robarCarta();

    public void jugarCarta(CartaDTO carta);

    public void seleccionarColor(TipoColor color);

    boolean solicitarFinalizacion(JugadorResumenDTO jugadorDTO);

    boolean responderFinalizacion(JugadorResumenDTO jugadorDTO, boolean acepta);

    Map<TipoColor, TipoColor> getColoresLocales();
}
