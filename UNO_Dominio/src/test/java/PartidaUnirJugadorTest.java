/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import DTOs.JugadorResumenDTO;
import Entidades.Partida;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Abraham Coronel Bringas
 */
class PartidaUnirJugadorTest {

    @Test
    void debeUnirJugadorProvisionalConSoloSuId() {
        Partida partida = new Partida(1);

        partida.unirJugador(1);

        assertEquals(1, partida.getJugadores().size());
        assertEquals(1, partida.getJugadores().get(0).getId());
        assertNull(partida.getJugadores().get(0).getUsuario());
        assertNull(partida.getJugadores().get(0).getAvatar());
    }

    @Test
    void noDebePermitirReservarDosVecesElMismoJugador() {
        Partida partida = new Partida(1);

        partida.unirJugador(1);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.unirJugador(1)
        );

        assertEquals("El jugador ya esta unido a la partida.", excepcion.getMessage());
    }

    @Test
    void debePermitirReservarJugadoresDistintosAunqueAunNoTenganPerfil() {
        Partida partida = new Partida(1);

        partida.unirJugador(1);
        partida.unirJugador(2);

        assertEquals(2, partida.getJugadores().size());
        assertEquals(1, partida.getJugadores().get(0).getId());
        assertEquals(2, partida.getJugadores().get(1).getId());
    }

    @Test
    void debeActualizarElPerfilDelJugadorReservado() {
        Partida partida = new Partida(1);
        partida.unirJugador(1);

        JugadorResumenDTO perfilActualizado = new JugadorResumenDTO();
        perfilActualizado.setId(1);
        perfilActualizado.setNombreUsuario("Abraham");
        perfilActualizado.setRutaAvatar("avatar.png");

        partida.actualizarPerfilJugador(perfilActualizado);

        assertEquals("Abraham", partida.getJugadores().get(0).getUsuario());
        assertEquals("avatar.png", partida.getJugadores().get(0).getAvatar());
    }

    @Test
    void noDebeActualizarPerfilSiLosDatosSonNulos() {
        Partida partida = new Partida(1);
        partida.unirJugador(1);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.actualizarPerfilJugador(null)
        );

        assertEquals("Los datos del jugador no pueden ser nulos.", excepcion.getMessage());
    }

    @Test
    void noDebeActualizarPerfilSiElJugadorNoExiste() {
        Partida partida = new Partida(1);
        partida.unirJugador(1);

        JugadorResumenDTO perfilActualizado = new JugadorResumenDTO();
        perfilActualizado.setId(99);
        perfilActualizado.setNombreUsuario("Abraham");

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.actualizarPerfilJugador(perfilActualizado)
        );

        assertEquals("Jugador no encontrado con ID: 99", excepcion.getMessage());
    }
}
