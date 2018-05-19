/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingMorphA;

//import processing.core.PApplet;

/**
 *
 * @author Estudiante
 */
public class EjerciciosVisual {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int width = 52;
        int height = 50;
        int iterations = 3000;
        
        Solver solver1 = new Solver(width,height, iterations,5,"turingmorph2.png");
        Solver solver2 = new Solver(width,height, iterations,1,"turingmorph3.png");
//        Solver solver3 = new Solver(width,height, iterations,5,"turingmorph4.png");
//        Solver solver4 = new Solver(width,height, iterations,5,"turingmorph5.png");
    }
    
}

