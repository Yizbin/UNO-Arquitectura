/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import Implementacion.ColaEntrada;
import Implementacion.Receptor;
import Implementacion.ServidorTCP;
import InterfacesConexion.IReceptor;
import java.net.Socket;

/**
 *
 * @author Abraham Coronel
 */
public class ReceptorFactory {

    public static void iniciarConexion(Socket socketCliente, IReceptor aplicacionPrincipal) {
        ColaEntrada colaIn = new ColaEntrada();

        new Receptor(colaIn, aplicacionPrincipal);

        ServidorTCP servidor = new ServidorTCP(socketCliente, colaIn);
        new Thread(servidor).start();
    }
}
