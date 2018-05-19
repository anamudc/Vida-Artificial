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
public class GA<T> {
    protected Seleccion<T> seleccion;
    protected Mutacion<T> mutacion;
    protected Cruce<T> cruce;
    protected Generar<T> generar;
    
    public GA( Seleccion<T> seleccion, Mutacion<T> mutacion, Cruce<T> cruce,
               Generar<T> generar){
        this.seleccion = seleccion;
        this.mutacion = mutacion;
        this.cruce = cruce;
        this.generar = generar;
    }
    
    public int mejor( double[] f ){
        int k=0;
        for( int i=1; i<f.length; i++ ) if(f[k] < f[i]) k=i; 
        return k;
    }
    
    public T aplicar( int MAXITER, Adaptabilidad<T> funcion ){
        int N = 100;
        T[] p = generar.aplicar(N);
        double[] f = funcion.obtener(p);
        int m = mejor(f);
        T elMejor = p[m];
        System.out.println("0 "+f[m]);
        for( int i=1; i<=MAXITER; i++ ){
            T[] padres = seleccion.aplicar(N, p, f);
            for( int k=0; k<N; k+=2 ){
                T[] h = cruce.aplicar(padres[k], padres[k+1]);
                h[0] = mutacion.aplicar(h[0]);
                h[1] = mutacion.aplicar(h[1]);
                p[k] = h[0];
                p[k+1] = h[1];
            }
            f = funcion.obtener(p);
            int mp = mejor(f);
            if( funcion.obtener(elMejor) < f[mp] ){
                m = mp;
                elMejor = p[m];
            }
            System.out.println(i+" "+f[m] + " " + elMejor);
        }
        return elMejor;
    }
}

