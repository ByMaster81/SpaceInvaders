package Enemy;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.gamePanel;

public class EnemyBullet extends Bullets {
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    gamePanel gp;
    public Image skin1;
    public EnemyBullet(int x, int y, int speedX, int speedY) {
        super(x, y, speedX, speedY);
    }

    public void move() {
        x += speedX;
        y += speedY;
    }

    protected int getWidth() {
        return WIDTH;
    }

    protected int getHeight() {
        return HEIGHT;
    }
    public void getBulletImage() {
        try {
            skin1 = ImageIO.read(getClass().getResourceAsStream("/allien/bulletEnemy.png"));
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    public void draw(Graphics2D g2) {
        try {
            skin1 = ImageIO.read(getClass().getResourceAsStream("/allien/bulletEnemy.png"));
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        Image image = skin1; 
        g2.drawImage(image, x, y, 40, 40, null);
    }
}
