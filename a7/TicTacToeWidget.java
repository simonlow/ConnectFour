package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import a7.JSpotBoard;
import a7.Spot;
import a7.SpotBoard;
import a7.SpotBoardIterator;
import a7.SpotListener;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener{
	
	private enum Player {WHITE, BLACK};
	
	private JSpotBoard board;
	private JLabel message;
	private boolean gameWon;
	private Player next;
	private int numWhite;
	private int numBlack;
	private String winner;
	private String [][] filledInSpots;
	//private boolean draw;
	
	
	public TicTacToeWidget() {
		//initializes winning player variable
		winner = null;
		
		//initializes these variables
		numWhite = 0;
		numBlack = 0;
		
		//initializes the filledInSpots variable
		filledInSpots = new String [3][3];
		for (int i = 0; i <=2; i++) {
			for (int j = 0; j <=2; j++) {
				filledInSpots[i][j] = "";
			}
		}
		
		//intializes if game is over without a winner
		//draw = false;
		
		//creates board
		board = new JSpotBoard(3,3);
		message = new JLabel();
		
		//sets board layout
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		
		//creates message area for saying who won, whose turn it is, etc
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		
		//creates new game button
		JButton newgame = new JButton("New Game");
		newgame.addActionListener(this);
		messagePanel.add(newgame, BorderLayout.EAST);
		messagePanel.add(message, BorderLayout.CENTER);
		
		
		add(messagePanel, BorderLayout.SOUTH);
		
		board.addSpotListener(this);
		
		resetGame();
	}
	public void resetGame() {
		
		//clears board so that no spaces are filled in
		for (Spot s: board) {
			s.clearSpot();
		}
		
		//if game had been won, makes it so that it is no longer won
		gameWon = false;
		
		//sets number of turns by each player to zero
		numWhite = 0;
		numBlack = 0;
		
		//initializes the filledInSpots variable
		filledInSpots = new String [3][3];
		for (int i = 0; i <=2; i++) {
			for (int j = 0; j <=2; j++) {
				filledInSpots[i][j] = "";
			}
		}
		
		//lets white space go first
		next = Player.WHITE;
		
		message.setText("Welcome to Tic Tac Toe. White to Play");
		
	}
	
	public void actionPerformed(ActionEvent e) {
		resetGame();
	}
	public void spotClicked(Spot s ) {
		if (gameWon) {
			return;
		}
		if (!s.isEmpty())
			return;
		//System.out.println(s.getSpotX() + ", " + s.getSpotY());
		String playerName = null;
		String nextPlayerName = null;
		Color playerColor = null;
		
		//checks to see whose turn it should be
		if (next == Player.WHITE) {
			playerColor = Color.WHITE;
			playerName = "White";
			nextPlayerName = "Black";
			next = Player.BLACK;
			numWhite++;
		} else {
			playerColor = Color.BLACK;
			playerName = "Black";
			nextPlayerName = "White";
			next = Player.WHITE;
			numBlack++;
		}
	
		
		
		if (s.isEmpty()) {
			s.setSpotColor(playerColor);
			s.toggleSpot();
			message.setText(nextPlayerName + " to play.");
			filledInSpots[s.getSpotX()][s.getSpotY()] = playerName;
		}
		
		//checks if game has been won
		gameWon = isThreeRow();
		if (gameWon) {
			
			if (playerName.contentEquals("White")) {
				message.setText("White wins!");
				return;
			}
			if (playerName.contentEquals("Black")) {
				message.setText("Black wins!");
				return;
			}
		}
		if ( (numWhite == 5 && numBlack == 4) && (!gameWon)) {
			message.setText("Draw Game.");
			return;
		}
		
		//checks what color the spot is in order to change message
		message.setText(nextPlayerName + " to play.");
		
	}
	public void spotEntered(Spot s) {
		if (gameWon)
			return;
		if (!s.isEmpty())
			return;
		s.highlightSpot();
	
	}
	
	public void spotExited(Spot s) {
		s.unhighlightSpot();
	}
	
	public boolean isThreeRow() {
		if (numWhite < 3 && numBlack < 3) {
			return false;
		}
		
		
		//checks horizontal possibilities
		/*for (int i = 0; i <3; i++) {
			if (board.getSpotAt(0,i).getSpotColor() == Color.WHITE) {
				if ( board.getSpotAt(1,i).getSpotColor() == Color.WHITE 
						&& board.getSpotAt(2,i).getSpotColor() == Color.WHITE ) {
					winner = "White";
					return true;
				}
			}
			if (board.getSpotAt(0,i).getSpotColor() == Color.BLACK) {
				if ( board.getSpotAt(1,i).getSpotColor() == Color.BLACK 
						&& board.getSpotAt(2,i).getSpotColor() == Color.BLACK ) {
					winner = "Black";
					return true;
				}
			}
		}
		*/
		
		//test code for horizontal possibilities
		for (int i = 0; i <3; i++) {
			if (filledInSpots[0][i].contentEquals(filledInSpots[1][i])
					&& filledInSpots[2][i].contentEquals(filledInSpots[1][i])
					&& (!filledInSpots[0][i].contentEquals(""))) {
				winner = filledInSpots[0][i];
				return true;
			}
		}
		
		//checks vertical possibilities
		/*for (int i = 0; i < 3; i++) {
			if (board.getSpotAt(i,0).getSpotColor()== Color.WHITE) {
				if (board.getSpotAt(i,2).getSpotColor() == Color.WHITE
						&& board.getSpotAt(i,2).getSpotColor() == Color.WHITE) {
					winner = "White";
					return true;
				}
			}
			if (board.getSpotAt(i,0).getSpotColor()== Color.BLACK) {
				if (board.getSpotAt(i,2).getSpotColor() == Color.BLACK
						&& board.getSpotAt(i,2).getSpotColor() == Color.BLACK) {
					winner = "Black";
					return true;
				}
			}	
		}
		*/
		
		//test code for vertical possibilities
		for (int i = 0; i < 3; i++) {
			if (filledInSpots[i][0].contentEquals(filledInSpots[i][1])
					&& filledInSpots[i][2].contentEquals(filledInSpots[i][1])
					&& (!filledInSpots[i][0].contentEquals("")) ){
				winner = filledInSpots[i][0];
				return true;
			}
		}
		
		//checks diagonal possibilities
		/*if (board.getSpotAt(0, 0).getSpotColor() == Color.WHITE) {
			if (board.getSpotAt(2, 2).getSpotColor() == Color.WHITE 
				&& board.getSpotAt(1, 1).getSpotColor() == Color.WHITE ) {
				winner = "White";
				return true;
			}
		}
		if (board.getSpotAt(0, 0).getSpotColor() == Color.BLACK) {
			if (board.getSpotAt(2, 2).getSpotColor() == Color.BLACK 
				&& board.getSpotAt(1, 1).getSpotColor() == Color.BLACK ) {
				winner = "Black";
				return true;
			}
		}
		if (board.getSpotAt(2, 0).getSpotColor() == Color.WHITE ) {
			if ( board.getSpotAt(1, 1).getSpotColor() == Color.WHITE 
				&& board.getSpotAt(0,2).getSpotColor() == Color.WHITE ) {
				winner = "White";
				return true;
			}
		}
		if (board.getSpotAt(2, 0).getSpotColor() == Color.BLACK ) {
			if ( board.getSpotAt(1, 1).getSpotColor() == Color.BLACK 
				&& board.getSpotAt(0,2).getSpotColor() == Color.BLACK ) {
				winner = "Black";
				return true;
			}
		
		}
		*/
		
		//test code for diagonal possibilities
		if (filledInSpots[0][0].contentEquals(filledInSpots[1][1]) 
				&& filledInSpots[2][2].contentEquals(filledInSpots[1][1])
				&& (!filledInSpots[0][0].contentEquals(""))) {
			winner = filledInSpots[0][0];
			return true;
		}
		if (filledInSpots[2][0].contentEquals(filledInSpots[1][1]) 
				&& filledInSpots[0][2].contentEquals(filledInSpots[1][1])
				&& (!filledInSpots[2][0].contentEquals(""))) {
			winner = filledInSpots[0][2];
			return true;
		}
		
		return false;
		
	}
}

