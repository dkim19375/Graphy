/*
 * File:    Graph2D.java
 * Package: main.scenes
 * Author:  Zachary Gill
 */

package main.scenes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import camera.Camera;
import main.Environment;
import math.vector.UniqueVectorSet;
import math.vector.Vector;
import objects.base.Object;
import objects.base.Scene;
import objects.base.simple.Edge;
import objects.system.Axes;
import utility.EquationUtility;

/**
 * Defines a Graph2D scene.
 */
public class Graph2D extends Scene {
    
    //Static Fields
    
    /**
     * The plane of the graph.
     */
    private static Object plane;
    
    /**
     * The axes of the graph.
     */
    private static Object axes;
    
    /**
     * The radius of the graph of the function on the screen.
     */
    public static double radius = 5;
    
    /**
     * The value by which to scale the function.
     */
    public static double scale = 0.25;
    
    /**
     * The density of the graph.
     */
    public static double density = 0.01;
    
    /**
     * The equation to graph
     */
//    private static EquationUtility.MathOperation equation = EquationUtility.parseMath("x^3 - 5 * x^2 - 12 * x + 2");
    private static EquationUtility.MathOperation equation = EquationUtility.parseMath("x^9 - 3 * x^7 + 2");
    
    
    //Main Method
    
    /**
     * The main method for the Graph2D scene.
     *
     * @param args The arguments to the main method.
     * @throws Exception When the Scene class cannot be constructed.
     */
    public static void main(String[] args) throws Exception {
        runScene(Graph2D.class);
    }
    
    //Constructors
    
    /**
     * Constructor for the Graph2D Scene.
     *
     * @param environment The Environment to render the Graph2D in.
     */
    public Graph2D(Environment environment) {
        super(environment);
    }
    
    
    //Methods
    
    /**
     * Calculates the components that compose the Graph2D.
     */
    @Override
    public void calculate() {
        if (plane != null) {
            unregisterComponent(plane);
        }
        plane = new Object(Color.BLACK);
        
        List<Vector> vs = new ArrayList<>();
        UniqueVectorSet uniqueVectorSet = new UniqueVectorSet();
        for (double x = -radius / scale; x <= radius / scale; x += density / scale) {
            
            List<Vector> vt = new ArrayList<>();
            vt.add(new Vector(x, 0, 0));
            vt.add(new Vector(x + density / scale, 0, 0));
            
            uniqueVectorSet.alignVectorsToSet(vt);
            
            Edge t = new Edge(plane, Color.BLACK, vt.get(0), vt.get(1));
            
            vs.addAll(vt);
        }
        
        for (Vector v : vs) {
            Map<String, Number> vars = new HashMap<>();
            vars.put("x", v.getX());
            v.setX(v.getX() * scale);
            v.setY(equation.evaluate(vars).doubleValue() * scale);
            if (v.getY() != v.getY()) {
                v.setY(0);
            }
            if (v.getY() > radius) {
                v.setY(radius);
            }
            if (v.getY() < -radius) {
                v.setY(-radius);
            }
        }
        
        registerComponent(plane);
        
        if (axes != null) {
            unregisterComponent(axes);
        }
        axes = new Axes((int) radius,
                true, true, false,
                true, true, false,
                1, 1 / scale,
                true, true, false,
                false);
        registerComponent(axes);
    }
    
    /**
     * Sets up components for the Graph2D scene.
     */
    @Override
    public void initComponents() {
    }
    
    /**
     * Sets up cameras for the Graph2D scene.
     */
    @Override
    public void setupCameras() {
        Camera camera = new Camera(this, true, false);
        camera.setLocation(0, Math.PI / 2, 10);
    }
    
    /**
     * Sets up controls for the Graph2D scene.
     */
    @Override
    public void setupControls() {
        environment.scene.environment.renderPanel.addMouseWheelListener(e -> {
            scale *= Math.pow(0.8, e.getWheelRotation());
            calculate();
        });
    }
    
}
