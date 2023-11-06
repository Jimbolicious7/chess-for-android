package com.example.chess;

/**
 * The class Move keeps track of the current player move and verifies if the move can be made
 * @author Vladislav Stepanov
 * @author Jimmy Kelly
*/
public class Move {
	
	private Position moveFrom;
	private Position moveTo;
	
	/**
	 * Move contructor with string representation of the move as a parameter
	 * @param move Represents the move being made
	 */
    public Move(String move){
    	moveFrom = new Position(move.substring(0, 2));
    	moveTo = new Position(move.substring(3, 5));
    }
    
	/**
	 * Move contructor with two positions (start and end positions) as parameters
	 * @param from The starting position of the piece
	 * @param to The ending position of the piece
	 */
    public Move(Position from, Position to){
    	moveFrom = from;
    	moveTo = to;
    }
    
	/**
	 * Get the starting position of the piece
	 * @return The starting position of the piece
	 */
    public Position getFrom(){
        return moveFrom;
    }
    
	/**
	 * Get the ending location of the piece
	 * @return The ending position of the piece
	 */
    public Position getTo(){
        return moveTo;
    }

	/**
	 * Tells if the move being made is inside the bounds of the board
	 * @return True if the move being made is inside the bounds of the board, false otherwise
	 */
    public boolean isInsideOfBoard(){
        return (moveTo.getColumn() >= 0 && moveTo.getColumn() <= 7 && moveTo.getRow() >= 0 && moveTo.getRow() <= 7);
    }
    
	/**
	 * Tells if the horizontal line of a move is clear for a movement
	 * @param board The board being checked for a clear line
	 * @return True if line is clear, false otherwise
	 */
    public boolean isEmptyHorizontalLine(Piece[][] board){
    
    	boolean result = true;
    	
        if ((moveFrom.getRow() == moveTo.getRow()) && //same row
        		(moveFrom.getColumn() != moveTo.getColumn())) //actually a move
        {
        	//check for emptiness
        	for(int column = Math.min(moveFrom.getColumn(), moveTo.getColumn())+1; column <Math.max(moveFrom.getColumn(), moveTo.getColumn()); column++){
        			if (board[moveFrom.getRow()][column] != null)
        			{
        				result = false;
        				break;
        			}
        	}
        } else
        {
        	result =false;
        }
        
        return result;
    }

	/**
	 * Tells if the vertical line of a move is clear for a movement
	 * @param board The board being checked for a clear line
	 * @return True if line is clear, false otherwise
	 */
    public boolean isEmptyVerticalLine(Piece[][] board){
        
    	boolean result = true;
    	
        if ((moveFrom.getColumn() == moveTo.getColumn()) && //same column
        		(moveFrom.getRow() != moveTo.getRow())) //actually a move
        {
        	//check for emptiness
        	for(int row = Math.min(moveFrom.getRow(), moveTo.getRow())+1; row <Math.max(moveFrom.getRow(), moveTo.getRow()); row++){
        			if (board[row][moveTo.getColumn()] != null)
        			{
        				result = false;
        				break;
        			}
        	}
        } else
        {
        	result =false;
        }
        
        return result;
    }
    
	/**
	 * Tells if the diogonal line of a move is clear for a movement
	 * @param board The board being checked for a clear line
	 * @return True if line is clear, false otherwise
	 */
    public boolean isEmptyDiagonalLine(Piece[][] board){
        
    	boolean result = true;
    	
        if ((Math.abs(moveFrom.getColumn() - moveTo.getColumn()) == Math.abs(moveFrom.getRow() - moveTo.getRow())) && //diagonal move
        		(moveFrom.getRow() != moveTo.getRow())) //actually a move
        {
        	//vector for rows
        	int rowVector = moveFrom.getRow() < moveTo.getRow()?1:-1;
        	int columnVector = moveFrom.getColumn() < moveTo.getColumn()?1:-1;
        	
        	int column= moveFrom.getColumn()+columnVector;
        	//check for emptiness
        	for(int row = moveFrom.getRow()+rowVector; row != moveTo.getRow(); row+=rowVector){
        			if (board[row][column] != null)
        			{
        				result = false;
        				break;
        			}
        			column += columnVector;
        	}
        } else
        {
        	result =false;
        }
        
        return result;
    }
}
