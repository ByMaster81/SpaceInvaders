package game;

import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class gameFrame  {
	JFrame mainmenu;
	JPanel menupanel;
	JMenuBar menubar;
	JMenu file;
	JMenu help;
	JMenuItem register;
	JMenuItem login;
	JMenuItem highscore;
	JMenuItem quit;
	JMenuItem about;
	JLabel backgroundlabel;
	File pi = new File("Playerinfos.txt");
	public static String activeUser = "";
	public static boolean loginCheck = false;
	Thread gameThread;
	int FPS = 60;
	int fps = 0;
	int frameCount = 0;
	gamePanel gp = new gamePanel();
	public gameFrame() {
		mainmenu = new JFrame("Space Invaders");
		mainmenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menupanel = new JPanel();
		menubar = new JMenuBar();
		file = new JMenu("File");
		help = new JMenu("Help");
		register = new JMenuItem("Register");
		login = new JMenuItem("Login");
		highscore = new JMenuItem("High Score");
		quit = new JMenuItem("Quit");
		about = new JMenuItem("About");
		
		mainmenu.add(menubar);
		menubar.add(file);
		menubar.add(help);
		file.add(register);
		file.add(login);
		help.add(about);
		file.add(highscore);
		file.add(quit);
		mainmenu.add(gp);
		gp.startGameThread();
		mainmenu.setJMenuBar(menubar);
		mainmenu.setSize(1200,900);
		mainmenu.setVisible(true);
		
		MenuBar menubar = new MenuBar();
		register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField usernameField = new JTextField();
                JPasswordField passwordField = new JPasswordField();
                Object[] fields = { "Username:", usernameField, "Password:", passwordField };
                int option = JOptionPane.showConfirmDialog(mainmenu, fields, "Register", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Playerinfos.txt", true))) {
                        writer.write(username + "," + password + "," + 0);
                        writer.newLine();
                        writer.flush();
                        JOptionPane.showMessageDialog(mainmenu, "Registration successful!");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(mainmenu, "Error occurred during registration.");
                    }
                }
            }
        });
		login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField usernameField = new JTextField();
                JPasswordField passwordField = new JPasswordField();
                Object[] fields = { "Username:", usernameField, "Password:", passwordField };
                int option = JOptionPane.showConfirmDialog(mainmenu, fields, "Login", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    try (BufferedReader reader = new BufferedReader(new FileReader("Playerinfos.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] userInfo = line.split(",");
                            String storedUsername = userInfo[0];
                            String storedPassword = userInfo[1];

                            if (username.equals(storedUsername) && password.equals(storedPassword)) {
                                loginCheck = true;
                                activeUser = storedUsername;
                                JOptionPane.showMessageDialog(mainmenu, "Login successful!");
                                break;
                            }
                        }
                        if (loginCheck == false) {
                            JOptionPane.showMessageDialog(mainmenu, "Invalid username or password.");
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(mainmenu, "Error occurred during login.");
                    }
                }
            }
        });
		
		quit.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int option = JOptionPane.showConfirmDialog(mainmenu, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
		        if (option == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
		    }
		});
		
		about.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String aboutMessage = "Developer: Rüştü Yemenici\n"
		                + "University: Yeditepe University\n"
		                + "This is my CSE212 Course Project\n"
		                + "School Email: rustuyemenici@std.yeditepe.edu.tr";
		        JOptionPane.showMessageDialog(mainmenu, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
		highscore.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        gp.gameState = gp.highScoresState;
		    }
		});
		
	}	
		
}