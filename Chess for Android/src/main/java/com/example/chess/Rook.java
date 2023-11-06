package com.example.chess;

/**
 * The class Rook represents a rook piece on the board
 * @author Vladislav Stepanov
 * @author Jimmy Kelly
*/
public class Rook extends Piece{
    
    /**
     * Rook default contructor
     * @param color The color of the piece (white or black)
     */
    public Rook(Color color){
        name = color.toString().toLowerCase().charAt(0) + "R";
        this.color = color;
        if(color == Color.White)
            image = R.drawable.wrook;
        else
            image = R.drawable.brook;
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
        else if ((colFrom != colTo) && (rowFrom == rowTo ) &&//horizontal move
        	(move.isEmptyHorizontalLine(board)) &&
        	(board[rowTo][colTo] == null || board[rowTo][colTo].getColor() != color)) //moving to empty or taking a piece
            
        {
        	return true;
        }
        else if ((rowFrom != rowTo) && (colFrom == colTo) && //vertical move
                	(move.isEmptyVerticalLine(board)) &&
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