package Client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Board.BoardManager;

public class Player extends Thread {
	static BoardManager mng;
	private static boolean isMyTurn = false;
	private static boolean myTurnStart = true;
	private static boolean otherTurnStart = true;
	static InetAddress addr;
	static Socket sock;
	static String sb, mb, cb;
	static String line;
	static boolean done = false;
	static Player player = new Player();

	public static void main(String[] args) throws InterruptedException {
		player.mainMenu();
		player.checkSetting();
		player.join();
		player.PlayGame();
	}

	public void getNewBlock() throws InterruptedException {
		mng.getNewBlock();
		while (mng.newBlockInfo == null) {
			sleep(100);
		}
	}

	public void myGuess() throws InterruptedException {
		while (!mng.otherboard.isSelected()) {
			sleep(100);
		}
		if (mng.getResult().equalsIgnoreCase("incorrect")) {
			isMyTurn = false;
			otherTurnStart = true;
		}
		line = mng.myGuess();
		System.out.println("myguess : " + line);
		System.out.println("result : " + mng.getResult());
	}

	private static boolean GameOver() {
		return mng.checkGameOver();
	}
	public void mainMenu() {
		final JFrame menu = new JFrame();
		menu.setTitle("Davinchi Code");
		// menu.setLocation(10000, 50000);
		menu.setResizable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension sc = tk.getScreenSize();

		try {
			addr = InetAddress.getLocalHost();
			sock = new Socket(addr.getHostAddress(), 1000);
			InputStream in = sock.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			mb = br.readLine();
			sb = br.readLine();

		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JPanel backgroundPanel = new JPanel() {
			public void paint(Graphics g) {

				ImageIcon icon = new ImageIcon(getClass().getResource(
						"../images/Davinchi.png"));
				g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(),
						this);
				setOpaque(false);// �����ϰ�
				super.paint(g);
			}
		};

		backgroundPanel.setLayout(null);

		backgroundPanel.setBounds(0, 0, 500, 500);

		JButton startButton = new JButton("Start");
		JButton helpButton = new JButton("Help");

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startGame();
				System.out.println("done startgame");
				menu.dispose();
			}
		});

		helpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame help = new JFrame();
				Toolkit tk = Toolkit.getDefaultToolkit();
				Dimension sc = tk.getScreenSize();
				help.setBounds(sc.width / 2 - 250, sc.height / 2 - 250, 500,
						500);
				JLabel helpLabel = new JLabel();

				helpLabel.setIcon(new ImageIcon(getClass().getResource(
						"../images/Davinchicode rule.png")));

				help.add(helpLabel);
				help.setVisible(true);
				help.setAlwaysOnTop(true);
			}
		});

		startButton.setBounds(170, 300, 160, 30);

		helpButton.setBounds(170, 350, 160, 30);

		backgroundPanel.add(startButton);
		backgroundPanel.add(helpButton);

		menu.setBounds(sc.width / 2 - 250, sc.height / 2 - 250, 500, 500);

		menu.add(backgroundPanel);

		menu.setVisible(true);
	}

	public static boolean startGame() {

		System.out.println("in startgame");
		mng = new BoardManager(sb, mb);
		mng.setVisible(true);
		done = true;

		return true;
	}

	public void run(Socket sock) throws IOException, InterruptedException {
		OutputStream out = sock.getOutputStream();
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
		if (myTurnStart) {
			JOptionPane.showMessageDialog(null, "myturn");
			player.getNewBlock();
			player.join();
			System.out.println(mng.newBlockInfo);
			pw.println(mng.newBlockInfo);
			pw.flush();
			myTurnStart = false;
		}

		player.myGuess();
		player.join();

		pw.println(mng.getResult() + "_" + mng.myGuess());
		pw.flush();
		pw.close();
	}

	public void wait(Socket sock) throws IOException, ClassNotFoundException {
		InputStream in = sock.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		if (otherTurnStart) {
			line = br.readLine(); // new Block
			mng.addBlock(line);
			otherTurnStart = false;
		}

		line = br.readLine();
		System.out.println(line);
		String result = line.split("_")[0];
		String block = line.split("_")[1];
		if (result.equals("incorrect")) {
			isMyTurn = true;
			myTurnStart = true;
		}else{
			int loc = Integer.parseInt(block.split(" ")[0]);
			String num = block.split(" ")[1];
			mng.myboard.openBlock(loc, num);
		}
		br.close();
	}

	public void PlayGame() {
		try {
			addr = InetAddress.getLocalHost();

			while (!GameOver()) {
				try {
					sock = new Socket(addr.getHostAddress(), 1000);
					if (isMyTurn) {
						System.out.println("My turn");
						run(sock);
					} else {
						System.out.println("wating");
						wait(sock);
					}
					sock.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}

	public void checkSetting() throws InterruptedException {
		while (!done) {
			sleep(100);
		}
		while (!mng.myboard.init) {
			sleep(100);
		}

		System.out.println("done checksetting");
		mng.myboard.sendBoard();
	}

}