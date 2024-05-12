package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import entity.Player;
import javax.swing.JPanel;

import Enemy.*;
import entity.bullet;
import tile.TileManager;

public class gamePanel extends JPanel implements Runnable {

	// res
	int screenWidth = 1200;
	int screenHeight = 900;

	// FPS
	int FPS = 60;
	int frameCount = 0;
	double fps = 0;

	// system
	private int alienCreationDelay = 1500;
	private int alienCreationDelay2 = 6000;
	private long lastAlienCreationTime = System.currentTimeMillis();
	private long lastAlienCreationTime2 = System.currentTimeMillis();
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	public Player player = new Player(this, keyH);
	int score = 0;
	int tileSpeed = 1;
	Thread gameThread;
	Sound music = new Sound();
	Sound se = new Sound();
	public UI ui = new UI(this, player);
	private List<Allien1> aliens = new ArrayList<>();
	private List<Allien2> aliens2 = new ArrayList<>();
	List<EnemyBullet> bulletEnemies = new ArrayList<>();
	double time = 0;
	boolean musicOn = false;

	// game state
	public int gameState;
	public int playState = 1;
	public int pauseState = 2;
	public int titleState = 0;
	public int gameOver = 3;
	public int highScoresState = 4;

	public gamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.requestFocus();
		bulletEnemies = new ArrayList<>();

	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		gameState = titleState;
		playMusic(8);
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;

			}

			if (timer >= 1000000000) {
				fps = drawCount;
				frameCount = drawCount;
				drawCount = 0;
				timer = 0;
			}
		}

	}

	public void update() {

		if (gameState == playState) {

			time += 0.02;
			System.out.println(time);
			player.update();

			Iterator<Allien1> iterator = aliens.iterator();
			while (iterator.hasNext()) {
				Allien1 iterated = iterator.next();
				iterated.y += Allien1.getSpeedY();
				iterated.x += iterated.getSpeedX();
				if (iterated.x > 1100 || iterated.x < 0)
					iterated.setSpeedX(-iterated.getSpeedX());
			}
			if (time > 200) {
				Allien1.setSpeedY(3);
				bulletEnemy.setSpeed(-15);
				alienCreationDelay = 500;
				tileSpeed = 3;
			} else if (time > 100) {
				Allien1.setSpeedY(2);
				tileSpeed = 2;
				bulletEnemy.setSpeed(-13);
				alienCreationDelay = 2000;
			}
			Iterator<Allien2> iterator2 = aliens2.iterator();
			while (iterator2.hasNext()) {
				Allien2 iterated = iterator2.next();
				iterated.y += Allien2.getSpeed();
			}
			if (time > 100) {
				Allien2.setSpeed(3);
				bulletEnemy.setSpeed(-15);
				alienCreationDelay2 = 1000;
			} else if (time > 50) {
				Allien2.setSpeed(2);
				bulletEnemy.setSpeed(-13);
				alienCreationDelay2 = 2000;
			}

			Iterator<EnemyBullet> iter = bulletEnemies.iterator();
			while (iter.hasNext()) {
				EnemyBullet bullet = iter.next();
				bullet.move();
				if (bullet.getY() < 0 || bullet.getY() > 900 || bullet.getX() < 0 || bullet.getX() > 1300) {
					iter.remove();
				}
			}

		}
		if (gameState == pauseState) {

		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (gameState == titleState) { // title state

			score = 0;
			player.hp = 3;
			ui.draw(g2);
			drawFPS(g2);
		} else if (gameState == playState) { // play state

			tileM.draw(g2);
			tileM.setY(tileM.getY() + tileSpeed);
			Iterator<bullet> bulletIterator = player.bullets.iterator();
			while (bulletIterator.hasNext()) {
				bullet bullet = bulletIterator.next();
				if (bullet.getY() == 0)
					bulletIterator.remove();
			}
			Iterator<EnemyBullet> bulletIterator2 = bulletEnemies.iterator();
			while (bulletIterator2.hasNext()) {
				EnemyBullet bullet = bulletIterator2.next();
				if (bullet.getY() == 0 || bullet.getY() == 1000 || bullet.getX() == -10 || bullet.getX() == 1300)
					bulletIterator2.remove();
			}

			long currentTime = System.currentTimeMillis();
			if (currentTime - lastAlienCreationTime > alienCreationDelay && aliens.size() < 100) {
				createRandomAlien();
				lastAlienCreationTime = currentTime;
			}
			if (currentTime - lastAlienCreationTime2 > alienCreationDelay2 && aliens2.size() < 100) {
				createRandomAlien2();
				lastAlienCreationTime2 = currentTime;
			}

			Iterator<Allien1> alienIterator = aliens.iterator();
			while (alienIterator.hasNext()) {
				Allien1 alien = alienIterator.next();
				if (alien.y > 900) {
					alienIterator.remove();
				} else {
					alien.draw(g2);
				}
			}

			Iterator<Allien2> alienIterator2 = aliens2.iterator();
			while (alienIterator2.hasNext()) {
				Allien2 alien2 = alienIterator2.next();
				if (alien2.y > 900) {
					alienIterator2.remove();
				} else {
					alien2.draw(g2);
				}
			}
			if (player.hp == 0) {
				gameState = gameOver;
				File file = new File("Playerinfos.txt");
				try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
					StringBuilder content = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						String[] userInfo = line.split(",");
						String storedUsername = userInfo[0];
						if (storedUsername.equals(gameFrame.activeUser)) {
			                
			                if (Integer.parseInt(userInfo[2]) < score) {
			                    userInfo[2] = String.valueOf(score);
			                }
						}
						
					
					  StringBuilder newLine = new StringBuilder();
			            for (String info : userInfo) {
			                newLine.append(info).append(",");
			            }

			            // Son virgülü sil
			            newLine.setLength(newLine.length() - 1);

			            // Düzenlenmiş satırı ekle
			            content.append(newLine);

			            // Satır sonuna yeni satır karakterini ekle
			            content.append(System.lineSeparator());
			        }
			

			        // Okuyucuyu ve dosyayı kapat
			        reader.close();

			        // Dosyayı yeniden yaz
			        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			        writer.write(content.toString());
			        writer.close();

			        System.out.println("Kullanıcı skoru güncellendi.");
					  // Satırı tekrar oluştur
			   
			    } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				stopMusic();
				playEffect(6);
				musicOn = false;
				keyH.spacePressed = false;
				playMusic(7);
				player.hp = 3;

			}
			player.draw(g2);
			ui.draw(g2);
			drawFPS(g2);
		} else if (gameState == pauseState) { // pause state
			drawFPS(g2);
			tileM.draw(g2);
			ui.draw(g2);
			player.draw(g2);
		} else if (gameState == gameOver) {

			drawFPS(g2);
			ui.draw(g2);
			Iterator<Allien1> alienIterator = aliens.iterator();
			while (alienIterator.hasNext()) {
				Allien1 alien = alienIterator.next();
				alienIterator.remove();
			}
			aliens.clear();
	        aliens2.clear();
	        bulletEnemies.clear();

		}else if (gameState == highScoresState) {
			ui.draw(g2);
			drawFPS(g2);
		}

		g2.dispose();
	}

	private void drawFPS(Graphics2D g2) {
		g2.setColor(Color.GREEN);
		g2.setFont(new Font("Arial", Font.BOLD, 16));
		g2.drawString("FPS: " + String.format("%.1f", fps), 10, 20);
	}

	private void createRandomAlien() {
		int x = (int) (Math.random() * (screenWidth - 140));
		int y = -140;

		Random random = new Random();

		int type = random.nextBoolean() ? 2 : -2;
		Allien1 alien = new Allien1(this, x, y);
		alien.setSpeedX(type);

		aliens.add(alien); //
	}

	private void createRandomAlien2() {
		int x = (int) (Math.random() * (screenWidth + 140));
		int y = -140;
		Allien2 alien = new Allien2(this, x, y);

		aliens2.add(alien); //
	}

	public void playMusic(int i) {

		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playEffect(int i) {

		se.setFile(i);
		se.play();
	}

	public void checkCollision() {
		Iterator<bullet> bulletIterator = player.bullets.iterator();

		while (bulletIterator.hasNext()) {
			bullet bullet = bulletIterator.next();
			Iterator<Allien1> alienIterator = aliens.iterator();
			Iterator<Allien2> alienIterator2 = aliens2.iterator();

			while (alienIterator.hasNext()) {
				Allien1 alien = alienIterator.next();
				if (bullet.getBounds().intersects(alien.getBounds())) {
					bulletIterator.remove();
					alien.hit();
					playEffect(5);
					score += 10;
					if (alien.getHP() == 0) {
						alienIterator.remove();
						
					}
					break;
				}
			}

			while (alienIterator2.hasNext()) {
				Allien2 alien2 = alienIterator2.next();
				if (bullet.getBounds().intersects(alien2.getBounds())) {
					bulletIterator.remove();
					alien2.hit();
					playEffect(5);
					score += 20;
					if (alien2.getHP() == 0) {
						alienIterator2.remove();
						
					}
					break;
				}
			}
		}
	}

	public void checkCollisionPlayer() {

		Iterator<Allien1> alienIterator = aliens.iterator();
		while (alienIterator.hasNext()) {
			Allien1 alien = alienIterator.next();
			if (player.getBounds().intersects(alien.getBounds())) {
				alienIterator.remove();
				player.hp--;
				break;

			}
		}
		Iterator<Allien2> alienIterator2 = aliens2.iterator();
		while (alienIterator2.hasNext()) {
			Allien2 alien2 = alienIterator2.next();
			if (player.getBounds().intersects(alien2.getBounds())) {
				alienIterator2.remove();
				player.hp--;
				break;

			}
		}
	}

	public void checkCollisionEnemyBullet() {

		Iterator<EnemyBullet> alienBulletIterator = bulletEnemies.iterator();
		while (alienBulletIterator.hasNext()) {
			EnemyBullet alienBullet = alienBulletIterator.next();
			if (player.getBounds().intersects(alienBullet.getBounds())) {
				alienBulletIterator.remove();
				player.hp--;
				break;

			}
		}
	}

	public void addBulletEnemy(EnemyBullet bullet) {
		bulletEnemies.add(bullet);
	}

}
