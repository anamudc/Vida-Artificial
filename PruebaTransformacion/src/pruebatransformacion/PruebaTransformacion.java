/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebatransformacion;

import javax.swing.JFrame;

/**
 *
 * @author angie
 */
public class PruebaTransformacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame f = new JFrame("Trasformacion");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        Transformacion tr = new Transformacion("/");
        
        f.add(tr);
        f.setSize(300, 300);
        f.setVisible(true);
        
    }
    
}
