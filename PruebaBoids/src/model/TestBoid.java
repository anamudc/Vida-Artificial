package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Each boids represents an individual in the flock. Test boids provide
 * additional functionality such as:
 * <ul>
 * <li>Console logging of events</li>
 * <li>Boids have optional names</li>
 * <li>Set boid colour</li>
 * </ul>
 *
 *
 */
public class TestBoid extends Boid {

    public TestBoid(int x, int y, String n, Image c) {
        super(x, y, n);
        this.display.setImage(c);
    }

    public ImageView getDisplay() {
        return this.display;
    }

}
