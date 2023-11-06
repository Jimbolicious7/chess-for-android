package com.example.chess;


/**
 * The class King represents a king piece on the board
 * @author Vladislav Stepanov
 * @author Jimmy Kelly
*/
public class King extends Piece{

    /**
     * King default contructor
     * @param color The color of the piece (white or black)
     */
    public King(Color color){
        name = color.toString().toLowerCase().charAt(0) + "K";
        this.color = color;
        if(color == Color.White)
            image = R.drawable.wking;
        else
            image = R.drawable.bking;
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
    	int rowFrom = move.getFrom().getRow();
    	int rowTo = move.getTo().getRow();

        if (!move.isInsideOfBoard())
        {
        	return false;
        }        
        else if (((Math.abs(rowTo - rowFrom) == 1 && Math.abs(colTo - colFrom) <=1) ||
        		(Math.abs(rowTo - rowFrom) <= 1 && Math.abs(colTo - colFrom) ==1)) &&
        		
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