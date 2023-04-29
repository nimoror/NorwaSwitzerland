package com.strikedevx.norwayvsswitzerland;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.strikedevx.norwayvsswitzerland.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Separate the images by country
    private int[] norwayImages = {
            R.drawable.norway_image1,
            R.drawable.norway_image2,
            R.drawable.norway_image3,
            R.drawable.norway_image4,
            R.drawable.norway_image5,
            R.drawable.norway_image6,
            R.drawable.norway_image7,
            R.drawable.norway_image8,
            R.drawable.norway_image9,
            R.drawable.norway_image10,
            R.drawable.norway_image11,
            R.drawable.norway_image12,
            R.drawable.norway_image13,
            R.drawable.norway_image14,
            R.drawable.norway_image15
    };

    private int[] switzerlandImages = {
            R.drawable.switzerland_image1,
            R.drawable.switzerland_image2,
            R.drawable.switzerland_image3,
            R.drawable.switzerland_image4,
            R.drawable.switzerland_image5,
            R.drawable.switzerland_image6,
            R.drawable.switzerland_image7,
            R.drawable.switzerland_image8,
            R.drawable.switzerland_image9,
            R.drawable.switzerland_image10,
            R.drawable.switzerland_image11,
            R.drawable.switzerland_image12,
            R.drawable.switzerland_image13,
            R.drawable.switzerland_image14
    };

    // This list will hold the selected images and their corresponding boolean values.
    private List<Integer> images = new ArrayList<>();
    private List<Boolean> isNorway = new ArrayList<>();

    // Variables to track the score and the number of images shown
    private int score = 0;
    private int imageCount = 0;
    // Add a variable for lives
    private int lives = 3;


    // View elements
    private ImageView imageView;
    private TextView scoreTextView;
    private TextView correctWrongTextView;

    private Button playAgainButton;
    private Button norwayButton;
    private Button switzerlandButton;

    // Add new ImageView variables for the heart (life) symbols
    private ImageView heart1, heart2, heart3;  // New ImageViews for the heart symbols

    private TextView highScoreTextView; // New TextView for the high score

    private SharedPreferences sharedPref; // New SharedPreferences to store the high score
    private static final String HIGH_SCORE_KEY = "HIGH_SCORE"; // Key for high score in SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the SharedPreferences
        sharedPref = getSharedPreferences("HighScore", MODE_PRIVATE);

        // Initialize the highScoreTextView
        highScoreTextView = findViewById(R.id.highScoreTextView);


        // Initialize the ImageView variables
        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);

        // Initialize the playAgainButton
        playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        // Initialize views
        imageView = findViewById(R.id.imageView);
        scoreTextView = findViewById(R.id.scoreTextView);
        correctWrongTextView = findViewById(R.id.correctWrongTextView);

        norwayButton = findViewById(R.id.norwayButton);
        switzerlandButton = findViewById(R.id.switzerlandButton);

        // Set click listeners for buttons
        norwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        switzerlandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        // Start the game
        initializeGame();
    }


    private void resetGame() {
        initializeGame();
        playAgainButton.setVisibility(View.GONE);
        norwayButton.setVisibility(View.VISIBLE);
        switzerlandButton.setVisibility(View.VISIBLE);

        // Set the hearts back to visible
        heart1.setVisibility(View.VISIBLE);
        heart2.setVisibility(View.VISIBLE);
        heart3.setVisibility(View.VISIBLE);

        // Hide the high score
        highScoreTextView.setVisibility(View.GONE);
    }

    private void initializeGame() {
        // Reset score
        score = 0;

        // Reset lives to 3 at the start of each game
        lives = 3;
        heart1.clearColorFilter();
        heart2.clearColorFilter();
        heart3.clearColorFilter();

        // Clear the previous game's images and isNorway list
        images.clear();
        isNorway.clear();

        // Add all images from each category to the images and isNorway list.
        for (int i = 0; i < norwayImages.length; i++) {
            images.add(norwayImages[i]);
            isNorway.add(true); // Norway images are tagged as true
        }

        for (int i = 0; i < switzerlandImages.length; i++) {
            images.add(switzerlandImages[i]);
            isNorway.add(false); // Switzerland images are tagged as false
        }

        // Randomize the order of the images and isNorway lists in the same way
        long seed = System.nanoTime();
        Collections.shuffle(images, new Random(seed));
        Collections.shuffle(isNorway, new Random(seed));

        // Now start the game by showing the first image
        showNextImage();
    }

    // Method to check the user's guess
    private void checkAnswer(boolean isNorwayGuess) {
        if ((isNorwayGuess && isNorway.get(0)) || (!isNorwayGuess && !isNorway.get(0))) {
            score++;
            showCorrectWrong("Correct", Color.GREEN);
        } else {
            lives--;
            switch (lives) {
                case 2:
                    heart3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);  // Grey out the third heart
                    break;
                case 1:
                    heart2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);  // Grey out the second heart
                    break;
                case 0:
                    heart1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);  // Grey out the first heart
                    // The game ends if the player has no lives left
                    scoreTextView.setText("Game Over. Score: " + score);
                    break;
            }
            showCorrectWrong("Wrong", Color.RED);
        }
    }


    // Method to display whether the guess was correct or wrong
    private void showCorrectWrong(String message, int color) {
        correctWrongTextView.setText(message);
        correctWrongTextView.setTextColor(color);
        correctWrongTextView.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                correctWrongTextView.setVisibility(View.GONE);

                // Remove the shown image and its tag from the lists
                images.remove(0);
                isNorway.remove(0);

                // Check if the game should continue or end
                continueOrEndGame();
            }
        }, 1000);  // The feedback message disappears after 1 second.
    }

    // Method to display the next image and update the score
    private void showNextImage() {
        if (!images.isEmpty() && lives > 0) {
            imageView.setImageResource(images.get(0));
            scoreTextView.setText("Score: " + score);
        } else {
            // Check if the game should continue or end
            continueOrEndGame();
        }
    }


    private void continueOrEndGame() {
        if (!images.isEmpty() && lives > 0) {
            showNextImage();
        } else {
            // Game ends

            // Hide the heart symbols
            heart1.setVisibility(View.GONE);
            heart2.setVisibility(View.GONE);
            heart3.setVisibility(View.GONE);

            // Hide the Norway and Switzerland buttons
            norwayButton.setVisibility(View.GONE);
            switzerlandButton.setVisibility(View.GONE);

            // Show the Play again button
            playAgainButton.setVisibility(View.VISIBLE);

            // Get the current high score
            int highScore = sharedPref.getInt(HIGH_SCORE_KEY, 0);

            // Check if the player got a new high score
            if (score > highScore) {
                // Save the new high score
                sharedPref.edit().putInt(HIGH_SCORE_KEY, score).apply();
                highScoreTextView.setText("New Highscore: " + score);
            } else {
                highScoreTextView.setText("Highscore: " + highScore);
            }

            // Show the high score
            highScoreTextView.setVisibility(View.VISIBLE);

            if (lives <= 0) {
                // Game ends if player has no lives left
                scoreTextView.setText("Game Over. Score: " + score);
            } else {
                // Game ends if no images left
                scoreTextView.setText("You're a Pro! Score: " + score);
            }
        }
    }

}