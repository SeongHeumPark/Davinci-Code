package Board;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Block.Block;
import Block.SetOfBlocks;

public class OtherBoard extends PlayBoard {
	private String message, result;
	public boolean isCorrect, selected;

	public OtherBoard(ArrayList<Block> blocks) {
		set = new SetOfBlocks(blocks);
		isCorrect = false;
		selected = false;
	}

	public boolean Guess() {
		boolean temp = isCorrect;
		temp = isCorrect;
		isCorrect = false;
		return temp;
	}

	public boolean isSelected() {
		boolean temp = selected;
		temp = selected;
		selected = false;
		return temp;
	}

	public JPanel showBoard() {
		ImageIcon icon;
		String[] blocks;

		JPanel npanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		blocks = set.showOthers();
		// JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for (int i = 0; i < set.getNumberOfBlocks(); i++) {
			icon = new ImageIcon(getClass().getResource(blocks[i]));
			MyLabel item = new MyLabel(icon, i) {
				@Override
				void iconClicked() {
					boolean correct;
					if (myTurn) {

						String guess = JOptionPane.showInputDialog(null,
								"Enter number");
						correct = set.compareNum(i, guess);
						message = i + " " + guess;
						
						if (correct) {
							JOptionPane.showMessageDialog(null, "correct");
							result = "correct";							
							isCorrect = true;
							selected = true;
							set.openBlock(i);
							updateScreen();
						} else {
							JOptionPane.showMessageDialog(null, "incorrect");
							result = "incorrect";
							selected = true;
							isCorrect = false;
							myTurn = false;
						}

					} else
						JOptionPane.showMessageDialog(null, "Not my turn");
				}
			};
			// item.
			npanel.add(item);
		}
		return npanel;
	}

	public void addBlock(MainBoard mb) {
	}

	public String getSelection() {
		return message;
	}

	public String getResult() {
		return result;
	}

	public void setBoard(String line) {
		set.reset();
		String[] block;
		ArrayList<Block> blocks = new ArrayList<Block>();
		block = line.split(" ");
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
		set = new SetOfBlocks(blocks);
	}

	public void addBlock(int loc,Block newblock){
		set.addBlock(loc, newblock);
		updateScreen();
	}
}
