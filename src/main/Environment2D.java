/*
 * File:    Environment.java
 * Package: main
 * Author:  Zachary Gill
 */

package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import math.vector.Vector;
import objects.base.Drawing;

/**
 * The main Environment.
 */
public class Environment2D {
    
    //Constants
    
    /**
     * The maximum x dimension of the Window.
     */
    public static final int MAX_SCREEN_X = Environment.MAX_SCREEN_X;
    
    /**
     * The maximum y dimension of the Window.
     */
    public static final int MAX_SCREEN_Y = Environment.MAX_SCREEN_Y;
    
    
    //Fields
    
    /**
     * The number of frames to render per second.
     */
    public int fps = 120;
    
    /**
     * The x dimension of the Window.
     */
    public int screenX = MAX_SCREEN_X;
    
    /**
     * The y dimension of the Window.
     */
    public int screenY = MAX_SCREEN_Y;
    
    /**
     * The Frame of the Window.
     */
    public JFrame frame;
    
    /**
     * The coordinates to center the Environment at.
     */
    public Vector origin = new Vector(0, 0);
    
    /**
     * The Drawing to render.
     */
    public Drawing drawing = null;
    
    /**
     * The size of the Drawing to render.
     */
    public Vector drawingSize;
    
    /**
     * The offset of the Drawing to render.
     */
    public Vector drawingOffset = new Vector(0, 0);
    
    /**
     * The background color of the Environment.
     */
    public Color background = null;
    
    
    //Constructors
    
    /**
     * Constructs an Environment2D.
     *
     * @param screenX The x dimension of the screen.
     * @param screenY The y dimension of the screen.
     */
    public Environment2D(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
    }
    
    /**
     * The default constructor for an Environment2D.
     */
    public Environment2D() {
    }
    
    
    //Methods
    
    /**
     * Sets up the Environment.
     */
    public void setup() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        if ((screenX == MAX_SCREEN_X) && (screenY == MAX_SCREEN_Y)) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        
        if (drawingSize == null) {
            drawingSize = new Vector(screenX, screenY);
        }
        
        // panel to display render results
        JPanel renderPanel = new JPanel() {
            
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                if (background != null) {
                    g2.setColor(background);
                    g2.fillRect(0, 0, screenX, screenY);
                }
                
                if (drawing != null) {
                    BufferedImage img = drawing.render();
                    g2.drawImage(img, (int) drawingOffset.getX(), (int) drawingOffset.getY(), null);
                    drawing.overlay(g2);
                }
            }
        };
        frame.getContentPane().add(renderPanel, BorderLayout.CENTER);
        
        renderPanel.setSize(screenX, screenY);
        frame.setSize(new Dimension(screenX + 16, screenY + 39));
        frame.setPreferredSize(new Dimension(screenX + 16, screenY + 39));
        
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Runs the Environment.
     */
    public void run() {
        if (fps == 0) {
            frame.getContentPane().getComponent(0).repaint();
            
        } else {
            Timer renderTimer = new Timer();
            renderTimer.scheduleAtFixedRate(new TimerTask() {
                private AtomicBoolean rendering = new AtomicBoolean(false);
                
                @Override
                public void run() {
                    if (rendering.compareAndSet(false, true)) {
                        frame.getContentPane().getComponent(0).repaint();
                        rendering.set(false);
                    }
                }
            }, 0, 1000 / fps);
        }
    }
    
    /**
     * Returns a random position on the screen.
     *
     * @return A random position on the screen.
     */
    public Vector getRandomPosition() {
        return new Vector(Math.random() * this.screenX, Math.random() * this.screenY);
    }
    
    /**
     * Returns the center position on the screen.
     *
     * @return The center position on the screen.
     */
    public Vector getCenterPosition() {
        return new Vector(this.screenX, this.screenY).scale(0.5);
    }
    
    
    //Setters
    
    /**
     * Sets the number of frames to render per second.
     *
     * @param fps The number of frames to render per second.
     */
    public void setFps(int fps) {
        this.fps = fps;
    }
    
    /**
     * Sets the x dimension of the Window.
     *
     * @param screenX The x dimension of the Window.
     */
    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }
    
    /**
     * Sets the y dimension of the Window.
     *
     * @param screenY The y dimension of the Window.
     */
    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }
    
    /**
     * Sets the Drawing to render.
     *
     * @param drawing The Drawing to render.
     */
    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }
    
    /**
     * Sets the size of the Drawing to render.
     *
     * @param size The size of the Drawing to render.
     */
    public void setDrawingSize(Vector size) {
        this.drawingSize = size;
        this.drawingOffset.setX((screenX > drawingSize.getX()) ? (screenX - drawingSize.getX()) / 2.0 : 0);
        this.drawingOffset.setY((screenY > drawingSize.getY()) ? (screenY - drawingSize.getY()) / 2.0 : 0);
    }
    
    /**
     * Sets the background color of the Environment.
     *
     * @param background The background color.
     */
    public void setBackground(Color background) {
        this.background = background;
    }
    
}
