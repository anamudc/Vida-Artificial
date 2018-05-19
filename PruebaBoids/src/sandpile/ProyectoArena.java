/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandpile;

/**
 *
 * @author angie
 */
public class ProyectoArena {

    int n;
    int m;
    int c;
    int matriz[][];
    int matrizAux[][];
    int vecinos[][]; //configuración de la vecindad
    int xInit = (int) n / 2;
    int yInit = (int) m / 2;
    private int alturaMax = 4;

    public int getAlturaMax() {
        return alturaMax;
    }

    public void setAlturaMax(int alturaMax) {
        this.alturaMax = alturaMax;
    }

    public ProyectoArena(int n, int m) {
        this.n = n;
        this.m = m;
        matriz = new int[n][m];
        this.matriz[(int) n / 2][(int) m / 2 + 1] = 3;
    }

    public int consultarMatriz(int x, int y){
        return this.matriz[x][y];
    }
    
    
    public void llenarMatriz(int n, int m) {
        matriz = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
//                matriz[i][j] = ((int) (Math.random() * 3));
//                matriz[i][j]=0;
            }
        }
    }

    public void vecinosMoore() {
        int vecinosVon[][] = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        alturaMax = 8;
        vecinos = vecinosVon;
    }

    public void vecinosVonNeuman() {
        int vecinosMoore[][] = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
        alturaMax = 4;
        vecinos = vecinosMoore;
    }

//    public boolean compararMatrices(){
//        
//    }
    
    public void siguienteEstadoArena(int valorCritico, int i, int j, int[][] matriz, int[][] vecinos) {

        matrizAux=matriz;
        matriz[i][j]++;
        if (matriz[i][j] == alturaMax) {
            System.out.println("\nHay un alturaMax en i:"+i+" j:"+j);
            matriz[i][j] = 0;
            int nv = vecinos.length;
            int mv = vecinos[0].length;

            for (int x = 0; x < nv; x++) {
                for (int y = 0; y < mv - 1; y++) {
                    siguienteEstadoArena(valorCritico, (i+vecinos[x][y]+n)%n, (j+vecinos[x][y+1]+m)%m, matriz, vecinos);
                    System.out.println("for i:"+i+" j:"+j+" x:"+((i+vecinos[x][y]+n)%n)+" y:"+((j+vecinos[x][y+1]+m)%m));
                }
            }
            
        }
        c++;
        System.out.println("c= "+c);
    }

    public int[][] cambioEstadosArena(int x, int y) {

//        xInit = (int) (Math.random() * n);
//        yInit = (int) (Math.random() * m);
        xInit = x;
        yInit = y;
        
        siguienteEstadoArena(alturaMax, xInit, yInit, matriz, vecinos);
        return matriz;
    }

    public void pintarMatriz(int matriz[][]) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matriz[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                s += matriz[i][j] + "\t";
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
        int m = 10;
        int iteraciones = 60;

        ProyectoArena pr = new ProyectoArena(n, m);

        pr.llenarMatriz(n, m);
        pr.matriz[(int)n/2][(int)m/2+1]=3;

        pr.vecinosVonNeuman();
        System.out.println("______________Matriz_Original_______________________________________________________________________");
        pr.pintarMatriz(pr.matriz);
        System.out.println("____________________________________________________________________________________________________");
        //pr.siguienteEstadoVida( 1 , 1 , pr.matriz, pr.vecinos);
        while (iteraciones > 0) {
            pr.cambioEstadosArena((int)n/2,(int)m/2);
            iteraciones--;
            pr.c=0;
            System.out.println("iteracion: "+iteraciones);
        }

        System.out.println(pr.c);
    }

}
