package Enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import game.gamePanel;

public class Allien1  {
	
	private long lastShotTime;
    private static int speedY = 1;
    private int speedX = 1;
	private BufferedImage[] animationFrames; 
    private int currentFrameIndex; 
    private int frameDelay; 
    private long lastFrameTime; 
    public int hp = 5;
    gamePanel gp;
	public int x;
	public int y;

    public Allien1(gamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        lastShotTime = System.currentTimeMillis();
        frameDelay = 200; 
        currentFrameIndex = 0;
        lastFrameTime = System.currentTimeMillis();
        loadAnimationFrames();
    }

    

	private void loadAnimationFrames() {
        try {
            animationFrames = new BufferedImage[2]; 
            animationFrames[0] = ImageIO.read(getClass().getResourceAsStream("/allien/allien.png"));
            animationFrames[1] = ImageIO.read(getClass().getResourceAsStream("/allien/allien2.png"));
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        
        BufferedImage currentFrame = animationFrames[currentFrameIndex];
        g2.drawImage(currentFrame, x, y, 130, 130, null);

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelay) {
            currentFrameIndex = (currentFrameIndex + 1) % animationFrames.length;
            lastFrameTime = currentTime;
        }
        
        
        if (currentTime - lastShotTime >= 2000) { 
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
        EnemyBullet bullet = new EnemyBullet(x+60, y+100,0,10); 
        gp.addBulletEnemy(bullet); 
    }

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}



	public static int getSpeedY() {
		return speedY;
	}



	public static void setSpeedY(int speedY) {
		Allien1.speedY = speedY;
	}
}
