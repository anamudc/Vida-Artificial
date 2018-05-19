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
public interface Adaptabilidad<T> {
    public double obtener( T x );
    default double[] obtener( T[] p ){
        int N = p.length;
        double[] obj = new double[N];
        for( int i=0; i<N; i++ ) obj[i] = obtener(p[i]);
        return obj;    
    }
}
