package Board;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Block.Block;
import Block.SetOfBlocks;

public class MyBoard extends PlayBoard {
	public MyBoard(int numOfBlocks) {
		int i, j = 0;
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block Joker[] = new Block[2];

		Block block;
		for (i = 0; i < numOfBlocks; i++) {
			block = pack.getABlock();
			if (block.getNum() == 12.0) {
				Joker[j++] = block;
			} else
				blocks.add(block);
		}
		set = new SetOfBlocks(blocks);
		set.sorting();
		if (j != 0) {
			for (int k = 0; k < j; k++) {
				addJoker(Joker[k]);
			}
		}
		// addJoker(new Block("white",12));
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
			npanel.add(item);
		}
		panel = npanel;

		return npanel;
	}

	public synchronized void addJoker(Block newBlock) {
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
			showBoard();
			selectFrame.dispose();
			updateScreen();
		}
	}

	public void openBlock(int loc, String num) {
		if(set.compareNum(loc, num)){
			set.openBlock(loc);
			updateScreen();
		}else
			System.out.println("wrong!!");		
	}
}
