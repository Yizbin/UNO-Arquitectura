/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Pride Factor Black
 */
public class ConfiguracionPartidaDTO {
    private int numeroInicio;
    private int numeroFin;
    private int numComodines;

    public ConfiguracionPartidaDTO() {
    }

    public ConfiguracionPartidaDTO(int numeroInicio, int numeroFin, int numComodines) {
        this.numeroInicio = numeroInicio;
        this.numeroFin = numeroFin;
        this.numComodines = numComodines;
    }

    public int getNumeroInicio() {
        return numeroInicio;
    }

    public void setNumeroInicio(int numeroInicio) {
        this.numeroInicio = numeroInicio;
    }

    public int getNumeroFin() {
        return numeroFin;
    }

    public void setNumeroFin(int numeroFin) {
        this.numeroFin = numeroFin;
    }

    public int getNumComodines() {
        return numComodines;
    }

    public void setNumComodines(int numComodines) {
        this.numComodines = numComodines;
    }
}
