package com.appinventiv.firstappinventivproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Button[][] buttons = new Button[3][3];

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView playerTurn_MainActivity;
    private Button buttonReset;

    private boolean player1Turn = true;

    ProgressDialog progressDialog;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = (TextView) findViewById(R.id.textView);
        textViewPlayer2 = (TextView) findViewById(R.id.textView2);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        playerTurn_MainActivity = (TextView) findViewById(R.id.playerTurn_MainActivity);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(LOG_TAG, "Reset Button works fine");

                resetGame();

                Log.d(LOG_TAG, "Hello World");

                Toast.makeText(MainActivity.this, "Reset to Initial", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
            playerTurn_MainActivity.setText("Player 2 Turn");
        } else {
            ((Button) v).setText("O");
            playerTurn_MainActivity.setText("Player 1 Turn");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Great Player 1 , You WIN!");
                progressDialog.setMessage("Press for next game");
                progressDialog.show();
                player1Wins();

            } else {
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Great Player 2 , You WIN!");
                progressDialog.setMessage("Press for next game");
                progressDialog.show();
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private void player2Wins() {
        player2Points++;
        updatePointsText();
        resetBoard();
    }

    private void player1Wins() {
        player1Points++;
        updatePointsText();
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        playerTurn_MainActivity.setText("Player 1 Turn");
        resetBoard();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }


    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        playerTurn_MainActivity.setText("Player 1 Turn");
        updatePointsText();
        resetBoard();
    }
}
