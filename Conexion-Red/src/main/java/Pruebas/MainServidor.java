/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package Pruebas;

import Interfaces.IReceptor;
import Deserializador.Deserializador;
import Servidor.Receptor;
import Servidor.Servidor;

/**
 *
 * @author Abraham Coronel
 */
public class MainServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("SERVIDOR");

        Deserializador deserializador = new Deserializador();

        IReceptor pantallaJuego = new IReceptor() {
            @Override
            public void update(byte[] datosBinarios) {
                System.out.println("\n[SERVIDOR] -> ¡Llegaron " + datosBinarios.length + " bytes por la red!");
                try {
                    // Quitamos lo seriado (binario -> texto)
                    String mensajeLimpio = deserializador.deserializar(datosBinarios, String.class);
                    System.out.println("[SERVIDOR] -> Mensaje revelado: \"" + mensajeLimpio + "\"");
                } catch (Exception e) {
                    System.err.println("Error quitando lo seriado: " + e.getMessage());
                }
            }
        };

        Receptor receptor = new Receptor(pantallaJuego);
        Servidor servidor = new Servidor(5000, receptor);
        
    }

}
