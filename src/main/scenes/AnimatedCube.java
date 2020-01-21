/*
 * File:    AnimatedCube.java
 * Package: main.scenes
 * Author:  Zachary Gill
 */

package main.scenes;

import java.awt.Color;

import camera.Camera;
import main.Environment;
import objects.base.Frame;
import objects.base.Scene;
import objects.polyhedron.regular.platonic.Hexahedron;
import utility.ColorUtility;

/**
 * Defines a Animated Cube scene.
 */
public class AnimatedCube extends Scene {
    
    //Main Method
    
    /**
     * The main method for the Animated Cube scene.
     *
     * @param args The arguments to the main method.
     */
    public static void main(String[] args) {
        Environment environment = new Environment();
        environment.setup();
        environment.setupMainKeyListener();
        
        AnimatedCube animatedCube = new AnimatedCube(environment);
        animatedCube.setupCameras();
        animatedCube.setupControls();
        
        environment.addObject(animatedCube);
        environment.run();
    }
    
    
    //Constructors
    
    /**
     * Constructor for the Animated Cube scene.
     *
     * @param environment The Environment to render the Animated Cube in.
     */
    public AnimatedCube(Environment environment) {
        super(environment);
        
        calculate();
    }
    
    
    //Methods
    
    /**
     * Calculates the components that compose the Animated Cube.
     */
    @Override
    public void calculate() {
        Hexahedron cube = new Hexahedron(Environment.ORIGIN, Color.BLUE, 2);
        cube.addRotationAnimation(Math.PI / 4, Math.PI / 4, Math.PI / 4);
        
        for (int f = 1; f < 6; f++) {
            cube.setFaceColor(f, ColorUtility.getRandomColor());
        }
        Frame frame = cube.addFrame(Color.BLACK);
        
        frame.addColorAnimation(5000, 2500);
        cube.addColorAnimation(5000, 0);
        registerComponent(cube);
    }
    
    /**
     * Sets up cameras for the Animated Cube scene.
     */
    public void setupCameras() {
        Camera camera = new Camera(this, true, true);
        camera.setLocation(Math.PI / 2, 0, 8);
    }
    
    /**
     * Sets up controls for the Animated Cube scene.
     */
    public void setupControls() {
    }
    
}
