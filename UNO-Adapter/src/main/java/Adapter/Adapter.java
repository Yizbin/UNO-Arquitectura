/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import Interfaces.ISink;
import Plantilla.ContextoPipeline;

/**
 *
 * @author Abraham Coronel
 * @param <T>
 */
public class Adapter<T> implements ISink<T> {

    private final IConexionSalida conexionSalida;

    public Adapter(IConexionSalida conexionSalida) {
        this.conexionSalida = conexionSalida;
    }

    @Override
    public void enviar(ContextoPipeline<T> contexto) throws Exception {

    }

}
