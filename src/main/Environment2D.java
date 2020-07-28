/*
 * File:    Environment.java
 * Package: main
 * Author:  Zachary Gill
 */

package main;

import java.awt.BorderLayout;
import java.awt.Graphics2D;

import math.vector.Vector;
import objects.base.Drawing;

/**
 * The main Environment.
 */
public class Environment2D extends EnvironmentBase {
    
    
    //Fields
    
    /**
     * The Drawing to render.
     */
    public Drawing drawing = null;
    
    
    //Constructors
    
    /**
     * The default constructor for an Environment2D.
     */
    public Environment2D() {
        background = null;
        layout = new BorderLayout();
    }
    
    
    //Methods
    
    /**
     * Renders the Environment.
     *
     * @param g2 The 2D Graphics entity.
     */
    @Override
    protected void render(Graphics2D g2) {
        Drawing.doRender(drawing, g2);
    }
    
    /**
     * Returns a random position on the screen.
     *
     * @return A random position on the screen.
     */
    public Vector getRandomPosition() {
        return new Vector(Math.random() * Environment2D.width, Math.random() * Environment2D.height);
    }
    
    /**
     * Returns the center position on the screen.
     *
     * @return The center position on the screen.
     */
    public Vector getCenterPosition() {
        return new Vector(Environment2D.width, Environment2D.height).scale(0.5);
    }
    
    
    //Setters
    
    /**
     * Sets the Drawing to render.
     *
     * @param drawing The Drawing to render.
     */
    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }
    
}
