/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC_Utilidades;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Abraham Coronel
 */
public class UtilidadesGraficas {

    private UtilidadesGraficas() {
    }

    public static ImageIcon escalarImagen(Image imagenOriginal, int ancho, int alto) {

        Image imagenEscalada = imagenOriginal.getScaledInstance(
                ancho,
                alto,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(imagenEscalada);
    }

    public static ImageIcon escalarImagen(ImageIcon icono, int ancho, int alto) {

        return escalarImagen(icono.getImage(), ancho, alto);
    }

    public static ImageIcon hacerAvatarCircular(Image imagenOriginal, int diametro) {

        BufferedImage buffer = new BufferedImage(
                diametro,
                diametro,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2 = buffer.createGraphics();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setClip(new Ellipse2D.Float(0, 0, diametro, diametro));

        g2.drawImage(
                imagenOriginal,
                0,
                0,
                diametro,
                diametro,
                null
        );

        g2.dispose();

        return new ImageIcon(buffer);
    }
}
