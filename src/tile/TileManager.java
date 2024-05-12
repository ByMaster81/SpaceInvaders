package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.gamePanel;

public class TileManager {
	private BufferedImage image;
	gamePanel gp;
	int x = 0;
	private int y = 0;
	public TileManager(gamePanel gp) {
		
		this.gp = gp;
		
		
		
		getTileImage();
		
	}
	
	public void getTileImage() {
		
		 try {
	            image = ImageIO.read(getClass().getResourceAsStream("/tiles/tile0.png"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	
	
	
	public void draw(Graphics2D g2) {
	
		BufferedImage background = image;
		
		g2.drawImage(background, x, getY()+900, 400, -32000, null);
		g2.drawImage(background, x+400, getY()+900, 400, -32000, null);	
		g2.drawImage(background, x+800, getY()+900, 400, -32000, null);	
		
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}


























