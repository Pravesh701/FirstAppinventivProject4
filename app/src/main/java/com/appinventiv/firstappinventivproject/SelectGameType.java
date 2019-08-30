package com.appinventiv.firstappinventivproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectGameType extends AppCompatActivity {
    private Button singlePlayerButton;
    private Button multiPlayerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game_type);

        singlePlayerButton =(Button) findViewById(R.id.btn_SelectGame);
        multiPlayerButton = (Button) findViewById(R.id.btn_SelectGame2);

        multiPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameType.this, MainActivity.class);
                startActivity(intent);
            }
        });

        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameType.this, SinglePlayerActivity.class);
                startActivity(intent);
            }
        });
    }
}
