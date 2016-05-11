package Board;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Block.Block;
import Block.SetOfBlocks;

public abstract class PlayBoard extends Board {
	boolean isMine;
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