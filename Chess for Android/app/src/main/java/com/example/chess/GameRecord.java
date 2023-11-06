package com.example.chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GameRecord  implements Serializable {
    private String title;
    private Date date;

    private ArrayList<String> moves;


    public void saveGame(String title){
        this.title = title;
        this.date = new Date();
    }
    public GameRecord() {
        this.moves = new ArrayList<>();
    }

    public ArrayList<String> getMoveList()
    {
        return moves;
    }

    public String getMove(int index) {
        if (index < moves.size()) {
            return moves.get(index);
        } else {
            return null;
        }
    }

    public int getMoveCount() {
        return moves.size();
    }

    public void addMove(String move){
        moves.add(move);
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String toString()
    {
        return title + " on " + date;
    }


}
