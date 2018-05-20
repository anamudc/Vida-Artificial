package boids2.src;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Bird extends AnimationObject {

    File imageFile;

    public Bird(int x, int y, int radius) {
        tiempoVida = (int) (Math.random() * 800);

        this.color = Color.BLUE;
        this.radius = radius;
        velocity = init();
        position = init(x, y);
        obstacle = false;
        try {
            name = "nuevoRaton.png";
            imagen = new ImageIcon("" + name);
            File imageFile = new File("" + name);
            imagen.setImage(ImageIO.read(getClass().getResourceAsStream(imageFile.toString())));
        } catch (IOException ex) {
            Logger.getLogger(Food.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        this.color = Color.blue;
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
