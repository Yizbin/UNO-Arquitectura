/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MVC_ConfigurarPartida;

import DTOs.ConfiguracionPartidaDTO;

/**
 *
 * @author Pride Factor Black
 */
public interface IModeloConfgPartida {
    public void configurarPartida(ConfiguracionPartidaDTO dto);

    public boolean isConfiguracionExitosa();

    public String getMensajeError();

    public void agregarSuscriptor(ISuscriptor suscriptor);

    public void notificar();
}
