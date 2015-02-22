/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import Audio.AudioPlayer;
import Entity.Enemies.Slugger;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Main.GamePanel;
import TileMap.Background;
import TileMap.Tile;
import TileMap.TileMap;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Level1State extends GameState {
    
    private TileMap tilemap;
    private Background bg;
    
    private Player player;
    
    private HUD hud;
    
    private AudioPlayer bgMusic;
    
    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    
    public Level1State(GameStateManager gamestatemanager) {
        gsm = gamestatemanager;
        init();
    }
    
    public void init() {
        tilemap = new TileMap(30);
        tilemap.loadTiles("/Tilesets/grasstileset.gif");
        tilemap.loadMap("/Maps/level1-1.map");
        tilemap.setPosition(0, 0);
        tilemap.setTween(0.07);
        
        bg = new Background("/Background/level1.png", 0.1);
        player = new Player(tilemap);
        player.setPosition(100, 100);
        
        populateEnemies();
        
        explosions = new ArrayList<Explosion>();
        
        hud = new HUD(player);
        
        bgMusic = new AudioPlayer("/Music/acdc.mp3");
        bgMusic.play();
    }
    
    public void update() {
        player.update();
        tilemap.setPosition(GamePanel.WIDTH / 2 - player.getX(), GamePanel.HEIGHT / 2 - player.getY());
        bg.setPosition(tilemap.getx(), tilemap.gety());
        
        player.checkAttack(enemies);
        
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if(e.isDead()) {
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(e.getX(), e.getY()));
            }
        }
        
         for (int i = 0; i < explosions.size(); i++) {
             explosions.get(i).update();
             if (explosions.get(i).shouldRemove()) {
                 explosions.remove(i);
                 i--;
             }
         }
    }
    
    public void draw(Graphics2D g) {
        bg.draw(g);
        tilemap.draw(g);
        player.draw(g);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosition((int)tilemap.getx(), (int)tilemap.gety());
            explosions.get(i).draw(g);
        }
        hud.draw(g);
    }
    
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }
        if (k == KeyEvent.VK_UP) {
            player.setUp(true);
        }
        if (k == KeyEvent.VK_DOWN) {
            player.setDown(true);
        }
        if (k == KeyEvent.VK_W) {
            player.setJumping(true);
        }
        if (k == KeyEvent.VK_E) {
            player.setGliding(true);
        }
        if (k == KeyEvent.VK_R) {
            player.setScratching();
        }
        if (k == KeyEvent.VK_F) {
            player.setFiring();
        }
    }
    
    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
        if (k == KeyEvent.VK_UP) {
            player.setUp(false);
        }
        if (k == KeyEvent.VK_DOWN) {
            player.setDown(false);
        }
        if (k == KeyEvent.VK_W) {
            player.setJumping(false);
        }
        if (k == KeyEvent.VK_E) {
            player.setGliding(false);
        }
    }

    private void populateEnemies() {
        enemies = new ArrayList<Enemy>();
        Point[] points = new Point[] {
            new Point(200, 100),
            new Point(860, 200),
            new Point(1525, 200),
            new Point(1680, 200),
            new Point(1800, 200)
        };
        
        Slugger s;
        
        for (int i = 0; i < points.length; i++) {
            s = new Slugger(tilemap);
            s.setPosition(points[i].x, points[i].y);
            enemies.add(s);
        }
        
    }
    
}