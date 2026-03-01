/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.EstadoPartidaDTO;
import Enums.TipoColor;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface IModeloVista {

    public void agregarSuscriptor(ISuscriptor suscriptor);

    public int getIdJugadorLocal();

    public EstadoPartidaDTO getEstado();

    public CartaDTO getCartaEnTope();

    public TipoColor getColorActual();
    
    public List<CartaDTO> getManoJugadorLocal();
}
