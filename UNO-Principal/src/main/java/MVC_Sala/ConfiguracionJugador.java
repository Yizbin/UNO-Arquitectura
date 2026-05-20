/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import DTOs.CartaDTO;
import DTOs.JugadorResumenDTO;
import Enums.TipoColor;
import static Enums.TipoColor.AMARILLO;
import static Enums.TipoColor.AZUL;
import static Enums.TipoColor.CAFE;
import static Enums.TipoColor.CIAN;
import static Enums.TipoColor.GRIS;
import static Enums.TipoColor.MAGENTA;
import static Enums.TipoColor.MORADO;
import static Enums.TipoColor.NARANJA;
import static Enums.TipoColor.ROJO;
import static Enums.TipoColor.ROSA;
import static Enums.TipoColor.VERDE;
import MVC_JugarTurno.PanelCartaMano;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    private final IModeloSalaVista modeloVista;
    private final PanelImagenFondo panelFondo;
    private PanelAzul panelCentro;
    private CampoTextoRedondeado txtField;
    private BotonRedondeado siguiente;
    private BotonRedondeado anterior;
    private JButton avatarSeleccionado = null;
    private PanelCartaMano cartaSeleccionada = null;
    private TipoColor colorBaseSeleccionado = null;
    private final Map<TipoColor, TipoColor> misColores = new HashMap<>();
    private final JugadorResumenDTO jugador = new JugadorResumenDTO();
    private final CartaDTO c1 = new CartaDTO();
    private final CartaDTO c2 = new CartaDTO();
    private final CartaDTO c3 = new CartaDTO();
    private final CartaDTO c4 = new CartaDTO();

    private final OrigenRegistro origenRegistro;
    private boolean solicitudUnionEnviada;

    public ConfiguracionJugador(
            ControladorSala control,
            IModeloSalaVista modeloVista,
            OrigenRegistro origenRegistro
    ) throws IOException {
        this.panelFondo = new PanelImagenFondo("/fondo.png");
        this.control = control;
        this.modeloVista = modeloVista;
        this.origenRegistro = origenRegistro;

        initComponents();

        this.modeloVista.suscribir(this);

        misColores.put(TipoColor.ROJO, TipoColor.ROJO);
        misColores.put(TipoColor.AZUL, TipoColor.AZUL);
        misColores.put(TipoColor.VERDE, TipoColor.VERDE);
        misColores.put(TipoColor.AMARILLO, TipoColor.AMARILLO);
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
        avatar1.addActionListener(e -> {
            seleccionarAvatar(avatar1, unicornio);
        });

        JButton avatar2 = new JButton();
        avatar2.setBackground(Color.decode("#0066DB"));
        avatar2.setBorder(null);
        avatar2.setBounds(435, 60, 120, 120);
        String panda = "/panda.png";
        avatar2.setIcon(new ImageIcon(getClass().getResource(panda)));
        panelCentro.add(avatar2);
        avatar2.addActionListener(e -> {
            seleccionarAvatar(avatar2, panda);
        });

        JButton avatar3 = new JButton();
        avatar3.setBackground(Color.decode("#0066DB"));
        avatar3.setBorder(null);
        avatar3.setBounds(585, 60, 120, 120);
        String mono = "/mono.png";
        avatar3.setIcon(new ImageIcon(getClass().getResource(mono)));
        panelCentro.add(avatar3);
        avatar3.addActionListener(e -> {
            seleccionarAvatar(avatar3, mono);
        });

        JButton avatar4 = new JButton();
        avatar4.setBackground(Color.decode("#0066DB"));
        avatar4.setBorder(null);
        avatar4.setBounds(735, 60, 120, 120);
        String gato = "/gato.png";
        avatar4.setIcon(new ImageIcon(getClass().getResource(gato)));
        panelCentro.add(avatar4);
        avatar4.addActionListener(e -> {
            seleccionarAvatar(avatar4, gato);
        });

        JButton avatar5 = new JButton();
        avatar5.setBackground(Color.decode("#0066DB"));
        avatar5.setBorder(null);
        avatar5.setBounds(285, 195, 120, 120);
        String vaca = "/vaca.png";
        avatar5.setIcon(new ImageIcon(getClass().getResource(vaca)));
        panelCentro.add(avatar5);
        avatar5.addActionListener(e -> {
            seleccionarAvatar(avatar5, vaca);
        });

        JButton avatar6 = new JButton();
        avatar6.setBackground(Color.decode("#0066DB"));
        avatar6.setBorder(null);
        avatar6.setBounds(435, 195, 120, 120);
        String zorro = "/zorro.png";
        avatar6.setIcon(new ImageIcon(getClass().getResource(zorro)));
        panelCentro.add(avatar6);
        avatar6.addActionListener(e -> {
            seleccionarAvatar(avatar6, zorro);
        });

        JButton avatar7 = new JButton();
        avatar7.setBackground(Color.decode("#0066DB"));
        avatar7.setBorder(null);
        avatar7.setBounds(585, 195, 120, 120);
        String pinguino = "/pinguino.png";
        avatar7.setIcon(new ImageIcon(getClass().getResource(pinguino)));
        panelCentro.add(avatar7);
        avatar7.addActionListener(e -> {
            seleccionarAvatar(avatar7, pinguino);
        });

        JButton avatar8 = new JButton();
        avatar8.setBackground(Color.decode("#0066DB"));
        avatar8.setBorder(null);
        avatar8.setBounds(735, 195, 120, 120);
        String perro = "/perro.png";
        avatar8.setIcon(new ImageIcon(getClass().getResource(perro)));
        panelCentro.add(avatar8);
        avatar8.addActionListener(e -> {
            seleccionarAvatar(avatar8, perro);
        });

        //label para decirle al usuario que ponga su nombre
        JLabel nombre = new JLabel("Nombre de usuario");
        nombre.setBounds(465, 350, 200, 30);
        nombre.setFont(new Font("Rubik One", Font.BOLD, 20));
        nombre.setForeground(Color.BLACK);
        panelCentro.add(nombre);

        //agregar el textfield para el nombre de usuario
        txtField = new CampoTextoRedondeado();
        txtField.setBounds(365, 400, 400, 60);
        if (jugador.getNombreUsuario() != null) {
            txtField.setText(jugador.getNombreUsuario());
        }
        panelCentro.add(txtField);

        cargarCartas();

        //boton de anterior y siguiente
        anterior = new BotonRedondeado(Color.red, 20);
        anterior.setText("Anterior");
        anterior.setBounds(50, 600, 150, 50);
        panelCentro.add(anterior);

        siguiente = new BotonRedondeado(Color.green, 20);
        siguiente.setText("Siguiente");
        siguiente.setBounds(931, 600, 150, 50);
        panelCentro.add(siguiente);

        acciones();

        if (jugador.getRutaAvatar() != null) {
            for (java.awt.Component comp : panelCentro.getComponents()) {
                if (comp instanceof JButton btn) {
                    javax.swing.Icon icon = btn.getIcon();
                    if (icon != null && icon.toString().contains(jugador.getRutaAvatar())) {
                        btn.setBackground(Color.decode("#003366"));
                        avatarSeleccionado = btn;
                        break;
                    }
                }
            }
        }
    }

    private void cargarCartas() {
        c1.setColor(misColores.get(TipoColor.ROJO));
        c2.setColor(misColores.get(TipoColor.AZUL));
        c3.setColor(misColores.get(TipoColor.VERDE));
        c4.setColor(misColores.get(TipoColor.AMARILLO));

        //agregar las cartas donde se eligen los colores
        PanelCartaMano carta1 = new PanelCartaMano();
        carta1.setBounds(350, 500, 85, 130);
        carta1.setCarta(c1);
        panelCentro.add(carta1);
        carta1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        carta1.addMouseListener(eventoClickCarta);

        PanelCartaMano carta2 = new PanelCartaMano();
        carta2.setBounds(465, 500, 85, 130);
        carta2.setCarta(c2);
        panelCentro.add(carta2);
        carta2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        carta2.addMouseListener(eventoClickCarta);

        PanelCartaMano carta3 = new PanelCartaMano();
        carta3.setBounds(580, 500, 85, 130);
        carta3.setCarta(c3);
        panelCentro.add(carta3);
        carta3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        carta3.addMouseListener(eventoClickCarta);

        PanelCartaMano carta4 = new PanelCartaMano();
        carta4.setBounds(695, 500, 85, 130);
        carta4.setCarta(c4);
        panelCentro.add(carta4);
        carta4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        carta4.addMouseListener(eventoClickCarta);

    }

    private void cargarColores() {
        panelCentro.removeAll();
        panelCentro.revalidate();
        panelCentro.repaint();

        JLabel nombre = new JLabel("Elegir un color", SwingConstants.CENTER);
        nombre.setBounds(0, 50, panelCentro.getWidth(), 50);
        nombre.setFont(new Font("Rubik One", Font.BOLD, 40));
        nombre.setForeground(Color.BLACK);
        panelCentro.add(nombre);

        java.util.List<TipoColor> coloresDisponibles = new java.util.ArrayList<>(java.util.Arrays.asList(
                TipoColor.ROJO, TipoColor.AZUL, TipoColor.VERDE, TipoColor.AMARILLO,
                TipoColor.NARANJA, TipoColor.MORADO, TipoColor.ROSA, TipoColor.CAFE,
                TipoColor.NEGRO, TipoColor.MAGENTA, TipoColor.GRIS, TipoColor.CIAN
        ));

        java.util.List<TipoColor> coloresUsados = java.util.Arrays.asList(
                c1.getColor(), c2.getColor(), c3.getColor(), c4.getColor()
        );
        coloresDisponibles.removeAll(coloresUsados);
        int startX = 285;
        int startY = 180;

        for (int i = 0; i < coloresDisponibles.size(); i++) {
            TipoColor colorLogico = coloresDisponibles.get(i);
            Color colorVisual = traducirColor(colorLogico);

            BotonRedondeado btnColor = new BotonRedondeado(colorVisual, 120);

            int fila = i / 4;
            int columna = i % 4;
            btnColor.setBounds(startX + (columna * 150), startY + (fila * 150), 120, 120);

            btnColor.addActionListener(e -> seleccionarColor(btnColor, colorVisual));
            panelCentro.add(btnColor);
        }

        BotonRedondeado btnCancelar = new BotonRedondeado(Color.RED, 20);
        btnCancelar.setText("Cancelar");
        btnCancelar.setBounds((panelCentro.getWidth() - 150) / 2, 530, 150, 50);
        btnCancelar.addActionListener(e -> cargarConfiguración());
        panelCentro.add(btnCancelar);

        panelCentro.revalidate();
        panelCentro.repaint();
    }

    private void seleccionarAvatar(JButton botonClickeado, String rutaImagen) {
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
            if (txtField != null) {
                jugador.setNombreUsuario(txtField.getText());
            }
            cartaSeleccionada = (PanelCartaMano) e.getSource();
            if (cartaSeleccionada.carta == c1) {
                colorBaseSeleccionado = TipoColor.ROJO;
            } else if (cartaSeleccionada.carta == c2) {
                colorBaseSeleccionado = TipoColor.AZUL;
            } else if (cartaSeleccionada.carta == c3) {
                colorBaseSeleccionado = TipoColor.VERDE;
            } else if (cartaSeleccionada.carta == c4) {
                colorBaseSeleccionado = TipoColor.AMARILLO;
            }
            cargarColores();
        }
    };

    private void seleccionarColor(BotonRedondeado boton, Color colorSeleccionado) {
        if (cartaSeleccionada == null || colorSeleccionado == null) {
            return;
        }
        TipoColor nuevoColor = traducirTipoColor(colorSeleccionado);

        if (cartaSeleccionada.carta != null) {
            cartaSeleccionada.carta.setColor(nuevoColor);
        }

        misColores.put(colorBaseSeleccionado, nuevoColor);
        cargarConfiguración();
    }

    private Color traducirColor(TipoColor tipo) {
        switch (tipo) {
            case ROJO -> {
                return Color.RED;
            }
            case AZUL -> {
                return Color.BLUE;
            }
            case AMARILLO -> {
                return Color.YELLOW;
            }
            case VERDE -> {
                return Color.GREEN;
            }
            case NARANJA -> {
                return Color.ORANGE;
            }
            case MORADO -> {
                return Color.decode("#D363FF");
            }
            case ROSA -> {
                return Color.PINK;
            }
            case CAFE -> {
                return Color.decode("#693A19");
            }
            case GRIS -> {
                return Color.LIGHT_GRAY;
            }
            case CIAN -> {
                return Color.CYAN;
            }
            case MAGENTA -> {
                return Color.MAGENTA;
            }
            case NEGRO -> {
                return Color.BLACK;
            }
            default ->
                throw new AssertionError();
        }
    }

    private TipoColor traducirTipoColor(Color color) {
        if (Color.RED.equals(color)) {
            return TipoColor.ROJO;
        } else if (Color.BLUE.equals(color)) {
            return TipoColor.AZUL;
        } else if (Color.YELLOW.equals(color)) {
            return TipoColor.AMARILLO;
        } else if (Color.GREEN.equals(color)) {
            return TipoColor.VERDE;
        } else if (Color.ORANGE.equals(color)) {
            return TipoColor.NARANJA;
        } else if (Color.decode("#D363FF").equals(color)) {
            return TipoColor.MORADO;
        } else if (Color.PINK.equals(color)) {
            return TipoColor.ROSA;
        } else if (Color.decode("#693A19").equals(color)) {
            return TipoColor.CAFE;
        } else if (Color.LIGHT_GRAY.equals(color)) {
            return TipoColor.GRIS;
        } else if (Color.CYAN.equals(color)) {
            return TipoColor.CIAN;
        } else if (Color.MAGENTA.equals(color)) {
            return TipoColor.MAGENTA;
        } else if (Color.BLACK.equals(color)) {
            return TipoColor.NEGRO;
        } else {
            throw new AssertionError("Color no reconocido: " + color);
        }
    }

    private void acciones() {
        siguiente.addActionListener(e -> {
            if (txtField == null) {
                return;
            }

            jugador.setNombreUsuario(txtField.getText());

            if (origenRegistro == OrigenRegistro.CREAR_PARTIDA) {
                jugador.setId(1);
                control.registrarJugador(jugador, misColores);
                abrirSalaEspera();
                return;
            }

            jugador.setId(0);
            control.registrarJugador(jugador, misColores);

            siguiente.setEnabled(false);
            siguiente.setText("Registrando...");
        });
    }

    private void abrirSalaEspera() {
        SalaEspera sala = new SalaEspera(control, modeloVista);
        sala.setVisible(true);
        this.dispose();
    }

    @Override
    public void update(IModeloSalaVista modeloVista) {
        if (modeloVista == null) {
            return;
        }

        if (!javax.swing.SwingUtilities.isEventDispatchThread()) {
            javax.swing.SwingUtilities.invokeLater(() -> update(modeloVista));
            return;
        }

        if (origenRegistro == OrigenRegistro.UNIRSE_PARTIDA) {
            if (!solicitudUnionEnviada && jugadorLocalYaTieneId(modeloVista)) {
                solicitudUnionEnviada = control.solicitarUnirsePartida();

                if (solicitudUnionEnviada) {
                    siguiente.setText("Esperando...");
                } else {
                    siguiente.setEnabled(true);
                    siguiente.setText("Siguiente");
                }

                return;
            }

            if (solicitudUnionEnviada && jugadorLocalFueAceptado(modeloVista)) {
                modeloVista.desuscribir(this);
                abrirSalaEspera();
                return;
            }
        }

        if (modeloVista.isCambiarFrame()) {
            modeloVista.desuscribir(this);
            this.dispose();

            MenuPrincipal menu = new MenuPrincipal(control, modeloVista);
            menu.setVisible(true);
        }
    }

    private boolean jugadorLocalYaTieneId(IModeloSalaVista modeloVista) {
        return modeloVista.getJugadorLocal() != null
                && modeloVista.getJugadorLocal().getId() > 0;
    }

    private boolean jugadorLocalFueAceptado(IModeloSalaVista modeloVista) {
        if (modeloVista == null
                || modeloVista.getJugadorLocal() == null
                || modeloVista.getJugadoresEnSala() == null) {
            return false;
        }

        int idJugadorLocal = modeloVista.getJugadorLocal().getId();

        for (JugadorResumenDTO jugadorEnSala : modeloVista.getJugadoresEnSala()) {
            if (jugadorEnSala != null
                    && jugadorEnSala.getId() == idJugadorLocal
                    && jugadorEnSala.isAceptado()) {
                return true;
            }
        }

        return false;
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
