/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingMorphA;

import static turingMorphA.TuringSystemSolver.UPDATE_RATE_TIME;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Estudiante
 */
public class Solver {
    
    private int width;
    private int height;
    private int iterations;
    private int color;
    private String imageName;
    private double CA, CB;
    private double[][] Ao, Bo, An, Bn;
    private Random rand = new Random();
    protected static final int UPDATE_RATE_FRAMES = 20;
    protected static final long UPDATE_RATE_TIME = 200000000L;
    
    public Solver(int width, int height, int iterations, int color, String imageName){
        this.width = width;
        this.height = height;
        this.iterations = iterations;
        this.color = color;
        this.imageName = imageName;
        
        Ao = new double[width][height];
        An = new double[width][height];
        Bo = new double[width][height];
        Bn = new double[width][height];
        randomize();
        solve(iterations, width, height);
    }
    
    public void solve( int iterations, double CA, double CB ) {
        this.iterations = iterations;
        this.CA = 2;
        this.CB = 10;
        solveImpl();
    }
    
    public void solveImpl() {
        int n, i, j, iplus1, iminus1, jplus1, jminus1;
        double DiA, ReA, DiB, ReB;

        // uses Euler's method to solve the diff eqns
        for( n=0; n<iterations; ++n ) {
            for( i=0; i<height; ++i) {
                // treat the surface as a torus by wrapping at the edges
                iplus1 = i+1;
                iminus1 = i-1;
                if( i == 0 ) iminus1 = height - 1;
                if( i == height - 1 ) iplus1 = 0;

                for( j=0; j<width; ++j ) {
                    jplus1 = j+1;
                    jminus1 = j-1;
                    if( j == 0 ) jminus1 = width - 1;
                    if( j == width - 1 ) jplus1 = 0;

                    // Component A
                    DiA = CA * ( Ao[iplus1][j] - 2.0 * Ao[i][j] + Ao[iminus1][j]
                               + Ao[i][jplus1] - 2.0 * Ao[i][j] + Ao[i][jminus1] );
                    ReA = Ao[i][j] * Bo[i][j] - Ao[i][j] - 12.0;
                    An[i][j] = Ao[i][j] + 0.01 * (ReA + DiA);
                    if( An[i][j] < 0.0 ) An[i][j] = 0.0;

                    // Component B
                    DiB = CB * ( Bo[iplus1][j] - 2.0 * Bo[i][j] + Bo[iminus1][j]
                               + Bo[i][jplus1] - 2.0 * Bo[i][j] + Bo[i][jminus1] );
                    ReB = 16.0 - Ao[i][j] * Bo[i][j];
                    Bn[i][j] = Bo[i][j] + 0.01 * (ReB + DiB);
                    if( Bn[i][j] < 0.0 ) Bn[i][j]=0.0;
                }
            }
            swapBuffers();
        }
        /*for(int i1 = 0; i1 < width; i1++){
            for(int j1 = 0; j1 < height; j1++){
                if( An[i1][j1] > 0)
                    System.out.println( An[i1][j1] );
            }
        }*/
        System.out.println( n );
        
        BufferedImage img = new BufferedImage(width, height, 3);
        
        double max = 0;
        
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(An[x][y] > max)
                    max = An[x][y];
            }
        }
        //int colorRGB = new Color( 60 + (int)(Math.random()*195+1), 60 + (int)(Math.random()*195+1), 60 + (int)(Math.random()*195+1)).getRGB();
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                int rgb = 0;
                if(color == 1)
                    rgb = new Color((int)(An[x][y]/max*255),80 + (int)(An[x][y]/max*140),80).getRGB();
                if(color == 2)
                    rgb = new Color(80 + (int)(An[x][y]/max*100),(int)(An[x][y]/max*255),80 + (int)(An[x][y]/max*140)).getRGB();
                if(color == 3)
                    rgb = new Color((int)(An[x][y]/max*255),80 + (int)(An[x][y]/max*140),(int)(An[x][y]/max*255)).getRGB();
                if(color == 4)
                    rgb = new Color(127 + (int)(An[x][y]/max*100),(int)(An[x][y]/max*255),80 + (int)(An[x][y]/max*120)).getRGB();
                if(color == 5)
                    rgb = new Color(80 + (int)(An[x][y]/max*140),(int)(An[x][y]/max*255),(int)(An[x][y]/max*255)).getRGB();
                if(color == 6)
                    rgb = new Color((int)(An[x][y]/max*255),68,(int)(An[x][y]/max*255)).getRGB();
                img.setRGB(x, y, rgb);
            }
        }
        try{
            ImageIO.write( img, "png", new File("imgs/"+imageName) );
            System.out.println("done");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void randomize() {
        for( int i=0; i<height; ++i ) {
            for( int j=0; j<width; ++j ) {
                Ao[i][j] = rand.nextDouble() * 12.0 + rand.nextGaussian() * 2.0;
                Bo[i][j] = rand.nextDouble() * 12.0 + rand.nextGaussian() * 2.0;
            }
        }
    }
    
    private void swapBuffers() {
        double[][] temp = Ao;
        Ao = An;
        An = temp;
        temp = Bo;
        Bo = Bn;
        Bn = temp;
    }
            
    
}
