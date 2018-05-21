/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boids2.src;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import pruebaturing.Piel;
import turingMorphA.Solver;

/**
 *
 * @author angie
 */
public class Transform {

    private ImageIcon icono = null;
    private ImageIcon icono2 = null;
    private ImageIcon icono3 = null;

    int pixelesAlto;
    int pixelesLargo;
    int parametro1; //es el divisor que define los limites donde estarán los vertices de las parabolas
    int parametro2; //el valor que define la variación de la amplitud de las parabolas
    int parametro3; //numero de iteraciones para el turingmorph
    int parametro4; //valor entre 1 y 6 para definir el color del turing morph
    boolean parametro5; //tipo de animal (gato o ratón)
    int parametro6; //numero de animal a crear (numeracion de animales)

    public ImageIcon cambioPiel(String imgAnimal, String imgPiel, String imgBase,
            int pVertices, int pAmplitud, int iteracionesMorph, int colorMorph, boolean tipoRaton, int numeroAnimal) {
        String ruta = "imgs/";
        BufferedImage bufferBase = null;

        try {
            icono = new ImageIcon(ruta + imgAnimal);

            icono2 = new ImageIcon(ruta + imgPiel);
            icono3 = new ImageIcon(ruta + imgBase);

            parametro1 = pVertices;
            parametro2 = pAmplitud;
            parametro3 = iteracionesMorph;
            parametro4 = colorMorph;
            if (tipoRaton) {
                parametro5 = true;
            } else {
                parametro5 = false;
            }
            parametro6 = numeroAnimal;

            File imageFile = new File(ruta + imgAnimal);
            File imageFile2 = new File(ruta + imgPiel);

            try {
                icono.setImage(ImageIO.read(ImageIO.createImageInputStream(imageFile)));

                pixelesAlto = icono.getIconHeight();
                pixelesLargo = icono.getIconWidth();

                if (pixelesAlto > pixelesLargo) {
                    Solver solver2 = new Solver(pixelesAlto, pixelesAlto, parametro3, parametro4, imgPiel);
                } else {
                    Solver solver2 = new Solver(pixelesLargo, pixelesLargo, parametro3, parametro4, imgPiel);
                }
                icono2.setImage(ImageIO.read(ImageIO.createImageInputStream(imageFile2)));

            } catch (IOException e) {
                e.printStackTrace();
            }

            bufferBase = new BufferedImage(pixelesLargo, pixelesAlto, 3);

            BufferedImage bufferRaton = toBufferedImage(ImageIO.read(ImageIO.createImageInputStream(imageFile)));
            BufferedImage bufferTuring = toBufferedImage(ImageIO.read(ImageIO.createImageInputStream(imageFile2)));

            int colorBase = 0;
            if (parametro5) {
                colorBase = 855638016;
            } else {
                colorBase = -251605446;
            }

            for (int x = 0; x < pixelesLargo - 1; x++) {
                for (int y = 0; y < pixelesAlto - 1; y++) {
                    int a = bufferRaton.getRGB(x, y);
                    int b = bufferTuring.getRGB(x, y);
                    int y2 = 0;
                    try {
                        if (a == colorBase) {
                            bufferRaton.setRGB(x, y, b);
                        }
                        if (parametro5) {
                            y2 = CoordenadaY(x, y);
                        }else{
                            y2 = CoordenadaYGato(x, y);
                        }

                        a = bufferRaton.getRGB(x, y);
                        bufferBase.setRGB(x, y2, a);
                    } catch (Exception e) {
                        System.out.println("x: " + x + " y:" + y + " y2:" + y2);
                        e.printStackTrace();
                    }
                }
            }

            ImageIO.write(bufferBase, "png", new File(ruta + parametro5 + parametro6 + ".png"));
            System.out.println("done");
            
            
        } catch (IOException ex) {
            Logger.getLogger(Piel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ImageIcon(bufferBase);
    }

    public int CoordenadaY(int x, int y) {
        int nuevoY = 0;

        if (y == 0) {
            return 0;
        }

        if (y > 0 && y < pixelesAlto / 2) {
            nuevoY = (int) ((-(Math.pow((x - pixelesLargo / 2), 2) / (y * parametro2))) + (y + (pixelesAlto / parametro1)));
        }
        if (y == pixelesAlto / 2) {
            nuevoY = y;
        }
        if (y > pixelesAlto / 2) {
            nuevoY = (int) (((Math.pow((x - pixelesLargo / 2), 2) / ((pixelesAlto - y) * parametro2))) + (y + (pixelesAlto / parametro1)));
        }

        if (nuevoY < 0 || nuevoY >= pixelesAlto || nuevoY >= pixelesLargo) {
            nuevoY = 0;
        }
        return nuevoY;
    }

    public int CoordenadaYGato(int x, int y) {
        int nuevoY = 0;

        if (y == 0) {
            return 0;
        }

        if (y > 0 && y < pixelesAlto / 2) {
            nuevoY = (int) (((Math.pow((x - pixelesLargo / 2), 2) / ((pixelesAlto - y) * parametro2))) + (y + (pixelesAlto / parametro1)));
        }
        if (y == pixelesAlto / 2) {
            nuevoY = y;
        }
        if (y > pixelesAlto / 2) {
            nuevoY = (int) ((-(Math.pow((x - pixelesLargo / 2), 2) / (y * parametro2))) + (y + (pixelesAlto / parametro1)));
        }

        if (nuevoY < 0 || nuevoY >= pixelesAlto || nuevoY >= pixelesLargo) {
            nuevoY = 0;
        }
        return nuevoY;
    }

    public static BufferedImage toBufferedImage(Image image) {
        BufferedImage bimage = null;
        try {
            if (image instanceof BufferedImage) {
                return (BufferedImage) image;
            }
            image = new ImageIcon(image).getImage();
            boolean hasAlpha = hasAlpha(image);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            try {
                int transparency = Transparency.OPAQUE;
                if (hasAlpha) {
                    transparency = Transparency.BITMASK;
                }
                GraphicsDevice gs = ge.getDefaultScreenDevice();
                GraphicsConfiguration gc = gs.getDefaultConfiguration();
                bimage = gc.createCompatibleImage(
                        image.getWidth(null), image.getHeight(null), transparency);
            } catch (HeadlessException e) {
            }
            if (bimage == null) {
                int type = BufferedImage.TYPE_INT_RGB;
                if (hasAlpha) {
                    type = BufferedImage.TYPE_INT_ARGB;
                }
                bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
            }
            Graphics g = bimage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            return bimage;
        } catch (java.lang.NullPointerException e) {
        }
        return bimage;
    }
// This method returns true if the specified image has transparent pixels 
/*COPIADO Y PEGADO DE: http://javaalmanac.com/egs/java.awt.image/pkg.html*/
    public static boolean hasAlpha(Image image) {
        try {
            if (image instanceof BufferedImage) {
                BufferedImage bimage = (BufferedImage) image;
                return bimage.getColorModel().hasAlpha();
            }
            PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
            pg.grabPixels();
            ColorModel cm = pg.getColorModel();
            return cm.hasAlpha();
        } catch (InterruptedException ex) {
            Logger.getLogger(Piel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
