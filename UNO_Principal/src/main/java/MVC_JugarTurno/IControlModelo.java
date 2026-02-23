/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_JugarTurno;

import dtos.CartaDTO;
import dtos.JugadorResumenDTO;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface IControlModelo {

    public void iniciarPartida(List<JugadorResumenDTO> jugadores) throws Exception;

    public void robarCarta() throws Exception;

    public List<CartaDTO> getManoJugadorActual();
    
    public void jugarCarta(CartaDTO carta) throws Exception;

}
