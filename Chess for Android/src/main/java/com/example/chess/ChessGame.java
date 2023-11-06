package com.example.chess;

/**
 * The class ChessGame integrates other classes to run a game of chess
 * @author Vladislav Stepanov
 * @author Jimmy Kelly
*/

import java.util.Random;

public class ChessGame{

    //Stores all piece locations
    //Value on the left is the row (1 - 8) and on the right is the column (A - H)
    Piece[][] board = new Piece[8][8];

    //True while game is going, false when one team wins
    boolean inProgress;
    
    Color turn = Color.White;

	private King whiteKing;

	private King blackKing;
	
	private Piece lastMoved;

	private boolean drawOffered;

	/**
	 * Makes sure that a castling move is legal
	 * @param pMove the move that the player wants to make
	 * @return a MoveResult indicating whether the castling move is legal or not
	 */
	public MoveResult VerifyCastling(String pMove)
	{
		boolean succesfullCast =  false; 
        if (turn == Color.White) // white
        {
        	if (pMove.compareToIgnoreCase("e1 g1") == 0) //left
        	{
        		if ((board[0][4] != null)  &&
        				(board[0][4].isMoved() == false) && //king was not moved
        				(board[0][7] != null) &&
        				(board[0][7].isMoved() == false) && //rook was not moved
        				(board[0][5] == null) && // no pieces in the middle
        				(board[0][6] == null))// no pieces in the middle
        		{
        			board[0][5] = board[0][7];
        			board[0][6] = board[0][4];
        			board[0][7] = null;
        			board[0][4] = null;
        			
        			// can't castle into check
        			if (isChecked(turn))
        			{
        				//rolling back
        				board[0][7] = board[0][5];
            			board[0][4] = board[0][6];
            			board[0][5] = null;
            			board[0][6] = null;
            			return MoveResult.Illegal;
        			}
        			else
        			{
        				succesfullCast = true;
        				board[0][5].move();
            			board[0][6].move();
            			lastMoved = board[0][6];
        			}
        		}
        				
        	}
        	if (pMove.compareToIgnoreCase("e1 c1") == 0) //left
        	{
        		if ((board[0][4] != null)  &&
        				(board[0][4].isMoved() == false) && //king was not moved
        				(board[0][0] != null) &&
        				(board[0][0].isMoved() == false) && //rook was not moved
        				(board[0][1] == null) && // no pieces in the middle
        				(board[0][2] == null) &&
        				(board[0][3] == null))// no pieces in the middle
        		{
        			board[0][3] = board[0][0];
        			board[0][2] = board[0][4];
        			board[0][0] = null;
        			board[0][4] = null;
        			
        			// can't castle into check
        			if (isChecked(turn))
        			{
        				//rolling back
        				board[0][0] = board[0][3];
            			board[0][4] = board[0][2];
            			board[0][3] = null;
            			board[0][2] = null;
            			return MoveResult.Illegal;
        			}
        			else
        			{
        				succesfullCast = true;
        				board[0][3].move();
            			board[0][2].move();
            			lastMoved = board[0][2];
        			}
        		}
        				
        	}
        }
    	if (turn == Color.Black) // black
        {
        	if (pMove.compareToIgnoreCase("e8 g8") == 0) //left
        	{
        		if ((board[7][4] != null)  &&
        				(board[7][4].isMoved() == false) && //king was not moved
        				(board[7][7] != null) &&
        				(board[7][7].isMoved() == false) && //rook was not moved
        				(board[7][5] == null) && // no pieces in the middle
        				(board[7][6] == null))// no pieces in the middle
        		{
        			board[7][5] = board[7][7];
        			board[7][6] = board[7][4];
        			board[7][7] = null;
        			board[7][4] = null;
        			
        			// can't castle into check
        			if (isChecked(turn))
        			{
        				//rolling back
        				board[7][7] = board[7][5];
            			board[7][4] = board[7][6];
            			board[7][5] = null;
            			board[7][6] = null;
            			return MoveResult.Illegal;
        			}
        			else
        			{
        				succesfullCast = true;
        				board[7][5].move();
            			board[7][6].move();
            			lastMoved = board[7][6];
        			}
        		}
        				
        	}
        	
        	if (pMove.compareToIgnoreCase("e8 c8") == 0) //left
        	{
        		if ((board[7][4] != null)  &&
        				(board[7][4].isMoved() == false) && //king was not moved
        				(board[7][0] != null) &&
        				(board[7][0].isMoved() == false) && //rook was not moved
        				(board[7][1] == null) && // no pieces in the middle
        				(board[7][2] == null) &&
        				(board[7][3] == null))// no pieces in the middle
        		{
        			board[7][3] = board[7][0];
        			board[7][2] = board[7][4];
        			board[7][0] = null;
        			board[7][4] = null;
        			
        			// can't castle into check
        			if (isChecked(turn))
        			{
        				//rolling back
        				board[7][0] = board[7][3];
            			board[7][4] = board[7][2];
            			board[7][3] = null;
            			board[7][2] = null;
            			return MoveResult.Illegal;
        			}
        			else
        			{
        				succesfullCast = true;
        				board[7][3].move();
            			board[7][2].move();
            			lastMoved = board[7][2];
        			}
        		}
        				
        	}
        }
        
        return succesfullCast ? MoveResult.Success : MoveResult.None;
	}
    //This method takes the column then row of the piece to be moved then its destination
    //It does not currently work right, but that's okay because we have time

	public void offerDraw(){
		drawOffered = true;
	}

	/**
	 * Prompts the user for a move, then initiates that move on the board
	 * @param player Represents which player's turn it is
	 * @param pMove The user input
	 * @return A MoveResult which represents the state of the board after the move is made
	 */
    public MoveResult move(Color player, String pMove){
        
        // according to the requirements the draw needs to be accepted
        /*if (drawOffered)
        {
        	return pMove.compareToIgnoreCase("draw") == 0? MoveResult.DrawAccepted : MoveResult.Illegal;
        }
        
        // check for resigning
        if (pMove.compareToIgnoreCase("resign") == 0)
        {
        	return MoveResult.Resign;
        }*/
               
        //castling
        MoveResult castlingResult = VerifyCastling(pMove);
        
        if (castlingResult  == MoveResult.Illegal)
        {
			System.out.println("Castling illegal");
        	return MoveResult.Illegal;
        }
        else if (castlingResult  == MoveResult.Success)
        {
			System.out.println("Castling success");
        	// similar to the regular move we need to see if we induced Check or CheckMate to the opposite side
        	
        	// verifying if check mate is done
            if ((turn == Color.Black && isCheckMated(Color.White)) ||
            	(turn == Color.White && isCheckMated(Color.Black)))
            {
            	return MoveResult.CheckMate;
            } 
            // verifying if check is done
            else if (turn == Color.Black && isChecked(Color.White) ||
                	(turn == Color.White && isChecked(Color.Black)))
            {
            	return pMove.contains("draw?")? MoveResult.DrawOffered : MoveResult.Check;
            }
            // this is a regular and proper move, but we still need to verify if draw was offered
            else
            {
            	return pMove.contains("draw?")? MoveResult.DrawOffered : MoveResult.Success;
            }
        }
        
        
        Move move = new Move(pMove);
        // no pieces
        if (board[move.getFrom().getRow()][move.getFrom().getColumn()] == null)
        {
        	return MoveResult.Illegal;
        }
        //wrong color
        else if (board[move.getFrom().getRow()][move.getFrom().getColumn()].getColor() != player)
        {
        	return MoveResult.Illegal;
        }
        //can the piece move like that?
        else if(board[move.getFrom().getRow()][move.getFrom().getColumn()].isLegal(move, board, lastMoved))
        {      
        	boolean enpassant = false;
        	
        	int colFrom = move.getFrom().getColumn();
        	int colTo = move.getTo().getColumn();
        	int rowFrom = move.getFrom().getRow();
        	int rowTo = move.getTo().getRow();
        	
        	//Enpassant??
        	if ((lastMoved != null) &&
        		(lastMoved instanceof Pawn) &&
        		(lastMoved.nMoves == 1) &&
        		(board[move.getFrom().getRow()][move.getFrom().getColumn()] instanceof Pawn) &&
        		(Math.abs(colFrom - colTo)==1) && //diagonal move
        		(((turn == Color.White) && (rowTo - rowFrom == 1)) || //white forward
        		 ((turn == Color.Black) && (rowTo - rowFrom == -1))) && //black backward
        			(board[rowTo + (turn == Color.White?-1:1)][colTo] != null) && //there is piece to take
        			(board[rowTo + (turn == Color.White?-1:1)][colTo].getColor() != turn) && //opposite color
        			(lastMoved == board[rowTo + (turn == Color.White?-1:1)][colTo]))
            {
        		enpassant = true;
            }
        	
        	Piece savedPeice = board[move.getTo().getRow()+ (enpassant == false?0:(turn == Color.White ?-1:1))][move.getTo().getColumn()];
        	
        	// making a move, before check verification
            board[move.getTo().getRow()][move.getTo().getColumn()] = board[move.getFrom().getRow()][move.getFrom().getColumn()];
            board[move.getFrom().getRow()][move.getFrom().getColumn()] = null;
            if (enpassant)
            {
            	board[move.getTo().getRow()+(enpassant == false?0:(turn == Color.White ?-1:1))][move.getTo().getColumn()] = null;
            }
            
        	// before making a move 
        	// we need to verify 
        	// if it opens the king for the check 
        	//or keep the king in the check, making the move illegal
            if (isChecked(turn))
			{
				//rolling back the move
            	board[move.getFrom().getRow()][move.getFrom().getColumn()]= board[move.getTo().getRow()][move.getTo().getColumn()];
            	board[move.getTo().getRow()+(enpassant == false?0:(turn == Color.White ?-1:1))][move.getTo().getColumn()] = savedPeice;
            	board[move.getTo().getRow()][move.getTo().getColumn()] = null;
            	return MoveResult.Illegal;
			}   
            else
            {
            	// letting it know about actual move
            	board[move.getTo().getRow()][move.getTo().getColumn()].move();
            	
            	// check for promoted pawns
            	if (((turn == Color.White) && (move.getTo().getRow() == 7) ||
            	   	 (turn == Color.Black) && (move.getTo().getRow() == 0))&&
            		( board[move.getTo().getRow()][move.getTo().getColumn()] instanceof Pawn))
        		{
            		// to which piece promote, by default Queen
            		String piecename = pMove.length()>5 ? pMove.substring(6, 7) : "Q";
            		
            		switch(piecename.charAt(0)) {
                    case 'Q':
                    	board[move.getTo().getRow()][move.getTo().getColumn()] = new Queen(turn);
                    	break;
                    case 'B':
                    	board[move.getTo().getRow()][move.getTo().getColumn()] = new Bishop(turn);
                    	break;
                    case 'N':
                    	board[move.getTo().getRow()][move.getTo().getColumn()] = new Knight(turn);
                    	break;
                    case 'R':
                    	board[move.getTo().getRow()][move.getTo().getColumn()] = new Rook(turn);
                    	break;
                    	
            		}
        		}
            	
            	lastMoved = board[move.getTo().getRow()][move.getTo().getColumn()];
            }
        } else 
        {
            return MoveResult.Illegal;
        }
        
        // verifying if check mate is done
        if ((turn == Color.Black && isCheckMated(Color.White)) ||
        	(turn == Color.White && isCheckMated(Color.Black)))
        {
        	return MoveResult.CheckMate;
        } 
        // verifying if check is done
        else if (turn == Color.Black && isChecked(Color.White) ||
            	(turn == Color.White && isChecked(Color.Black)))
        {
        	return pMove.contains("draw?")? MoveResult.DrawOffered : MoveResult.Check;
        }
        // this is a regular and proper move, but we still need to verify if draw was offered
        else
        {
        	return pMove.contains("draw?")? MoveResult.DrawOffered : MoveResult.Success;
        }
    }

	public void AIMove(Color color){
		Random random = new Random();
		MoveResult result = MoveResult.Illegal;
		while(result == MoveResult.Illegal) {
			String pMove = (char) ((random.nextInt(8) + (int) 'a')) + "" + (random.nextInt(8) + 1) + " " + (char) ((random.nextInt(8) + (int) 'a')) + "" + (random.nextInt(8) + 1);
			result = move(color, pMove);
		}
	}

    /**
	 * Checks if the king of specified color is in check
	 * @param color The color of the king to see if it is in check
	 * @return True if specified king is in check, false otherwise
	 */
    public boolean isChecked(Color color){

    	// find king location
    	int kingRow=0;
    	int kingColumn=0;
    	for(int row = 0; row < 8; row++){
            for(int column = 0; column < 8; column++){
            	if ((color == Color.White && board[row][column] == whiteKing) ||
            		(color == Color.Black && board[row][column] == blackKing))
            	{
            		kingRow = row;
            		kingColumn = column;
            		break;
            	}
            }
    	}
            
        Position toPosition = new Position(kingRow, kingColumn);
    	
        // testing all opposite pieces
    	for(int row = 0; row < 8; row++){
            for(int column = 0; column < 8; column++){
                if ((board[row][column] != null) && (board[row][column].getColor() != color))
                {
                	Position fromPosition = new Position(row, column);
                	Move move = new Move(fromPosition, toPosition);
                		
                	// if any opposite piece has a legal move to the king cell, it means king is under check
                	if (board[row][column].isLegal(move, board, lastMoved))
                	{
                		return true;
                	}                    
                }
            }
        }
        return false;
    }
    
    /**
	 * Checks if the king of specified color is in checkmate
	 * @param color The color if the king to see if it is in checkmate
	 * @return True if specified king is in checkmate, false otherwise
	 */
    public boolean isCheckMated(Color color){
    	
    	// no point in verifying mate if there is no check
    	if (!isChecked(color))
    	{
    		return false;
    	}
    	           
    	// we need try every every "color" piece and find at least one valid move that stops check
    	
    	// looping through all "color" pieces
    	for(int pieceRow = 0; pieceRow < 8; pieceRow++){
            for(int pieceColumn = 0; pieceColumn < 8; pieceColumn++){
                if ((board[pieceRow][pieceColumn] != null) && (board[pieceRow][pieceColumn].getColor() == color))
                {
                	Position piecePosition = new Position(pieceRow, pieceColumn);
                	
                	//looping through all cells and checking if 
                	// 1. Move is legal
                	// 2. There is NO more checks
                	for(int newRow = 0; newRow < 8; newRow++){
                        for(int newColumn = 0; newColumn < 8; newColumn++){
                        	
                        	Position newPosition = new Position(newRow, newColumn);
                        	Move move = new Move(piecePosition, newPosition);
                        	
                        	if (board[pieceRow][pieceColumn].isLegal(move, board, lastMoved))
                        	{
                        		// temp swapping to verify check
                   
                        		Piece tmpPiece = board[newRow][newColumn];
                        		board[newRow][newColumn] = board[pieceRow][pieceColumn];
                        		board[pieceRow][pieceColumn] = null;
                        		
                        		if (!isChecked(color))
                            	{
                        			// found a safe contr move
                        			// rolling back
                        			board[pieceRow][pieceColumn] = board[newRow][newColumn];
                        			board[newRow][newColumn] = tmpPiece;
                            		return false;
                            	}
                        		// rolling back
                        		board[pieceRow][pieceColumn] = board[newRow][newColumn];
                    			board[newRow][newColumn] = tmpPiece;
                        	}
                        }
                	}
                }
            }
    	}        
    	
        // if we are here, there are no safe moves
        return true;
    }
    
    /**
	 * Prints the current layout of the board
	 */
    public void print(){
    	System.out.println("");
        for(int a = 7; a >= 0; a--){
            for(int b = 0; b <= 7; b++){
                if(board[a][b] != null)
                    System.out.print(board[a][b].toString() + " ");
                else{
                    if(a % 2 == b % 2)
                        System.out.print("## ");
                    else
                        System.out.print("   ");
                }
            }
            System.out.println(a + 1);
        }
        System.out.println(" a  b  c  d  e  f  g  h");
        System.out.println("");
    }
    
    public void InitiateBoard()
    {
    	//Sets all spaces to null
        for(int a = 0; a < 8; a++){
            for(int b = 0; b < 8; b++){
                board[a][b] = null;
            }
        }
		StandardSetup();
    }

	/**
	 * ChessGame default constructor
	 */
    public ChessGame(){
    	InitiateBoard();
    }

	public void setGame(ChessGame game){
		for(int a = 0; a < 8; a++)
			for(int b = 0; b < 8; b++)
				this.board[a][b] = game.getBoard()[a][b];
		//this.board = game.getBoard();
		this.whiteKing = game.getKing("w");
		this.blackKing = game.getKing("b");
		this.lastMoved = game.getLastMoved();
	}

	public King getKing(String color){
		if(color.equals("w"))
			return whiteKing;
		else
			return blackKing;
	}

	public Piece getLastMoved(){
		return lastMoved;
	}

    /**
	 * Sets up the board in a way so that one king is in check, used for testing
	 */
    public void CheckTestSetup()
	{
        whiteKing = new King(Color.White);
        board[0][4] = whiteKing;

        blackKing= new King(Color.Black);
        board[7][4] = blackKing;

        board[0][2] = new Bishop(Color.White);
        board[0][5] = new Bishop(Color.White);
        board[0][0] = new Rook(Color.White);
    }
    
	/**
	 * Sets up the board in a way so that one king is in checkmate, used for testing
	 */
    public void CheckMateTestSetup()
	{  	
        board[0][0] = new Rook(Color.White);
        board[0][1] = new Knight(Color.White);
        board[0][2] = new Bishop(Color.White);
        board[0][3] = new Queen(Color.White);
        whiteKing = new King(Color.White);
        board[0][4] = whiteKing;
        
        board[0][5] = new Bishop(Color.White);
        board[0][6] = new Knight(Color.White);
        board[0][7] = new Rook(Color.White);
     
        board[7][1] = new Knight(Color.Black);
        board[7][2] = new Bishop(Color.Black);
        board[7][3] = new Queen(Color.Black);
        
        blackKing= new King(Color.Black);
        board[7][4] = blackKing;
        board[7][5] = new Bishop(Color.Black);
        board[7][6] = new Knight(Color.Black);
 
        for(int a = 0; a < 6; a++){
            if (a != 5) 
            {
            	board[6][a] = new Pawn(Color.Black);
            }
            else
            {
            	board[5][a] = new Pawn(Color.Black);
            }
        }
	} 
	
	/**
	 * Sets up the board for a normal game
	 */
	public void StandardSetup()
	{
        board[0][0] = new Rook(Color.White);
        board[0][1] = new Knight(Color.White);
        board[0][2] = new Bishop(Color.White);
        board[0][3] = new Queen(Color.White);
        whiteKing = new King(Color.White);
        board[0][4] = whiteKing;
        
        board[0][5] = new Bishop(Color.White);
        board[0][6] = new Knight(Color.White);
        board[0][7] = new Rook(Color.White);
        board[7][0] = new Rook(Color.Black);
        board[7][1] = new Knight(Color.Black);
        board[7][2] = new Bishop(Color.Black);
        board[7][3] = new Queen(Color.Black);
        
        blackKing= new King(Color.Black);
        board[7][4] = blackKing;
        board[7][5] = new Bishop(Color.Black);
        board[7][6] = new Knight(Color.Black);
        board[7][7] = new Rook(Color.Black);
        for(int a = 0; a < 8; a++){
            board[1][a] = new Pawn(Color.White);
            board[6][a] = new Pawn(Color.Black);
        }
	}
	
	/**
	 * Sets up the board in a way so that enpassant can be used, used for testing
	 */
	public void EnpassantTestSetup()
	{
        board[0][0] = new Rook(Color.White);
        board[0][1] = new Knight(Color.White);
        board[0][2] = new Bishop(Color.White);
        board[0][3] = new Queen(Color.White);
        whiteKing = new King(Color.White);
        board[0][4] = whiteKing;
        
        board[0][5] = new Bishop(Color.White);
        board[0][6] = new Knight(Color.White);
        board[0][7] = new Rook(Color.White);
        board[7][0] = new Rook(Color.Black);
        board[7][1] = new Knight(Color.Black);
        board[7][2] = new Bishop(Color.Black);
        board[7][3] = new Queen(Color.Black);
        
        blackKing= new King(Color.Black);
        board[7][4] = blackKing;
        board[7][5] = new Bishop(Color.Black);
        board[7][6] = new Knight(Color.Black);
        board[7][7] = new Rook(Color.Black);
        for(int a = 0; a < 8; a++){
            board[1][a] = new Pawn(Color.White);
            board[6][a] = new Pawn(Color.Black);
        }
        board[4][5]=new Pawn(Color.White);
	}
    
	/**
	 * Sets up the board in a way so that castling can be used, used for testing
	 */
	public void CastleTestSetup()
	{
       
        whiteKing = new King(Color.White);
        board[0][4] = whiteKing;
        
        
       
        
        blackKing= new King(Color.Black);
        board[7][4] = blackKing;
        
        
        board[6][1] = new Pawn(Color.White);
	}
	
	/**
	 * Sets up the board in a way so that castling can be done by the black king, used for testing
	 */
	public void BlackCastleTestSetup()
	{
        board[0][0] = new Rook(Color.White);
        board[0][1] = new Knight(Color.White);
        board[0][2] = new Bishop(Color.White);
        
        //board[1][2] = new Queen(Color.White);        
        board[1][6] = new Queen(Color.White);
        
        whiteKing = new King(Color.White);
        board[0][4] = whiteKing;
        
        board[0][5] = new Bishop(Color.White);
        board[0][6] = new Knight(Color.White);
        board[0][7] = new Rook(Color.White);
        board[7][0] = new Rook(Color.Black);

        
        blackKing= new King(Color.Black);
        board[7][4] = blackKing;
        board[7][7] = new Rook(Color.Black);
	}

	/**
	 * Sets up the board in a way so that the white king can use castling, used for testing
	 */
	public void WhiteCastleTestSetup()
	{
        board[0][0] = new Rook(Color.White);
        whiteKing = new King(Color.White);
        board[0][4] = whiteKing;
        
        board[0][7] = new Rook(Color.White);
        board[7][0] = new Rook(Color.Black);
        board[7][1] = new Knight(Color.Black);
        board[7][2] = new Bishop(Color.Black);
        
        //board[6][6] = new Queen(Color.Black);
        board[6][2] = new Queen(Color.Black);
        
        blackKing= new King(Color.Black);
        board[7][4] = blackKing;
        board[7][5] = new Bishop(Color.Black);
        board[7][6] = new Knight(Color.Black);
        board[7][7] = new Rook(Color.Black);
        
	}
	
	/**
	 * Runs each time a piece is moves in the mother game
	 */
	public MoveResult Run(Color turn, String pMove)
    {
		this.turn = turn;

            MoveResult moveResult = move(turn, pMove);
            switch(moveResult) {
            case Success:
            	turn = turn == Color.Black?Color.White:Color.Black;
            	break;
            case DrawOffered:
            	drawOffered = true;
            	turn = turn == Color.Black?Color.White:Color.Black;
            	break;
            case DrawAccepted:
            	System.out.println("draw");
            	inProgress = false;
            	break;
            case Check:
            	System.out.println("Check");
            	turn = turn == Color.Black?Color.White:Color.Black;
            	break;
            case Illegal:
            	System.out.println("Illegal move, try again");
            	break;
            case Resign:
            	System.out.println((turn == Color.Black?Color.White:Color.Black).toString().concat(" wins"));
            	inProgress = false;
            	break;
            case CheckMate:
                System.out.println("Checkmate");
                System.out.println(turn.toString().concat(" wins"));
                inProgress = false;
                break;
            case None:
            	break;
        }
		return moveResult;
    }

	public Piece[][] getBoard(){
		return board;
	}

    /*public static void main(String[] args){
    	ChessGame chessGame = new ChessGame();
    	chessGame.StandardSetup();
    	//chessGame.CheckTestSetup();
    	//chessGame.CheckMateTestSetup();
    	//chessGame.WhiteCastleTestSetup();
    	//chessGame.BlackCastleTestSetup();
    	//chessGame.CastleTestSetup();
    	//chessGame.EnpassantTestSetup();
    	chessGame.Run();
    }*/
}