

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;




public abstract class Piece {
		protected String color;
		protected Square occupying;
		protected boolean moved;
		protected ImageIcon icon;
		
		
		
	
		public Piece(Square _occupying, String _color) {
			this.setOccupying(_occupying);
			this.setColor(_color);
			this.moved = false;
		}


		public Piece(Piece copy) {
			this.setColor(copy.color);
			this.setMoved(copy.moved);
			this.setOccupying(copy.occupying);
		}
		
	
		public abstract List<Point> getPossibleMoves();
		
		
		
		public List<Point> getFilteredMoves() {
			List<Point> moves = this.getPossibleMoves();
			
			List<Point> forRemoval = new ArrayList<Point>();
			
			for(Point p : moves) {
				if(getOccupying().getBoard().testSelfCheck(this.getOccupying(), this.getOccupying().getBoard().getSquare(p)) == true) {
					forRemoval.add(p);
				}
			}
			for (Point p : forRemoval){
                moves.remove(p);
			}
			return moves;
		}
		
		
		
		
		public String getColor() {
			return color;
		}

		
		public void setColor(String color) {
			this.color = color;
		}

		
		public Square getOccupying() {
			return occupying;
		}

		
		public void setOccupying(Square occupying) {
			this.occupying = occupying;
		}

		
		public boolean isMoved() {
			return moved;
		}

		
		public void setMoved(boolean moved) {
			this.moved = moved;
		}
		
		public ImageIcon getIcon() {
			return icon;
		}

		public void setIcon(ImageIcon icon) {
			this.icon = icon;
		}

		protected void getVertDown(List<Point> moves, Point moveIterator, int startx,
				int starty) {
					moveIterator.x = startx;
					moveIterator.y = starty;
					moveIterator.y--;
					
					while(moveIterator.y >= 0) {
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() == null ||
								occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier().getColor() != getColor()) {
							moves.add(new Point(moveIterator.x, moveIterator.y));
						}
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() != null) {
							break;
						}
						moveIterator.y--;
					}
				}

		protected void getVertUp(List<Point> moves, Point moveIterator, int startx,
				int starty) {
					moveIterator.x = startx;
					moveIterator.y = starty;
					
					moveIterator.y++;
					while(moveIterator.y <= 7) {
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() == null ||
								occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier().getColor() != getColor()) {
							moves.add(new Point(moveIterator.x, moveIterator.y));
						}
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() != null) {
							break;
						}
						moveIterator.y++;
					}
				}

		protected void getHorizLeft(List<Point> moves, Point moveIterator, int startx,
				int starty) {
					moveIterator.x = startx;
					moveIterator.y = starty;
					
					moveIterator.x--;
					while(moveIterator.x >= 0) {
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() == null ||
								occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier().getColor() != getColor()) {
							moves.add(new Point(moveIterator.x, moveIterator.y));
						}
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() != null) {
							break;
						}
						moveIterator.x--;
					}
				}

		protected void getHorizRight(List<Point> moves, Point moveIterator, int startx,
				int starty) {
					moveIterator.x = startx;
					moveIterator.y = starty;
					moveIterator.x++;
					while(moveIterator.x <= 7) {
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() == null ||
								occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier().getColor() != getColor()) {
							moves.add(new Point(moveIterator.x, moveIterator.y));
						}
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() != null) {
							break;
						}
						moveIterator.x++;
					}
				}

		protected void getDiagDownLeft(List<Point> moves, Point moveIterator,
				int startx, int starty) {
					moveIterator.x = startx;
					moveIterator.y = starty;
					moveIterator.x--;
					moveIterator.y--;
					while(moveIterator.x >= 0 && moveIterator.y >= 0) {
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() == null ||
								occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier().getColor() != getColor()) {
							moves.add(new Point(moveIterator.x, moveIterator.y));
						}
						if(occupying.getBoard().getSquare(moveIterator).getOccupier() != null) {
							break;
						}
						
						moveIterator.x--;
						moveIterator.y--;
					}
				}

		protected void getDiagUpLeft(List<Point> moves, Point moveIterator, int startx,
				int starty) {
					moveIterator.x = startx;
					moveIterator.y = starty;
					moveIterator.x--;
					moveIterator.y++;
					while(moveIterator.x >= 0 && moveIterator.y <= 7) {
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() == null ||
								occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier().getColor() != getColor()) {
				
							moves.add(new Point(moveIterator.x, moveIterator.y));
						}
						if(occupying.getBoard().getSquare(moveIterator).getOccupier() != null) {
							break;
						}
						
						moveIterator.x--;
						moveIterator.y++;
					}
				}

		protected void getDiagDownRight(List<Point> moves, Point moveIterator,
				int startx, int starty) {
					moveIterator.x = startx;
					moveIterator.y = starty;
					moveIterator.x++;
					moveIterator.y--;
					while(moveIterator.x <= 7 && moveIterator.y >= 0) {
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() == null ||
								occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier().getColor() != getColor()) {
							moves.add(new Point(moveIterator.x, moveIterator.y));
						}
						if(occupying.getBoard().getSquare(moveIterator).getOccupier() != null) {
							break;
						}
						
						moveIterator.x++;
						moveIterator.y--;
					}
				}

		protected void getDiagUpRight(List<Point> moves, Point moveIterator, int startx,
				int starty) {
					moveIterator.x = startx;
					moveIterator.y = starty;
					moveIterator.x++;
					moveIterator.y++;
					while(moveIterator.x <= 7 && moveIterator.y <= 7) {
						if(occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier() == null ||
								occupying.getBoard().getSquare(moveIterator.x, moveIterator.y).getOccupier().getColor() != getColor()) {
							moves.add(new Point(moveIterator.x, moveIterator.y));
						}
						if(occupying.getBoard().getSquare(moveIterator).getOccupier() != null) {
							break;
						}
						
						moveIterator.x++;
						moveIterator.y++;
					}
				}

		
}
