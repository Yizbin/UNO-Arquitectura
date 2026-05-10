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
public class Deserializador<T> implements IFiltro<byte[], T> {

    private final ObjectMapper mapper;
    private final Class<T> claseDestino;

    public Deserializador(Class<T> claseDestino) {
        this.mapper = new ObjectMapper();
        this.claseDestino = claseDestino;
    }

    @Override
    public ContextoPipeline<T> procesar(ContextoPipeline<byte[]> contexto) throws Exception {
        byte[] datosEntrada = contexto.getMensaje();

        if (datosEntrada == null || datosEntrada.length == 0) {
            ContextoPipeline<T> ctxError = new ContextoPipeline<>(null);
            ctxError.detenerConError("El arreglo de bytes recibido está vacío o es nulo.");
            return ctxError;
        }

        try {
            T objetoDeserializado = mapper.readValue(datosEntrada, claseDestino);

            return new ContextoPipeline<>(objetoDeserializado);

        } catch (Exception e) {
            ContextoPipeline<T> ctxError = new ContextoPipeline<>(null);
            ctxError.detenerConError("Error critico al deserializar los bytes a JSON: " + e.getMessage());
            return ctxError;
        }
    }
}
