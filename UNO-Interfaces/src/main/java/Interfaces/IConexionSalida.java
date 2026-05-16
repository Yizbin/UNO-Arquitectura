/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package Interfaces;

/**
 *
 * @author Abraham Coronel
 * @param <T>
 */
public interface IConexionSalida<T> {
    
    void enviarMensaje(String ip, int puerto, byte[] payload);
    
    void preConectar(String ip, int puerto);
}
