/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lSystem;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angie
 */
public class Pantalla2 extends JFrame {

    public Pantalla2() {
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        String aux = "";
        String axioma = "F";
        int iteraciones = 4;
        DOLSystem sys = new DOLSystem();
        String res = axioma;

        for (int j = 0; j < iteraciones; j++) {
            res = sys.GenerarRama(res);
        }
        System.out.println(res);
        String axiomaTurtle = res;

        Planta1 ti = new Planta1();
        ti.n = 500;
        ti.m = 500;
        ti.llenarMatriz();
        ti.distacia = 5;
        ti.delta = 27;
        Turtle turtle = new Turtle((int) ti.n / 2, ti.m, 270);

        for (int i = 0; i < axiomaTurtle.length(); i++) {
            aux = axiomaTurtle.substring(i, i + 1);
            int x = turtle.xPosition;
            int y = turtle.yPosition;
            turtle = ti.siguientePaso(aux, turtle, ti.distacia, ti.delta);
            g.setColor(Color.ORANGE);
            g.drawLine(x, y, turtle.xPosition, turtle.yPosition);
        }
        System.out.println("fin");

    }

    public static void main(String[] args) {
        Pantalla2 p = new Pantalla2();
    }

}
