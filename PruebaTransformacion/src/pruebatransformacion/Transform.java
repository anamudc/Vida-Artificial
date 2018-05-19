/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebatransformacion;

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

/**
 *
 * @author angie
 */
public class Transform {

    private ImageIcon icono = null;
    private ImageIcon icono2 = null;

    int pixelesAlto;
    int pixelesLargo;
    int parametro1;

    public void cambioPiel(String imgAnimal, String imgPiel) {
        try {
            icono = new ImageIcon(imgAnimal);
            icono2 = new ImageIcon(imgPiel);
            parametro1 = 4;

            File imageFile = new File("" + imgAnimal);
            File imageFile2 = new File("" + imgPiel);
            try {
                icono.setImage(ImageIO.read(getClass().getResourceAsStream(imageFile.toString())));
                icono2.setImage(ImageIO.read(getClass().getResourceAsStream(imageFile2.toString())));
            } catch (IOException e) {
                e.printStackTrace();
            }

            pixelesAlto = icono.getIconHeight();
            pixelesLargo = icono.getIconWidth();

            BufferedImage bufferRaton = toBufferedImage(ImageIO.read(getClass().getResourceAsStream(imageFile.toString())));
            BufferedImage bufferTuring = toBufferedImage(ImageIO.read(getClass().getResourceAsStream(imageFile2.toString())));

            for (int x = 0; x < pixelesLargo - 1; x++) {
                for (int y = 0; y < pixelesAlto - 1; y++) {
                    int a = bufferRaton.getRGB(x, y);
                    int b = bufferTuring.getRGB(x, y);
                    int y2 = 0;
                    try {
                        y2 = CoordenadaY(x, y);
                        
                        bufferRaton.setRGB(x, y2, b);
                    } catch (Exception e) {
                        System.out.println("x: " + x + " y:" + y +" y2:"+y2);
                        e.printStackTrace();
                    }
                }
            }

            ImageIO.write(bufferRaton, "png", new File("transformRaton"+(int)(Math.random()*10)+".png"));
            System.out.println("done");

        } catch (IOException ex) {
            Logger.getLogger(Piel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int CoordenadaY(int x, int y) {
        int nuevoY = 0;
        
        if(y==0)return 0;

//        nuevoY = (int) ((-(Math.pow((x - pixelesLargo / 2), 2) / (y*10)))+ (y/2 +(pixelesAlto / parametro1))); //funciona
//        nuevoY = (int) (((Math.pow((x - pixelesLargo / 2), 2) / ((pixelesAlto-y)*10)))+(y/2 +(pixelesAlto / parametro1)));

        if(y>0&&y<pixelesAlto/2){
            nuevoY = (int) ((-(Math.pow((x - pixelesLargo / 2), 2) / (y*10)))+ (y/2 +(pixelesAlto / parametro1))); //funciona
        }
        if(y==pixelesAlto/2){
            nuevoY = y;
        }
        if(y>pixelesAlto/2){
            nuevoY = (int) (((Math.pow((x - pixelesLargo / 2), 2) / ((pixelesAlto-y)*10)))+(y/2 +(pixelesAlto / parametro1)));
        }
        
        if (nuevoY < 0 || nuevoY>=pixelesAlto || nuevoY>=pixelesLargo) {
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
