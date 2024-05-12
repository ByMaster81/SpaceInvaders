package Enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import game.gamePanel;

public class Allien2 {
	
	private long lastShotTime;
    private static int speed = 1;
	private BufferedImage[] animationFrames; 
    private int currentFrameIndex; 
    private int frameDelay; 
    private long lastFrameTime; 
    public int hp = 8;
    gamePanel gp;
	private int x;
	public int y;

    public Allien2(gamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        lastShotTime = System.currentTimeMillis();
        frameDelay = 60; 
        currentFrameIndex = 0;
        lastFrameTime = System.currentTimeMillis();
        loadAnimationFrames();
    }

    public static void setSpeed(int speed) {
		Allien2.speed = speed;
	}

	

	public static int getSpeed() {
		return speed;
	}

	private void loadAnimationFrames() {
        try {
            animationFrames = new BufferedImage[4]; 
            animationFrames[0] = ImageIO.read(getClass().getResourceAsStream("/allien/allien21.png"));
            animationFrames[1] = ImageIO.read(getClass().getResourceAsStream("/allien/allien22.png"));
            animationFrames[2]= ImageIO.read(getClass().getResourceAsStream("/allien/allien23.png"));
            animationFrames[3] = ImageIO.read(getClass().getResourceAsStream("/allien/allien24.png"));
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        
        BufferedImage currentFrame = animationFrames[currentFrameIndex];
        g2.drawImage(currentFrame, x, y, 150, 150, null);

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelay) {
            currentFrameIndex = (currentFrameIndex + 1) % animationFrames.length;
            lastFrameTime = currentTime;
        }
        
        
        if (currentTime - lastShotTime >= 500) { 
            shoot(); 
            lastShotTime = currentTime; 
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 100, 100);
    }

    public void hit() {
        hp--;
    }

    public int getHP() {
        return hp;
    }
    
    private void shoot() {
    	
    	
            

    	Random random = new Random();
        int bulletType = random.nextInt(4);

        switch (bulletType) {
            case 0:
            	gp.addBulletEnemy(new EnemyBulletLeft(x, y+60));
                break;
            case 1:
            	gp.addBulletEnemy(new EnemyBulletRight(x+100, y+55));
                break;
            case 2:
            	gp.addBulletEnemy(new EnemyBulletUp(x+60, y));
                break;
            case 3:
                gp.addBulletEnemy(new EnemyBulletDown(x+50, y+100));
                break;
            default:
                break;
            
        }
         
    }
}
