package entity;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;


public class bullet extends Entity {
    
	
	public bullet(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 30; 
        
        getBulletImage();
    }
    public void getBulletImage() {
        try {
            skin1 = ImageIO.read(getClass().getResourceAsStream("/player/bullet.png"));
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
    	g2.drawImage(image, x, y, 25, 25, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
	
}