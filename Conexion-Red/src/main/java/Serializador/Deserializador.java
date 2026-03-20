/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Serializador;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

/**
 *
 * @author Abraham Coronel
 */
public class Deserializador {

    private final ObjectMapper mapper;

    public Deserializador() {
        //Le damos el motor de MessagePack para que entienda los bytes
        this.mapper = new ObjectMapper(new MessagePackFactory());
    }

    //Crea un objeto en java apartir de bytes
    public <T> T deserializar(byte[] datos, Class<T> claseDestino) throws Exception {
        return mapper.readValue(datos, claseDestino);
    }
}
