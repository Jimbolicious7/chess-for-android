package com.example.chess;



/**
 * The class Bishop represents a bishop piece on the board
 * @author Vladislav Stepanov
 * @author Jimmy Kelly
*/
public class Bishop extends Piece{
    
    /**
     * Bishop constructor with color as parameter
     * @param color The color of the piece (white or black)
     */
    public Bishop(Color color){
        name = color.toString().toLowerCase().charAt(0) + "B";
        this.color = color;
        if(color == Color.White)
            image = R.drawable.wbishop;
        else
            image = R.drawable.bbishop;
    }

    /**
     * Tells if a specified move is legal for this piece
     * @param move The move which is being tried
     * @param board The board on which this move is taking place
     * @param lastMoved The pieve which moved last
     * @return True if specified move is legal, false otherwise
     */
    public boolean isLegal(Move move, Piece[][] board, Piece lastMoved){
    	int colFrom = move.getFrom().getColumn();
    	int colTo = move.getTo().getColumn();
    	int rowTo = move.getTo().getRow();
    	
        if (!move.isInsideOfBoard())
        {
        	return false;
        }        
        else if ((colFrom != colTo) && //actual move
        	(move.isEmptyDiagonalLine(board)) && //no pieces on the way
        	(board[rowTo][colTo] == null || board[rowTo][colTo].getColor() != color)) //moving to empty or taking a piece
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
}