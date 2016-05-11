package Board;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import Block.*;

public class MainBoard extends Board {

	boolean isItemSelected = false;
	int selectedLoc;
	Block selectedBlock;
	static boolean getDone;

	public MainBoard() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		while (!pack.isEmpty()) {
			blocks.add(pack.getABlock());
		}
		set = new SetOfBlocks(blocks);
	}
	
	public Block getNewBlock() {
		if (selectedBlock != null) {
			Block block = selectedBlock;
			selectedBlock = null;
			removeBlock(block);
			return block;
		} else
			return null;

	}
	
	public Block getRandomBlock() {
		Block block = set.delete(set.getNumberOfBlocks() - 1);
		//selectedBlock = block;
		updateUI();
		updateScreen();
		return block;
	}

	public void delete() {
		if (selectedBlock == null) {
			Block block = set.delete(selectedLoc);
			isItemSelected = false;
			selectedBlock = block;
			updateUI();
			updateScreen();
		}
	}
	
	public void removeBlock(Block block) {
		for (int i = 0; i < set.getNumberOfBlocks(); i++) {
			if (set.getBlockNum(i) == block.getNum()
					&& set.getColor(i).equals(block.getColor())) {
				set.delete(i);
				System.out.println("found");
				break;
			}
		}
		updateUI();
		updateScreen();
	}
	
	public void removeSelectedBlcok() {
		selectedBlock = null;
	}

	public JPanel showBoard() {
		ImageIcon icon;
		String[] blocks;
		blocks = set.showOthers();
		GridLayout layout = new GridLayout(2, 10);
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		for (int i = 0; i < set.getNumberOfBlocks(); i++) {
			icon = new ImageIcon(getClass().getResource(blocks[i]));
			MyLabel item = new MyLabel(icon, i) {
				@Override
				void iconClicked() {
					if (myTurn) {
						selectedLoc = i;
						delete();
					} else
						JOptionPane.showMessageDialog(null, "Not my turn");
				}
			};
			panel.add(item);
		}
		return panel;
	}
}
