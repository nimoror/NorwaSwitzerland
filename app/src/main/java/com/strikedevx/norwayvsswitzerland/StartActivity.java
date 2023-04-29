package com.strikedevx.norwayvsswitzerland;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private Button startButton;
    private TextView highScoreStartTextView;

    private SharedPreferences sharedPref;
    private static final String HIGH_SCORE_KEY = "HIGH_SCORE"; // Key for high score in SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sharedPref = getSharedPreferences("HighScore", MODE_PRIVATE);

        highScoreStartTextView = findViewById(R.id.highScoreStartTextView);

        // Set the high score text
        int highScore = sharedPref.getInt(HIGH_SCORE_KEY, 0);
        highScoreStartTextView.setText("Highscore: " + highScore);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
