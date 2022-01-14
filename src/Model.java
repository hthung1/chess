



import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.Stack;


public class Model {
	
	private Board board;
	private int whiteScore;
	private int blackScore;
	private Stack<Command> commands;
	
	public Model() {
		setWhiteScore(0);
		setBlackScore(0);
		setBoard(new Board(false));
		setCommands(new Stack<Command>());
	}
	
	public Model(int whiteScore, int blackScore, boolean custom) {
		setWhiteScore(whiteScore);
		setBlackScore(blackScore);
		setBoard(new Board(custom));
		setCommands(new Stack<Command>());
	}
	

	public void addActionListeners(ActionListener a) {
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				getBoard().chessBoard[x][y].setActionCommand("square");
				getBoard().chessBoard[x][y].addActionListener(a);
			}
		}
	}
	

	public boolean undo() {
		if(commands.size() > 0) {
			Command commandToUndo = commands.pop();
			Piece moved = commandToUndo.getMoved();
			Piece taken = commandToUndo.getTaken();
			Point from = commandToUndo.getFrom();
			Point to = commandToUndo.getTo();
			
			Square fromSquare = getBoard().getSquare(from);
			Square toSquare = getBoard().getSquare(to);
			
			fromSquare.setOccupier(moved);
			fromSquare.getOccupier().setOccupying(fromSquare);
			fromSquare.setIcon(moved.getIcon());
			if(fromSquare.getOccupier().getClass() == Pawn.class) {
				if(fromSquare.getOccupier().getColor() == "white") {
					if(fromSquare.getPosition().y == 6) {
						fromSquare.getOccupier().setMoved(false);
					}
				}
				if(fromSquare.getOccupier().getColor() == "black") {
					if(fromSquare.getPosition().y == 1) {
						fromSquare.getOccupier().setMoved(false);
					}
				}
			}
			
			toSquare.setOccupier(taken);
			if(taken != null) {
				toSquare.getOccupier().setOccupying(toSquare);
				toSquare.setIcon(taken.getIcon());
			}
			else {
				toSquare.setIcon(null);
			}
			return true;
		}
		return false;
	}
	

	public void move(Point from, Point to) {
		Piece moved = getBoard().getSquare(from).getOccupier();
		Piece taken = getBoard().getSquare(to).getOccupier();
		Command command = new Command(moved, taken, new Point(from), new Point(to));
		board.move(from, to);
		commands.push(command);	
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getWhiteScore() {
		return whiteScore;
	}

	public void setWhiteScore(int whiteScore) {
		this.whiteScore = whiteScore;
	}
	
	public int getBlackScore() {
		return blackScore;
	}

	public void setBlackScore(int blackScore) {
		this.blackScore = blackScore;
	}

	public Stack<Command> getCommands() {
		return commands;
	}

	public void setCommands(Stack<Command> commands) {
		this.commands = commands;
	}
}
