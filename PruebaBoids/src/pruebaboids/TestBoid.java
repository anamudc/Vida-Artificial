package pruebaboids;

import model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.ImageIcon;

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

    public TestBoid(int x, int y, String n, ImageIcon c) {
        super(x, y, n);
        this.setImagenBoid(c);
    }

}
