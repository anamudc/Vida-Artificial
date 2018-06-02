/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lSystem;

import java.util.Stack;

/**
 *
 * @author angie
 */
public class Planta1 {

    Stack<Turtle> pila = new Stack();
    
    public Turtle siguientePaso(String axioma, Turtle turtle, int distancia, int delta) {

        if ("F".equals(axioma)) {
            turtle.setxPosition((int) (turtle.getxPosition() + distancia * Math.cos(Math.toRadians(turtle.getAngle()))));
            turtle.setyPosition((int) (turtle.getyPosition() + distancia * Math.sin(Math.toRadians(turtle.getAngle()))));
            
//            System.out.println(" x= " + turtle.xPosition + "  y= " + turtle.yPosition + " a= " + turtle.angle + " cos("+turtle.angle+") "+(int)Math.cos(Math.toRadians(turtle.angle))+ " sin("+turtle.angle+") "+ (int)Math.sin(Math.toRadians(turtle.angle)));
        }
//        if("f".equals(axioma)) paso="blar";
        if ("+".equals(axioma)) {
            turtle.setAngle(turtle.getAngle() + delta);
        }
        if ("-".equals(axioma)) {
            turtle.setAngle (turtle.getAngle() - delta);
        }
        if("[".equals(axioma)){
            Turtle tAux = new Turtle(turtle.getxPosition(),turtle.getyPosition(),turtle.getAngle());
            pila.push(tAux);
        }
        if("]".equals(axioma)){
            turtle = pila.pop();
        }
        return turtle;
    }

    private int matriz[][];
    private String aux = "";
    private int delta;
    private int distacia;
    private int n;
    private int m;

    private Turtle turtle = new Turtle((int) n / 2, (int) m / 2, 180);

    public Stack<Turtle> getPila() {
        return pila;
    }

    public void setPila(Stack<Turtle> pila) {
        this.pila = pila;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public int getDistacia() {
        return distacia;
    }

    public void setDistacia(int distacia) {
        this.distacia = distacia;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public Turtle getTurtle() {
        return turtle;
    }

    public void setTurtle(Turtle turtle) {
        this.turtle = turtle;
    }
    

    public void moverTurtle(String axioma) {
        matriz[turtle.getxPosition()][turtle.getyPosition()]=-1;
        for (int i = 0; i < axioma.length(); i++) {
            aux = axioma.substring(i, i + 1);
            turtle = siguientePaso(aux, turtle, distacia, delta);
            matriz[(turtle.getxPosition() + n) % n][(turtle.getyPosition() + m) % m] = i + 1;
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
/*    public static void main(String[] args) {
        // TODO code application logic here
        
        String axioma = "F";
        int iteraciones = 1;
        DOLSystem sys = new DOLSystem();
        String res = axioma;
        
        for (int j=0;j<iteraciones;j++){
            res = sys.GenerarRama(res);
        }
//        System.out.println(res);
        String axiomaTurtle = res;
        
        Planta1 ti = new Planta1();
        ti.n = 30;
        ti.m = 30;
        ti.llenarMatriz();
        ti.pintarMatriz();
        ti.distacia = 1;
        ti.delta = 26;
        ti.moverTurtle(axiomaTurtle);

    }*/
}
