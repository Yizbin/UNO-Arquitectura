/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Serializador;

import Interfaces.ISerializador;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

/**
 *
 * @author Abraham Coronel
 */
public class Serializador implements ISerializador {
    
    private final ObjectMapper mapper;

    public Serializador() {
        //Configura para que use MessagePack que es lo que utilizamos para pasar la informacion serializada
        this.mapper = new ObjectMapper(new MessagePackFactory());
    }

    @Override
    public byte[] serializar(Object objeto) throws Exception {
        return mapper.writeValueAsBytes(objeto);
    }

}
