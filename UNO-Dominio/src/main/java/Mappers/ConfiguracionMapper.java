/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;
import DTOs.ConfiguracionPartidaDTO;
import Entidades.ConfiguracionPartida;
/**
 *
 * @author Pride Factor Black
 */
public class ConfiguracionMapper {
    
    public ConfiguracionPartida toEntity(ConfiguracionPartidaDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("La configuración no puede ser nula.");
        }

        return new ConfiguracionPartida(
                dto.getNumeroInicio(),
                dto.getNumeroFin(),
                dto.getNumComodines()
        );
    }
}
