package Board;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Block.Block;
import Client.*;

public class BoardManager extends JFrame {
	public MyBoard myboard;
	public OtherBoard otherboard;
	public MainBoard mainboard;
	public String newBlockInfo;
	
	public BoardManager(String sbstring, String mbstring) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1100, 700);
		setTitle("Davinci Code : Client");
		
		otherboard = new OtherBoard(getBlockArray(sbstring));
		mainboard = new MainBoard(getBlockArray(mbstring));
		Block[] blocks = mainboard.getBlockSet(3);
		myboard = new MyBoard(blocks);
		

		getContentPane().add(myboard, BorderLayout.SOUTH);
		getContentPane().add(otherboard, BorderLayout.NORTH);
		getContentPane().add(mainboard, BorderLayout.CENTER);

		myboard.add(myboard.showBoard());
		otherboard.add(otherboard.showBoard());
		mainboard.add(mainboard.showBoard());
	}

	public boolean getNewBlock() {
		Board.myTurn=true;
		Timer t = new Timer(true);
		NewBlock my = new NewBlock();
		t.schedule(my, 3000);
		
		mainboard.updateScreen();
		myboard.updateScreen();
		return true;
	}

	public String myGuess() {
		boolean isOpen = otherboard.Guess();
		if (isOpen) {
			otherboard.updateScreen();
		}
		return otherboard.getSelection();
	}

	public String getResult() {
		return otherboard.getResult();
	}

	class NewBlock extends TimerTask {
		public void run() {
			Block newBlock;

			newBlock = mainboard.getNewBlock();
			myboard.updateUI();
			if (newBlock == null) {
				newBlock = mainboard.getRandomBlock();
			}

			if (newBlock.getNum() == 12) {
				myboard.addJoker(newBlock);
				myboard.updateScreen();
			} else {
				myboard.set.add(newBlock);
				myboard.updateScreen();
			}
			newBlockInfo = myboard.set.getBlockInfo(newBlock);
		}
	}

	private ArrayList<Block> getBlockArray(String st) {
		String[] block;
		ArrayList<Block> blocks = new ArrayList<Block>();
		block = st.split(" ");
		for (int i = 0; i < block.length; i++) {
			String c, color;
			String num;
			double numainboarder;
			c = block[i].split("_")[0];
			if (c.equals("b")) {
				color = "black";
			} else
				color = "white";
			num = block[i].split("_")[1].trim();
			numainboarder = Double.parseDouble(num);
			blocks.add(new Block(color, numainboarder));
		}
		return blocks;
	}

	
	public void addBlock(String newblock){
		int loc;
		String color;
		double num;
		loc=Integer.parseInt(newblock.split("_")[0]);
		color=newblock.split("_")[1];
		num=Double.parseDouble(newblock.split("_")[2]);
		Block block = new Block(color,num);
		otherboard.addBlock(loc, block);
		if(block.isJoker()){
			block = new Block(block.getColor(),12);
		}
		mainboard.removeBlock(block);		
	}
	

	public boolean checkGameOver() {
		if(otherboard.isAllBlockOpen()){
			System.out.print("Win");
			JOptionPane.showMessageDialog(null, "Win");
			return true;
		}
		if(myboard.isAllBlockOpen()){
			System.out.print("Lose");
			JOptionPane.showMessageDialog(null, "Lose");
			return true;
		}
		return false;
	}

}
