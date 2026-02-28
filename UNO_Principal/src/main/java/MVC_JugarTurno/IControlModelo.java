/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.JugadorResumenDTO;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface IControlModelo {

    public void iniciarPartida(List<JugadorResumenDTO> jugadores) throws Exception;

    public void robarCarta() throws Exception;

    public void jugarCarta(CartaDTO carta) throws Exception;
    
    public void notificarError(String mensaje);

}
