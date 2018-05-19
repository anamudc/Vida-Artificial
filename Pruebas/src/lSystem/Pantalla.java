/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lSystem;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Graphics;

/**
 *
 * @author angie
 */
public class Pantalla extends JFrame {

    public Pantalla() {
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        String aux = "";
        String axioma = "F-F-F-F";
        int iteraciones = 4;
        DOLSystem sys = new DOLSystem();
        String res = axioma;
        
        for (int j=0;j<iteraciones;j++){
            res = sys.GenerarPalabra(res);
        }
//        System.out.println(res);
        String axiomaTurtle = res;
        
        TurtleInt2 ti = new TurtleInt2();
        ti.n = 500;
        ti.m = 500;
        int matriz[][] = new int[ti.n][ti.m];
        ti.llenarMatriz();
        ti.pintarMatriz();
        ti.distacia = 2;
        ti.delta = 90;
        Turtle turtle = new Turtle((int) ti.n / 2, (int) ti.m / 2, 180);
        
        matriz[turtle.xPosition][turtle.yPosition]=-1;
//        System.out.println(" x= " + turtle.xPosition + "  y= " + turtle.yPosition + " a= " + turtle.angle);
        for (int i = 0; i < axiomaTurtle.length(); i++) {
            aux = axiomaTurtle.substring(i, i + 1);
            int x = turtle.xPosition;
            int y = turtle.yPosition;
            turtle = ti.siguientePaso(aux, turtle, ti.distacia, ti.delta);

            matriz[(turtle.xPosition + ti.n) % ti.n][(turtle.yPosition + ti.m) % ti.m] = i + 1;
//            System.out.print(" i= "+i+" aux= "+aux);
//            System.out.println(" x= "+turtle.xPosition+"  y= "+turtle.yPosition+ " a= "+turtle.angle);
//            ti.pintarMatriz();
            g.setColor(Color.RED);
            g.drawLine(x, y, turtle.xPosition, turtle.yPosition);
        }
        
        
        
        g.setColor(Color.RED);
        
    }

    public static void main(String[] args) {
        Pantalla p = new Pantalla();
    }

}
