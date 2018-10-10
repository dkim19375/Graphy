/*
 * File:    Sphere.java
 * Package: objects.sphere
 * Author:  Zachary Gill
 */

package objects.sphere;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Environment;
import math.vector.Vector;
import objects.base.AbstractObject;
import objects.base.Object;
import objects.base.polygon.Triangle;

/**
 * Defines a Sphere.
 */
public class Sphere extends Object {
    
    //Fields
    
    /**
     * The radius of the Sphere.
     */
    private double radius;
    
    /**
     * The step with which to calculate the Sphere.
     */
    private double step;
    
    /**
     * The secondary color of the Sphere.
     */
    private Color color2;
    
    
    //Constructors
    
    /**
     * The constructor for a Sphere.
     *
     * @param parent The parent of the Sphere.
     * @param center The center point of the Sphere.
     * @param color1 The color of the Sphere.
     * @param color2 The secondary color of the Sphere.
     * @param radius The radius of the bounding sphere of the Sphere.
     * @param step   The step with which to calculate the Sphere.
     */
    public Sphere(AbstractObject parent, Vector center, Color color1, Color color2, double radius, double step) {
        super(parent, center, color1);
        this.color2 = color2;
        this.radius = radius;
        this.step = step;
        
        calculate();
    }
    
    /**
     * The constructor for a Sphere.
     *
     * @param parent The parent of the Sphere.
     * @param center The center point of the Sphere.
     * @param color  The color of the Sphere.
     * @param radius The radius of the bounding sphere of the Sphere.
     * @param step   The step with which to calculate the Sphere.
     */
    public Sphere(AbstractObject parent, Vector center, Color color, double radius, double step) {
        this(parent, center, color, color, radius, step);
    }
    
    /**
     * The constructor for a Sphere.
     *
     * @param parent The parent of the Sphere.
     * @param center The center point of the Sphere.
     * @param radius The radius of the bounding sphere of the Sphere.
     * @param step   The step with which to calculate the Sphere.
     */
    public Sphere(AbstractObject parent, Vector center, double radius, double step) {
        this(parent, center, Color.BLACK, Color.BLACK, radius, step);
    }
    
    /**
     * The constructor for a Sphere.
     *
     * @param center The center point of the Sphere.
     * @param color1 The color of the Sphere.
     * @param color2 The secondary color of the Sphere.
     * @param radius The radius of the bounding sphere of the Sphere.
     * @param step   The step with which to calculate the Sphere.
     */
    public Sphere(Vector center, Color color1, Color color2, double radius, double step) {
        this(null, center, color1, color2, radius, step);
    }
    
    /**
     * The constructor for a Sphere.
     *
     * @param center The center point of the Sphere.
     * @param color  The color of the Sphere.
     * @param radius The radius of the bounding sphere of the Sphere.
     * @param step   The step with which to calculate the Sphere.
     */
    public Sphere(Vector center, Color color, double radius, double step) {
        this(null, center, color, color, radius, step);
    }
    
    
    //Methods
    
    /**
     * Calculates the structure of the Sphere.
     */
    @Override
    protected void calculate() {
        components.clear();
    
        int layer = 0;
        List<List<Vector>> vertices = new ArrayList<>();
        boolean offset = false;
        for (double phi = 0; Math.PI - phi > -Environment.omega; phi += step / 2) {
            vertices.add(new ArrayList<>());
            for (double theta = 0; Math.PI * 2 - theta > -Environment.omega; theta += step) {
                double x = radius * Math.sin(phi) * Math.cos(theta + (offset ? step / 2 : 0));
                double y = radius * Math.cos(phi);
                double z = radius * Math.sin(phi) * Math.sin(theta + (offset ? step / 2 : 0));
                
                vertices.get(layer).add(new Vector(x, y, z).plus(center));
            }
            offset = !offset;
            layer++;
        }
        
        for (int i = 0; i < layer - 1; i++) {
            for (int j = 0; j < layer; j++) {
                new Triangle(this, color, vertices.get(i).get(j), vertices.get(i + 1).get(j), vertices.get(i + 1).get((j + 1) % layer));
            }
        }
        for (int i = layer - 1; i > 0; i--) {
            for (int j = 0; j < layer; j++) {
                Triangle t = new Triangle(this, color2, vertices.get(i).get(j), vertices.get(i - 1).get(j), vertices.get(i - 1).get((j == 0) ? layer - 1 : j - 1)); //(j == 0) ? layer - 1 : j - 1
            }
        }
        
        setVisible(visible);
    }
    
}
