package Server;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import Board.*;


public class player extends Thread {
	static BoardManager mng;
	static boolean isMyTurn = true;
	static boolean myTurnStart = true, otherTurnStart = true;
	
	static JFrame menu;
	static player playgame = new player();

	public static void main(String[] args) {
		mainMenu();
		try {
			ServerSocket ss = new ServerSocket(1000);
			System.out.println("Waiting Connect...");

			Socket sock = ss.accept();
			sendBoards(sock);
			System.out.print("Board Sent\n");
			sock = ss.accept();
			receiveBoard(sock);
			System.out.print("Board Received\n");
			while (!GameOver()) {
				sock = ss.accept();
				if (isMyTurn) {
					System.out.println("my turn");
					run(sock);
				} else {
					System.out.println("your turn");
					wait(sock);
				}
				
				sock.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static boolean GameOver() {
		return mng.checkGameOver();
	}

	public static void run(Socket sock) throws IOException,
			InterruptedException {
		OutputStream out = sock.getOutputStream();
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

		if (myTurnStart) {
			JOptionPane.showMessageDialog(null, "It's My Turn");
			playgame.getNewBlock();
			playgame.join();
			myTurnStart = false;
			System.out.println(mng.newBlockInfo);
			pw.println(mng.newBlockInfo);
			pw.flush();
		}

		playgame.myGuess();
		playgame.join();
		
		System.out.println("my Guess : " + mng.myGuess());
		pw.println(mng.getResult() + "_" + mng.myGuess());
		pw.flush();
		pw.close();
	}

	public static void wait(Socket sock) throws IOException,
			ClassNotFoundException {
		String line;
		InputStream in = sock.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		if (otherTurnStart) {
			line = br.readLine(); // new Block
			System.out.println(line);
			mng.addBlock(line);
			otherTurnStart = false;			
		}
		
		line = br.readLine();
		System.out.println(line);
		String result = line.split("_")[0];
		String block = line.split("_")[1];
		if (result.equals("incorrect")){
			isMyTurn = true;
			myTurnStart = true;
		}else{
			int loc = Integer.parseInt(block.split(" ")[0]);
			String num = block.split(" ")[1];
			mng.my.openBlock(loc, num);
		}
		br.close();
	}

	public void getNewBlock() throws InterruptedException {
		mng.getNewBlock();
		while (mng.newBlockInfo == null) {
			sleep(100);
		}
	}

	public void myGuess() throws InterruptedException {
		while (!mng.other.isSelected()) {
			sleep(100);
		};
		if (mng.getResult().equalsIgnoreCase("incorrect")) {
			System.out.println("End my Turn");
			isMyTurn = false;
			otherTurnStart = true;
		}
	}

	public static void sendBoards(Socket sock) throws IOException {

		OutputStream out = sock.getOutputStream();
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

		String mb = mng.mb.getMessage();
		String sb = mng.my.getMessage();

		pw.println(mb);
		pw.flush();
		pw.println(sb);
		pw.flush();
		pw.close();
	}

	public static void receiveBoard(Socket sock) throws IOException {
		InputStream in = sock.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line = br.readLine();
		mng.setOtherBoard(line);
		sock.close();
	}

	public static void mainMenu() {
		menu = new JFrame();
		menu.setTitle("Davinchi Code");
		// menu.setLocation(10000, 50000);
		menu.setResizable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension sc = tk.getScreenSize();

		JPanel backgroundPanel = new JPanel() {
			public void paint(Graphics g) {
				ImageIcon icon = new ImageIcon(getClass().getResource(
						"../images/Davinchi.PNG"));
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
				mng = new BoardManager();
				mng.setVisible(true);
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
						"../images/rule.png")));

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
}
