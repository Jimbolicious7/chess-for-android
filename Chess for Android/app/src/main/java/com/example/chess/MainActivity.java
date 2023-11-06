package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import com.example.chess.ListViewActivity;
public class MainActivity extends AppCompatActivity{

    ArrayList<ImageView> board = new ArrayList();

    GameRecord thisGame;

    ArrayList<GameRecord> gameRecords =new ArrayList<>();
    boolean frozenPromotion = false;
    boolean frozenDraw = false;

    boolean selectedFirst = false;
    int tile1;
    Color turn = Color.White;

    ChessGame game = new ChessGame();

    ChessGame previousMove = new ChessGame();

    private boolean AIMoving = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisGame = new GameRecord();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        load();


        int i = 0;
        while (i < 64) {
            board.add((ImageView) ((TableRow) ((TableLayout) findViewById(R.id.table)).getChildAt(i / 8)).getChildAt(i % 8));
            int finalI = i;
            board.get(i).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(!frozenPromotion && !frozenDraw) {
                        if (!selectedFirst) {
                            selectedFirst = true;
                            board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(153, 255, 153));
                            tile1 = finalI;
                        } else {
                            String pMove = ((char) (tile1 % 8 + (int) 'a') + "" + (8 - (tile1 / 8))) + " " + (char) (finalI % 8 + (int) 'a') + (8 - (finalI / 8));
                            selectedFirst = false;
                            if (finalI % 2 == 0 && (finalI / 8) % 2 == 0)
                                board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(255, 255, 255));
                            else if (finalI % 2 == 0 && (finalI / 8) % 2 == 1)
                                board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(70, 70, 70));
                            else if (finalI % 2 == 1 && (finalI / 8) % 2 == 0)
                                board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(70, 70, 70));
                            else
                                board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(255, 255, 255));
                            if (tile1 % 2 == 0 && (tile1 / 8) % 2 == 0)
                                board.get(tile1).setBackgroundColor(android.graphics.Color.rgb(255, 255, 255));
                            else if (tile1 % 2 == 0 && (tile1 / 8) % 2 == 1)
                                board.get(tile1).setBackgroundColor(android.graphics.Color.rgb(70, 70, 70));
                            else if (tile1 % 2 == 1 && (tile1 / 8) % 2 == 0)
                                board.get(tile1).setBackgroundColor(android.graphics.Color.rgb(70, 70, 70));
                            else
                                board.get(tile1).setBackgroundColor(android.graphics.Color.rgb(255, 255, 255));
                            previousMove.setGame(game);
                            MoveResult moveResult = game.move(turn, pMove);
                            if (!(moveResult == MoveResult.Illegal)) {
                                thisGame.addMove(pMove);
                                //Moves the pieces on the board
                                setBoard();
                                //Ends AI movement sequence
                                AIMoving = false;
                                //Checks if game is in promotion sequence
                                if(game.isFrozenPromotion()) {
                                    frozenPromotion = true;
                                    promotePawn();
                                }
                                //Check for checkmate
                                if(moveResult == MoveResult.CheckMate)
                                    if(turn == Color.White)
                                        gameEnd('C');
                                    else
                                        gameEnd('M');
                                //Switch to next player's turn
                                if (turn == Color.White)
                                    turn = Color.Black;
                                else
                                    turn = Color.White;
                                save();
                            }
                        }
                    }
                }
            });
            i++;
        }
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!frozenPromotion && !frozenDraw) {
                    game.setGame(previousMove);
                    if (turn == Color.White)
                        turn = Color.Black;
                    else
                        turn = Color.White;
                    setBoard();
                }
            }
        });
        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!frozenPromotion && !frozenDraw) {
                    AIMoving = true;
                    previousMove.setGame(game);
                    String pMove = game.AIMove(turn);
                    thisGame.addMove(pMove);
                    setBoard();

                    if (turn == Color.White)
                        turn = Color.Black;
                    else
                        turn = Color.White;
                }
            }
        });
        ((Button) findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean toggle = false;
                if(!frozenPromotion && !frozenDraw) {
                    frozenDraw = true;
                    toggle = true;
                    ((Button) findViewById(R.id.button3)).setText("Yes");
                    ((Button) findViewById(R.id.button4)).setText("No");
                    game.offerDraw();
                }
                if(frozenDraw && !toggle){
                    gameEnd('D');
                    frozenDraw = false;
                    ((Button) findViewById(R.id.button3)).setText("Draw");
                    ((Button) findViewById(R.id.button4)).setText("Resign");
                    //
                    //This does not yet record or save the game
                    //
                }
            }
        });
        ((Button) findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!frozenPromotion && !frozenDraw) {
                    System.out.println("Resign");
                    if(turn == Color.White)
                        gameEnd('B');
                    else
                        gameEnd('W');
                    //
                    //This does not yet record or save the game
                    //
                }
                if(frozenDraw){
                    ((Button) findViewById(R.id.button3)).setText("Draw");
                    ((Button) findViewById(R.id.button4)).setText("Resign");
                    frozenDraw = false;
                }
            }
        });
        ((Button) findViewById(R.id.button8)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                intent.putExtra("gameRecords", gameRecords);
                startActivity(intent);
            }
        });

        //Pawn Promotion Buttons
        ((ImageView) findViewById(R.id.promoteRook)).setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               //Promote Rook
               if(frozenPromotion)
                   game.promotePawn('R');
               setBoard();
               frozenPromotion = false;
               blankPromotionTiles();
           }
        });
        ((ImageView) findViewById(R.id.promoteKnight)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Promote Knight
                if(frozenPromotion)
                    game.promotePawn('N');
                setBoard();
                frozenPromotion = false;
                blankPromotionTiles();
            }
        });
        ((ImageView) findViewById(R.id.promoteBishop)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Promote Bishop
                if(frozenPromotion)
                    game.promotePawn('B');
                setBoard();
                frozenPromotion = false;
                blankPromotionTiles();
            }
        });
        ((ImageView) findViewById(R.id.promoteQueen)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Promote Queen
                if(frozenPromotion)
                    game.promotePawn('Q');
                setBoard();
                frozenPromotion = false;
                blankPromotionTiles();
            }
        });

        //End screen click events
        ((RelativeLayout) findViewById(R.id.endScreen)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RelativeLayout) findViewById(R.id.endScreen)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.checkmateBlack)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.checkmateWhite)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.blackWins)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.whiteWins)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.draw)).setVisibility(View.GONE);
                game = new ChessGame();
                turn = Color.White;
                setBoard();
            }
        });

    }

    public void promotePawn(){
        if(turn == Color.White) {
            ((ImageView) findViewById(R.id.promoteRook)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wrook));
            ((ImageView) findViewById(R.id.promoteKnight)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wknight));
            ((ImageView) findViewById(R.id.promoteBishop)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wbishop));
            ((ImageView) findViewById(R.id.promoteQueen)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wqueen));
        }
        else {
            ((ImageView) findViewById(R.id.promoteRook)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.brook));
            ((ImageView) findViewById(R.id.promoteKnight)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bknight));
            ((ImageView) findViewById(R.id.promoteBishop)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bbishop));
            ((ImageView) findViewById(R.id.promoteQueen)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bqueen));
        }
    }

    public void blankPromotionTiles(){
        ((ImageView) findViewById(R.id.promoteRook)).setImageDrawable(null);
        ((ImageView) findViewById(R.id.promoteKnight)).setImageDrawable(new ColorDrawable(0));
        ((ImageView) findViewById(R.id.promoteBishop)).setImageDrawable(new ColorDrawable(0));
        ((ImageView) findViewById(R.id.promoteQueen)).setImageDrawable(new ColorDrawable(0));
    }

    public void setBoard(){
        for(int i = 0; i < 64; i++){
            Piece[][] p = game.getBoard();
            Piece[][] d = previousMove.getBoard();
            if(p[7 - (i / 8)][i % 8] != null)
                board.get(i).setImageDrawable(ContextCompat.getDrawable(this, p[7 - (i / 8)][i % 8].getDrawable()));
            if(p[7 - (i / 8)][i % 8] == null)
                board.get(i).setImageDrawable(null);
        }
    }

    public void gameEnd(char endState){
        if(endState == 'D'){
            ((RelativeLayout) findViewById(R.id.endScreen)).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.draw)).setVisibility(View.VISIBLE);
        }
        if(endState == 'W'){
            ((RelativeLayout) findViewById(R.id.endScreen)).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.whiteWins)).setVisibility(View.VISIBLE);
        }
        if(endState == 'B'){
            ((RelativeLayout) findViewById(R.id.endScreen)).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.blackWins)).setVisibility(View.VISIBLE);
        }
        if(endState == 'C'){
            ((RelativeLayout) findViewById(R.id.endScreen)).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.checkmateWhite)).setVisibility(View.VISIBLE);
        }
        if(endState == 'M'){
            ((RelativeLayout) findViewById(R.id.endScreen)).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.checkmateBlack)).setVisibility(View.VISIBLE);
        }

        if (endState == 'D' || endState == 'W' || endState == 'B' || endState == 'C' || endState == 'M') {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Enter game title");

            final EditText input = new EditText(MainActivity.this);
            builder.setView(input);

            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    String title = input.getText().toString();
                    thisGame.saveGame(title);
                    gameRecords.add(thisGame);
                    thisGame = new GameRecord();

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }


    }

    public void save(){

    }

    public void load(){

    }
}