package a7;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;;

public class TicTacToeGame {
	public static void main (String [] args) {
		
		//creates main frame for tic tac toe
		JFrame mainframe = new JFrame();
		mainframe.setTitle("Tic Tac Toe");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Panel for all things for tic tac toe
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		mainframe.setContentPane(panel);
		
		//What tic tac toe will be played on
		TicTacToeWidget widget = new TicTacToeWidget();
		panel.add(widget, BorderLayout.CENTER);
		
		mainframe.pack();
		mainframe.setVisible(true);
	}
}
