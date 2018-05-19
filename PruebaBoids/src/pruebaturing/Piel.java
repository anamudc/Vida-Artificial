/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaturing;

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

/**
 *
 * @author angie
 */
public class Piel {

    private ImageIcon icono = null;
    private ImageIcon icono2 = null;

    public void cambioPiel(String imgAnimal, String imgPiel) {
        try {
            icono = new ImageIcon(imgAnimal);
            icono2 = new ImageIcon(imgPiel);

            File imageFile = new File("" + imgAnimal);
            File imageFile2 = new File("" + imgPiel);
            try {
                icono.setImage(ImageIO.read(getClass().getResourceAsStream(imageFile.toString())));
                icono2.setImage(ImageIO.read(getClass().getResourceAsStream(imageFile2.toString())));
            } catch (IOException e) {
                e.printStackTrace();
            }

            int pixelesAlto = icono.getIconHeight();
            int pixelesLargo = icono.getIconWidth();

            BufferedImage bufferRaton = toBufferedImage(ImageIO.read(getClass().getResourceAsStream(imageFile.toString())));
            BufferedImage bufferTuring = toBufferedImage(ImageIO.read(getClass().getResourceAsStream(imageFile2.toString())));

            for (int i = 0; i < pixelesLargo - 1; i++) {
                for (int j = 0; j < pixelesAlto - 1; j++) {
                    int a = bufferRaton.getRGB(i, j);
                    int b = bufferTuring.getRGB(i, j);
                    
                            System.out.println("color: "+a);
//-16777216 Negro
//-3355444 Gris objetivo

//16777215 verde -251605446
                    try {
                        if (a == -251605446) {
                            bufferRaton.setRGB(i, j, b);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            ImageIO.write(bufferRaton, "png", new File("./src/images/nuevoRaton.png"));
            System.out.println("done");

        } catch (IOException ex) {
            Logger.getLogger(Piel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
