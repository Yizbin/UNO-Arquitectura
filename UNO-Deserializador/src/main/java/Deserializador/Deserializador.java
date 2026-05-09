/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Deserializador;

import com.fasterxml.jackson.databind.ObjectMapper;
import Plantilla.ContextoPipeline;
import Interfaces.IFiltro;

/**
 *
 * @author Abraham Coronel
 * @param <T>
 */
public class Deserializador<T> implements IFiltro<String, T> {

    private final ObjectMapper mapper;
    private final Class<T> claseDestino;

    public Deserializador(Class<T> claseDestino) {
        this.mapper = new ObjectMapper();
        this.claseDestino = claseDestino;
    }

    @Override
    public ContextoPipeline<T> procesar(ContextoPipeline<String> contexto) throws Exception {
        String jsonEntrada = contexto.getMensaje();

        if (jsonEntrada == null || jsonEntrada.trim().isEmpty()) {
            ContextoPipeline<T> ctxError = new ContextoPipeline<>(null);
            ctxError.detenerConError("El JSON recibido está vacío o nulo.");
            return ctxError;
        }

        try {
            T objetoDeserializado = mapper.readValue(jsonEntrada, claseDestino);

            return new ContextoPipeline<>(objetoDeserializado);

        } catch (Exception e) {
            ContextoPipeline<T> ctxError = new ContextoPipeline<>(null);
            ctxError.detenerConError("Error critico al leer el JSON: " + e.getMessage());
            return ctxError;
        }
    }
}
