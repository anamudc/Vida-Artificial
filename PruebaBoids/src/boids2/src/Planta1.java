/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boids2.src;

import java.util.Stack;

/**
 *
 * @author angie
 */
public class Planta1 {

    Stack<Turtle> pila = new Stack();
    
    public Turtle siguientePaso(String axioma, Turtle turtle, int distancia, int delta) {

        if ("F".equals(axioma)) {
            turtle.xPosition += distancia * Math.cos(Math.toRadians(turtle.angle));
            turtle.yPosition += distancia * Math.sin(Math.toRadians(turtle.angle));
            
//            System.out.println(" x= " + turtle.xPosition + "  y= " + turtle.yPosition + " a= " + turtle.angle + " cos("+turtle.angle+") "+(int)Math.cos(Math.toRadians(turtle.angle))+ " sin("+turtle.angle+") "+ (int)Math.sin(Math.toRadians(turtle.angle)));
        }
//        if("f".equals(axioma)) paso="blar";
        if ("+".equals(axioma)) {
            turtle.angle += delta;
        }
        if ("-".equals(axioma)) {
            turtle.angle -= delta;
        }
        if("[".equals(axioma)){
            Turtle tAux = new Turtle(turtle.xPosition,turtle.yPosition,turtle.angle);
            pila.push(tAux);
        }
        if("]".equals(axioma)){
            turtle = pila.pop();
        }
        return turtle;
    }

    int matriz[][];
    String aux = "";
    int delta;
    int distacia;
    int n;
    int m;

    Turtle turtle = new Turtle((int) n / 2, (int) m / 2, 180);
    

    public void moverTurtle(String axioma) {
        matriz[turtle.xPosition][turtle.yPosition]=-1;
        System.out.println(" x= " + turtle.xPosition + "  y= " + turtle.yPosition + " a= " + turtle.angle);
        for (int i = 0; i < axioma.length(); i++) {
            aux = axioma.substring(i, i + 1);
            turtle = siguientePaso(aux, turtle, distacia, delta);

            matriz[(turtle.xPosition + n) % n][(turtle.yPosition + m) % m] = i + 1;
//            System.out.print(" i= "+i+" aux= "+aux);
//            System.out.println(" x= "+turtle.xPosition+"  y= "+turtle.yPosition+ " a= "+turtle.angle);
//            pintarMatriz();
        }
        
    }

    public void pintarMatriz() {

        for (int j = 0; j < n; j++) {
            for (int k = 0; k < m; k++) {
//                System.out.print(matriz[j][k] == 0 ? " \t" : matriz[j][k] + "\t");
                System.out.print(matriz[j][k] == 0 ? " " : "*");
            }
            System.out.println();
        }
    }
    public void llenarMatriz() {
        matriz = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String axioma = "F";
        int iteraciones = 1;
        DOLSystem sys = new DOLSystem();
        String res = axioma;
        
        for (int j=0;j<iteraciones;j++){
            res = sys.GenerarRama(res);
        }
        System.out.println(res);
        String axiomaTurtle = res;
        
        Planta1 ti = new Planta1();
        ti.n = 30;
        ti.m = 30;
        ti.llenarMatriz();
        ti.pintarMatriz();
        ti.distacia = 1;
        ti.delta = 26;
        ti.moverTurtle(axiomaTurtle);

    }
}
