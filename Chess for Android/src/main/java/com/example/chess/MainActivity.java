package com.example.chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    //
    // Castling, promoting pawns, and enpassant do not work
    //
    ArrayList<ImageView> board = new ArrayList();

    //moves stores every move made, moves.get(0).get(0) is the first tile in move 1, moves.get(0).get(1) is tile 2 in move 1
    //moves.get(1).get(0) is the first move of the previous game, loaded from file
    ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
    boolean selectedFirst = false;
    int tile1;
    Color turn = Color.White;

    ChessGame game = new ChessGame();

    ChessGame previousMove = new ChessGame();

    private boolean AIMoving = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        load();

        int i = 0;
        while(i < 64) {
            board.add((ImageView)((TableRow)((TableLayout)findViewById(R.id.table)).getChildAt(i/8)).getChildAt(i%8));
            int finalI = i;
            board.get(i).setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    System.out.println("Moving");
                    if(!selectedFirst){
                        selectedFirst = true;
                        board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(153, 255, 153));
                        tile1 = finalI;
                    }
                    else {
                        String pMove = ((char)(tile1 % 8 + (int)'a') + "" + (8 - (tile1 / 8))) + " " + (char)(finalI % 8 + (int)'a') + (8 - (finalI / 8));
                        selectedFirst = false;
                        if (finalI % 2 == 0 && (finalI / 8) % 2 == 0)
                            board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(255, 255, 255));
                        else if(finalI % 2 == 0 && (finalI / 8) % 2 == 1)
                            board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(70, 70, 70));
                        else if(finalI % 2 == 1 && (finalI / 8) % 2 == 0)
                            board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(70, 70, 70));
                        else
                            board.get(finalI).setBackgroundColor(android.graphics.Color.rgb(255, 255, 255));
                        if (tile1 % 2 == 0 && (tile1 / 8) % 2 == 0)
                            board.get(tile1).setBackgroundColor(android.graphics.Color.rgb(255, 255, 255));
                        else if(tile1 % 2 == 0 && (tile1 / 8) % 2 == 1)
                            board.get(tile1).setBackgroundColor(android.graphics.Color.rgb(70, 70, 70));
                        else if(tile1 % 2 == 1 && (tile1 / 8) % 2 == 0)
                            board.get(tile1).setBackgroundColor(android.graphics.Color.rgb(70, 70, 70));
                        else
                            board.get(tile1).setBackgroundColor(android.graphics.Color.rgb(255, 255, 255));
                        previousMove.setGame(game);
                        MoveResult moveResult = game.move(turn, pMove);
                        if(!(moveResult == MoveResult.Illegal)) {
                            board.get(finalI).setImageDrawable(board.get(tile1).getDrawable());
                            board.get(tile1).setImageDrawable(null);
                            setBoard();
                            AIMoving = false;
                            if (turn == Color.White)
                                turn = Color.Black;
                            else
                                turn = Color.White;
                            save();
                        }
                    }
                }
            });
            i++;
        }
        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                game.setGame(previousMove);
                if(turn == Color.White)
                    turn = Color.Black;
                else
                    turn = Color.White;
                setBoard();
            }
        });
        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                System.out.println("AI");
                AIMoving = true;
                game.AIMove(turn);
                setBoard();
                if(turn == Color.White)
                    turn = Color.Black;
                else
                    turn = Color.White;
            }
        });
        ((Button)findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                System.out.println("Draw");
                game.offerDraw();
            }
        });
        ((Button)findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                System.out.println("Resign");
            }
        });
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

    public void save(){

    }

    public void load(){

    }
}