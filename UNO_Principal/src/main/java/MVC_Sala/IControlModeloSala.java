/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;

/**
 *
 * @author Abraham Coronel
 */
public interface IControlModeloSala {

    boolean solicitarUnirsePartida();
    
    void actualizarDatosJugador(JugadorResumenDTO datos);
    
    void abrirSalaEspera();
}
