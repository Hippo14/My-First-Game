/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import GameState.GameStateManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.event.*;

/**
 *
 * @author ASUS
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 4;
    
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    
    private BufferedImage image;
    private Graphics2D g;
    
    private GameStateManager gsm;
    
    public GamePanel() {
            super();
            setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
            setFocusable(true);
            requestFocus();
	}
    
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            
            addKeyListener(this);
            
            thread.start();
        }
    }
    
    private void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
        gsm = new GameStateManager();
    }
    
    public void run() {
        init();
        
        long start;
        long elapsed;
        long wait;
        
        //main game loop
        while(running) {
            
            start = System.nanoTime();
            
            update();
            draw();
            showOnScreen();
            
            elapsed = System.nanoTime() - start;
            
            wait = targetTime - elapsed / 1000000;
            
            if (wait < 0) {
                wait = 5;
            }
            
            try {
                Thread.sleep(wait);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
    private void update() { gsm.update(); }
    private void draw() { gsm.draw(g); }
    private void showOnScreen() { 
        Graphics g2 = getGraphics();
        
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT* SCALE,null);
        g2.dispose();
    }
    
    public void keyTyped(KeyEvent key) { }
    public void keyPressed(KeyEvent key) { gsm.keyPressed(key.getKeyCode()); }
    public void keyReleased(KeyEvent key) { gsm.keyReleased(key.getKeyCode()); }
}
