/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolucion;

/**
 *
 * @author angie
 */
public class HamletGenerar implements Generar<String>{

    @Override
    public String generar() {
        StringBuilder sb = new StringBuilder();
        for( int i=0; i<23; i++ ){
          sb.append((char)('A'+(int)(26*Math.random())));
        }
        return sb.toString();
    }
    
}
