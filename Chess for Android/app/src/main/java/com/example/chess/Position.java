package com.example.chess;

/**
 * The class Position keeps track of the position of a piece
 * @author Vladislav Stepanov
 * @author Jimmy Kelly
*/
public class Position {
	
	private int row;
	private int column;
	
    /**
     * Position constructor which takes a movement and sets the column and row to the initial location
     * @param move The move being made
     */
    public Position(String move){                
    	column = ((int)move.charAt(0) - 'a');
    	row = (int)move.charAt(1) - '1';
    }
    
    /**
     * Position constructor which takes integers and sets the column and row
     * @param r
     * @param c
     */
    public Position(int r, int c){                
    	column = c;
    	row = r;
    }
    
    /**
     * Get the row value of this position
     * @return Index of row on the board
     */
    public int getRow(){
        return row;
    }
    
    /**
     * Get the column value of this position
     * @return Index of column on the board
     */
    public int getColumn(){
        return column;
    }

}
