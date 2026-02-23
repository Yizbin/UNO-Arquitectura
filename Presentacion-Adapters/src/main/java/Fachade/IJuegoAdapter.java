/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Fachade;

import Excepciones.MazoVacioException;
import dtos.CartaDTO;
import dtos.JugadorResumenDTO;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface IJuegoAdapter {

    public void iniciarPartida(List<JugadorResumenDTO> jugadoresDTO) throws MazoVacioException;

    public void robarCarta() throws Exception;

    public List<CartaDTO> getManoJugadorActual();
}
