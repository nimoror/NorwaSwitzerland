package com.strikedevx.norwayvsswitzerland;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;



public class StartActivity extends AppCompatActivity {

    private Button startButton;
    private TextView highScoreStartTextView;
    private EditText playerNameEditText;

    private FirebaseAuth auth;

    private SharedPreferences sharedPref;
    private static final String HIGH_SCORE_KEY = "HIGH_SCORE"; // Key for high score in SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        auth = FirebaseAuth.getInstance();

        sharedPref = getSharedPreferences("HighScore", MODE_PRIVATE);

        highScoreStartTextView = findViewById(R.id.highScoreStartTextView);
        playerNameEditText = findViewById(R.id.playerNameEditText);

        // Set the high score text
        int highScore = sharedPref.getInt(HIGH_SCORE_KEY, 0);
        highScoreStartTextView.setText("Highscore: " + highScore);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAnonymously();
            }
        });
    }

    private void signInAnonymously() {
        auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User is signed in
                            FirebaseUser user = auth.getCurrentUser();
                            // Save the player name associated with this user in your database
                            String playerName = playerNameEditText.getText().toString();

                            // Start the game
                            Intent intent = new Intent(StartActivity.this, MainActivity.class);
                            intent.putExtra("playerName", playerName); // Pass the player name to the next activity
                            startActivity(intent);
                        } else {
                            // Sign-in failed, handle exception
                            Toast.makeText(StartActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
