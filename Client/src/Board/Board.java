package Board;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import Block.PackOfBlocks;

public abstract class Board extends JPanel {
	public static PackOfBlocks pack = new PackOfBlocks();
	Container cp;
	JPanel panel;
	static boolean myTurn = false;

	abstract public JPanel showBoard();

	abstract class MyLabel extends JPanel {

		private static final long serialVersionUID = 1L;
		JLabel iconLabel;
		MouseAdapter iconMA;
		int i;

		public MyLabel(ImageIcon icon, int i) {
			iconLabel = new JLabel(icon);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			iconMA = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent me) {
					super.mouseClicked(me);
					iconClicked();
				}
			};
			iconLabel.addMouseListener(iconMA);
			this.i = i;
			add(iconLabel,BorderLayout.NORTH);
		}

		abstract void iconClicked();

		public JLabel getIconLabel() {
			return iconLabel;
		}
	}

	public void updateScreen() {
		removeAll();
		panel = showBoard();
		add(panel);
		panel.updateUI();
	}

}