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
	public boolean isCorrect;
	public boolean selected;

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

	public JPanel showBoard() {
		ImageIcon icon;
		String[] blocks;

		JPanel npanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		blocks = set.showOthers();
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
							set.openBlock(i);
							updateScreen();
							selected = true;
						} else {
							JOptionPane.showMessageDialog(null, "incorrect");
							result = "incorrect";
							isCorrect = false;
							myTurn = false;
							selected = true;
						}

					} else
						JOptionPane.showMessageDialog(null, "Not your turn");
				}
			};
			// item.
			npanel.add(item);
		}
		return npanel;
	}

	public String getSelection() {
		return message;
	}

	public String getResult() {
		return result;
	}

	public void addBlock(int loc, Block newblock) {
		set.addBlock(loc, newblock);
		//updateUI();
		updateScreen();
	}

	public boolean isSelected() {
		boolean temp = selected;
		temp = selected;
		selected = false;
		return temp;
	}
}
