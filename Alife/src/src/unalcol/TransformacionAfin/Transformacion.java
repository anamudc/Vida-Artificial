/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.unalcol.TransformacionAfin;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author angie
 */
public class Transformacion extends JPanel{
    
    int tasa;
    int x;
    
    public void transformacionElipse(int x, int tasa){
        this.tasa = tasa;
        this.x = x;
    }
    
    public void paint(Graphics g) {    
//        int foco1 = 0;        
//        int width = 34;
//        int height = 12;     
//        
//        g.drawOval(x, foco1, width*tasa, height);   
//        
        int b = 30;
        int c = 40;        
        int nPuntos =6;
        int xPuntos[] = {2,5,4,5,3,1};
        int yPuntos[] = {-1,-1,-2,-4,-4,-3};
        double a = Math.sqrt(c*c + b*b);
        
        for(int i = 0; i<xPuntos.length-1; i++){
//            xPuntos[i]=(nPuntos/2)-i;
//            yPuntos[i]= (int) (b * Math.sqrt((1- (xPuntos[i]*xPuntos[i])/(a*a))));

            g.drawLine(xPuntos[i],yPuntos[i], xPuntos[i+1],yPuntos[i+1]);
            System.out.println("i="+i+"; x: " + xPuntos[i]+" y: " + yPuntos[i]);
        }
        
        
//        g.drawPolygon(xPuntos, yPuntos, nPuntos);
    
//        g.drawOval(25, 25, 120, 120);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        Transformacion panel = new Transformacion();
        int tasaTransformacion = (int) (Math.random()*5);
        panel.transformacionElipse(2, tasaTransformacion);
        frame.getContentPane().add(panel);
     
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
    }
    
}
