/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementacion;

import Plantilla.ContextoPipeline;
import Interfaces.ISink;


/**
 *
 * @author Abraham Coronel
 */
public class Dispatcher implements ISink<byte[]> {

    private ColaSalida cola;
    private ClienteTCP cliente;

    public Dispatcher(String ip, int puerto) {
        this.cola = new ColaSalida();
        this.cliente = new ClienteTCP(ip, puerto, cola);
    }

    @Override
    public void enviar(ContextoPipeline<byte[]> contexto) throws Exception {
        if (contexto != null && !contexto.estaDetenido()) {
            byte[] datos = contexto.getMensaje();
            
            if (datos != null && datos.length > 0) {
                cola.encolar(datos); 
            }
        }
    }

}
