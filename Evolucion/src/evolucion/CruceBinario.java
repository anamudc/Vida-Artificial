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
public class CruceBinario implements Cruce<boolean[]>{

    @Override
    public boolean[][] aplicar(boolean[] uno, boolean[] dos) {
        int pos = (int)((uno.length-1)*Math.random()) + 1;
        boolean[][] hijos = new boolean[][]{uno.clone(), dos.clone()};
        for( int i=pos; i<uno.length; i++){
            hijos[0][i] = dos[i];
            hijos[1][i] = uno[i];
        }
        return hijos;
    }
    
}
