/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 *
 * @author Abraham Coronel
 */
public enum AccionesPosibles {
    CASI_UNO, //Descartar todas excepto dos
    DESCARTAR_POR_NUMERO, //Descartar cartas de un mismo número
    DESCARTAR_POR_COLOR, //Descartar cartas de un mismo color
    ROBAR_HASTA_AZUL_ROJO, //Robar hasta que salga azul o rojo
    GUERRA, //Competir por el numero mas alto
    MOSTRAR_MANO, //Mostrar cartas a todos los jugadores
    INTERCAMBIO_DE_MANOS, //Pasar cartas al jugador de la izquierda
    PUNTUACION_MAS_BAJA    //El de menos puntos descarta una carta
}
