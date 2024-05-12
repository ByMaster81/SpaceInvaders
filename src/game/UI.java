package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

import entity.Player;

import Enemy.EnemyBullet;

public class UI {
	private Player player;
	private gamePanel gp;
	private Graphics2D g2;
	private int commandNum = 0;
	private Font MCFont;
	private Font FFF;
	private Image background;
	private Image spaceInvadersLogo;
	private Image gameOverImage;

	public UI(gamePanel gp, Player player) {
		this.gp = gp;
		this.player = player;
		loadFonts();
		loadImages();
	}

	private void loadFonts() {
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/Minecraft.TTF");
			MCFont = Font.createFont(Font.TRUETYPE_FONT, is);
			InputStream is1 = getClass().getResourceAsStream("/fonts/FFF.TTF");
			FFF = Font.createFont(Font.TRUETYPE_FONT, is1);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadImages() {
		background = new ImageIcon(getClass().getResource("/background/background.png")).getImage();
		spaceInvadersLogo = new ImageIcon(getClass().getResource("/menu/spaceinvaders.png")).getImage();
		gameOverImage = new ImageIcon(getClass().getResource("/background/gameover.png")).getImage();
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(MCFont);

		if (gp.gameState == gp.playState) {
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
			g2.setColor(Color.YELLOW);
			g2.drawString("HP: " + player.hp, 10, 100);
			g2.drawString("Score: " + gp.score, 10, 150);
			gp.checkCollision();
			gp.checkCollisionPlayer();
			gp.checkCollisionEnemyBullet();
			List<EnemyBullet> copyBulletEnemies = new ArrayList<>(gp.bulletEnemies);
			for (EnemyBullet alienBullet : copyBulletEnemies) {
				alienBullet.draw(g2);
			}
			if (gp.time>100) {
				g2.drawString("LEVEL: 3 ",10,200);
			}
			else if (gp.time>50) {
				g2.drawString("LEVEL: 2 ",10,200);
				
			}
			else g2.drawString("LEVEL: 1 ",10,200);
			
		}
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
			gp.time = 0;
		}
		if (gp.gameState == gp.gameOver) {

			drawGameOverScreen();

		}
		if (gp.gameState == gp.highScoresState) {

			drawScoresScreen();

		}
	}

	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		g2.setColor(Color.YELLOW);
		g2.drawString("HP: " + player.hp, 10, 200);
		String text = "PAUSED";
		int x = getXCentered(text);
		int y = gp.screenHeight / 2;
		g2.drawString(text, x, y);
	}

	public void drawTitleScreen() {
		g2.drawImage(background, 0, 0, gp.getWidth(), gp.getHeight(), null);
		int width = (int) (spaceInvadersLogo.getWidth(null) * 0.9);
		int height = (int) (spaceInvadersLogo.getHeight(null) * 0.8);
		g2.drawImage(spaceInvadersLogo, gp.screenWidth / 2 - width / 2, 100, width, height, null);

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		g2.setColor(Color.YELLOW);

		String text = "START GAME";
		int x = getXCentered(text);
		int y = 520;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 25, y);
			g2.drawString("<", x + 7 + (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(), y);
		}
		text = "SCOREBOARD";
		x = getXCentered(text);
		y = 590;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 25, y);
			g2.drawString("<", x + 7 + (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(), y);
		}

		text = "EXIT";
		x = getXCentered(text);
		y = 660;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - 25, y);
			g2.drawString("<", x + 4 + (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(), y);
		}
		g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 30F));
		if (!gameFrame.loginCheck) {

			text = "Please Login From File Menu(Register if you don't have an account !)";
			x = 20;
			y = 800;
			g2.drawString(text, x, y);
		} else {
			text = "Active User: " + gameFrame.activeUser;
			x = 20;
			y = 800;
			g2.drawString(text, x, y);
		}

	}

	public void drawGameOverScreen() {
		g2.drawImage(gameOverImage, 0, 0, gp.getWidth(), gp.getHeight(), null);
	}

	public void drawScoresScreen() {
		g2.drawImage(background, 0, 0, gp.getWidth(), gp.getHeight(), null);
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
	    g2.setColor(Color.YELLOW);

	    String text = "SCOREBOARD";
	    int x = getXCentered(text);
	    int y = 200;
	    g2.drawString(text, x, y);
	    
	    String text1 = "ESC to main menu";
	    int x1 = getXCentered(text1);
	    int y1 = 800;
	    g2.drawString(text1, x1, y1);

	    try {
	        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
	        BufferedReader reader = new BufferedReader(new FileReader("Playerinfos.txt"));

	        String line;
	        List<Scores> scoreList = new ArrayList<>();
	        int i = 0;
	    	while ((line = reader.readLine()) != null && i<9) {
	            String[] elements = line.split(",");
	            if (elements.length >= 3) {
	                String name = elements[0];
	                int score = Integer.parseInt(elements[2]);
	                scoreList.add(new Scores(name, score));
	            }
	            i++;
	        }

	        scoreList.sort(Comparator.comparingInt(Scores::getScore).reversed());

	        int lineNum = 1;
	        for (Scores entry : scoreList) {
	            g2.drawString(entry.getName(), x + 90, y + (lineNum * 40) + 100);
	            g2.drawString(String.valueOf(entry.getScore()), x + 400, y + (lineNum * 40) + 100);
	            lineNum++;
	        }

	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
	}

	public int getXCentered(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}

	public int getCommandNum() {
		return commandNum;
	}

	public void setCommandNum(int commandNum) {
		this.commandNum = commandNum;
	}
}
