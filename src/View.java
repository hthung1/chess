import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class View {
	
	JFrame view;
	private int whiteScoreValue;
	private int blackScoreValue;
	private String currTurn;
	private Game game;
	private String side;
	
	JPanel mainPanel, infoBox, scoreBox, turnBox, boardPanel, sideBox;
	JLabel whiteScore, blackScore, turnTitle, turn, sideTitle, sideText; 
	JMenuBar menuBar;
	JMenu gameOptions;
	JMenuItem restart, forfeit, undo, exit, restartCustom;
	
	public View(Board board, Game game, String color) {
		setCurrTurn("white");
		setWhiteScoreValue(0);
		setBlackScoreValue(0);
		boardPanel = board;
		setSide(color);
		createAndShowGUI();
		setGame(game);
		
	}

	
	public void createAndShowGUI() {
		view = new JFrame("Chess");
		view.getContentPane().add(createPanels());
		view.setJMenuBar(createMenu());
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.pack();
		view.setVisible(true);
	}

	
	public JMenuBar createMenu() {
		menuBar = new JMenuBar();
		
		gameOptions = new JMenu("Game Options");
		menuBar.add(gameOptions);
		
		restart = new JMenuItem("Restart");
		forfeit = new JMenuItem("Forfeit");
		undo = new JMenuItem("Undo last move");
		exit = new JMenuItem("Exit Game");
		restartCustom = new JMenuItem("Custom Restart");
		
		gameOptions.add(restart);
		gameOptions.add(restartCustom);
		gameOptions.add(forfeit);
		gameOptions.add(undo);
		gameOptions.add(exit);
		
		return menuBar;
	}


	public JPanel createPanels() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
			infoBox = new JPanel();
			infoBox.setLayout(new BorderLayout());
					
			scoreBox = new JPanel();
			whiteScore = new JLabel("White side score: " + whiteScoreValue);
			blackScore = new JLabel("Black side score: " + blackScoreValue);
			scoreBox.add(whiteScore);
			scoreBox.add(blackScore);
					
			turnBox = new JPanel();
			turnTitle = new JLabel("Current Turn: ");
			turn = new JLabel("" + getCurrTurn());
			turnBox.add(turnTitle);
			turnBox.add(turn);
			
			sideBox = new JPanel();
			sideTitle = new JLabel("Your side is: ");
			sideText = new JLabel("" + getSide());
			sideBox.add(sideTitle);
			sideBox.add(sideText);
			
			infoBox.add(scoreBox, BorderLayout.SOUTH);
			infoBox.add(turnBox, BorderLayout.NORTH);
			infoBox.add(sideBox, BorderLayout.CENTER);
		
		mainPanel.add(boardPanel, BorderLayout.CENTER);
		mainPanel.add(infoBox, BorderLayout.EAST);
		
		return mainPanel;
	}


	public void notifyCheckmate(String turn) {
		JOptionPane.showMessageDialog(view, "Checkmate! " + turn + " has lost!");
		if(turn == "black") {
			incrementWhiteScore();
		}
		else if(turn == "white") {
			incrementBlackScore();
		}
	}

	
	public void notifyStalemate() {
		JOptionPane.showMessageDialog(view, "Stalemate! The game results in a draw.");
	}

	
	public void notifyChecked(String turn) {
		JOptionPane.showMessageDialog(view, turn + " is in check!");
	}

	
	public void turnSwitchDisplay(String string) {
		setCurrTurn(string);
		turn.setText("" + getCurrTurn());
	}

	
	public void addActionListeners(ActionListener a) {
		restart.addActionListener(a);
		restart.setActionCommand("restart");
		
		forfeit.addActionListener(a);
		forfeit.setActionCommand("forfeit");
		
		undo.addActionListener(a);
		undo.setActionCommand("undo");
		
		exit.addActionListener(a);
		exit.setActionCommand("exit");
		
		restartCustom.addActionListener(a);
		restartCustom.setActionCommand("custom");
	}

	
	public boolean promptForfeit() {
		int result = JOptionPane.showConfirmDialog(view, "Are you sure you want to forfeit?", "Forfeit prompt", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION) {
			if(getCurrTurn() == "white") {
				incrementBlackScore();
			}
			if(getCurrTurn() == "black") {
				incrementWhiteScore();
			}
			return true;
		}
		return false;
	}
	
	
	public boolean promptRestart() {
		int result = JOptionPane.showConfirmDialog(view, "Would you like to restart the game?", "Restart prompt", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	

	
	public boolean promptUndo() {
		int result = JOptionPane.showConfirmDialog(view, "Would you like to undo the last move?", "Undo prompt", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
	
	public boolean promptExit() {
		int result = JOptionPane.showConfirmDialog(view, "Are you sure you want to exit the game?", "Exit prompt", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
	
	public boolean promptRestartCustom() {
		int result = JOptionPane.showConfirmDialog(view, "Would you like to restart the game with custom pieces?", "Custom Restart prompt", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	
	public void opponentQuit() {
		JOptionPane.showMessageDialog(view, "Opponent has exited the game", "Opponent quit", JOptionPane.WARNING_MESSAGE);
	}
	
	
	public void resetBoardPanel(Board boardReset) {
		mainPanel.remove(boardPanel);
		boardPanel = boardReset;
		mainPanel.add(boardPanel, BorderLayout.CENTER);
		view.invalidate();
		view.validate();
	}
	
	
	public void incrementWhiteScore() {
		setWhiteScoreValue(getWhiteScoreValue() + 1);
		whiteScore.setText("White side score: " + getWhiteScoreValue());
	}

	
	public void incrementBlackScore() {
		setBlackScoreValue(getBlackScoreValue() + 1);
		blackScore.setText("Black side score: " + getBlackScoreValue());
	}
	
	
	void close () {
		view.setVisible(false);
		view.dispose();
	}
	
	public int getWhiteScoreValue() {
		return whiteScoreValue;
	}

	public void setWhiteScoreValue(int whiteScoreValue) {
		this.whiteScoreValue = whiteScoreValue;
	}
	
	public int getBlackScoreValue() {
		return blackScoreValue;
	}

	public void setBlackScoreValue(int blackScoreValue) {
		this.blackScoreValue = blackScoreValue;
	}

	public String getCurrTurn() {
		return currTurn;
	}

	public void setCurrTurn(String currTurn) {
		this.currTurn = currTurn;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}



}
