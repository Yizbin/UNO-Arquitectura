/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 *
 * @author saula
 */
public interface ISink<T> {
    
    void enviar(ContextoPipeline<T> contexto) throws Exception;
}
