package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.JugadorResumenDTO;
import java.util.List;

/**
 * Snapshot de presentacion para la pantalla de turno.
 * Contiene la informacion ya acomodada para renderizar la mesa desde la
 * perspectiva del jugador local.
 */
public class EstadoPantallaTurnoDTO {

    private JugadorResumenDTO jugadorLocal;
    private JugadorResumenDTO jugadorEste;
    private JugadorResumenDTO jugadorNorte;
    private JugadorResumenDTO jugadorOeste;
    private List<CartaDTO> manoLocal;
    private CartaDTO cartaEnDescarte;
    private boolean esperandoColor;
    private boolean turnoLocal;

    public JugadorResumenDTO getJugadorLocal() {
        return jugadorLocal;
    }

    public void setJugadorLocal(JugadorResumenDTO jugadorLocal) {
        this.jugadorLocal = jugadorLocal;
    }

    public JugadorResumenDTO getJugadorEste() {
        return jugadorEste;
    }

    public void setJugadorEste(JugadorResumenDTO jugadorEste) {
        this.jugadorEste = jugadorEste;
    }

    public JugadorResumenDTO getJugadorNorte() {
        return jugadorNorte;
    }

    public void setJugadorNorte(JugadorResumenDTO jugadorNorte) {
        this.jugadorNorte = jugadorNorte;
    }

    public JugadorResumenDTO getJugadorOeste() {
        return jugadorOeste;
    }

    public void setJugadorOeste(JugadorResumenDTO jugadorOeste) {
        this.jugadorOeste = jugadorOeste;
    }

    public List<CartaDTO> getManoLocal() {
        return manoLocal;
    }

    public void setManoLocal(List<CartaDTO> manoLocal) {
        this.manoLocal = manoLocal;
    }

    public CartaDTO getCartaEnDescarte() {
        return cartaEnDescarte;
    }

    public void setCartaEnDescarte(CartaDTO cartaEnDescarte) {
        this.cartaEnDescarte = cartaEnDescarte;
    }

    public boolean isEsperandoColor() {
        return esperandoColor;
    }

    public void setEsperandoColor(boolean esperandoColor) {
        this.esperandoColor = esperandoColor;
    }

    public boolean isTurnoLocal() {
        return turnoLocal;
    }

    public void setTurnoLocal(boolean turnoLocal) {
        this.turnoLocal = turnoLocal;
    }
}
