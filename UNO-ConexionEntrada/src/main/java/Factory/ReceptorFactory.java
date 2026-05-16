/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import Implementacion.ColaEntrada;
import Implementacion.Receptor;
import Implementacion.ServidorTCP;
import Interfaces.IObserverConexion;
import Interfaces.ISink;

/**
 *
 * @author Abraham Coronel
 */
public class ReceptorFactory {

    public static void iniciarConexion(int puerto, ISink<byte[]> pipeline, IObserverConexion observador) {
        try {
            ColaEntrada cola = new ColaEntrada();
            Receptor receptor = new Receptor(cola);
            receptor.conectarDestino(pipeline);

            ServidorTCP servidor = new ServidorTCP(puerto, cola, observador);
            new Thread(servidor).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método original intacto para que el Ensamblador del Cliente no marque error
    public static void iniciarConexion(int puerto, ISink<byte[]> pipeline) {
        iniciarConexion(puerto, pipeline, null);
    }
}
