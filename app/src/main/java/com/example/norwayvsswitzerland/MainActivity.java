package com.example.norwayvsswitzerland;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
            R.drawable.norway_image12
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
    };

    // This list will hold the selected images and their corresponding boolean values.
    private List<Integer> images = new ArrayList<>();
    private List<Boolean> isNorway = new ArrayList<>();

    // Variables to track the score and the number of images shown
    private int score = 0;
    private int imageCount = 0;

    // View elements
    private ImageView imageView;
    private TextView scoreTextView;
    private TextView correctWrongTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        imageView = findViewById(R.id.imageView);
        scoreTextView = findViewById(R.id.scoreTextView);
        correctWrongTextView = findViewById(R.id.correctWrongTextView);

        Button norwayButton = findViewById(R.id.norwayButton);
        Button switzerlandButton = findViewById(R.id.switzerlandButton);

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

    private void initializeGame() {
        // Reset score and image count
        score = 0;
        imageCount = 0;
        // Clear the previous game's images and isNorway list
        images.clear();
        isNorway.clear();

        // Select five random images from each category and add them to the images and isNorway list.
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomNorwayIndex = random.nextInt(norwayImages.length);
            images.add(norwayImages[randomNorwayIndex]);
            isNorway.add(true); // Norway images are tagged as true

            int randomSwitzerlandIndex = random.nextInt(switzerlandImages.length);
            images.add(switzerlandImages[randomSwitzerlandIndex]);
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
        // If the guess matches the tag of the current image, increment the score
        if ((isNorwayGuess && isNorway.get(imageCount)) || (!isNorwayGuess && !isNorway.get(imageCount))) {
            score++;
            showCorrectWrong("Correct", Color.GREEN);
        } else {
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

                // Now we can increment imageCount and show the next image.
                imageCount++;
                // If all images have been shown, display the final score
                if (imageCount == images.size()) {
                    scoreTextView.setText("The End. Your score: " + score);
                // If there are still images left, show the next one
                } else {
                    showNextImage();
                }
            }
        }, 1000);  // The feedback message disappears after 1 second.
    }

    // Method to display the next image and update the score
    private void showNextImage() {
        imageView.setImageResource(images.get(imageCount));
        scoreTextView.setText("Score: " + score);
    }
}
