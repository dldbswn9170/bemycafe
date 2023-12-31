package BeMyCafe;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BeMyCafe extends JFrame {
	JButton startbt;
	JButton nextbt;
	JButton nextbt1;
	JButton nextbt2;
	JButton backbt;
	JButton backbt1;
	JButton backbt2;
	BufferedImage img = null;

	public BeMyCafe() {
		BeMyCafeFrame();
		cursor();
		playBackgroundMusic("bgm/bbgm.wav");
	}

	public void cursor() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("images/cursor.png");
		Point point = new Point(10, 10);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "");

		this.setCursor(cursor);
	}

	public void BeMyCafeFrame() {
		ImageIcon main = new ImageIcon("images/main.png");
		ImageIcon mainGame = new ImageIcon("images/main-game.png");
		ImageIcon mainItem = new ImageIcon("images/main-item.png");
		ImageIcon mainRecipe = new ImageIcon("images/main-recipe.png");

		setTitle("Be My Cafe"); // 프레임 타이틀
		setSize(922, 519); // 프레임 크기
		setResizable(false); // 사이즈 재조정 불가능
		setLocationRelativeTo(null); // 창이 가운데에 뜨도록 함
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 창을 끄면 프로그램을 종료
		setLayout(null);

		JPanel mainPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(main.getImage(), 0, 0, 922, 519, null);
			}
		};
		mainPanel.setBounds(0, 0, 922, 519);
		mainPanel.setLayout(null);

		startbt = new JButton(new ImageIcon("images/start-bt.png"));
		startbt.setBounds(291, 358, 341, 101);
		startbt.setBorder(null);

		mainPanel.add("Center", startbt);

		JPanel mainGamePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(mainGame.getImage(), 0, 0, 922, 519, null);
			}
		};
		mainGamePanel.setBounds(0, 0, 922, 519);
		mainGamePanel.setLayout(null);
		mainGamePanel.setVisible(false);

		nextbt = new JButton(new ImageIcon("images/nextbt.png"));
		nextbt.setBounds(847, 419, 50, 40);
		nextbt.setBorder(null);
		mainGamePanel.add(nextbt);

		JPanel mainItemPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(mainItem.getImage(), 0, 0, 922, 519, null);
			}
		};
		mainItemPanel.setBounds(0, 0, 922, 519);
		mainItemPanel.setLayout(null);
		mainItemPanel.setVisible(false);

		nextbt1 = new JButton(new ImageIcon("images/nextbt.png"));
		nextbt1.setBounds(847, 419, 50, 40);
		nextbt1.setBorder(null);
		mainItemPanel.add(nextbt1);

		backbt1 = new JButton(new ImageIcon("images/backbt.png"));
		backbt1.setBounds(50, 419, 50, 40);
		backbt1.setBorder(null);
		mainItemPanel.add(backbt1);

		JPanel mainRecipePanel = new JPanel() {
			public void paintComponent(Graphics g) {

				g.drawImage(mainRecipe.getImage(), 0, 0, 922, 519, null);
			}
		};
		mainRecipePanel.setBounds(0, 0, 922, 519);
		mainRecipePanel.setLayout(null);
		mainRecipePanel.setVisible(false);

		nextbt2 = new JButton(new ImageIcon("images/nextbt.png"));
		nextbt2.setBounds(847, 419, 50, 40);
		nextbt2.setBorder(null);
		mainRecipePanel.add(nextbt2);

		backbt2 = new JButton(new ImageIcon("images/backbt.png"));
		backbt2.setBounds(50, 419, 50, 40);
		backbt2.setBorder(null);
		mainRecipePanel.add(backbt2);

		startbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				mainPanel.setVisible(false);
				mainGamePanel.setVisible(true);
			}
		});

		nextbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				mainGamePanel.setVisible(false);
				mainItemPanel.setVisible(true);
			}
		});

		nextbt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				mainItemPanel.setVisible(false);
				mainRecipePanel.setVisible(true);
			}
		});

		nextbt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				stopBackgroundMusic();
				setVisible(false);
				new GameStart();
			}
		});

		backbt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				mainItemPanel.setVisible(false);
				mainGamePanel.setVisible(true);
			}
		});

		backbt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				mainRecipePanel.setVisible(false);
				mainItemPanel.setVisible(true);
			}
		});

		add(mainRecipePanel);
		add(mainItemPanel);
		add(mainGamePanel);
		add(mainPanel);

		setVisible(true); // 화면에 프레임 출력
	}

//버튼 조작음 재생
	private void playSound(String soundFilePath) {
		try {
			File soundFile = new File(soundFilePath);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

			Clip soundClip = AudioSystem.getClip();
			soundClip.open(audioInputStream);
			soundClip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Clip clip;

	public static void playBackgroundMusic(String musicFilePath) {
		try {
			File musicFile = new File(musicFilePath);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);

			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);

			clip.addLineListener(event -> {
				if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP)
					clip.close();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stopBackgroundMusic() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
		}
	}

	public static void main(String[] args) {
		new BeMyCafe();
	}
}