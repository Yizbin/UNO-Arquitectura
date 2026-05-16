/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Usuario
 */
public class ConfiguracionJugador extends JFrame implements ISuscriptorSala {

    private final ControladorSala control;
    private final IModeloSalaVista modelo;
    private final PanelImagenFondo panelFondo;
    private PanelAzul panelCentro;

    public ConfiguracionJugador(ControladorSala control, IModeloSalaVista modelo) throws IOException {
        this.panelFondo = new PanelImagenFondo("/fondo.png");
        initComponents();
        this.control = control;
        this.modelo = modelo;
        this.modelo.suscribir(this);
    }

    private void initComponents() {
        //definir aspectos del frame
        setSize(1440, 1024);
        setBackground(Color.white);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //agrego la imagen de fondo
        add(panelFondo);
        panelFondo.setLocation(0, 60);
        panelFondo.setLayout(null);

        //agregar el título
        JLabel titulo = new JLabel("Registrar Jugador", SwingConstants.CENTER);
        titulo.setFont(new Font("Rubik One", Font.BOLD, 50));
        titulo.setForeground(Color.BLACK);
        panelFondo.add(titulo);
        titulo.setBounds(409, 40, 626, 65);

        //agrego el panel azul
        panelCentro = new PanelAzul();
        panelCentro.setLayout(null);
        panelFondo.add(panelCentro);
        panelCentro.setBounds(154, 140, 1131, 730);

        cargarConfiguración();

        setVisible(true);
    }

    public void cargarConfiguración() {
        //agregar los avatars
        JButton avatar1 = new JButton();
        avatar1.setBackground(Color.decode("#0066DB"));
        avatar1.setBorder(null);
        avatar1.setBounds(285, 60, 120, 120);
        avatar1.setIcon(new ImageIcon(getClass().getResource("/unicornio.png")));
        panelCentro.add(avatar1);

        JButton avatar2 = new JButton();
        avatar2.setBackground(Color.decode("#0066DB"));
        avatar2.setBorder(null);
        avatar2.setBounds(435, 60, 120, 120);
        avatar2.setIcon(new ImageIcon(getClass().getResource("/panda.png")));
        panelCentro.add(avatar2);

        JButton avatar3 = new JButton();
        avatar3.setBackground(Color.decode("#0066DB"));
        avatar3.setBorder(null);
        avatar3.setBounds(585, 60, 120, 120);
        avatar3.setIcon(new ImageIcon(getClass().getResource("/mono.png")));
        panelCentro.add(avatar3);

        JButton avatar4 = new JButton();
        avatar4.setBackground(Color.decode("#0066DB"));
        avatar4.setBorder(null);
        avatar4.setBounds(735, 60, 120, 120);
        avatar4.setIcon(new ImageIcon(getClass().getResource("/gato.png")));
        panelCentro.add(avatar4);

        JButton avatar5 = new JButton();
        avatar5.setBackground(Color.decode("#0066DB"));
        avatar5.setBorder(null);
        avatar5.setBounds(285, 195, 120, 120);
        avatar5.setIcon(new ImageIcon(getClass().getResource("/vaca.png")));
        panelCentro.add(avatar5);

        JButton avatar6 = new JButton();
        avatar6.setBackground(Color.decode("#0066DB"));
        avatar6.setBorder(null);
        avatar6.setBounds(435, 195, 120, 120);
        avatar6.setIcon(new ImageIcon(getClass().getResource("/zorro.png")));
        panelCentro.add(avatar6);

        JButton avatar7 = new JButton();
        avatar7.setBackground(Color.decode("#0066DB"));
        avatar7.setBorder(null);
        avatar7.setBounds(585, 195, 120, 120);
        avatar7.setIcon(new ImageIcon(getClass().getResource("/pinguino.png")));
        panelCentro.add(avatar7);

        JButton avatar8 = new JButton();
        avatar8.setBackground(Color.decode("#0066DB"));
        avatar8.setBorder(null);
        avatar8.setBounds(735, 195, 120, 120);
        avatar8.setIcon(new ImageIcon(getClass().getResource("/perro.png")));
        panelCentro.add(avatar8);

        //label para decirle al usuario que ponga su nombre
        JLabel nombre = new JLabel("Nombre de usuario");
        nombre.setBounds(465, 350, 200, 30);
        nombre.setFont(new Font("Rubik One", Font.BOLD, 20));
        nombre.setForeground(Color.BLACK);
        panelCentro.add(nombre);

        //agregar el textfield para el nombre de usuario
        CampoTextoRedondeado txtField = new CampoTextoRedondeado();
        txtField.setBounds(365, 400, 400, 60);
        panelCentro.add(txtField);

        PanelCarta carta1 = new PanelCarta();
        carta1.setBounds(350, 500, 85, 130);
        panelCentro.add(carta1);

        PanelCarta carta2 = new PanelCarta();
        carta2.setBounds(465, 500, 85, 130);
        panelCentro.add(carta2);

        PanelCarta carta3 = new PanelCarta();
        carta3.setBounds(580, 500, 85, 130);
        panelCentro.add(carta3);

        PanelCarta carta4 = new PanelCarta();
        carta4.setBounds(695, 500, 85, 130);
        panelCentro.add(carta4);

        //boton de anterior y siguiente
        BotonRedondeado anterior = new BotonRedondeado(Color.red);
        anterior.setText("Anterior");
        anterior.setBounds(50, 600, 150, 50);
        panelCentro.add(anterior);

        BotonRedondeado siguiente = new BotonRedondeado(Color.green);
        siguiente.setText("Siguiente");
        siguiente.setBounds(931, 600, 150, 50);
        panelCentro.add(siguiente);
    }
    
    private void cargarColores(){
        panelCentro.removeAll();
        panelCentro.revalidate();
        panelCentro.repaint();
        
        
    }

    @Override
    public void update(IModeloSalaVista modeloVista) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    static class CampoTextoRedondeado extends JTextField {

        private final Color colorFondo = Color.WHITE;
        private final Color colorBorde = Color.decode("#E2E8F0");
        private final int radio = 20;

        public CampoTextoRedondeado() {
            setOpaque(false);
            setBorder(new EmptyBorder(15, 20, 15, 60));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(colorFondo);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);

            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(colorBorde);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);

            g2.dispose();
        }
    }

    public class BotonRedondeado extends JButton {

        private final int radio = 20;
        private final Color colorFondo;

        public BotonRedondeado(Color colorFondo) {
            super();
            this.colorFondo = colorFondo;
            setBackground(colorFondo);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(colorFondo);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
            super.paintComponent(g);
            g2.dispose();
        }
    }

}
