package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import game.KeyHandler;
import game.gamePanel;


public class Player extends Entity {
	
	public int hp;
    gamePanel gp;
    
   
	KeyHandler keyH;
    public List<bullet> bullets;
    ScheduledExecutorService executorService;
    public Player(gamePanel gp, KeyHandler keyH) {
       
    	this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        bullets = new LinkedList<>();
        solidArea = new Rectangle(0,0,40,40);
        executorService = Executors.newSingleThreadScheduledExecutor();
    }
    
    public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

    public void setDefaultValues() {
        x = 500;
        y = 600;
        speed = 10;
        
    }

    public void getPlayerImage() {
        try {
            skin1 = ImageIO.read(getClass().getResourceAsStream("/player/ship.png"));
            skin2 = ImageIO.read(getClass().getResourceAsStream("/player/shipL.png"));
            skin3 = ImageIO.read(getClass().getResourceAsStream("/player/shipR.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed && y > 0) {
            y -= speed;
        }
        if (keyH.downPressed && y < 900 - 100) {
            y += speed;
        }
        if (keyH.leftPressed && x > 0) {
            x -= speed;
        }
        if (keyH.rightPressed && x < 1200 - 67) {
            x += speed;
        }

       
        if (keyH.spacePressed) {
            startFiring();
        } else {
            stopFiring();
        }
        
        
       
        for (int i = 0; i < bullets.size(); i++) {
            bullet bullet = bullets.get(i);
            bullet.update();
            //here
            if (bullet.getY() < 0) {
                bullets.remove(i);
            }
        }
    }

    public void draw(Graphics2D g2) {
        
        
        if (keyH.leftPressed && x > 0) {
        	g2.drawImage(skin2, x, y, 70, 70, null);
        }
        else if (keyH.rightPressed && x < 1200 - 67) {
        	g2.drawImage(skin3, x, y, 70, 70, null);
        }else g2.drawImage(skin1, x, y, 70, 70, null);
        
        List<bullet> bulletsCopy = new LinkedList<>(bullets);
        for (bullet b : bulletsCopy) {
            b.draw(g2);
        }
    }
    private void startFiring() {
    	 if (bullets.size() < 3 && executorService.isShutdown() && gp.gameState == gp.playState) {
             executorService = Executors.newSingleThreadScheduledExecutor();
             executorService.scheduleAtFixedRate(() -> fire(), 0, 230, TimeUnit.MILLISECONDS);
         }
    }
    
    private void stopFiring() {
        executorService.shutdownNow();
        
    }
    private void fire() {
        int bulletX = x + 25; 
        int bulletY = y; 

        bullet bullet = new bullet(bulletX, bulletY);
        gp.playEffect(1);
        bullets.add(bullet);
    }
    public Rectangle getBounds() {
    	return new Rectangle(x, y, 50, 50); 
    }
    
        
}
