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
public interface Seleccion<T> {
    public int[] aplicar( int n, double[] f );
    default T[] aplicar( int n, T[] x, double [] f ){
        int[] indices = aplicar( n, f );
        Object[] sel = new Object[indices.length];
        for( int i=0; i<indices.length; i++ ) sel[i] = x[indices[i]];
        return (T[])sel;
    }
}
