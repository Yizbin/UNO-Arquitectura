/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_JugarTurno;

import Excepciones.MazoVacioException;
import Fachade.IJuegoAdapter;
import dtos.CartaDTO;
import dtos.EstadoPartidaDTO;
import dtos.JugadorResumenDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Abraham Coronel
 */
public class ModeloJuego implements IControlModelo, IModeloVista {

    private final List<ISuscriptor> suscriptores = new ArrayList<>();

    private final IJuegoAdapter adapterJuego;

    private EstadoPartidaDTO estado;

    private int idJugadorLocal;

    public ModeloJuego(IJuegoAdapter adapterJuego) {
        this.adapterJuego = adapterJuego;
    }

    @Override
    public EstadoPartidaDTO getEstado() {
        return estado;
    }

    @Override
    public int getIdJugadorLocal() {
        return idJugadorLocal;
    }

    @Override
    public void iniciarPartida(List<JugadorResumenDTO> jugadores) {
        try {
            adapterJuego.iniciarPartida(jugadores);
            this.notificar();
        } catch (MazoVacioException e) {
            JOptionPane.showMessageDialog(null, "Error: No es su turno");
        }
    }

    @Override
    public void robarCarta() {
        try {
            adapterJuego.robarCarta();
            this.notificar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Hubo un error al robar carta");
        }
    }

    @Override
    public List<CartaDTO> getManoJugadorActual() {
        return adapterJuego.getManoJugadorActual();
    }

    @Override
    public void agregarSuscriptor(ISuscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    //Metodo Privados
    private void notificar() {
        for (ISuscriptor s : suscriptores) {
            s.update();
        }
    }

}
