/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Serializador;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Abraham Coronel
 */
public class Deserializador {

    private final ObjectMapper mapper;

    public Deserializador() {
        this.mapper = new ObjectMapper();
    }

    public <T> T deserializar(byte[] datos, Class<T> claseDestino) throws Exception {
        return mapper.readValue(datos, claseDestino);
    }
}
