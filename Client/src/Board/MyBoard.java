package Board;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import Block.*;

public class MyBoard extends PlayBoard {
	public boolean init=false;
	public int numberOfJoker,setjokerDone=0;
	public MyBoard(Block[] newblocks) {
		int i, j = 0;
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block Joker[] = new Block[2];

		Block block;
		for (i = 0; i < newblocks.length; i++) {
			block = newblocks[i];
			if (block.getNum() == 12.0) {
				Joker[j++] = block;
			} else
				blocks.add(block);
		}
		set = new SetOfBlocks(blocks);
		set.sorting();
		if (j != 0) {
			numberOfJoker=j;
			for (int k = 0; k < j; k++) {
				addJoker(Joker[k]);
			}
		}else{
			init=true;
		}
	}
	
	public JPanel showBoard() {
		ImageIcon icon;
		String[] blocks;

		JPanel npanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		blocks = set.show();
		for (int i = 0; i < set.getNumberOfBlocks(); i++) {
			icon = new ImageIcon(getClass().getResource(blocks[i]));
			MyLabel item = new MyLabel(icon, i) {
				@Override
				void iconClicked() {}
			};
			if(set.getOpenState(i)==true){
				item.add(new JLabel("\n"),BorderLayout.SOUTH);
			}
			// item.
			npanel.add(item);
		}
		panel = npanel;
		
		return npanel;
	}

	public void addJoker(Block newBlock) {
		JPanel npanel = showPutJoker(newBlock);
		JFrame selectLoc = new JFrame();
		selectLoc.setBounds(200, 200, 900, 350);
		selectLoc.add(npanel, BorderLayout.CENTER);

		Icon newicon = new ImageIcon(getClass().getResource(
				"../images/" + newBlock.toString()));
		JLabel newblock = new JLabel(newicon);
		JPanel what = new JPanel();
		what.add(newblock);
		selectLoc.add(what, BorderLayout.NORTH);

		selectLoc.setAlwaysOnTop(true);
		selectLoc.setVisible(true);
		ButtonHandler handler = new ButtonHandler(newBlock, selectLoc);
		// frame.add(panel,BorderLayout.SOUTH);
		for (int i = 0; i < btn.length; i++) {
			btn[i].addActionListener(handler);
		}
	}

	public JPanel showPutJoker(Block newBlock) {
		String[] blocks;
		Icon icon;
		blocks = set.show();
		btn = new JButton[set.getNumberOfBlocks() + 1];
		JPanel npanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		int j = 0;
		btn[j] = new JButton(Integer.toString(j));
		npanel.add(btn[j++]);

		for (int i = 0; i < set.getNumberOfBlocks(); i++) {

			// if((i!= set.getNumberOfBlocks()-1) && set.getBlockNum(i) ==
			// set.getBlockNum(i+1))
			icon = new ImageIcon(getClass().getResource(blocks[i]));
			JLabel item = new JLabel(icon);
			npanel.add(item);
			btn[j] = new JButton(Integer.toString(j));
			npanel.add(btn[j++]);
		}
		return npanel;
	}

	private class ButtonHandler implements ActionListener {
		int num;
		Block block;
		JFrame selectFrame;

		public ButtonHandler(Block newBlock, JFrame selectFrame) {
			block = newBlock;
			this.selectFrame = selectFrame;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			num = Integer.parseInt(arg0.getActionCommand());
			set.addJoker(block, num);
			setjokerDone++;
			if(numberOfJoker == setjokerDone){
				init=true;
			}		
			showBoard();
			selectFrame.dispose();
			updateScreen();
		}
	}

	public void sendBoard() {
		OutputStream out;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			Socket sock = new Socket(addr.getHostAddress(), 1000);
			out = sock.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(getMessage());
			pw.flush();
			pw.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void openBlock(int loc, String num){
		if(set.compareNum(loc, num)){
			set.openBlock(loc);
			updateScreen();
		}else
			System.out.println("wrong!!");
	}
}
