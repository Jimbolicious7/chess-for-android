package com.example.chess;

/**
 * The class Piece is an abstract representing the properties of all pieces on the board
 * @author Vladislav Stepanov
 * @author Jimmy Kelly
*/

import android.graphics.drawable.Drawable;
import android.content.res.Resources;

public abstract class Piece{

    Color color;
    String name;
    public int nMoves = 0;

    public int image;

    /**
     * Tells if a specified move is legal for this piece
     * @param move The move which is being tried
     * @param board The board on which this move is taking place
     * @param lastMoved The pieve which moved last
     * @return True if specified move is legal, false otherwise
     */
    public boolean isLegal(Move move, Piece[][] board, Piece lastMoved){
        return false;
    }
    
    /**
     * Get the color of this piece
     * @return Color of piece
     */
    public Color getColor(){
        return color;
    }

    /**
     * Tells if this piece has been moved yet
     * @return True if piece has been moved at all yet, false otherwise
     */
    public boolean isMoved(){
        return nMoves > 0;
    }
    
    /**
     * Gives the name of the piece (piece type + color)
     * @return Name of piece
     */
    public String toString(){
        return name;
    }
    
    /**
     * Initiates movement sequences of piece
     */
    public void move(){
    	nMoves++;
    }

    public int getDrawable(){
        return image;
    }
}