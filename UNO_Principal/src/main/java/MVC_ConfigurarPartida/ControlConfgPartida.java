/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_ConfigurarPartida;

import DTOs.ConfiguracionPartidaDTO;

/**
 *
 * @author Pride Factor Black
 */
public class ControlConfgPartida implements IControlConfgPartida{
    private final IModeloConfgPartida modelo;

    public ControlConfgPartida(IModeloConfgPartida modelo) {
        this.modelo = modelo;
    }

    @Override
    public void procesarConfiguracion(int numeroInicio, int numeroFin, int numComodines) {
        ConfiguracionPartidaDTO dto = new ConfiguracionPartidaDTO(
                numeroInicio,
                numeroFin,
                numComodines
        );

        modelo.configurarPartida(dto);
    }

    @Override
    public void mostrarPantallaConfigurarPartida() {
       PantallaConfigurarPartida pantalla = new PantallaConfigurarPartida(this, modelo);
       pantalla.setVisible(true);
    }
}
