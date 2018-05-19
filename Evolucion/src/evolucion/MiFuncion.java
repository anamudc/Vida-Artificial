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
public class MiFuncion implements Adaptabilidad<boolean[]>{

    @Override
    public double obtener(boolean[] x) {
        int k=0;
        for( int i=0; i<x.length; i++) k += x[i]?1:0;
        return k;
    }
}
