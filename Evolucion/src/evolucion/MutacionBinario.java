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
public class MutacionBinario implements Mutacion<boolean[]>{

    @Override
    public boolean[] aplicar(boolean[] x) {
        double p = 1.0/x.length;
        boolean[] hijo = x.clone();
        for( int i=0; i<hijo.length; i++ ) if( Math.random() < p ) hijo[i] = !hijo[i];
        return hijo;
    }
    
}
