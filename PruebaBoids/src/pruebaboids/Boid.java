/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaboids;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import utility.Vector2d;

/**
 *
 * @author angie
 */
// The Boid class
class Boid {
    
    /**
     * Vector objects store the x/y coordinates of the boids grid position.
     */
    protected Vector2d position;
    protected Vector2d velocity;
    protected final int speedLimit = 2;
    protected String name;
    protected ImageIcon imagenBoid;

    /**
     * Initialises the bird at the given grid coordinates.
     *
     * An <Code>imageView</Code> is created to provide a visual representation
     * of the boid.
     *
     * @param x X Position
     * @param y	Y Position
     */
    public Boid(int x, int y, String n) {

        imagenBoid = new ImageIcon(name);
        File imageFile = new File("/images/" + name);
        this.position = new Vector2d(x, y);
        this.velocity = new Vector2d(0, 0);

        if (n != null) {
            this.name = n;
        }
    }

    /**
     * Returns a Vector2d object with the boids x, y position coordinates
     *
     * @return	Vector2d
     *
     */
    public Vector2d getPosition() {
        return new Vector2d(position.xPos, position.yPos);
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    /**
     * Set the boid velocity
     *
     * @param velocity a Vector2d object with the boids x, y coordinates
     *
     */
    public void setVelocity(Vector2d velocity) {
        if (velocity.xPos > speedLimit) {
            velocity.xPos = speedLimit;
        }
        if (velocity.yPos > speedLimit) {
            velocity.yPos = speedLimit;
        }

        this.velocity = velocity;
    }

    /**
     * Set the position of the boid.
     *
     * @param position	A Vector2d object with a grid position
     */
    public void setPosition(Vector2d pos) {
        setAngle(this.position, pos);
        this.position = pos;
    }

    /**
     * Returns an 360 degree angle representing the boids direction of travel.
     *
     * The cartesian coordinates accepted as arguments are converted to polar
     * coordinates and converted to an angle. Based on the boids current and
     * next position.
     *
     * @param current position of the boid
     * @param next position the boid will be moving too
     */
    public void setAngle(Vector2d current, Vector2d next) {
        // calc the deltas as next minus current
        double delta_x = next.xPos - current.xPos;
        double delta_y = next.yPos - current.yPos;
        Double theta = Math.atan2(delta_y, delta_x);

        // Change this.angle is into degrees
        double angle = theta * 180 / Math.PI;

    }

    public String getName() {
        return this.name;
    }

    public ImageIcon getImagenBoid() {
        return imagenBoid;
    }

    public void setImagenBoid(ImageIcon imagenBoid) {
        this.imagenBoid = imagenBoid;
    }

}