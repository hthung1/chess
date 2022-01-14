import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;



public class Board extends JPanel{


	private static final long serialVersionUID = 1L;
	Square[][] chessBoard;
	
	
	
	public Board(boolean custom) {
		chessBoard = new Square[8][8];
		setLayout(new GridLayout(8, 8));
		boardInit(custom);
	}

	

	public void boardInit(boolean custom){
		removeAll();
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x++) {
				
				this.chessBoard[x][y] = new Square(this, new Point(x, y));
				
				if((x + y)%2 == 0){
                    this.chessBoard[x][y].setBackground(Color.white);
                    this.chessBoard[x][y].setOrigColor(Color.white);
				}
				else{
                    this.chessBoard[x][y].setBackground(Color.black);
                    this.chessBoard[x][y].setOrigColor(Color.black);
				}
				
				Piece p = null;
				String color = null;
				
				if(y == 0 || y == 1) {
					color = "black";
				}
				else if(y == 6 || y == 7) {
					color = "white";
				}
				
				if(y == 1 || y == 6) {
					p = new Pawn(chessBoard[x][y], color);
				}
				if(y == 0 || y == 7){
					switch(x) {
					case 0: case 7:
						p = new Rook(chessBoard[x][y], color);
						break;
					case 1: case 6:
						p = new Knight(chessBoard[x][y], color);
						break;
					case 2: case 5:
						p = new Bishop(chessBoard[x][y], color);
						break;
					case 3:
						p = new Queen(chessBoard[x][y], color);
						break;
					case 4:
						p = new King(chessBoard[x][y], color);
						break;
					}
				}
				
				
				chessBoard[x][y].setOccupier(p);
				add(chessBoard[x][y]); //add to panel
			}
		}
		if(custom) {
			chessBoard[1][6].setOccupier(new Soldier(chessBoard[1][6], "white"));
			chessBoard[6][6].setOccupier(new Elephant(chessBoard[6][6], "white"));
			chessBoard[6][1].setOccupier(new Soldier(chessBoard[6][1], "black"));
			chessBoard[1][1].setOccupier(new Elephant(chessBoard[1][1], "black"));
		}
	}
	
	
	public boolean move(Point from, Point to) {		
		
		if(from.x <= 7 && from.x >= 0 && to.x <= 7 && to.y >= 0) {
			
			Square origin = getSquare(from);
			Square destination = getSquare(to);
			if(origin.getOccupier() != null) {  
				List<Point> possibleMoves = origin.getOccupier().getPossibleMoves();
				if(possibleMoves.contains(to)) {
					if(testSelfCheck(origin, destination) == false) {
						destination.setOccupier(origin.getOccupier());
						destination.getOccupier().setOccupying(destination);
						origin.setOccupier(null);
						origin.setIcon(null);
						destination.getOccupier().setMoved(true);
						return true;
					}
				}
			}
		}
		return false;
	}
	

	public boolean testSelfCheck(Square from, Square to) {
		
		boolean check;
		Piece mover = from.getOccupier();
		Piece replaced = to.getOccupier();
		String moverColor = mover.getColor();
		
		Square holdReplacedPiece = new Square(from.getBoard(), new Point(-1, -1));
		
		holdReplacedPiece.setOccupier(replaced);  
		to.setOccupier(mover);   
		from.setOccupier(null);
		
		check = isChecked(moverColor);  
		
		if(replaced == null) {
			to.setIcon(null);
		}
		from.setOccupier(to.getOccupier());				
		to.setOccupier(holdReplacedPiece.getOccupier());  
		return check;
	}
	
	
	
	public Square getSquare(int x, int y) {
		if(x >= 0 && x <= 7 && y <= 7 && y >= 0) {
			return chessBoard[x][y];
		}
		return null;
	}
	

	public Square getSquare(Point _point) {
		return getSquare(_point.x, _point.y);
	}
	
	
	public boolean isChecked(String color) {
		Point kingLoc = kingLocation(color);
		String opponentColor = null;
		if(color.equals("white")){
			opponentColor = "black";
		}
		else if(color.equals("black")) {
			opponentColor = "white";
		}
		
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				Piece testPiece = getSquare(x, y).getOccupier();
				
				if(testPiece != null) {
					if(testPiece.getColor().equals(opponentColor) && testPiece.getPossibleMoves().contains(kingLoc)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	

	public boolean hasMoves(String color) {
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				Piece testPiece = getSquare(x, y).getOccupier();
				
				if(testPiece != null) {
					if(testPiece.getColor().equals(color)) {
						List<Point> test = testPiece.getPossibleMoves();
						if(!testPiece.getPossibleMoves().isEmpty()) {
							Square origin = testPiece.getOccupying();
							for(Point potentialMoves : test) {
								Square destination = getSquare(potentialMoves);
								if(testSelfCheck(origin, destination) == false) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	

	public Point kingLocation(String color) {
		for(int x = 0; x < 8; x++) {
			for(int y= 0; y < 8; y++) {
				Piece testPiece = getSquare(x, y).getOccupier();
				if(testPiece != null && testPiece.getClass().equals(King.class) && testPiece.getColor().equals(color)){
					return new Point(x, y);
				}
			}
		}
		return new Point(-1, -1);
	}
	

	public boolean isStalemate(String color) {
		boolean check = isChecked(color);
		boolean hasMoves = hasMoves(color);
		
		if(check == false) {
			if(hasMoves == false) {
				return true;
			}
		}	
		return false;
	}
	

	public boolean isCheckmate(String color) {
		boolean check = isChecked(color);
		boolean hasMoves = hasMoves(color);
		
		if(check == true) {
			if(hasMoves == false) {
				return true;
			}
		}
		return false;
	}
}
