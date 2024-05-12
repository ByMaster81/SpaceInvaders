package Enemy;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import game.gamePanel;


public class bulletEnemy {
    
	gamePanel gp;
	private int x;
	private int y;
	private static int speed = -10;
	public BufferedImage skin1;
	
	public static void setSpeed(int speed) {
		bulletEnemy.speed = speed;
	}
	public bulletEnemy(int x, int y) {
        this.x = x;
        this.y = y;
        
        getBulletImage();
        
    }
    public void getBulletImage() {
        try {
            skin1 = ImageIO.read(getClass().getResourceAsStream("/allien/bulletEnemy.png"));
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 25, 25); 
    }
    
    
	
    public void update() {
        y -= speed; // 
    }

    public void draw(Graphics2D g2) {
    	BufferedImage image = skin1; 
    	g2.drawImage(image, x, y, 40, 40, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
	
}