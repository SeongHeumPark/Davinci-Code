package Board;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Block.Block;
import Server.*;

public class BoardManager extends JFrame {

	public MainBoard mb;
	public MyBoard my;
	public OtherBoard other;
	public String newBlockInfo;

	public BoardManager() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1100, 700);
		setTitle("Davinci Code : Server");

		my = new MyBoard(3);
		mb = new MainBoard();

		getContentPane().add(my, BorderLayout.SOUTH);
		getContentPane().add(mb, BorderLayout.CENTER);

		my.add(my.showBoard());
		mb.add(mb.showBoard());

	}

	public boolean getNewBlock() {
		Board.myTurn = true;
		Timer t = new Timer(true);
		NewBlock myturn = new NewBlock();		
		t.schedule(myturn, 3000);
		
		mb.updateScreen();
		my.updateScreen();
		return true;
	}

	public String myGuess() {
		boolean isOpen = other.Guess();
		if (isOpen) {
			other.updateScreen();
		}
		return other.getSelection();
	}
	
	public String getResult(){
		return other.getResult();
	}

	class NewBlock extends TimerTask {
		public void run() {
			Block newBlock;

			newBlock = mb.getNewBlock();
			my.updateUI();
			if (newBlock == null) {
				newBlock = mb.getRandomBlock();
			}

			if (newBlock.getNum() == 12) {
				my.addJoker(newBlock);
				my.updateScreen();
			} else {
				my.set.add(newBlock);
				my.updateScreen();
			}
			newBlockInfo = my.set.getBlockInfo(newBlock);
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
		other.addBlock(loc, block);
		if(block.isJoker()){
			block = new Block(block.getColor(),12);
		}
		mb.removeBlock(block);		
	}
	
	public void setOtherBoard(String line) {
		ArrayList<Block> blocks = getBlockArray(line);
		Block block;
		other = new OtherBoard(blocks);
		for(int i=0;i<blocks.size();i++){
			System.out.println(blocks.size());
			block = blocks.get(i);
			if(block.isJoker()){
				block = new Block(block.getColor(),12);
			}
			mb.removeBlock(block);
		}
		getContentPane().add(other, BorderLayout.NORTH);
		other.add(other.showBoard());
		other.updateScreen();
		other.updateUI();
	}
	
	public boolean checkGameOver() {
		if(other.isAllBlockOpen()){
			System.out.print("Win");
			JOptionPane.showMessageDialog(null, "Win");
			return true;
		}
		if(my.isAllBlockOpen()){
			System.out.print("Lose");
			JOptionPane.showMessageDialog(null, "Lose");
			return true;
		}
		return false;
	}
}
