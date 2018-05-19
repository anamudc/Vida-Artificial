/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalImagen;

/**
 *
 * @author angie
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Transform p = new Transform();
                        //raton,piel,base,vertices,amplitud,iteraciones
        p.cambioPiel("raton.png","turingmorph3.png","transparente.png",4,10,3000);
        
        p.cambioPiel("raton.png","turingmorph2.png","transparente.png",5,5,3000);
        
//        p.cambioPiel("transparente.png","turingmorph3.png");
//        p.cambioPiel("transparente.png","cuadricula2.png");
        
    }
    
}
