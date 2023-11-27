package BeMyCafe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import javax.swing.*;
import java.io.IOException;

public class GameOver extends JFrame {
	Clip clip;
	JButton restartbt;

	public GameOver() {
		GameOverFrame();
		cursor();
	}

	// 최고 점수 !!
	JLabel highestScoreLabel;

	public int getHighestScore() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/score_db?serverTimezone=UTC",
				"root", "1234")) {
			System.out.println("데이터베이스 연결 성공");

			String selectHighestScoreSQL = "SELECT MAX(score) AS highestScore FROM scores";
			try (PreparedStatement selectStatement = conn.prepareStatement(selectHighestScoreSQL);
					ResultSet resultSet = selectStatement.executeQuery()) {

				if (resultSet.next()) {
					return resultSet.getInt("highestScore");
				}

			} catch (Exception ex) {
				System.out.println("오류:" + ex);
			}
		} catch (Exception ex) {
			System.out.println("오류:" + ex);
		}

		return 0;
	}

	public void GameOverFrame() {
		ImageIcon score = new ImageIcon("images/score.png");
		restartbt = new JButton(new ImageIcon("images/restart-bt.png"));

		Font font = null;
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font/Goyang.ttf")).deriveFont(Font.BOLD, 28);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Color fontColor = new Color(62, 49, 10);

		setTitle("Be My Cafe");
		setSize(922, 519);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

		JPanel Panel1 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(score.getImage(), 0, 0, 922, 519, null);
			}
		};
		Panel1.setLayout(null);
		Panel1.setBounds(0, 0, 922, 519);

		try {
			File musicFile = new File("bgm/bbgm.wav");

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

		restartbt.setBounds(340, 358, 245, 101);
		restartbt.setBorder(null);
		Panel1.add(restartbt);

		JLabel fscore = new JLabel("최종 스코어");
		fscore.setBounds(340, 120, 241, 30);
		fscore.setFont(font);
		fscore.setHorizontalAlignment(JLabel.CENTER);
		fscore.setForeground(fontColor);
		Panel1.add(fscore);

		JLabel gameScoreLabel = new JLabel(GameStart.gameScore + " 점");
		gameScoreLabel.setBounds(340, 160, 241, 30);
		gameScoreLabel.setFont(font);
		gameScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		gameScoreLabel.setForeground(fontColor);
		Panel1.add(gameScoreLabel);

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/score_db?serverTimezone=UTC",
				"root", "1234")) {
			System.out.println("데이터베이스 연결 성공");

			String insertScoreSQL = "INSERT INTO scores (score) VALUES (?)";
			try (PreparedStatement scoreStatement = conn.prepareStatement(insertScoreSQL)) {
				scoreStatement.setInt(1, GameStart.gameScore); // 따로 저장한 현재 점수 사용
				scoreStatement.executeUpdate();
				System.out.println("데이터베이스에 점수가 저장되었습니다.");
			}

			conn.close();
		} catch (Exception ex) {
			System.out.println("오류:" + ex);
		}

		JLabel hscore = new JLabel("최고 스코어");
		hscore.setBounds(340, 240, 241, 30);
		hscore.setFont(font);
		hscore.setHorizontalAlignment(JLabel.CENTER);
		hscore.setForeground(fontColor);
		Panel1.add(hscore);

		JLabel highestScoreLabel = new JLabel(getHighestScore() + " 점");
		highestScoreLabel.setBounds(340, 280, 241, 30);
		highestScoreLabel.setFont(font);
		highestScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		highestScoreLabel.setForeground(fontColor);
		Panel1.add(highestScoreLabel);

		restartbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				setVisible(false);
				GameStart.gameScore = 0; // 리스타트 버튼을 누를 때 현재 점수 초기화
				new GameStart();
			}
		});

		add(Panel1);
		setVisible(true);
	}

	// 버튼 조작음 재생
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

	public void cursor() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("images/cursor.png");
		Point point = new Point(10, 10);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "");

		this.setCursor(cursor);
	}
}