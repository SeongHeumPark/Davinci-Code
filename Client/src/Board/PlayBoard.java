package Board;

import javax.swing.JButton;
import javax.swing.JPanel;

import Block.SetOfBlocks;

public abstract class PlayBoard extends Board {
	boolean isMine;
	public SetOfBlocks set;
	JButton[] btn;
	int jokerLoc;

	abstract public JPanel showBoard();


	public String getMessage() {
		String board = set.getStringMessage();

		return board;
	}

	public boolean isAllBlockOpen() {
		int count = 0;
		for (int i = 0; i < set.getNumberOfBlocks(); i++) {
			if (set.getOpenState(i) == true) {
				count++;
			}
		}
		if (count == set.getNumberOfBlocks()) {
			return true;
		} else
			return false;
	}
}
