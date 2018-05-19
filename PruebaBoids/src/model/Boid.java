package model;

import utility.Vector2d;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Each boid represents an individual in the flock. Displayed on screen as a
 * small coloured circle.
 *
 * @author Shaun
 *
 */
public class Boid {

    /**
     * Vector objects store the x/y coordinates of the boids grid position.
     */
    protected Vector2d position;
    protected Vector2d velocity;
    protected final int speedLimit = 2;
    protected String name;
    protected ImageView display;

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

        this.display = new ImageView();
        this.display.setImage(new Image("images/boid.png"));
        this.display.setX(x);
        this.display.setY(y);
        this.display.setSmooth(true);
        this.display.setCache(true);

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
        this.display.setX(pos.xPos);
        this.display.setY(pos.yPos);
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
        this.display.setRotate(angle);

    }

    public String getName() {
        return this.name;
    }

    /**
     * Return the ImageView representing the boid
     *
     * @return visual representation of boid data.
     */
    public ImageView getDisplay() {
        return this.display;
    }

}
