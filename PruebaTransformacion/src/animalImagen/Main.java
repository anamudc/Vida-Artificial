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
                        //raton,piel,base,vertices,amplitud,iteraciones,color,esRaton,identificador
//        p.cambioPiel("raton.png","turingmorph3.png","transparente.png",4,10,3000,1,true,1);
//        
//        p.cambioPiel("raton.png","turingmorph2.png","transparente.png",2,5,3000, 4,true,2);
//        
//        p.cambioPiel("gato.png","turingmorph2.png","transparente.png",5,120,3000, 5,false,0);
//        
//        p.cambioPiel("gato.png","turingmorph2.png","transparente.png",5,90,3000, 4,false,1);
//        
//        p.cambioPiel("gato.png","turingmorph2.png","transparente.png",5,70,3000, 4,false,2);
//        
//        p.cambioPiel("gato.png","turingmorph2.png","transparente.png",5,30,3000, 4,false,3);
//        
//        p.cambioPiel("gato.png","turingmorph2.png","transparente.png",5,20,3000, 4,false,4);
        
        
        for (int i = 0; i < 10; i++) {
            p.cambioPiel("gato.png","turingmorph3.png","transparente.png",(int)(3+Math.random()*6),(int)(20+Math.random()*100),(int)(2000+Math.random()*3000),(int)(1+Math.random()*6),false,i);
        }
        
//        p.cambioPiel("transparente.png","turingmorph3.png");
//        p.cambioPiel("transparente.png","cuadricula2.png");
        
    }
    
}
