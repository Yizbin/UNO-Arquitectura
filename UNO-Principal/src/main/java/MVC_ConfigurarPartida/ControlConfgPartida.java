/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_ConfigurarPartida;
import DTOs.ConfiguracionPartidaDTO;
import java.util.Objects;

/**
 *
 * @author Pride Factor Black
 */
public class ControlConfgPartida implements IControlConfgPartida{
    private final IModeloConfgPartida modelo;
    private Runnable accionConfiguracionExitosa;

    public ControlConfgPartida(IModeloConfgPartida modelo) {
        this.modelo = Objects.requireNonNull(
                modelo,
                "El modelo de configuracion de partida es requerido."
        );
    }

    @Override
    public void mostrarPantallaConfigurarPartida() {
        PantallaConfigurarPartida pantalla = new PantallaConfigurarPartida(this, modelo);
        pantalla.setVisible(true);
    }


    @Override
    public void procesarConfiguracion(int numeroInicio, int numeroFin, int numComodines) {
        ConfiguracionPartidaDTO dto = new ConfiguracionPartidaDTO(numeroInicio, numeroFin, numComodines);
        modelo.configurarPartida(dto);
    }

    public void setAccionConfiguracionExitosa(Runnable accionConfiguracionExitosa) {
        this.accionConfiguracionExitosa = accionConfiguracionExitosa;
    }

    @Override
    public void notificarConfiguracionExitosa() {
        if (accionConfiguracionExitosa != null) {
            accionConfiguracionExitosa.run();
        }
    }
}
