/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Pride Factor Black
 */
public class ConfiguracionPartida {
    private final int numeroInicio;
    private final int numeroFin;
    private final int numComodines;

    public ConfiguracionPartida(int numeroInicio, int numeroFin, int numComodines) {
        this.numeroInicio = numeroInicio;
        this.numeroFin = numeroFin;
        this.numComodines = numComodines;
        validarConfiguracion();
    }

    public void validarConfiguracion() {
        if (numeroInicio < 0 || numeroFin > 9) {
            throw new IllegalArgumentException("El rango de números debe estar entre 0 y 9.");
        }

        if (numeroInicio > numeroFin) {
            throw new IllegalArgumentException("El número inicial no puede ser mayor que el número final.");
        }

        if (numComodines < 0 || numComodines > 8) {
            throw new IllegalArgumentException("El número de comodines debe estar entre 0 y 8.");
        }
    }

    public boolean esValida() {
        validarConfiguracion();
        return true;
    }

    public int getNumeroInicio() {
        return numeroInicio;
    }

    public int getNumeroFin() {
        return numeroFin;
    }

    public int getNumComodines() {
        return numComodines;
    }
}
