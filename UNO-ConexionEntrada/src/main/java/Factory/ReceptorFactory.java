/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import Implementacion.ColaEntrada;
import Implementacion.Receptor;
import Implementacion.ServidorTCP;
import Interfaces.ISink;
import java.io.IOException;

/**
 *
 * @author Abraham Coronel
 */
public class ReceptorFactory {

    public static void iniciarConexion(int puerto, ISink<byte[]> pipeline) {
        ColaEntrada colaIn = new ColaEntrada();
        
        Receptor receptor = new Receptor(colaIn);
        receptor.conectarDestino(pipeline);
        
        try {
            ServidorTCP servidor = new ServidorTCP(puerto, colaIn);
            
            new Thread(servidor).start();
            
        } catch (IOException e) {
            System.err.println("Error al iniciar el ServidorTCP en el puerto: " + puerto);
            e.printStackTrace();
        }
    }
}
