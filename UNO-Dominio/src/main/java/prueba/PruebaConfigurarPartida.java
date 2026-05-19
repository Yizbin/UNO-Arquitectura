/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba;
import DTOs.ConfiguracionPartidaDTO;
import Entidades.Partida;
import Interfaces.SubDominioConcreto;
/**
 *
 * @author Pride Factor Black
 */
public class PruebaConfigurarPartida {
    
    public static void main(String[] args) {
        try {
            SubDominioConcreto subDominio = new SubDominioConcreto();
                    

            ConfiguracionPartidaDTO dto = new ConfiguracionPartidaDTO(
                    3,
                    9,
                    4
            );

            subDominio.configurarPartida(dto);

            System.out.println("Prueba terminada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al configurar partida:");
            e.printStackTrace();
        }
    }
}
