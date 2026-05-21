/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_ConfigurarPartida;

/**
 *
 * @author Pride Factor Black
 */
public interface IControlConfgPartida {
    void mostrarPantallaConfigurarPartida();

    void procesarConfiguracion(int numeroInicio, int numeroFin, int numComodines);

    void notificarConfiguracionExitosa();
}
