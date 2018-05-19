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
public interface Generar<T> {
    public T generar();
    default T[] aplicar( int N ){
        Object[] obj = new Object[N];
        for( int i=0; i<N; i++ ) obj[i] = generar();
        return (T[])obj;
    }
}
