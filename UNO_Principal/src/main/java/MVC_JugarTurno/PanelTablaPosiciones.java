package MVC_JugarTurno;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import DTOs.JugadorResumenDTO;
import DTOs.TablaPosicionesDTO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 *
 * @author adell
 */
public class PanelTablaPosiciones extends javax.swing.JPanel {

    private List<JugadorResumenDTO> jugadores = new ArrayList<>();
    private final Color[] coloresBarras = {
        new Color(199, 82, 222),
        new Color(58, 157, 226),
        new Color(230, 0, 0),
        new Color(99, 170, 68)
    };

    /**
     * Creates new form PanelTablaPosiciones
     */
    public PanelTablaPosiciones() {
        initComponents();
        jLabel1.setVisible(false);
        setOpaque(false);
    }

    public static void mostrarDialogo(java.awt.Component parent, TablaPosicionesDTO tablaPosiciones) {
        PanelTablaPosiciones panel = new PanelTablaPosiciones();
        panel.setTablaPosiciones(tablaPosiciones);

        Window owner = parent != null ? SwingUtilities.getWindowAncestor(parent) : null;
        JDialog dialogo = new JDialog(owner, "Tabla de posiciones", Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setContentPane(panel);
        dialogo.pack();
        dialogo.setResizable(false);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setVisible(true);
    }

    public void setTablaPosiciones(TablaPosicionesDTO tablaPosiciones) {
        setJugadores(tablaPosiciones != null ? tablaPosiciones.getPosiciones() : null);
    }

    public void setJugadores(List<JugadorResumenDTO> jugadores) {
        this.jugadores = new ArrayList<>();
        if (jugadores != null) {
            this.jugadores.addAll(jugadores);
        }

        this.jugadores.sort(
                Comparator.comparingInt(JugadorResumenDTO::getPuntos).reversed()
                        .thenComparingInt(JugadorResumenDTO::getCantidadDeCartas)
                        .thenComparing(JugadorResumenDTO::getNombreUsuario, Comparator.nullsLast(String::compareToIgnoreCase))
        );

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        pintarFondo(g2, w, h);
        pintarFlechaRegreso(g2);
        pintarTitulo(g2, w);
        pintarBarras(g2, w, h);

        g2.dispose();
    }

    private void pintarFondo(Graphics2D g2, int w, int h) {
        g2.setColor(new Color(0, 111, 220));
        g2.fillRoundRect(0, 0, w - 1, h - 1, 12, 12);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(1, 1, w - 3, h - 3, 12, 12);
    }

    private void pintarFlechaRegreso(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int x = 38;
        int y = 64;
        g2.drawLine(x, y, x + 22, y);
        g2.drawLine(x, y, x + 12, y - 12);
        g2.drawLine(x, y, x + 12, y + 12);
    }

    private void pintarTitulo(Graphics2D g2, int w) {
        g2.setFont(new Font("Trebuchet MS", Font.BOLD, 36));
        g2.setColor(Color.WHITE);
        dibujarTextoCentrado(g2, "GANASTE!", w / 2, 82);
    }

    private void pintarBarras(Graphics2D g2, int w, int h) {
        if (jugadores.isEmpty()) {
            g2.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
            g2.setColor(Color.WHITE);
            dibujarTextoCentrado(g2, "Sin posiciones disponibles", w / 2, h / 2);
            return;
        }

        List<Integer> ordenVisual = obtenerOrdenVisual(jugadores.size());
        int maxPuntos = jugadores.stream()
                .mapToInt(JugadorResumenDTO::getPuntos)
                .max()
                .orElse(1);

        int cantidad = Math.min(jugadores.size(), 4);
        int anchoBarra = Math.max(58, Math.min(92, w / 9));
        int separacion = Math.max(12, w / 45);
        int anchoTotal = cantidad * anchoBarra + (cantidad - 1) * separacion;
        int inicioX = (w - anchoTotal) / 2;
        int baseY = h - 28;
        int altoMaximo = Math.max(120, h - 210);

        for (int posicionVisual = 0; posicionVisual < cantidad; posicionVisual++) {
            int indiceJugador = ordenVisual.get(posicionVisual);
            JugadorResumenDTO jugador = jugadores.get(indiceJugador);

            int puntos = Math.max(0, jugador.getPuntos());
            int alto = 70 + (maxPuntos > 0 ? puntos * altoMaximo / maxPuntos : 0);
            if (indiceJugador == 0) {
                alto += 18;
            }

            int x = inicioX + posicionVisual * (anchoBarra + separacion);
            int y = baseY - alto;

            g2.setColor(coloresBarras[indiceJugador % coloresBarras.length]);
            g2.fillRect(x, y, anchoBarra, alto);

            pintarAvatar(g2, x + anchoBarra / 2, y - 48, indiceJugador);
            pintarNombre(g2, jugador, x + anchoBarra / 2, y - 8);
            pintarPuntos(g2, puntos, x + anchoBarra / 2, y + 26);
        }
    }

    private List<Integer> obtenerOrdenVisual(int cantidadJugadores) {
        List<Integer> orden = new ArrayList<>();
        if (cantidadJugadores >= 3) {
            orden.add(2);
        }
        if (cantidadJugadores >= 2) {
            orden.add(1);
        }
        orden.add(0);
        if (cantidadJugadores >= 4) {
            orden.add(3);
        }
        for (int i = 4; i < cantidadJugadores; i++) {
            orden.add(i);
        }
        return orden;
    }

    private void pintarAvatar(Graphics2D g2, int centroX, int centroY, int indiceJugador) {
        Color colorAvatar = coloresBarras[indiceJugador % coloresBarras.length].brighter();
        g2.setColor(colorAvatar);
        g2.fillOval(centroX - 18, centroY - 18, 36, 36);

        g2.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
        g2.setColor(Color.WHITE);
        String texto = String.valueOf(indiceJugador + 1);
        dibujarTextoCentrado(g2, texto, centroX, centroY + 7);
    }

    private void pintarNombre(Graphics2D g2, JugadorResumenDTO jugador, int centroX, int y) {
        String nombre = jugador.getNombreUsuario() != null && !jugador.getNombreUsuario().isBlank()
                ? jugador.getNombreUsuario()
                : "Jugador " + jugador.getId();

        g2.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        dibujarTextoCentrado(g2, nombre, centroX, y);
    }

    private void pintarPuntos(Graphics2D g2, int puntos, int centroX, int y) {
        g2.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        g2.setColor(Color.WHITE);
        dibujarTextoCentrado(g2, String.valueOf(puntos), centroX, y);
        dibujarTextoCentrado(g2, "Puntos", centroX, y + 18);
    }

    private void dibujarTextoCentrado(Graphics2D g2, String texto, int centroX, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int x = centroX - fm.stringWidth(texto) / 2;
        g2.drawString(texto, x, y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(31, 84, 182));
        setPreferredSize(new java.awt.Dimension(1000, 600));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("¡GANO!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(425, 425, 425)
                .addComponent(jLabel1)
                .addContainerGap(453, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel1)
                .addContainerGap(499, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
