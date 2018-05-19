/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandpile;


import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angie
 */
public class Pantalla3 extends JFrame {

    public Pantalla3(int width, int height) {
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        int n = this.getSize().width;
        int m = this.getSize().height;
        ProyectoArena pr = new ProyectoArena(n, m);
        pr.vecinosVonNeuman();
        int iteraciones =3;
        System.out.println("______________Matriz_Original_______________________________________________________________________");
//        pr.pintarMatriz(pr.matriz);
        System.out.println("____________________________________________________________________________________________________");
        //pr.siguienteEstadoVida( 1 , 1 , pr.matriz, pr.vecinos);
        while (iteraciones > 0) {
//            pr.cambioEstadosArena((int) n / 2, (int) m / 2);

            if(((int) (Math.random()*2)%2)==0){                
                pr.cambioEstadosArena((int) (Math.random()*n/2), (int) (Math.random()*m/2));
            }else{
                pr.cambioEstadosArena((int) (Math.random()*n/2)+n/2-1, (int) (Math.random()*m/2)+m/2-1);
            }
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < m; y++) {
                    if (pr.matriz[x][y] == 0) {
                        g.setColor(Color.BLACK);
                        g.drawLine(x, y, x, y);
                    }
                    if (pr.matriz[x][y] == 1) {
                        g.setColor(Color.YELLOW);
                        g.drawLine(x, y, x, y);
                    }
                    if (pr.matriz[x][y] == 2) {
                        g.setColor(Color.GREEN);
                        g.drawLine(x, y, x, y);
                    }
                    if (pr.matriz[x][y] == 3) {
                        g.setColor(Color.RED);
                        g.drawLine(x, y, x, y);
                    }
                }
            }
            iteraciones--;
            try {
                Thread.sleep(3);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pantalla3.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        System.out.println(pr.c);
        //________________________________________________________________

        System.out.println("fin");
        g.setColor(Color.RED);

    }

    public static void main(String[] args) {
        Pantalla3 p = new Pantalla3(100, 100);
    }

}
