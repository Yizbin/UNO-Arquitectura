/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package Interfaces;

/**
 *
 * @author Abraham Coronel
 */
public interface IDispatcher {
    void enviar(Object mensaje, String ip, int puerto);
public interface IModeloSalaVista {

    List<JugadorResumenDTO> getJugadoresEnSala();
    
    void suscribir(ISuscriptorSala suscriptor);
    
    void desuscribir(ISuscriptorSala suscriptor);

}
