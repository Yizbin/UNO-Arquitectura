/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Sala;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Usuario
 */
public class PanelImagenFondo extends JPanel{
    
    private Image imagenFondo;
    
    public PanelImagenFondo(String imagenPath) throws IOException {
        try{
        URL imgUrl = getClass().getResource(imagenPath);
            if (imgUrl != null) {
                imagenFondo = ImageIO.read(imgUrl);
            } else {
                System.err.println("No se encontró la imagen: " + imagenPath);
            }
        }catch(IOException e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}