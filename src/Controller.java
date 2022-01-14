import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller implements ActionListener {
	
	private Model model;
	private View view;
	private Square selectedSquare;
	private String turn;
	private Game game;
	
	public Controller(Model model, View view, Game game) {
		this.setModel(model);
		this.setView(view);
		this.setGame(game);
		this.setTurn("white");
		this.setSelectedSquare(null);
		model.addActionListeners(this);
		view.addActionListeners(this);
	}
	

	public void actionPerformed(ActionEvent e) {

		String action_com = e.getActionCommand();
		if(action_com == "square") {
			isClicked((Square) e.getSource()); 
		}
		
		if(action_com == "restart") {
			boolean restarted = view.promptRestart();
			if(restarted == true) {
				game.sendPacket(null, true, false, false, false, false, false, false);
			}
		}
		
		if(action_com == "forfeit") {
			if((this.getTurn() == getGame().getConnectable().getColor())) {
				boolean forfeited = view.promptForfeit();
				if(forfeited == true) {
					resetBoard();
					game.sendPacket(null, false, false, true, false, false, false, false);
				}
			}
		}
	
		if(action_com == "undo") {
			if((this.getTurn() != getGame().getConnectable().getColor())) {  
				boolean undo = view.promptUndo();
				if(undo == true) {
					if(model.undo()) {
						if(getSelectedSquare() != null) {
							deselect(selectedSquare);
						}
						switchTurns();
						game.sendPacket(null, false, false, false, true, false, false, false);
					}
				}
			}
		}
		if(action_com == "exit") {
			boolean exit = view.promptExit();
			if(exit == true) {
				game.sendPacket(null, false, false, false, false, true, false, false);
				view.close();
			}
		}
		if(action_com == "custom") {
			boolean restartedCustom = view.promptRestartCustom();
			if(restartedCustom == true) {
				game.sendPacket(null, false, false, false, false, false, true, false);
			}
		}
	}
	
	
	public void resetBoard() {
		this.setModel(new Model(model.getWhiteScore(), model.getBlackScore(), false));
		this.setTurn("white");
		view.turnSwitchDisplay("white");
		this.setSelectedSquare(null);
		model.addActionListeners(this);
		view.resetBoardPanel(getModel().getBoard());
	}
	
	
	public void resetCustomBoard() {
		this.setModel(new Model(model.getWhiteScore(), model.getBlackScore(), true));
		this.setTurn("white");
		view.turnSwitchDisplay("white");
		this.setSelectedSquare(null);
		model.addActionListeners(this);
		view.resetBoardPanel(getModel().getBoard());
	}

	
	public void isClicked(Square square) {  
		if(getSelectedSquare() == null) {
			if(square.getOccupier() != null) { 		
				if(checkProperTurn(square)) {  		
					select(square);
				}
			}
		}
		else if(getSelectedSquare() != null) {
			if(getSelectedSquare() == square) {
				deselect(square);
			}
			else if(square.isHighlighted()) {
				game.sendPacket(new Command(selectedSquare.getPosition().getLocation(), square.getPosition().getLocation()), false, false, false, false, false, false, false); //SEND THE PACKET
				move(selectedSquare.getPosition(), square.getPosition()); 

				if(getSelectedSquare() != null) {
					deselect(getSelectedSquare());
				}
			}
		}
	}
	
	
	public void select(Square square) {
		square.setBackground(Color.green);
		
		setSelectedSquare(square);    
		
		List<Point> moves = square.getOccupier().getFilteredMoves();  
		
		for(Point p : moves) {			
			model.getBoard().getSquare(p).setBackground(Color.green);
			model.getBoard().getSquare(p).setHighlighted(true);
		}		
	}
	

	public void deselect(Square square) {
		square.setBackground(square.getOrigColor());
		
		setSelectedSquare(null);					
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				model.getBoard().getSquare(new Point(i, j)).setBackground(model.getBoard().getSquare(new Point(i, j)).getOrigColor());
				model.getBoard().getSquare(new Point(i, j)).setHighlighted(false);
			}
		}
	}


	public void move(Point from, Point to) {
		model.move(from, to); 			
		switchTurns();						
		testGameStatus(getTurn());			
	}
	

	public void testGameStatus(String turn) { 		 
		if(model.getBoard().isCheckmate(turn)) {		
			view.notifyCheckmate(turn);  					
			resetBoard();
			view.resetBoardPanel(getModel().getBoard());
			return;
		}
		
		if(model.getBoard().isStalemate(turn)) {
			view.notifyStalemate();
			resetBoard();
			view.resetBoardPanel(getModel().getBoard());
			return;
		}
		
		if(model.getBoard().isChecked(turn)) {
			view.notifyChecked(turn);
			return;
		}
	}
	

	public void switchTurns() {
		if(this.getTurn() == "white") {
			this.setTurn("black");
			view.turnSwitchDisplay("black");  
		}
		else if(this.getTurn() == "black") {
			this.setTurn("white");
			view.turnSwitchDisplay("white");
		}
	}
	

	public boolean checkProperTurn(Square test) {
		if((test.getOccupier().getColor() == this.getTurn()) && (this.getTurn() == getGame().getConnectable().getColor())) {
			return true;
		}
		return false;
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public Square getSelectedSquare() {
		return selectedSquare;
	}

	public void setSelectedSquare(Square selectedSquare) {
		this.selectedSquare = selectedSquare;
	}


	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
