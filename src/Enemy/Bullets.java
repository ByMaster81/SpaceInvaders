package Enemy;

import java.awt.Rectangle;

public abstract class Bullets {
    protected int x;
    protected int y;
    protected int speedX;
    protected int speedY;
    protected boolean isPlayerBullet;

    public Bullets(int x, int y, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
       
    }

    public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public abstract void move();

    public Rectangle getBounds() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

    
}