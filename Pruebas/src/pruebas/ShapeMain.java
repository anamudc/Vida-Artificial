/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author angie
 */
public class ShapeMain extends JPanel {

    private int frameNumber = 0;
    long elapsedTimeMillis;
    static float pixelSize;
    static int translateX = 0;
    static int translateY = 0;
    static double rotation = 0.0;
    static double scaleX = 1.0;
    static double scaleY = 1.0;
    Images myImages = new Images();
    Images myImages2 = new Images();
    Images myImages3 = new Images();
    Images myImages4 = new Images();
    
    BufferedImage pacMan = myImages.getImage(Images.pacMan);
    BufferedImage letterH = myImages2.getImage(Images.letterH);
    BufferedImage triforce = myImages3.getImage(Images.triforce);
//    BufferedImage triforce2 = myImages4.getImage(Images.triforce2);

    public ShapeMain() {
        setPreferredSize(new Dimension(800, 600));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.BLUE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        applyWindowToViewportTransformation(g2, -62, 75, -75, 100, true);

        AffineTransform savedTransform = g2.getTransform();
        System.out.println("Frame is " + frameNumber);
        switch (frameNumber) {
            case 1: // First frame is unmodified.
                translateX = 0;
                translateY = 0;
                scaleX = 1.0;
                scaleY = 1.0;
                rotation = 0;
                break;
            case 2: // Second frame translates each image by (-5, 7).
                translateX = (int) (Math.random()*(-5));
                translateY = (int) (Math.random()*7);
                break;
            case 3: // Third frame rotates each image by 45 degrees Counter
                translateX = -5;
                translateY = 7;
                rotation = 45 * Math.PI / 180.0;
                break;
            case 4: // Fourth frame rotates each image by 90 degrees Clockwise
                translateX = -5;
                translateY = 7;
                rotation = (int) (Math.random()*(-45)) * Math.PI / 180.0;
                break;
            case 5: // Fifth frame scales image 2 times for x and .5 times for y
                translateX = -5;
                translateY = 7;
                scaleX = 2.0;
                scaleY = .5;
                break;

            default:
                break;
        } // End switch

        g2.translate(translateX, translateY); // Move image.
        // To offset translate again
        g2.translate(10, 10);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(pacMan, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);

        g2.translate(translateX, translateY); // Move image.
        // To offset translate again
        g2.translate(-30, 10);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(letterH, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);

        g2.translate(translateX, translateY); // Move image.
        // To offset translate again
        g2.translate(40, 10);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(triforce, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
        
//        g2.translate(translateX, translateY); // Move image.
//        // To offset translate again
//        g2.translate(80, 10);
//        g2.rotate(rotation); // Rotate image.
//        g2.scale(scaleX, scaleY); // Scale image.
//        g2.drawImage(triforce2, 0, 0, this); // Draw image.
//        g2.setTransform(savedTransform);

    }

    // Method taken directly from AnimationStarter.java Code
    private void applyWindowToViewportTransformation(Graphics2D g2, double left, 
                                                        double right, double bottom,
                                                        double top, boolean preserveAspect) {
        int width = getWidth(); // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        final ShapeMain panel = new ShapeMain();
        window.setTitle("Shape Transformation");
        window.setContentPane(panel);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screen.width - window.getWidth()) / 2, (screen.height - window.getHeight()) / 2);
        Timer animationTimer;
        final long startTime = System.currentTimeMillis();

        animationTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panel.frameNumber > 5) {
                    panel.frameNumber = 1;
                } else {
                    panel.frameNumber++;
                }
                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });
        animationTimer.start();
    }

}
