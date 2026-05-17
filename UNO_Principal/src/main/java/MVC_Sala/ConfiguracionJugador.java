/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.JugadorResumenDTO;
import MVC_JugarTurno.PanelCarta;
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
    private CampoTextoRedondeado txtField;
    private BotonRedondeado siguiente;
    private BotonRedondeado anterior;
    private JButton avatarSeleccionado = null;
    private final JugadorResumenDTO  jugador = new JugadorResumenDTO();

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
        panelCentro.removeAll();
        panelCentro.revalidate();
        panelCentro.repaint();
        
        //agregar los avatars
        JButton avatar1 = new JButton();
        avatar1.setBackground(Color.decode("#0066DB"));
        avatar1.setBorder(null);
        avatar1.setBounds(285, 60, 120, 120);
        String unicornio = "/unicornio.png";
        avatar1.setIcon(new ImageIcon(getClass().getResource(unicornio)));
        panelCentro.add(avatar1);
        avatar1.addActionListener(e ->{seleccionarAvatar(avatar1, unicornio);});

        JButton avatar2 = new JButton();
        avatar2.setBackground(Color.decode("#0066DB"));
        avatar2.setBorder(null);
        avatar2.setBounds(435, 60, 120, 120);
        String panda = "/panda.png";
        avatar2.setIcon(new ImageIcon(getClass().getResource(panda)));
        panelCentro.add(avatar2);
        avatar2.addActionListener(e ->{seleccionarAvatar(avatar2, panda);});

        JButton avatar3 = new JButton();
        avatar3.setBackground(Color.decode("#0066DB"));
        avatar3.setBorder(null);
        avatar3.setBounds(585, 60, 120, 120);
        String mono = "/mono.png";
        avatar3.setIcon(new ImageIcon(getClass().getResource(mono)));
        panelCentro.add(avatar3);
        avatar3.addActionListener(e ->{seleccionarAvatar(avatar3, mono);});

        JButton avatar4 = new JButton();
        avatar4.setBackground(Color.decode("#0066DB"));
        avatar4.setBorder(null);
        avatar4.setBounds(735, 60, 120, 120);
        String gato = "/gato.png";
        avatar4.setIcon(new ImageIcon(getClass().getResource(gato)));
        panelCentro.add(avatar4);
        avatar4.addActionListener(e ->{seleccionarAvatar(avatar4, gato);});

        JButton avatar5 = new JButton();
        avatar5.setBackground(Color.decode("#0066DB"));
        avatar5.setBorder(null);
        avatar5.setBounds(285, 195, 120, 120);
        String vaca = "/vaca.png";
        avatar5.setIcon(new ImageIcon(getClass().getResource(vaca)));
        panelCentro.add(avatar5);
        avatar5.addActionListener(e ->{seleccionarAvatar(avatar5, vaca);});

        JButton avatar6 = new JButton();
        avatar6.setBackground(Color.decode("#0066DB"));
        avatar6.setBorder(null);
        avatar6.setBounds(435, 195, 120, 120);
        String zorro = "/zorro.png";
        avatar6.setIcon(new ImageIcon(getClass().getResource(zorro)));
        panelCentro.add(avatar6);
        avatar6.addActionListener(e ->{seleccionarAvatar(avatar6, zorro);});


        JButton avatar7 = new JButton();
        avatar7.setBackground(Color.decode("#0066DB"));
        avatar7.setBorder(null);
        avatar7.setBounds(585, 195, 120, 120);
        String pinguino = "/pinguino.png";
        avatar7.setIcon(new ImageIcon(getClass().getResource(pinguino)));
        panelCentro.add(avatar7);
        avatar7.addActionListener(e ->{seleccionarAvatar(avatar7, pinguino);});

        JButton avatar8 = new JButton();
        avatar8.setBackground(Color.decode("#0066DB"));
        avatar8.setBorder(null);
        avatar8.setBounds(735, 195, 120, 120);
        String perro = "/perro.png";
        avatar8.setIcon(new ImageIcon(getClass().getResource(perro)));
        panelCentro.add(avatar8);
        avatar8.addActionListener(e ->{seleccionarAvatar(avatar8, perro);});

        //label para decirle al usuario que ponga su nombre
        JLabel nombre = new JLabel("Nombre de usuario");
        nombre.setBounds(465, 350, 200, 30);
        nombre.setFont(new Font("Rubik One", Font.BOLD, 20));
        nombre.setForeground(Color.BLACK);
        panelCentro.add(nombre);

        //agregar el textfield para el nombre de usuario
        txtField = new CampoTextoRedondeado();
        txtField.setBounds(365, 400, 400, 60);
        panelCentro.add(txtField);

        //agregar las cartas donde se eligen los colores
        PanelCarta carta1 = new PanelCarta();
        carta1.setBounds(350, 500, 85, 130);
        panelCentro.add(carta1);
        carta1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        carta1.addMouseListener(eventoClickCarta);

        PanelCarta carta2 = new PanelCarta();
        carta2.setBounds(465, 500, 85, 130);
        panelCentro.add(carta2);
        carta2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        carta2.addMouseListener(eventoClickCarta);

        PanelCarta carta3 = new PanelCarta();
        carta3.setBounds(580, 500, 85, 130);
        panelCentro.add(carta3);
        carta3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        carta3.addMouseListener(eventoClickCarta);

        PanelCarta carta4 = new PanelCarta();
        carta4.setBounds(695, 500, 85, 130);
        panelCentro.add(carta4);
        carta4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        carta4.addMouseListener(eventoClickCarta);

        //boton de anterior y siguiente
        anterior = new BotonRedondeado(Color.red, 20);
        anterior.setText("Anterior");
        anterior.setBounds(50, 600, 150, 50);
        panelCentro.add(anterior);

        siguiente = new BotonRedondeado(Color.green, 20);
        siguiente.setText("Siguiente");
        siguiente.setBounds(931, 600, 150, 50);
        panelCentro.add(siguiente);
        
    }

    private void cargarColores() {
        panelCentro.removeAll();
        panelCentro.revalidate();
        panelCentro.repaint();
        
        //label para decirle al usuario que ponga su nombre
        JLabel nombre = new JLabel("Elegir un color");
        nombre.setBounds(415, 50, 300, 50);
        nombre.setFont(new Font("Rubik One", Font.BOLD, 40));
        nombre.setForeground(Color.BLACK);
        panelCentro.add(nombre);
        
        //agregar los colores para elegir
        BotonRedondeado color1 = new BotonRedondeado(Color.decode("#0AC500"), 120);
        color1.setBounds(285, 230, 120, 120);
        panelCentro.add(color1);
        color1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        color1.addMouseListener(eventoClickColor);

        BotonRedondeado color2 = new BotonRedondeado(Color.decode("#FF9D00"), 120);
        color2.setBounds(435, 230, 120, 120);
        panelCentro.add(color2);
        color2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        color2.addMouseListener(eventoClickColor);

        BotonRedondeado color3 = new BotonRedondeado(Color.decode("#00C559"), 120);
        color3.setBounds(585, 230, 120, 120);
        panelCentro.add(color3);
        color3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        color3.addMouseListener(eventoClickColor);

        BotonRedondeado color4 = new BotonRedondeado(Color.decode("#00397B"), 120);
        color4.setBounds(735, 230, 120, 120);
        panelCentro.add(color4);
        color4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        color4.addMouseListener(eventoClickColor);

        BotonRedondeado color5 = new BotonRedondeado(Color.decode("#D06868"), 120);
        color5.setBounds(285, 365, 120, 120);
        panelCentro.add(color5);
        color5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        color5.addMouseListener(eventoClickColor);

        BotonRedondeado color6 = new BotonRedondeado(Color.decode("#6EB2CF"), 120);
        color6.setBounds(435, 365, 120, 120);
        panelCentro.add(color6);
        color6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        color6.addMouseListener(eventoClickColor);

        BotonRedondeado color7 = new BotonRedondeado(Color.decode("#FF79F2"), 120);
        color7.setBounds(585, 365, 120, 120);
        panelCentro.add(color7);
        color7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        color7.addMouseListener(eventoClickColor);

        BotonRedondeado color8 = new BotonRedondeado(Color.decode("#E6E600"), 120);
        color8.setBounds(735, 365, 120, 120);
        panelCentro.add(color8);
        color8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        color8.addMouseListener(eventoClickColor);
    }
    
    private void seleccionarAvatar(JButton botonClickeado, String rutaImagen){
        if (avatarSeleccionado != null) {
            avatarSeleccionado.setBackground(Color.decode("#0066DB"));
        }
        botonClickeado.setBackground(Color.decode("#003366"));
        avatarSeleccionado = botonClickeado;
        jugador.setRutaAvatar(rutaImagen);
    }

    private final java.awt.event.MouseAdapter eventoClickCarta = new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            cargarColores();
        }
    };
    
    private final java.awt.event.MouseAdapter eventoClickColor = new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            cargarConfiguración();
        }
    };
    
    private void acciones(){
        
        siguiente.addActionListener(e ->{
            try{
                if (txtField != null) {
                    jugador.setNombreUsuario(txtField.getText());
                }else{
                    throw new Exception("El campo de texto no puede estar vacío");
                }
                
                
            }catch(Exception ex){
                
            }
            
        });
        
    }

    @Override
    public void update(IModeloSalaVista modeloVista) {
        
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

        private final int radio;
        private final Color colorFondo;

        public BotonRedondeado(Color colorFondo, int radio) {
            super();
            this.colorFondo = colorFondo;
            this.radio = radio;
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
