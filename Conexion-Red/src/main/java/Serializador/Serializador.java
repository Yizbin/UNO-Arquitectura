/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Serializador;

import Interfaces.ISerializador;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Abraham Coronel
 */
public class Serializador implements ISerializador {

    private final ObjectMapper mapper;

    public Serializador() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public byte[] serializar(Object objeto) throws Exception {
        return mapper.writeValueAsBytes(objeto);
    }

}
