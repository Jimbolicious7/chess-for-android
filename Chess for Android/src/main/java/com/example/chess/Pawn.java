package com.example.chess;

/**
 * The class Pawn represents a pawn piece on the board
 * @author Vladislav Stepanov
 * @author Jimmy Kelly
*/
public class Pawn extends Piece{

	/**
     * Pawn default contructor
     * @param color The color of the piece (white or black)
     */
    public Pawn(Color color){
        name = color.toString().toLowerCase().charAt(0) + "P";
        this.color = color;
		if(color == Color.White)
			image = R.drawable.wpawn;
		else
			image = R.drawable.bpawn;
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
        // simple move by 1 row
        else if ((colFrom == colTo) &&
        	(board[rowTo][colTo] == null) && //there is no piece
        	(((color == Color.White) && (rowTo - rowFrom == 1)) || //white forward
        	 ((color == Color.Black) && (rowTo - rowFrom == -1))) //black backward
        		)
        {
        	return true;
        }
        // first double move
        else if ((colFrom == colTo) &&
        		(isMoved() == false) &&
        	(board[rowTo][colTo] == null) && //there is no piece
        	(move.isEmptyVerticalLine(board)) &&
        	(((color == Color.White) && (rowFrom ==1) && (rowTo - rowFrom == 2)) || //white forward
       		 ((color == Color.Black) && (rowFrom ==6) && (rowTo - rowFrom == -2))) //black backward
        		)
        {
        	return true;
        }
        
        //taking
        else if ((Math.abs(colFrom - colTo)==1) && //diagonal move
        			(((color == Color.White) && (rowTo - rowFrom == 1)) || //white forward
        			 ((color == Color.Black) && (rowTo - rowFrom == -1))) && //black backward
        			(board[rowTo][colTo] != null) && //there is piece to take
        			(board[rowTo][colTo].getColor() != color)) //opposite color
        {
        	return true;
        }
        // taking enpassant?
        else if ((Math.abs(colFrom - colTo)==1) && //diagonal move
    			(((color == Color.White) && (rowTo - rowFrom == 1)) || //white forward
    			 ((color == Color.Black) && (rowTo - rowFrom == -1))) && //black backward
    			(board[rowTo + (color == Color.White?-1:1)][colTo] != null) && //there is piece to take
    			(board[rowTo + (color == Color.White?-1:1)][colTo].getColor() != color) && //opposite color
    			(lastMoved == board[rowTo + (color == Color.White?-1:1)][colTo]) &&
    			(lastMoved instanceof Pawn) &&
    			(lastMoved.nMoves == 1))
        {
        	return true;
        }
        else
        {
        	return false;
        }
        
    }
}