package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;

	private gamePanel gp;

	public KeyHandler(gamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		// title state
		if (gp.gameState == gp.titleState) {
			if (gameFrame.loginCheck) {
				if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					if (gp.ui.getCommandNum() > 0) {
						gp.ui.setCommandNum(gp.ui.getCommandNum() - 1);
						gp.playEffect(2);
					}
				}
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					if (gp.ui.getCommandNum() < 2) {
						gp.ui.setCommandNum(gp.ui.getCommandNum() + 1);
						gp.playEffect(2);
					}
				}

				if (code == KeyEvent.VK_ENTER) {
					if (gp.ui.getCommandNum() == 0) {
						gp.playEffect(4);
						gp.stopMusic();
						gp.playMusic(0);
						gp.gameState = gp.playState;
					} else if (gp.ui.getCommandNum() == 2) {
						System.exit(0);
					}else if (gp.ui.getCommandNum() == 1) {
						gp.gameState = gp.highScoresState;
					}
				}
			}
		}
		if (gp.gameState == gp.highScoresState) {
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.titleState;
			}
		}

		// play state
		if (gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				upPressed = true;
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (code == KeyEvent.VK_SPACE) {
				spacePressed = true;
			}
			if (code == KeyEvent.VK_P) {
				if (gp.gameState == gp.playState) {
					gp.gameState = gp.pauseState;
				} else if (gp.gameState == gp.pauseState) {
					gp.gameState = gp.playState;
				}
			}
		}

		if (gp.gameState == gp.gameOver) {
			if (code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.titleState;
				gp.stopMusic();
				gp.playMusic(8);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}
}
