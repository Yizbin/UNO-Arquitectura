/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_JugarTurno;

import dtos.CartaDTO;
import dtos.EstadoPartidaDTO;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface IModeloVista {

    public void agregarSuscriptor(ISuscriptor suscriptor);

    public List<CartaDTO> getManoJugadorActual();

    public EstadoPartidaDTO getEstado();

    public int getIdJugadorLocal();
}
