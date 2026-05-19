package MVC_JugarTurno;

import DTOs.CartaDTO;
import DTOs.JugadorResumenDTO;
import DTOs.ResultadoFinalizacionDTO;
import DTOs.TablaPosicionesDTO;
import Enums.EstadoFinalizacion;
import Enums.TipoColor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private EstadoFinalizacion estadoFinalizacion;
    private ResultadoFinalizacionDTO resultadoFinalizacion;
    private TablaPosicionesDTO tablaPosiciones;
    
    private Map<TipoColor, TipoColor> preferenciasColor = new HashMap<>();

    public EstadoPantallaTurnoDTO() {

    }
    
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

    public EstadoFinalizacion getEstadoFinalizacion() {
        return estadoFinalizacion;
    }

    public void setEstadoFinalizacion(EstadoFinalizacion estadoFinalizacion) {
        this.estadoFinalizacion = estadoFinalizacion;
    }

    public ResultadoFinalizacionDTO getResultadoFinalizacion() {
        return resultadoFinalizacion;
    }

    public void setResultadoFinalizacion(ResultadoFinalizacionDTO resultadoFinalizacion) {
        this.resultadoFinalizacion = resultadoFinalizacion;
    }

    public TablaPosicionesDTO getTablaPosiciones() {
        return tablaPosiciones;
    }

    public void setTablaPosiciones(TablaPosicionesDTO tablaPosiciones) {
        this.tablaPosiciones = tablaPosiciones;
    }

    public Map<TipoColor, TipoColor> getPreferenciasColor() {
        return preferenciasColor;
    }

    public void setPreferenciasColor(Map<TipoColor, TipoColor> preferenciasColor) {
        this.preferenciasColor = preferenciasColor;
    }
    
    
}
