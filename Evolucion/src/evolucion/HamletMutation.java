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
public class HamletMutation implements Mutacion<String>{

    @Override
    public String aplicar(String x) {
        double p = 1.0 / x.length();
        StringBuilder s = new StringBuilder();
        for( int i=0; i<x.length(); i++ ){
            if( Math.random() < p ){
                s.append((char)('A'+(int)(26*Math.random())));
            }else{
                s.append(x.charAt(i));
            }
        }
        return s.toString();
    }
    
}
