/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public interface IModeloSalaVista {

    List<JugadorResumenDTO> getJugadoresEnSala();
    
    void suscribir(ISuscriptorSala suscriptor);
    
    void desuscribir(ISuscriptorSala suscriptor);
    
    void notificar();
    
}
