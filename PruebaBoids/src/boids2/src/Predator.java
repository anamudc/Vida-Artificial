package boids2.src;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Predator extends AnimationObject {

    File imageFile;

    public Predator(double x, double y, int maxVelocity) {
        tiempoVida = (int)(Math.random()*1000);
        
        color = Color.red;
        radius = 15;
        position = init(x, y);
        velocity = randomVelocity(maxVelocity);
        try {
            name="nuevoGato.png";
            imagen = new ImageIcon("" + name);
            File imageFile = new File("" + name);
            imagen.setImage(ImageIO.read(getClass().getResourceAsStream(imageFile.toString())));
        } catch (IOException ex) {
            Logger.getLogger(Food.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void calculatePosition(int width, int height, ArrayList<Bird> neighbours, double sepParam, double alParam,
            double cohParam, int maxVelocity, int maxCloseness, int kNeighboursAl, int kNeighboursCoh,
            ArrayList<Obstacle> obstacles, ArrayList<Predator> predators) {
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
