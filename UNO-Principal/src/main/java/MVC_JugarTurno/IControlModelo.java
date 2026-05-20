/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.JugadorResumenDTO;
import Enums.TipoColor;
import DTOs.CartaDTO;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface IControlModelo {

    public void iniciarJuego(List<JugadorResumenDTO> jugadores);

    public void robarCarta();

    public void jugarCarta(CartaDTO carta);

    public void seleccionarColor(TipoColor color);

    boolean solicitarFinalizacion(JugadorResumenDTO jugadorDTO);

    boolean responderFinalizacion(JugadorResumenDTO jugadorDTO, boolean acepta);

    void cerrarPartidaTerminada();

}
