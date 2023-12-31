package BeMyCafe;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

// gameStart클래스는 이미 JFrame의 하위 클래스로 정의되어있으므로
// gameStart 객체가 JFrame 자체
public class GameStart extends JFrame {
	Clip clip;
	private Clip backgroundMusicClip; // 배경음악을 재생할 Clip 객체

	public static int gameScore = 0;

	// 아이스
	JPanel cupPanel;
	JPanel iceCubeInCupPanel;
	JPanel icedWaterInCupPanel;
	JPanel icedAmericanoPanel;
	JPanel icedMilkPanel;
	JPanel icedLattePanel;
	JPanel icedChocoLattePanel;
	JPanel icedMatchaLattePanel;
	// 핫
	JPanel mugPanel;
	JPanel hotWaterPanel;
	JPanel hotMilkPanel;
	JPanel hotAmericanoPanel;
	JPanel hotLattePanel;
	JPanel hotChocoLattePanel;
	JPanel hotMatchaLattePanel;

	// 버튼
	JButton leftbt;
	JButton rightbt;
	JButton submitbt;
	JButton hintbt;
	JButton resetbt;
	JButton bean;
	JButton iceCube;
	JButton icedMilk;
	JButton hotMilk;
	JButton icedWater;
	JButton hotWater;
	JButton chocoPowder;
	JButton matchaPowder;

	BufferedImage img = null;

	// 타이머 관련
	int secondsRemaining;
	Timer timer;
	JLabel timerLabel;

	// 생성자
	public GameStart() {
		GameStartFrame();
		initializePanels();
		cursor();
	}

	// 패널 전환과 스레드에서 쓰임
	private List<JPanel> panels;
	private int currentPanelIndex;

	private void initializePanels() {
		panels = new ArrayList<>();

		panels.add(cupPanel); // 0번부터 패널 인덱스 부여
		panels.add(iceCubeInCupPanel);
		panels.add(icedWaterInCupPanel);
		panels.add(icedAmericanoPanel);
		panels.add(icedMilkPanel);
		panels.add(icedLattePanel);
		panels.add(icedChocoLattePanel);
		panels.add(icedMatchaLattePanel);
		panels.add(mugPanel);
		panels.add(hotWaterPanel);
		panels.add(hotMilkPanel);
		panels.add(hotAmericanoPanel);
		panels.add(hotLattePanel);
		panels.add(hotChocoLattePanel);
		panels.add(hotMatchaLattePanel);

		currentPanelIndex = 0;
	}

	// 인게임 프레임
	public void GameStartFrame() {
		ImageIcon background = new ImageIcon("images/background.png");
		ImageIcon cup = new ImageIcon("images/cup.png");
		ImageIcon mug = new ImageIcon("images/mug.png");
		ImageIcon icecubeincup = new ImageIcon("images/icecubeincup.png");
		ImageIcon icedwaterincup = new ImageIcon("images/icedwaterincup.png");
		ImageIcon icedamericano = new ImageIcon("images/icedamericano.png");
		ImageIcon icedmilkincup = new ImageIcon("images/icedmilkincup.png");
		ImageIcon icedlatte = new ImageIcon("images/icedlatte.png");
		ImageIcon icedchocolatte = new ImageIcon("images/icedchocolatte.png");
		ImageIcon icedmatchalatte = new ImageIcon("images/icedmatchalatte.png");
		ImageIcon hotwater = new ImageIcon("images/hotwater.png");
		ImageIcon hotmilk = new ImageIcon("images/hotmilk.png");
		ImageIcon hotamericano = new ImageIcon("images/hotamericano.png");
		ImageIcon hotlatte = new ImageIcon("images/hotlatte.png");
		ImageIcon hotchocolatte = new ImageIcon("images/hotchocolatte.png");
		ImageIcon hotmatchalatte = new ImageIcon("images/hotmatchalatte.png");

		setTitle("Be My Cafe"); // 프레임 타이틀
		setSize(922, 519); // 프레임 크기
		setResizable(false); // 사이즈 재조정 불가능
		setLocationRelativeTo(null); // 창이 가운데에 뜨도록 함
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 창을 끄면 프로그램을 종료
		setLayout(null);

		JPanel bgPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(background.getImage(), 0, 0, 922, 519, null);
			}
		};
		bgPanel.setBounds(0, 0, 922, 519);
		bgPanel.setLayout(null);

		cupPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(cup.getImage(), 383, 80, 155, 305, null);
			}
		};
		cupPanel.setBounds(0, 0, 922, 519);
		cupPanel.setLayout(null);

		mugPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(mug.getImage(), 368, 135, 185, 210, null);
			}
		};
		mugPanel.setBounds(0, 0, 922, 519);
		mugPanel.setLayout(null);
		mugPanel.setVisible(false);

		iceCubeInCupPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icecubeincup.getImage(), 383, 80, 155, 305, null);
			}
		};
		iceCubeInCupPanel.setBounds(0, 0, 922, 519);
		iceCubeInCupPanel.setLayout(null);
		iceCubeInCupPanel.setVisible(false);

		icedWaterInCupPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icedwaterincup.getImage(), 383, 80, 155, 305, null);
			}
		};
		icedWaterInCupPanel.setBounds(0, 0, 922, 519);
		icedWaterInCupPanel.setLayout(null);
		icedWaterInCupPanel.setVisible(false);

		hotWaterPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(hotwater.getImage(), 368, 135, 185, 210, null);
			}
		};
		hotWaterPanel.setBounds(0, 0, 922, 519);
		hotWaterPanel.setLayout(null);
		hotWaterPanel.setVisible(false);

		icedAmericanoPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icedamericano.getImage(), 383, 80, 155, 305, null);
			}
		};
		icedAmericanoPanel.setBounds(0, 0, 922, 519);
		icedAmericanoPanel.setLayout(null);
		icedAmericanoPanel.setVisible(false);

		hotAmericanoPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(hotamericano.getImage(), 368, 135, 185, 210, null);
			}
		};
		hotAmericanoPanel.setBounds(0, 0, 922, 519);
		hotAmericanoPanel.setLayout(null);
		hotAmericanoPanel.setVisible(false);

		icedMilkPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icedmilkincup.getImage(), 383, 80, 155, 305, null);
			}
		};
		icedMilkPanel.setBounds(0, 0, 922, 519);
		icedMilkPanel.setLayout(null);
		icedMilkPanel.setVisible(false);

		hotMilkPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(hotmilk.getImage(), 368, 135, 185, 210, null);
			}
		};
		hotMilkPanel.setBounds(0, 0, 922, 519);
		hotMilkPanel.setLayout(null);
		hotMilkPanel.setVisible(false);

		icedLattePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icedlatte.getImage(), 383, 80, 155, 305, null);
			}
		};
		icedLattePanel.setBounds(0, 0, 922, 519);
		icedLattePanel.setLayout(null);
		icedLattePanel.setVisible(false);

		icedChocoLattePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icedchocolatte.getImage(), 383, 80, 155, 305, null);
			}
		};
		icedChocoLattePanel.setBounds(0, 0, 922, 519);
		icedChocoLattePanel.setLayout(null);
		icedChocoLattePanel.setVisible(false);

		icedMatchaLattePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icedmatchalatte.getImage(), 383, 80, 155, 305, null);
			}
		};
		icedMatchaLattePanel.setBounds(0, 0, 922, 519);
		icedMatchaLattePanel.setLayout(null);
		icedMatchaLattePanel.setVisible(false);

		hotLattePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(hotlatte.getImage(), 368, 135, 185, 210, null);
			}
		};
		hotLattePanel.setBounds(0, 0, 922, 519);
		hotLattePanel.setLayout(null);
		hotLattePanel.setVisible(false);

		hotChocoLattePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(hotchocolatte.getImage(), 368, 135, 185, 210, null);
			}
		};
		hotChocoLattePanel.setBounds(0, 0, 922, 519);
		hotChocoLattePanel.setLayout(null);
		hotChocoLattePanel.setVisible(false);

		hotMatchaLattePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(hotmatchalatte.getImage(), 368, 135, 185, 210, null);
			}
		};
		hotMatchaLattePanel.setBounds(0, 0, 922, 519);
		hotMatchaLattePanel.setLayout(null);
		hotMatchaLattePanel.setVisible(false);

		// 컵 위치
		add(iceCubeInCupPanel);
		add(icedWaterInCupPanel);
		add(icedAmericanoPanel);
		add(icedMilkPanel);
		add(icedLattePanel);
		add(icedChocoLattePanel);
		add(icedMatchaLattePanel);
		add(hotWaterPanel);
		add(hotMilkPanel);
		add(hotAmericanoPanel);
		add(hotLattePanel);
		add(hotChocoLattePanel);
		add(hotMatchaLattePanel);
		add(cupPanel);
		add(mugPanel);
		add(bgPanel); // 배경 패널 맨 밑에

		// 컵 변경 왼쪽
		leftbt = new JButton(new ImageIcon("images/left.png"));
		leftbt.setBounds(311, 209, 50, 50);
		leftbt.setBorder(null);
		leftbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 0) {
					switchPanel(mugPanel);
				} else if (currentPanelIndex == 8) {
					switchPanel(cupPanel);
				} else if (currentPanelIndex <= 7) {
					switchPanel(mugPanel);
				} else {
					switchPanel(cupPanel);
				}
			}
		});

		bgPanel.add(leftbt);

		// 컵 변경 오른쪽
		rightbt = new JButton(new ImageIcon("images/right.png"));
		rightbt.setBounds(561, 209, 50, 50);
		rightbt.setBorder(null);
		rightbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 0) {
					switchPanel(mugPanel);
				} else if (currentPanelIndex == 8) {
					switchPanel(cupPanel);
				} else if (currentPanelIndex <= 7) {
					switchPanel(mugPanel);
				} else {
					switchPanel(cupPanel);
				}
			}
		});

		bgPanel.add(rightbt);

		// 제조 완료 버튼
		submitbt = new JButton(new ImageIcon("images/submit.png"));
		submitbt.setBounds(340, 408, 241, 71);
		submitbt.setBorder(null);
		submitbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex <= 7) {
					switchPanel(cupPanel);
				} else {
					switchPanel(mugPanel);
				}
			}
		});

		bgPanel.add("Center", submitbt);

		// 하단 힌트
		GameStartPanel hint = new GameStartPanel();

		hint.setBounds(0, 0, 922, 519);

		hintbt = new JButton(new ImageIcon("images/hint.png"));
		hintbt.setBounds(850, 425, 55, 55);
		hintbt.setBorder(null);

		bgPanel.add("Center", hintbt);

		// 하단 리셋
		resetbt = new JButton(new ImageIcon("images/resetbt.png"));
		resetbt.setBounds(15, 425, 55, 55);
		resetbt.setBorder(null);
		resetbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex <= 7) {
					switchPanel(cupPanel);
				} else {
					switchPanel(mugPanel);
				}
			}
		});

		bgPanel.add("Center", resetbt);

		// 인게임 타이머
		TimerPanel timerPanel = new TimerPanel(1, 30);
		JPanel timerPanelComponent = timerPanel.createTimerPanel();
		timerPanelComponent.setBounds(10, 10, 110, 50);
		bgPanel.add(timerPanelComponent);

		// 인게임 스코어
		ScorePanel scorePanel = new ScorePanel();
		JPanel scorePanelComponent = scorePanel.createScorePanel();
		scorePanelComponent.setBounds(406, 10, 110, 50);
		bgPanel.add(scorePanelComponent);

		// 인게임 메뉴판
		String[] strings = { "따뜻한 아메리카노", "차가운 아메리카노", "따뜻한 카페 라떼", "차가운 카페 라떼", "따뜻한 초코 라떼", "차가운 초코 라떼", "따뜻한 녹차 라떼",
				"차가운 녹차 라떼" };
		RandomStringPanel menu = new RandomStringPanel(strings);
		JPanel randomStringPanel = menu.createRandomStringPanel();
		randomStringPanel.setBounds(802, 10, 110, 50);
		bgPanel.add(randomStringPanel);

		submitbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nowMenu = menu.getCurrentRandomString();
				if (nowMenu.equals("차가운 아메리카노") && icedAmericanoPanel.isVisible()) {
					gameScore += 50;
				} else if (nowMenu.equals("따뜻한 아메리카노") && hotAmericanoPanel.isVisible()) {
					gameScore += 50;
				} else if (nowMenu.equals("차가운 카페 라떼") && icedLattePanel.isVisible()) {
					gameScore += 50;
				} else if (nowMenu.equals("따뜻한 카페 라떼") && hotLattePanel.isVisible()) {
					gameScore += 50;
				} else if (nowMenu.equals("차가운 초코 라떼") && icedChocoLattePanel.isVisible()) {
					gameScore += 50;
				} else if (nowMenu.equals("따뜻한 초코 라떼") && hotChocoLattePanel.isVisible()) {
					gameScore += 50;
				} else if (nowMenu.equals("차가운 녹차 라떼") && icedMatchaLattePanel.isVisible()) {
					gameScore += 50;
				} else if (nowMenu.equals("따뜻한 녹차 라떼") && hotMatchaLattePanel.isVisible()) {
					gameScore += 50;
				}
			}
		});

		// 힌트 버튼 클릭
		hintbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				popup();
			}
		});

		bean = new JButton(new ImageIcon("images/beanbt.png"));
		bean.setBounds(150, 70, 80, 80);
		bean.setBorder(null);
		bean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 2) {
					switchPanel(icedAmericanoPanel);
				} else if (currentPanelIndex == 4) {
					switchPanel(icedLattePanel);
				} else if (currentPanelIndex == 9) {
					switchPanel(hotAmericanoPanel);
				} else if (currentPanelIndex == 10) {
					switchPanel(hotLattePanel);
				} else {
					playSound("bgm/wrong.wav");
					bean.setEnabled(false);
					movePanelWithThread(bean, panels.get(currentPanelIndex), 5);
				}
			}
		});

		bgPanel.add(bean);

		iceCube = new JButton(new ImageIcon("images/icescoop.png"));
		iceCube.setBounds(75, 160, 190, 120); // 버튼 위치와 크기 설정
		iceCube.setBorder(null);
		iceCube.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 0) {
					switchPanel(iceCubeInCupPanel);
				} else {
					playSound("bgm/wrong.wav");
					iceCube.setEnabled(false);
					movePanelWithThread(iceCube, panels.get(currentPanelIndex), 5);
				}
			}
		});

		bgPanel.add(iceCube);

		icedMilk = new JButton(new ImageIcon("images/icedmilk.png"));
		icedMilk.setBounds(625, 260, 80, 160);
		icedMilk.setBorder(null);
		icedMilk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 1) {
					switchPanel(icedMilkPanel);
				} else {
					playSound("bgm/wrong.wav");
					icedMilk.setEnabled(false);
					movePanelWithThread(icedMilk, panels.get(currentPanelIndex), 5);
				}
			}
		});

		bgPanel.add(icedMilk);

		hotMilk = new JButton(new ImageIcon("images/pitcher.png"));
		hotMilk.setBounds(740, 240, 150, 170);
		hotMilk.setBorder(null);
		hotMilk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 8) {
					switchPanel(hotMilkPanel);
				} else {
					playSound("bgm/wrong.wav");
					hotMilk.setEnabled(false);
					movePanelWithThread(hotMilk, panels.get(currentPanelIndex), 5);
				}
			}
		});

		bgPanel.add(hotMilk);

		icedWater = new JButton(new ImageIcon("images/icedwater.png"));
		icedWater.setBounds(600, 120, 130, 110);
		icedWater.setBorder(null);
		icedWater.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 1) {
					switchPanel(icedWaterInCupPanel);
				} else {
					playSound("bgm/wrong.wav");
					icedWater.setEnabled(false);
					movePanelWithThread(icedWater, panels.get(currentPanelIndex), 5);
				}
			}
		});

		bgPanel.add(icedWater);

		hotWater = new JButton(new ImageIcon("images/boiledwater.png"));
		hotWater.setBounds(710, 60, 200, 170);
		hotWater.setBorder(null);
		hotWater.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 8) {
					switchPanel(hotWaterPanel);
				} else {
					playSound("bgm/wrong.wav");
					hotWater.setEnabled(false);
					movePanelWithThread(hotWater, panels.get(currentPanelIndex), 5);
				}
			}
		});

		bgPanel.add(hotWater);

		chocoPowder = new JButton(new ImageIcon("images/chocopowderspoon.png"));
		chocoPowder.setBounds(20, 300, 150, 75);
		chocoPowder.setBorder(null);
		chocoPowder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 4) {
					switchPanel(icedChocoLattePanel);
				} else if (currentPanelIndex == 10) {
					switchPanel(hotChocoLattePanel);
				} else {
					playSound("bgm/wrong.wav");
					chocoPowder.setEnabled(false);
					movePanelWithThread(chocoPowder, panels.get(currentPanelIndex), 5);
				}
			}
		});

		bgPanel.add(chocoPowder);

		matchaPowder = new JButton(new ImageIcon("images/matchapowderspoon.png"));
		matchaPowder.setBounds(185, 300, 150, 75);
		matchaPowder.setBorder(null);
		matchaPowder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.close();
				}
				playSound("bgm/buttonclick.wav"); // 효과음 재생
				if (currentPanelIndex == 4) {
					switchPanel(icedMatchaLattePanel);
				} else if (currentPanelIndex == 10) {
					switchPanel(hotMatchaLattePanel);
				} else {
					playSound("bgm/wrong.wav");
					matchaPowder.setEnabled(false);
					movePanelWithThread(matchaPowder, panels.get(currentPanelIndex), 5);
				}
			}
		});

		bgPanel.add(matchaPowder);

		setVisible(true);
	}

	// 패널 전환
	private void switchPanel(JPanel newPanel) {
		panels.get(currentPanelIndex).setVisible(false);
		newPanel.setVisible(true);
		currentPanelIndex = panels.indexOf(newPanel);
	}

	// 패널 흔들림 효과 스레드
	private static void movePanelWithThread(JButton button, JPanel panel, int xOffset) {
		new Thread(() -> {
			movePanel(panel, xOffset); // 패널 x축 이동
			sleep(50); // 0.05초 sleep
			movePanel(panel, -xOffset);
			sleep(50);
			movePanel(panel, -xOffset);
			sleep(50);
			movePanel(panel, xOffset);

			SwingUtilities.invokeLater(() -> {
				button.setEnabled(true);
			});
		}).start();
	}

	// 패널 흔들림 효과 함수
	private static void movePanel(JPanel panel, int xOffset) {
		SwingUtilities.invokeLater(() -> {
			panel.setLocation(panel.getLocation().x + xOffset, panel.getLocation().y);
		});
	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 커서 변경
	public void cursor() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("images/cursor.png");
		Point point = new Point(10, 10);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "");

		this.setCursor(cursor);
	}

	class GameStartPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	// 타이머 패널
	class TimerPanel {
		private int secondsRemaining;
		private Timer timer;
		private JLabel timerLabel;
		private ImageIcon image = new ImageIcon("images/label.png");

		public TimerPanel(int minutes, int seconds) {
			playBackgroundMusic("bgm/gbgm.wav"); // 배경음악 설정
			secondsRemaining = minutes * 60 + seconds; // 분을 초로 변환하여 더한다
			timerLabel = new JLabel(secondsRemaining / 60 + "분 " + secondsRemaining % 60 + "초 ");

			timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					secondsRemaining--;
					timerLabel.setText(secondsRemaining / 60 + "분 " + secondsRemaining % 60 + "초 ");
					if (secondsRemaining <= 0) {
						timer.stop();
						setVisible(false);
						stopBackgroundMusic();
						new GameOver();
					}
				}
			});
			timer.start(); // 타이머 자동 시작
		}

		public JPanel createTimerPanel() {
			JPanel panel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(image.getImage(), 0, 0, 110, 50, null);
				}
			};

			// 폰트
			Font font = null;

			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("font/Goyang.ttf")).deriveFont(font.BOLD, 15);
			} catch (FontFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Color fontColor = new Color(62, 49, 10);

			timerLabel.setBounds(28, 23, 60, 15);
			timerLabel.setFont(font);
			timerLabel.setForeground(fontColor);

			panel.setLayout(null);
			panel.add(timerLabel);

			return panel;
		}
	}

	private void playBackgroundMusic(String musicFilePath) {
		try {
			File audioFile = new File(musicFilePath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

			// Clip 생성 및 열기
			backgroundMusicClip = AudioSystem.getClip();
			backgroundMusicClip.open(audioStream);
			backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // 배경음악 반복 재생
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopBackgroundMusic() {
		if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
			backgroundMusicClip.stop(); // 배경음악 정지
		}
	}

	// 스코어 패널
	class ScorePanel {
		private JLabel scoreLabel;
		private ImageIcon image = new ImageIcon("images/label.png");

		public ScorePanel() {
			scoreLabel = new JLabel("현재 점수 " + GameStart.gameScore + "점");
			createScorePanel();
		}

		public void setScoreLabel() {
			scoreLabel.setText("현재 점수 " + GameStart.gameScore + "점");
		}

		public JPanel createScorePanel() {
			JPanel panel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(image.getImage(), 0, 0, 110, 50, null);
				}
			};

			submitbt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setScoreLabel();
				}
			});

			// 폰트
			Font font = null;

			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("font/Goyang.ttf")).deriveFont(font.BOLD, 15);
			} catch (FontFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Color fontColor = new Color(62, 49, 10);

			scoreLabel.setBounds(10, 5, 110, 50);
			scoreLabel.setFont(font);
			scoreLabel.setForeground(fontColor);

			panel.setLayout(null);
			panel.add(scoreLabel);
			return panel;
		}
	}

	// 메뉴 패널
	class RandomStringPanel {
		private String[] strings;
		private JLabel outputLabel;
		private ImageIcon image = new ImageIcon("images/label.png");

		public RandomStringPanel(String[] strings) {
			this.strings = strings;
			outputLabel = new JLabel("");
			createRandomStringPanel();
		}

		public void generateRandomString() {
			Random random = new Random();
			int randomIndex = random.nextInt(strings.length);
			String randomString = strings[randomIndex];
			outputLabel.setText(randomString);
		}

		public String getCurrentRandomString() {
			return outputLabel.getText();
		}

		public JPanel createRandomStringPanel() {
			JPanel panel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(image.getImage(), 0, 0, 110, 50, null);
				}
			};

			generateRandomString();

			submitbt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					generateRandomString();
				}
			});

			// 폰트
			Font font = null;

			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("font/Goyang.ttf")).deriveFont(font.BOLD, 12);
			} catch (FontFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Color fontColor = new Color(62, 49, 10);

			outputLabel.setBounds(13, 5, 110, 50);
			outputLabel.setFont(font);
			outputLabel.setForeground(fontColor);

			panel.setLayout(null); // 레이아웃을 null로 설정
			panel.add(outputLabel);

			return panel;
		}
	}

	// 팝업창
	public void popup() {
		try {
			// 이미지 파일 불러오기
			BufferedImage image = ImageIO.read(new File("images/recipe.png")); // 팝업 배경 이미지

			// 이미지를 화면에 표시
			JFrame hintFrame = new JFrame();

			hintFrame.setSize(505, 635);
			hintFrame.setResizable(false);
			hintFrame.setLocationRelativeTo(null);
			hintFrame.getContentPane().add(new JLabel(new ImageIcon(image)));
			hintFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void playSound(String soundFilePath) {
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
}