/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package MVC_JugarTurno;
import DTOs.CartaDTO;
import DTOs.TipoCartaDTO;
import Enums.TipoColor;
import java.awt.*;

/**
 *
 * @author angel
 */
public class PanelCartaMano extends javax.swing.JPanel {

    private static final int CARD_W = 85;
    private static final int CARD_H = 130;
    private static final int ARC = 14;

    public CartaDTO carta;
    private boolean esDorso = true;

    public PanelCartaMano() {
        setPreferredSize(new Dimension(CARD_W, CARD_H));
        setOpaque(false);
    }

    public void setCarta(CartaDTO carta) {
        this.carta = carta;
        this.esDorso = false;
        repaint();
    }

    public void mostrarDorso() {
        this.carta = null;
        this.esDorso = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (esDorso || carta == null) {
            dibujarDorso(g2);
        } else {
            dibujarFrente(g2);
        }
        g2.dispose();
    }

    private void dibujarDorso(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(2, 2, CARD_W - 4, CARD_H - 4, ARC, ARC);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(2, 2, CARD_W - 4, CARD_H - 4, ARC, ARC);
        g2.setFont(new Font("Arial Black", Font.BOLD, 18));
        FontMetrics fm = g2.getFontMetrics();
        String txt = "UNO";
        g2.setColor(new Color(220, 50, 50));
        g2.drawString(txt, (CARD_W - fm.stringWidth(txt)) / 2, CARD_H / 2 + fm.getAscent() / 2 - 4);
    }

    private void dibujarFrente(Graphics2D g2) {
        Color fondo = colorAWT(carta.getColor());

        g2.setColor(fondo);
        g2.fillRoundRect(2, 2, CARD_W - 4, CARD_H - 4, ARC, ARC);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawRoundRect(2, 2, CARD_W - 4, CARD_H - 4, ARC, ARC);

        // óvalo blanco interior
        g2.setColor(Color.WHITE);
        g2.fillOval(12, 20, CARD_W - 24, CARD_H - 40);
        g2.setColor(fondo);
        g2.fillOval(17, 25, CARD_W - 34, CARD_H - 50);

        // texto central
        String centro = textoCenter();
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial Black", Font.BOLD, tamanoFuente(centro)));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(centro,
                (CARD_W - fm.stringWidth(centro)) / 2,
                CARD_H / 2 + fm.getAscent() / 2 - 6);

        // esquina superior izquierda
        g2.setFont(new Font("Arial Black", Font.BOLD, 11));
        g2.setColor(Color.WHITE);
        g2.drawString(centro, 5, 15);

        // esquina inferior derecha (rotada)
        Graphics2D gr = (Graphics2D) g2.create();
        gr.rotate(Math.PI, CARD_W / 2.0, CARD_H / 2.0);
        gr.setFont(new Font("Arial Black", Font.BOLD, 11));
        gr.setColor(Color.WHITE);
        gr.drawString(centro, 5, 15);
        gr.dispose();
    }

    private String textoCenter() {
        if (carta == null) {
            return "?";
        }
        if (carta.getTipoCarta() == TipoCartaDTO.NUMERO) {
            return String.valueOf(carta.getNumero());
        }
        if (carta.getTipoCarta() == TipoCartaDTO.ACCION && carta.getAcciones() != null) {
            switch (carta.getAcciones()) {
                case TOMA_DOS:
                    return "+2";
                case REVERSA:
                    return "⇄";
                case SALTA:
                    return "⊘";
            }
        }
        if (carta.getTipoCarta() == TipoCartaDTO.COMODIN && carta.getComodines() != null) {
            switch (carta.getComodines()) {
                case CAMBIO_COLOR:
                    return "★";
                case TOMA_CUATRO:
                    return "+4";
            }
        }
        return "?";
    }

    private int tamanoFuente(String t) {
        return t.length() == 1 ? 34 : t.length() == 2 ? 28 : 22;
    }

    private Color colorAWT(TipoColor c) {
        if (c == null) {
            return new Color(50, 50, 50);
        }
        return switch (c) {
            case ROJO -> new Color(210, 40, 40);
            case AZUL -> new Color(30, 90, 200);
            case VERDE -> new Color(40, 160, 60);
            case AMARILLO -> new Color(220, 190, 20);
            case NARANJA -> Color.ORANGE;
            case MORADO -> Color.decode("#D363FF");
            case ROSA -> Color.PINK;
            case CAFE -> Color.decode("#693A19");
            case GRIS -> Color.LIGHT_GRAY;
            case CIAN -> Color.CYAN;
            case MAGENTA -> Color.MAGENTA;
            case NEGRO -> Color.BLACK;
            default -> new Color(60, 60, 60);
        };
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(CARD_W, CARD_H);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
