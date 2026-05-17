/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import DTOs.JugadorResumenDTO;
import Entidades.Jugador;
import Entidades.Partida;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Abraham Coronel Bringas
 */
class PartidaUnirJugadorTest {

    @Test
    void debeRegistrarSolicitudPendienteSinAgregarJugadorALaPartida() {
        Partida partida = new Partida(1);

        partida.solicitarUnion(2);

        assertEquals(1, partida.getSolicitudesPendientes().size());
        assertTrue(partida.getSolicitudesPendientes().contains(2));
        assertEquals(0, partida.getJugadores().size());
    }

    @Test
    void noDebePermitirAceptarSolicitudSiLaPartidaYaInicio() {
        Partida partida = new Partida(1);
        partida.solicitarUnion(2);
        partida.setPartidaIniciada(true);

        IllegalStateException excepcion = assertThrows(
                IllegalStateException.class,
                () -> partida.aceptarSolicitudUnion(1, 2)
        );

        assertEquals(
                "No se puede aceptar jugadores en una partida iniciada.",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebePermitirAceptarSolicitudSiLaPartidaYaTieneCuatroJugadores() {
        Partida partida = new Partida(1);
        partida.solicitarUnion(5);
        partida.setJugadores(List.of(
                new Jugador(1),
                new Jugador(2),
                new Jugador(3),
                new Jugador(4)
        ));

        IllegalStateException excepcion = assertThrows(
                IllegalStateException.class,
                () -> partida.aceptarSolicitudUnion(1, 5)
        );

        assertEquals(
                "La partida ya alcanzo el numero maximo de jugadores.",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebePermitirSolicitarUnionSiElJugadorYaEstaUnido() {
        Partida partida = new Partida(1);
        partida.setJugadores(List.of(new Jugador(2)));

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.solicitarUnion(2)
        );

        assertEquals(
                "El jugador ya esta unido a la partida.",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebePermitirSolicitudesPendientesDuplicadas() {
        Partida partida = new Partida(1);

        partida.solicitarUnion(2);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.solicitarUnion(2)
        );

        assertEquals(
                "El jugador ya tiene una solicitud pendiente.",
                excepcion.getMessage()
        );
    }

    @Test
    void debeAgregarJugadorCuandoElAnfitrionAceptaLaSolicitud() {
        Partida partida = new Partida(1);

        partida.solicitarUnion(2);
        partida.aceptarSolicitudUnion(1, 2);

        assertEquals(0, partida.getSolicitudesPendientes().size());
        assertEquals(1, partida.getJugadores().size());
        assertEquals(2, partida.getJugadores().get(0).getId());
        assertNull(partida.getJugadores().get(0).getUsuario());
        assertNull(partida.getJugadores().get(0).getAvatar());
    }

    @Test
    void noDebePermitirAceptarSolicitudSiQuienRespondeNoEsElAnfitrion() {
        Partida partida = new Partida(1);
        partida.solicitarUnion(2);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.aceptarSolicitudUnion(99, 2)
        );

        assertEquals(
                "Solo el anfitrion puede responder solicitudes.",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebePermitirAceptarSolicitudInexistente() {
        Partida partida = new Partida(1);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.aceptarSolicitudUnion(1, 2)
        );

        assertEquals(
                "No existe una solicitud pendiente para ese jugador.",
                excepcion.getMessage()
        );
    }

    @Test
    void debeEliminarSolicitudCuandoElAnfitrionLaRechaza() {
        Partida partida = new Partida(1);

        partida.solicitarUnion(2);
        partida.rechazarSolicitudUnion(1, 2);

        assertEquals(0, partida.getSolicitudesPendientes().size());
        assertEquals(0, partida.getJugadores().size());
    }

    @Test
    void noDebePermitirRechazarSolicitudSiQuienRespondeNoEsElAnfitrion() {
        Partida partida = new Partida(1);
        partida.solicitarUnion(2);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.rechazarSolicitudUnion(99, 2)
        );

        assertEquals(
                "Solo el anfitrion puede responder solicitudes.",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebePermitirRechazarSolicitudInexistente() {
        Partida partida = new Partida(1);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.rechazarSolicitudUnion(1, 2)
        );

        assertEquals(
                "No existe una solicitud pendiente para ese jugador.",
                excepcion.getMessage()
        );
    }

    @Test
    void debePermitirActualizarPerfilDelJugadorAceptado() {
        Partida partida = new Partida(1);
        partida.solicitarUnion(2);
        partida.aceptarSolicitudUnion(1, 2);

        JugadorResumenDTO perfilActualizado = new JugadorResumenDTO();
        perfilActualizado.setId(2);
        perfilActualizado.setNombreUsuario("Abraham");
        perfilActualizado.setRutaAvatar("avatar.png");

        partida.actualizarPerfilJugador(perfilActualizado);

        assertEquals("Abraham", partida.getJugadores().get(0).getUsuario());
        assertEquals("avatar.png", partida.getJugadores().get(0).getAvatar());
    }

    @Test
    void noDebeActualizarPerfilSiLosDatosSonNulos() {
        Partida partida = new Partida(1);
        partida.solicitarUnion(2);
        partida.aceptarSolicitudUnion(1, 2);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.actualizarPerfilJugador(null)
        );

        assertEquals(
                "Los datos del jugador no pueden ser nulos.",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebeActualizarPerfilSiElJugadorNoExiste() {
        Partida partida = new Partida(1);
        partida.solicitarUnion(2);
        partida.aceptarSolicitudUnion(1, 2);

        JugadorResumenDTO perfilActualizado = new JugadorResumenDTO();
        perfilActualizado.setId(99);
        perfilActualizado.setNombreUsuario("Abraham");

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> partida.actualizarPerfilJugador(perfilActualizado)
        );

        assertEquals(
                "Jugador no encontrado con ID: 99",
                excepcion.getMessage()
        );
    }
}
