/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

/**
 *
 * @author Abraham Coronel
 */
public interface ISerializador {

    byte[] serializar(Object objeto) throws Exception;

    boolean solicitarUnirsePartida();
    
    void actualizarDatosJugador(JugadorResumenDTO datos);
    
    void notificar();
    
}
