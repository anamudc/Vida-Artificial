/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebatransformacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author angie
 */
public class Transformacion extends JComponent {

    /**
     * Devuelve como tamaño preferido el de la foto.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(icono.getIconWidth(), icono.getIconHeight());
    }

    /**
     * La foto
     */
    private ImageIcon icono = null;

    /**
     * Carga la foto y la guarda
     *
     * @param ficheroImagen
     */
    public Transformacion(String ficheroImagen) {
        icono = new ImageIcon(ficheroImagen);
        File imageFile = new File("raton.png"); // guarda la imagen en un archivo
        try {
            icono.setImage(ImageIO.read(getClass().getResourceAsStream(imageFile.toString()))); // la carga en una BufferedReader
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Cuanto queremos que se rote la foto, en radianes.
     */
    private double rotacion = 0.0;

    /**
     * Dibujo de la foto rotandola.
     */
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // AffineTransform realiza el giro, usando como eje de giro el centro
        // de la foto (width/2, height/2) y el angulo que indica el atributo
        // rotacion.
        AffineTransform tx = AffineTransform.getRotateInstance(rotacion,
                icono.getIconWidth() / 2, icono.getIconHeight() / 2);

        AffineTransform tx2 = AffineTransform.getQuadrantRotateInstance((int)(rotacion*rotacion*10), 20.5, 80.8);

        AffineTransform tx3 = AffineTransform.getScaleInstance(rotacion, Math.pow(rotacion, 2));

        AffineTransform tx4 = AffineTransform.getShearInstance(Math.pow(Math.E,rotacion), Math.pow(rotacion, 2));

        // dibujado con la AffineTransform de rotacion
        g2d.drawImage(icono.getImage(), tx4, this);
    }

    /**
     * Devuelve la rotacion actual.
     *
     * @return rotacion en radianes
     */
    public double getRotacion() {
        return rotacion;
    }

    /**
     * Se le pasa la rotación deseada.
     *
     * @param rotacion La rotacion en radianes.
     */
    public void setRotacion(double rotacion) {
        this.rotacion = rotacion;
    }

//    public static Image img2 = new ImageIcon("turingmorph2.png").getImage();
//
//    private BufferedImage img = null;
//
//    public void PanelOverDraw() {
//
//        File imageFile = new File("turingmorph2.png"); // guarda la imagen en un archivo
//        try {
//            img = ImageIO.read(getClass().getResourceAsStream(imageFile.toString())); // la carga en una BufferedReader
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        this.setPreferredSize(new Dimension(484, 409));
//
//        // creamos una instancia graphics desde la imagen para pintar sobre ella
//        Graphics2D pint = img.createGraphics();
//        pint.setColor(Color.GREEN);
//        pint.fillRect(200, 200, 100, 100);
//        pint.dispose();
//
//    }
}
