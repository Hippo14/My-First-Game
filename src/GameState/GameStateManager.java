/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;
        
/**
 *
 * @author ASUS
 */
public class GameStateManager {
    
    private GameState gameStates[];
    private int currentState;
    
    public static final int NUMGAMESTATES = 2;
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    
    public GameStateManager() {
        gameStates = new GameState[NUMGAMESTATES];
        
        currentState = MENUSTATE;
        
        loadState(currentState);
    }
    
    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }
    
    public void update() {
        if (gameStates[currentState] != null)
            gameStates[currentState].update();
    }
    
    public void draw(Graphics2D g) {
        if (gameStates[currentState] != null)
            gameStates[currentState].draw(g);
    }
    
    public void keyPressed(int k) {
        if (gameStates[currentState] != null)
            gameStates[currentState].keyPressed(k);
    }
    
    public void keyReleased(int k) {
        if (gameStates[currentState] != null)
            gameStates[currentState].keyReleased(k);
    }

    private void loadState(int currentState) {
        if (currentState == MENUSTATE) {
            gameStates[currentState] = new MenuState(this);
        }
        if (currentState == LEVEL1STATE) {
            gameStates[currentState] = new Level1State(this);
        }
    }
    
    private void unloadState(int currentState) {
        gameStates[currentState] = null;
    }
    
}