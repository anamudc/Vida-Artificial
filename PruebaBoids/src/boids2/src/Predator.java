package boids2.src;

import java.io.File;
import java.util.List;
import java.util.Vector;

public class Predator extends AnimationObject {

    String ruta = "imgs/";
    File imageFile;
    
    int amplitud;
    int verticesLimit;
    int iterTuringMorph;
    int id;

    public Predator(double x, double y, int maxVelocity, int id) {
        this.id = id;
        //codigo
        tiempoVida = (int) (Math.random() * 800);        
        this.color = (int)(1+Math.random()*5);
        amplitud = (int)(5+Math.random()*10);
        verticesLimit = (int)(2+Math.random()*4);
        iterTuringMorph = (int)(2000+Math.random()*3000);
        
        Transform transformacion = new Transform(); 
        imagen = transformacion.cambioPiel("gato.png","turingmorph3.png","transparente.png",verticesLimit,amplitud,iterTuringMorph,this.color,false,this.id);
            
        name = ruta+"true"+id;
        radius = 15;
        position = init(x, y);
        velocity = randomVelocity(maxVelocity);
        
    }

    @Override
    public void calculatePosition(int width, int height, List<Bird> neighbours, double sepParam, double alParam,
            double cohParam, int maxVelocity, int maxCloseness, int kNeighboursAl, int kNeighboursCoh,
            List<Obstacle> obstacles, List<Predator> predators) {
        // TODO Auto-generated method stub

        Vector<Double> s = separatePredators(predators, maxCloseness + 50);
        Vector<Double> a = align(neighbours, kNeighboursAl + 150); //bigger range for chasing birds
        Vector<Double> c = cohesion(neighbours, kNeighboursCoh + 150); //the same as above

        s = multiply(s, 1.0);
        a = multiply(a, 3.0);
        c = multiply(c, 7.0);

        this.velocity = add(this.velocity, s, a, c);
        Vector<Double> o = avoidObstacles(obstacles);
        if (obstacle) {
            o = multiply(o, 0.5);
            velocity = add(this.velocity, o);
        }
        limitSpeed(maxVelocity);
        this.position = add(this.position, this.velocity);
        limitBorders(width, height);
        obstacle = false;

    }
}
