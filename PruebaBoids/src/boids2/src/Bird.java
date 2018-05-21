package boids2.src;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Bird extends AnimationObject {

    String ruta = "imgs/";
    File imageFile;

    int amplitud;
    int verticesLimit;
    int iterTuringMorph;
    int id;

    public Bird(int x, int y, int radius, int id) {
        this.id = id;
        name = ruta + "true" + id+".png";
        //codigo
        tiempoVida = (int) (Math.random() * 800);
        this.color = (int) (1 + Math.random() * 5);
        amplitud = (int) (5 + Math.random() * 10);
        verticesLimit = (int) (2 + Math.random() * 4);
        iterTuringMorph = (int) (2000 + Math.random() * 3000);

        Transform transformacion = new Transform();
        imagen = transformacion.cambioPiel("raton.png", "turingmorph3.png", "transparente.png", verticesLimit, amplitud, iterTuringMorph, this.color, true, this.id);
        this.radius = radius;
        velocity = init();
        position = init(x, y);
        obstacle = false;
    }

    public void calculatePosition(int width, int height, List<Bird> neighbours,
            double sepParam, double alParam, double cohParam, int maxVelocity, int maxCloseness,
            int kNeighboursAl, int kNeighboursCoh, List<Obstacle> obstacles,
            List<Predator> predators, List<Food> listaComida, int kComida) {

        this.width = width;
        this.height = height;
        Vector<Double> s = separate(neighbours, maxCloseness);
        Vector<Double> a = align(neighbours, kNeighboursAl);
        Vector<Double> c = cohesion(neighbours, kNeighboursCoh);
        Vector<Double> p = avoidPredators(predators);
        Vector<Double> f = preferirComida(listaComida, kComida);

        s = multiply(s, sepParam);
        a = multiply(a, alParam);
        c = multiply(c, cohParam);
        p = multiply(p, 1);
        f = multiply(f, 7.0);

        this.velocity = add(this.velocity, s, a, c);
        this.velocity = add(this.velocity, p);

        this.velocity = add(this.velocity, f);

        Vector<Double> o = avoidObstacles(obstacles);
        if (obstacle) {
            o = multiply(o, 5);
            velocity = add(this.velocity, o);
        }
        limitSpeed(maxVelocity);
        this.position = add(this.position, this.velocity);
        limitBorders(width, height);
        obstacle = false;

    }

    private Vector<Double> avoidPredators(List<Predator> predators) {
        Vector<Double> v = init();
        int i = 0;
        for (Predator p : predators) {
            double dist = distance(p.position, position);
            if (dist < 150) {
                v = add(v, sub(position, p.position));
                i++;
            }
        }
        if (i > 0) {
            v = divide(v, (double) i);
        }
        return v;
    }

    private Vector<Double> preferirComida(List<Food> neighbours, int kNeighboursCoh) {

        Vector<Double> a = init();
        int i = 0;
        synchronized (neighbours) {
            Iterator<Food> iterator = neighbours.iterator();
            while (iterator.hasNext()) {
                Food b = iterator.next();
                if (!b.equals(this)) {
                    double d = distance(b.position, this.position);
                    if (d < kNeighboursCoh && d > 0) {
                        a = add(a, b.position);
                        i++;
                    }
                }
            }
        }
        if (i > 0) {
            a = divide(a, i);
            a = divide(sub(a, this.position), 100.0);
        }
        return a;
    }

}
