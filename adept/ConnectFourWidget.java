package adept;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import a7.JSpotBoard;
import a7.Spot;
import a7.SpotListener;


public class ConnectFourWidget extends JPanel implements ActionListener, SpotListener{
	
	public enum Player {RED, BLACK};
	
	private JSpotBoard board;
	private JLabel message;
	private Player next;
	private boolean gameWon;
	private String winner; 
	private int numRed;
	private int numBlack;
	private String [][] coloredSpots;
	private int totalSpots;
	
	
	public ConnectFourWidget() {
		
		coloredSpots = new String [7][6];
		for (int i = 0; i< 7; i++) {
			for ( int j = 0; j < 6;j++) {
				coloredSpots[i][j] = "";
			}
		}
		board = new JSpotBoard(7,6);
		message = new JLabel();
		
		//initializes as no winner
		winner = "";
		
		//sets number of each tile to zero
		numRed = 0;
		numBlack = 0;
		totalSpots = 0;
		
		//Set layout and place SpotBoard at center.
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);

		// Create subpanel for message area and reset button. 
		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());

		// Reset button. Add ourselves as the action listener. 
		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(message, BorderLayout.CENTER);

		// Add subpanel in south area of layout. 
		add(reset_message_panel, BorderLayout.SOUTH);

		/* Add ourselves as a spot listener for all of the
		 * spots on the spot board.
		 */
		board.addSpotListener(this);

		// Reset game.
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
		numRed = 0;
		numBlack = 0;
		totalSpots = 0;
		
		//initializes as no winner
		winner = "";
		
		//resets array of board
		for (int i = 0; i< 7; i++) {
			for ( int j = 0; j < 6;j++) {
				coloredSpots[i][j] = "";
			}
		}
		
		//lets white space go first
		next = Player.RED;
		
		message.setText("Welcome to Connect Four. Red to Play");
	}

	@Override
	public void spotClicked(Spot spot) {
		if (gameWon) {
			return;
		}
		
		//newY will give coordinate of which space will be filled next, initialized to 8 in case row is filled
		int newY = 8;
		for ( int i = 5; i >=0;i--) {
			if (coloredSpots[spot.getSpotX()][i].contentEquals("")) {
				newY = i;
				break;
			}
			/*if (!( (board.getSpotAt(spot.getSpotX(),i).getSpotColor().equals("Red")) ||
			 (board.getSpotAt(spot.getSpotX(),i).getSpotColor().equals("Black")) )) {
				newY = 5-i;
				break;
			}	
			*/
		}
		totalSpots++;
		if (newY == 8)
			return;
		
		
		String playerName = null;
		String nextPlayerName = null;
		Color playerColor = null;
		
		if (next == Player.RED) {
			playerColor = Color.RED;
			playerName = "Red";
			nextPlayerName = "Black";
			next = Player.BLACK;
		} else {
			playerColor = Color.BLACK;
			playerName = "Black";
			nextPlayerName = "Red";
			next = Player.RED;			
		}
		coloredSpots[spot.getSpotX()][newY] = playerName;
		//set gameWon condition to true or false
		gameWon = areFour() || isDraw();
		
		board.getSpotAt(spot.getSpotX(), newY).setSpotColor(playerColor);
		board.getSpotAt(spot.getSpotX(), newY).toggleSpot();
		if (playerName.contentEquals("Red"))
			numRed++;
		if (playerName.contentEquals("Black"))
			numBlack++;
		
		if (gameWon) {
			if (isDraw()) {
				message.setText("Game is a draw");
				return;
			}
			else { 
				message.setText(winner + " wins!");
				return;
			}
		}
		
		message.setText(nextPlayerName + " to play.");
	}
	

	@Override
	
	public void spotEntered(Spot spot) {
		if (gameWon) {
			return;
		}
		int x = spot.getSpotX();
		
		//for loop highlights all spots in the column, as long as they haven't been checked yet
		for (int i = 5; i >=0;i--) {
			Spot s = board.getSpotAt(x, i);
			if (coloredSpots[x][i].contentEquals("")) {
				s.highlightSpot();
			}
		}
		
	}

	@Override
	public void spotExited(Spot spot) {
		for (int i = 0; i< 6;i++ ) {
			board.getSpotAt(spot.getSpotX(), i).unhighlightSpot();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resetGame();
		
	}
	
	//method checks if there are four in a row at any part of the board or if all spots are filled
	public boolean areFour() {
		//checks vertical possibilities
		for (int i = 0; i < 7; i++) {
			for ( int x = 0; x < 3; x++ ) {
				if ( (coloredSpots[i][x].contentEquals(coloredSpots[i][x+1]))
						&& (coloredSpots[i][x+1].contentEquals(coloredSpots[i][x+2]))
						&& (coloredSpots[i][x+2].contentEquals(coloredSpots[i][x+3]))  
						&& (!coloredSpots[i][x].contentEquals("")) ) {
						if (coloredSpots[i][x].contentEquals("Red")) {
							winner = "Red";
						}
						if (coloredSpots[i][x].contentEquals("Black")) {
							winner = "Black";
						}
						return true;
					}
			}
		}
		//check horizontal possibilities
		for (int i = 0; i < 6;i++) {
			for (int x = 0; x < 4; x++) {
				if ( (coloredSpots[x][i].contentEquals(coloredSpots[x+1][i]))
						&& (coloredSpots[x+1][i].contentEquals(coloredSpots[x+2][i]))
						&& (coloredSpots[x+2][i].contentEquals(coloredSpots[x+3][i]))
						&& (!coloredSpots[x][i].contentEquals("")) ) {
						return true;
					}
			}
		}

		//checks diagonals with 3 possibilities
		for (int i = 0; i< 3;i++) {
			//sloping up and right
			if ( (coloredSpots[i][5-i].contentEquals(coloredSpots[i+1][4-i]))
					&& (coloredSpots[i+1][4-i].contentEquals(coloredSpots[i+2][3-i]))
					&& (coloredSpots[i+2][3-i].contentEquals(coloredSpots[i+3][2-i]))
					&& (!coloredSpots[i][5-i].contentEquals(""))) {
				if (coloredSpots[i][5-i].contentEquals("Red")) {
					winner = "Red";
				}
				if (coloredSpots[i][5-i].contentEquals("Black")) {
					winner = "Black";
				}
				return true;
			}
			if ( (coloredSpots[i+1][5-i].contentEquals(coloredSpots[i+2][4-i]))
					&& (coloredSpots[i+2][4-i].contentEquals(coloredSpots[i+3][3-i]))
					&& (coloredSpots[i+3][3-i].contentEquals(coloredSpots[i+4][2-i]))
					&& (!coloredSpots[i+1][5-i].contentEquals(""))) {
				if (coloredSpots[i+1][5-i].contentEquals("Red")) {
					winner = "Red";
				}
				if (coloredSpots[i+1][5-i].contentEquals("Black")) {
					winner = "Black";
				}
				return true;
			}
			//sloping up and left
			if ( (coloredSpots[6-i][5-i].contentEquals(coloredSpots[5-i][4-i]))
					&& (coloredSpots[5-i][4-i].contentEquals(coloredSpots[4-i][3-i]))
					&& (coloredSpots[4-i][3-i].contentEquals(coloredSpots[3-i][2-i]))
					&& (!coloredSpots[6-i][5-i].contentEquals(""))) {
				if (coloredSpots[6-i][5-i].contentEquals("Red")) {
					winner = "Red";
				}
				if (coloredSpots[6-i][5-i].contentEquals("Black")) {
					winner = "Black";
				}
				return true;
			}
			if ( (coloredSpots[5-i][5-i].contentEquals(coloredSpots[4-i][4-i]))
					&& (coloredSpots[4-i][4-i].contentEquals(coloredSpots[3-i][3-i]))
					&& (coloredSpots[3-i][3-i].contentEquals(coloredSpots[2-i][2-i]))
					&& (!coloredSpots[5-i][5-i].contentEquals(""))) {
				if (coloredSpots[5-i][5-i].contentEquals("Red")) {
					winner = "Red";
				}
				if (coloredSpots[5-i][5-i].contentEquals("Black")) {
					winner = "Black";
				}
				return true;
			}
		}
		//checks diagonals with 2 possibilities 
		for (int i = 0; i< 2;i++) {
			if ( (coloredSpots[i][4-i].contentEquals(coloredSpots[i+1][3-i]))
					&& (coloredSpots[i+1][3-i].contentEquals(coloredSpots[i+2][2-i]))
					&& (coloredSpots[i+2][2-i].contentEquals(coloredSpots[i+3][1-i]))
					&& (!coloredSpots[i][4-i].contentEquals(""))) {
				if (coloredSpots[i][4-i].contentEquals("Red")) {
					winner = "Red";
				}
				if (coloredSpots[i][4-i].contentEquals("Black")) {
					winner = "Black";
				}
				return true;
			}
			if ( (coloredSpots[i][i+1].contentEquals(coloredSpots[i+1][i+2]))
					&& (coloredSpots[i+1][i+2].contentEquals(coloredSpots[i+2][i+3]))
					&& (coloredSpots[i+2][i+3].contentEquals(coloredSpots[i+3][i+4]))
					&& (!coloredSpots[i][i+1].contentEquals(""))) {
				if (coloredSpots[i][i+1].contentEquals("Red")) {
					winner = "Red";
				}
				if (coloredSpots[i][i+1].contentEquals("Black")) {
					winner = "Black";
				}
				return true;
			}
			if ( (coloredSpots[2+i][5-i].contentEquals(coloredSpots[3+i][4-i]))
					&& (coloredSpots[3+i][4-i].contentEquals(coloredSpots[4+i][3-i]))
					&& (coloredSpots[4+i][3-i].contentEquals(coloredSpots[5+i][2-i]))
					&& (!coloredSpots[2+i][5-i].contentEquals(""))) {
				if (coloredSpots[2+i][5-i].contentEquals("Red")) {
					winner = "Red";
				}
				if (coloredSpots[2+i][5-i].contentEquals("Black")) {
					winner = "Black";
				}
				return true;
			}
			if ( (coloredSpots[2+i][i].contentEquals(coloredSpots[3+i][1+i]))
					&& (coloredSpots[3+i][1+i].contentEquals(coloredSpots[4+i][2+i]))
					&& (coloredSpots[4+i][2+i].contentEquals(coloredSpots[5+i][3+i]))
					&& (!coloredSpots[2+i][i].contentEquals(""))) {
				if (coloredSpots[2+i][i].contentEquals("Red")) {
					winner = "Red";
				}
				if (coloredSpots[2+i][i].contentEquals("Black")) {
					winner = "Black";
				}
				return true;
			}
		}
		
		//checks diagonals with 1 possibility
		if ( (coloredSpots[0][3].contentEquals(coloredSpots[1][2]))
				&& (coloredSpots[1][2].contentEquals(coloredSpots[2][1]))
				&& (coloredSpots[2][1].contentEquals(coloredSpots[3][0]))
				&& (!coloredSpots[3][0].contentEquals(""))) {
			if (coloredSpots[0][3].contentEquals("Red")) {
				winner = "Red";
			}
			if (coloredSpots[0][3].contentEquals("Black")) {
				winner = "Black";
			}
			return true;
		}
		if ( (coloredSpots[0][2].contentEquals(coloredSpots[1][3]))
				&& (coloredSpots[1][3].contentEquals(coloredSpots[2][4]))
				&& (coloredSpots[2][4].contentEquals(coloredSpots[3][5]))
				&& (!coloredSpots[0][2].contentEquals(""))) {
			if (coloredSpots[0][2].contentEquals("Red")) {
				winner = "Red";
			}
			if (coloredSpots[0][2].contentEquals("Black")) {
				winner = "Black";
			}
			return true;
		}
		if ( (coloredSpots[3][5].contentEquals(coloredSpots[4][4]))
				&& (coloredSpots[4][4].contentEquals(coloredSpots[5][3]))
				&& (coloredSpots[5][3].contentEquals(coloredSpots[6][2]))
				&& (!coloredSpots[3][5].contentEquals(""))) {
			if (coloredSpots[3][5].contentEquals("Red")) {
				winner = "Red";
			}
			if (coloredSpots[3][5].contentEquals("Black")) {
				winner = "Black";
			}
			return true;
		}
		if ( (coloredSpots[3][0].contentEquals(coloredSpots[4][1]))
				&& (coloredSpots[4][1].contentEquals(coloredSpots[5][2]))
				&& (coloredSpots[5][2].contentEquals(coloredSpots[6][3]))
				&& (!coloredSpots[3][0].contentEquals(""))) {
			if (coloredSpots[3][0].contentEquals("Red")) {
				winner = "Red";
			}
			if (coloredSpots[3][0].contentEquals("Black")) {
				winner = "Black";
			}
			return true;
		}
		
		return false;
		
	}
	public boolean isDraw() {
		if ( (!areFour()) && (totalSpots == 42)){
			return true;
		}
		return false;
	}
}
