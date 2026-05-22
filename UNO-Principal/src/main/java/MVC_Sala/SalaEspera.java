/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;
import Enums.EstadoJugadorSala;
import MVC_Utilidades.UtilidadesGraficas;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Abraham Coronel
 */
public class SalaEspera extends javax.swing.JFrame implements ISuscriptorSala {

    private IModeloSalaVista modeloVista;
    private final ControladorSala controlador;
    private boolean inicioNotificado;

    public SalaEspera(ControladorSala controlador, IModeloSalaVista modeloVista) {
        initComponents();
        this.controlador = controlador;
        this.modeloVista = modeloVista;
        this.modeloVista.suscribir(this);
        btnListo.addActionListener(evt -> solicitarInicio());
        update(modeloVista);
    }

    private void solicitarInicio() {
        boolean respuesta = controlador != null && controlador.actualizarEstadoJugadorSala();
        responderInicio(respuesta);
    }

    private void responderInicio(boolean respuesta) {
        if (respuesta) {
            mostrarInicioPartida();
        }
    }

    private void mostrarInicioPartida() {
        btnListo.setEnabled(false);
        btnListo.setText("Confirmado");
    }

    private void actualizarPanelJugadoresConfirmados(List<JugadorResumenDTO> jugadores) {
        refrescarPanelJugadores(jugadores);
    }

    private void refrescarPanelJugadores(List<JugadorResumenDTO> jugadores) {
        PanelListaJugadores.removeAll();

        if (jugadores == null || jugadores.isEmpty()) {
            PanelListaJugadores.revalidate();
            PanelListaJugadores.repaint();
            return;
        }

        for (JugadorResumenDTO jugador : jugadores) {
            if (jugador == null) {
                continue;
            }

            ImageIcon avatar = crearAvatar(jugador);

            PanelJugador tarjeta = new PanelJugador(
                    jugador.getNombreUsuario(),
                    avatar,
                    jugador.isAceptado(),
                    obtenerEstadoJugador(jugador.getId())
            );

            PanelListaJugadores.add(crearFilaJugador(jugador, tarjeta));
            PanelListaJugadores.add(Box.createVerticalStrut(12));
        }

        PanelListaJugadores.revalidate();
        PanelListaJugadores.repaint();
    }

    private ImageIcon crearAvatar(JugadorResumenDTO jugador) {
        if (jugador.getRutaAvatar() == null) {
            return null;
        }

        java.net.URL recurso = getClass().getResource(jugador.getRutaAvatar());
        if (recurso == null) {
            return null;
        }

        ImageIcon imagen = new ImageIcon(recurso);
        return UtilidadesGraficas.hacerAvatarCircular(imagen.getImage(), 60);
    }

    private EstadoJugadorSala obtenerEstadoJugador(int idJugador) {
        if (controlador == null) {
            return EstadoJugadorSala.ESPERANDO;
        }

        return controlador.obtenerEstadoJugador(idJugador);
    }

    private void actualizarEstadoBotonListo(IModeloSalaVista modeloVista) {
        JugadorResumenDTO jugadorLocal = modeloVista.getJugadorLocal();

        if (jugadorLocal == null) {
            btnListo.setEnabled(false);
            return;
        }

        EstadoJugadorSala estado = obtenerEstadoJugador(jugadorLocal.getId());

        if (estado == EstadoJugadorSala.CONFIRMADO) {
            btnListo.setEnabled(false);
            btnListo.setText("Confirmado");
        } else {
            btnListo.setEnabled(true);
            btnListo.setText("Listo!");
        }
    }

    private void intentarNotificarInicio(IModeloSalaVista modeloVista) {
        if (!modeloVista.isPartidaListaParaIniciar() || inicioNotificado) {
            return;
        }

        JugadorResumenDTO jugadorLocal = modeloVista.getJugadorLocal();

        if (jugadorLocal == null || jugadorLocal.getId() != 1) {
            return;
        }

        inicioNotificado = true;
        boolean respuesta = controlador != null && controlador.notificarInicio(modeloVista);
        responderInicio(respuesta);
    }

    private JPanel crearFilaJugador(JugadorResumenDTO jugador, PanelJugador tarjeta) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        fila.add(tarjeta, BorderLayout.CENTER);

        if (modeloVista != null && modeloVista.puedeResponderSolicitudUnion(jugador.getId())) {
            fila.add(crearPanelAccionesUnion(jugador), BorderLayout.EAST);
        }

        return fila;
    }

    private JPanel crearPanelAccionesUnion(JugadorResumenDTO jugador) {
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 20));
        panelAcciones.setOpaque(false);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnRechazar = new JButton("Rechazar");

        btnAceptar.addActionListener(e -> controlador.aceptarSolicitudUnion(jugador.getId()));
        btnRechazar.addActionListener(e -> controlador.rechazarSolicitudUnion(jugador.getId()));

        panelAcciones.add(btnAceptar);
        panelAcciones.add(btnRechazar);

        return panelAcciones;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelFondo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        PanelEspera = new javax.swing.JPanel();
        PanelBotones = new javax.swing.JPanel();
        btnListo = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        PanelListaJugadores = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelFondo.setBackground(new java.awt.Color(255, 255, 255));
        PanelFondo.setLayout(new java.awt.BorderLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("SALA ESPERA");
        lblTitulo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PanelFondo.add(lblTitulo, java.awt.BorderLayout.PAGE_START);

        PanelEspera.setForeground(new java.awt.Color(255, 255, 255));
        PanelEspera.setLayout(new java.awt.BorderLayout());

        PanelBotones.setBackground(new java.awt.Color(255, 255, 255));

        btnListo.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnListo.setText("Listo!");
        btnListo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListoActionPerformed(evt);
            }
        });

        btnAtras.setBackground(new java.awt.Color(255, 0, 0));
        btnAtras.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnAtras.setText("Atras");

        javax.swing.GroupLayout PanelBotonesLayout = new javax.swing.GroupLayout(PanelBotones);
        PanelBotones.setLayout(PanelBotonesLayout);
        PanelBotonesLayout.setHorizontalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotonesLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(btnAtras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 635, Short.MAX_VALUE)
                .addComponent(btnListo)
                .addGap(64, 64, 64))
        );
        PanelBotonesLayout.setVerticalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotonesLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListo)
                    .addComponent(btnAtras))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        PanelEspera.add(PanelBotones, java.awt.BorderLayout.PAGE_END);

        PanelListaJugadores.setBackground(new java.awt.Color(255, 255, 255));
        PanelListaJugadores.setLayout(new javax.swing.BoxLayout(PanelListaJugadores, javax.swing.BoxLayout.Y_AXIS));
        PanelEspera.add(PanelListaJugadores, java.awt.BorderLayout.CENTER);

        PanelFondo.add(PanelEspera, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnListoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnListoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotones;
    private javax.swing.JPanel PanelEspera;
    private javax.swing.JPanel PanelFondo;
    private javax.swing.JPanel PanelListaJugadores;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnListo;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(IModeloSalaVista modeloVista) {
        if (modeloVista == null) {
            return;
        }

        if (!javax.swing.SwingUtilities.isEventDispatchThread()) {
            javax.swing.SwingUtilities.invokeLater(() -> update(modeloVista));
            return;
        }

        this.modeloVista = modeloVista;

        List<JugadorResumenDTO> jugadores = modeloVista.getJugadoresEnSala();

        if (jugadores == null) {
            PanelListaJugadores.removeAll();
            PanelListaJugadores.revalidate();
            PanelListaJugadores.repaint();
            return;
        }

        actualizarPanelJugadoresConfirmados(jugadores);
        actualizarEstadoBotonListo(modeloVista);
        intentarNotificarInicio(modeloVista);
    }
}
