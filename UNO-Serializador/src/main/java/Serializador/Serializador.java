/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Serializador;

import com.fasterxml.jackson.databind.ObjectMapper;
import Plantilla.ContextoPipeline;
import Interfaces.IFiltro;

/**
 *
 * @author Abraham Coronel
 * @param <T>
 */
public class Serializador<T> implements IFiltro<T, String> {

    private final ObjectMapper mapper;

    public Serializador() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public ContextoPipeline<String> procesar(ContextoPipeline<T> contexto) throws Exception {
        T mensajeEntrada = contexto.getMensaje();

        if (mensajeEntrada == null) {
            ContextoPipeline<String> ctxError = new ContextoPipeline<>(null);
            ctxError.detenerConError("El mensaje a serializar es nulo.");
            return ctxError;
        }

        try {
            String json = mapper.writeValueAsString(mensajeEntrada);
            
            return new ContextoPipeline<>(json);
            
        } catch (Exception e) {
            ContextoPipeline<String> ctxError = new ContextoPipeline<>(null);
            ctxError.detenerConError("Error al convertir a JSON: " + e.getMessage());
            return ctxError;
        }
    }

    

}
