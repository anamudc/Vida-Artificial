/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto;

/**
 *
 * @author angie
 */
public class Proyecto {

    int n;
    int m;
    int k; //numero de estados
    int c;
    int matriz[][];    
    int matrizB[][];    
    int vecinos[][]; //configuración de la vecindad

    public Proyecto(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
    }
    
    public void llenarMatriz(int n, int m){
        matriz = new int [n][m] ;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                matriz [i][j]= ((int) (Math.random()*(k+5)))==0?1:0;
            }
        }
    }
    
    public void vecinosMoore(){
        int vecinosVon [][] = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};        
        vecinos = vecinosVon;
    }
    public void vecinosVonNeuman(){
        int vecinosMoore[][] = {{-1,0},{0,-1},{0,1},{1,0}};        
        vecinos = vecinosMoore;
    }
    
    public void siguienteEstadoVida(int i, int j, int[][] matriz, int[][]vecinos){
        int contador = 0;        
        int nv = vecinos.length;
        int mv = vecinos[0].length;
        
        for(int x=0;x<nv;x++){
            for(int y=0;y<mv-1;y++){                
                contador += matriz[(i+vecinos[x][y]+n)%n][(j+vecinos[x][y+1]+m)%m];                                
            }
        } 
        c= contador;
    }
    
    public void cambioEstadosVida(){
        matrizB= new int[n][m];
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                siguienteEstadoVida(i, j, matriz, vecinos);
                if(c<2) matrizB[i][j]=0;
                if(c==2) matrizB[i][j] = matriz[i][j];
                if(c==3) matrizB[i][j]=1;
                if(c>3) matrizB[i][j]=0;
            }
        }
        matriz = matrizB;
        pintarMatriz(matriz);
        System.out.println("____________________________________________________________________________________________________");
        
    }
    
    public void pintarMatriz(int matriz[][]){
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print(matriz [i][j]==1?"*":" ");
            }
            System.out.println();
        }
    }
    
    
    @Override
    public String toString(){
        String s = "";
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                s += matriz [i][j] + "\t";
            }
            s += "\n";
        }
        return s;
    }
    
    //matriz de tamaño m x n - toroidal
    //funcion retorne pares ordenados que son sus vecinos
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int n = 10;
        int m = 90;
        int k = 2;
        int iteraciones = 13;
        
        Proyecto pr = new Proyecto(n,m,k);

        pr.llenarMatriz(n, m);        
      
        pr.vecinosMoore();
        System.out.println("______________Matriz_Original_______________________________________________________________________");
        pr.pintarMatriz(pr.matriz);
        System.out.println("____________________________________________________________________________________________________");
        //pr.siguienteEstadoVida( 1 , 1 , pr.matriz, pr.vecinos);
        while(iteraciones>0){
            pr.cambioEstadosVida();
            
            iteraciones--;
        }
        
        System.out.println(pr.c);
    }
    
}
