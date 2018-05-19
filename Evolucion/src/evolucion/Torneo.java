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
public class Torneo<T> implements Seleccion<T> {

    public int uno(int N ){ return (int)(N*Math.random()); }
    @Override
    public int[] aplicar(int n, double[] f) {
        int[] indices = new int[n];
        for( int i=0; i<n; i++ ){
             int m = uno(f.length);
             for( int k=1; k<4; k++ ){
                 int m2 = uno(f.length);
                 if( f[m2] > f[m] ) m = m2;
             }
             indices[i] = m;
        }
        return indices;
    }    
}
