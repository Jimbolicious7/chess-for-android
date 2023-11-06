package com.example.chess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private ArrayList<GameRecord> gameRecords;
    ArrayAdapter<GameRecord> arrayAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        gameRecords = (ArrayList<GameRecord>) getIntent().getSerializableExtra("gameRecords");

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        Button sortByDateButton = findViewById(R.id.sortByDate);
        sortByDateButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sortByDate();
            }
        });

        Button sortByTitleButton = findViewById(R.id.sortByTitle);
        sortByTitleButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sortByTitle();
            }
        });

        ListView listView = findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<GameRecord>(this, R.layout.list_item_textview, gameRecords) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View listItem = super.getView(position, convertView, parent);
                TextView gameRecordTitle = listItem.findViewById(R.id.list_item_text);
                gameRecordTitle.setText(gameRecords.get(position).toString());
                return listItem;
            }
        };
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListViewActivity.this, PlayBackActivity.class);
                intent.putExtra("selectedGame", gameRecords.get(position));
                startActivity(intent);
            }
        });

    }
    private void sortByDate() {
        Collections.sort(gameRecords, new Comparator<GameRecord>() {

            public int compare(GameRecord game1, GameRecord game2) {
                return game1.getDate().compareTo(game2.getDate());
            }
        });
        arrayAdapter.notifyDataSetChanged();
    }

    private void sortByTitle() {
        Collections.sort(gameRecords, new Comparator<GameRecord>() {

            public int compare(GameRecord game1, GameRecord game2) {
                return game1.getTitle().compareTo(game2.getTitle());
            }
        });
        arrayAdapter.notifyDataSetChanged();
    }
}