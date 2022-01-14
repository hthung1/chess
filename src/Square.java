
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JButton;


public class Square extends JButton {

	private static final long serialVersionUID = 1L;
	private Piece occupier;
	private Point position;
	private Board board;
	private boolean highlighted;
	private Color origColor;
	
	static Dimension size = new Dimension(48, 48);
	

	public Square(Board _board, Point _position) {
		this.setBoard(_board);
		this.setOccupier(null);
		this.setPosition(_position);
		this.setPreferredSize(size);
		this.setHighlighted(false);
	}



	public Piece getOccupier() {
		return occupier;
	}


	public void setOccupier(Piece _occupier) {
		this.occupier = _occupier;
		
		if(occupier != null) {
			setIcon(occupier.getIcon());
		}
	}


	public Point getPosition() {
		return position;
	}


	public void setPosition(Point _position) {
		this.position = _position;
	}


	public Board getBoard() {
		return board;
	}


	public void setBoard(Board _board) {
		this.board = _board;
	}



	public boolean isHighlighted() {
		return highlighted;
	}


	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}



	public Color getOrigColor() {
		return origColor;
	}



	public void setOrigColor(Color origColor) {
		this.origColor = origColor;
	}

}	