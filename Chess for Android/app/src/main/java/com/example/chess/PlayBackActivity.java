package com.example.chess;

import static com.example.chess.Color.Black;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PlayBackActivity extends AppCompatActivity {

    ArrayList<ImageView> board = new ArrayList();
    ChessGame game = new ChessGame();
    private List<String> moves;
    private int moveIndex = 0;

    boolean inProgress;
    boolean selectedFirst = false;
    Color turn = Color.White;

    private King whiteKing;

    private King blackKing;

    boolean frozenPromotion = false;
    boolean frozenDraw = false;

    private Piece lastMoved;
    int tile1;
    private int lastRow, lastCol;

    private boolean drawOffered;

    int i = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playback);

        GameRecord selectedGame = (GameRecord) getIntent().getSerializableExtra("selectedGame");
        moves = selectedGame.getMoveList();



        while (i < 64) {
            board.add((ImageView) ((TableRow) ((TableLayout) findViewById(R.id.table)).getChildAt(i / 8)).getChildAt(i % 8));
            Button nextMoveButton = findViewById(R.id.button);
            nextMoveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(moveIndex < moves.size()) {

                        String pMove = moves.get(moveIndex);
                        MoveResult moveResult = game.move(turn, pMove);

                        setBoard();
                        //Ends AI movement sequence

                        //Check for checkmate
                        if(moveResult == MoveResult.CheckMate)
                        {
                            finish();
                        }
                        //Switch to next player's turn
                        if (turn == Color.White)
                            turn = Color.Black;
                        else
                            turn = Color.White;
                    }
                    else {
                        finish();
                    }
                    moveIndex++;

                }
            });
            i++;

        }

        Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setBoard(){
        for(int i = 0; i < 64; i++){
            Piece[][] p = game.getBoard();
            if(p[7 - (i / 8)][i % 8] != null)
                board.get(i).setImageDrawable(ContextCompat.getDrawable(this, p[7 - (i / 8)][i % 8].getDrawable()));
            if(p[7 - (i / 8)][i % 8] == null)
                board.get(i).setImageDrawable(null);
        }
    }
}
