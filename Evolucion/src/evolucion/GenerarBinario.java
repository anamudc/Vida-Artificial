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
public class GenerarBinario implements Generar<boolean[]>{
    protected int DIM ;
    public GenerarBinario( int DIM ){ this.DIM = DIM; }
    @Override
    public boolean[] generar() {
        boolean[] x = new boolean[DIM];
        for( int i=0; i<DIM; i++ ) x[i] = Math.random() < 0.5;
        return x;
    }    
}
