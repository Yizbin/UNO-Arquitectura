/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;
import Enums.TipoColor;
import java.util.Map;

/**
 *
 * @author Abraham Coronel
 */
public interface IControlModeloSala {

    boolean solicitarUnirsePartida();

    void abrirSalaEspera();

    void actualizarDatosJugador(JugadorResumenDTO datos, Map<TipoColor, TipoColor> misColores);


    void establecerJugadorLocal(JugadorResumenDTO datos);
    
    boolean actualizarEstadoJugadorSala();
}
