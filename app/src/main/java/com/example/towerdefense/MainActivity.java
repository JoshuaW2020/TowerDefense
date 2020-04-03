package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    // Declare an instance of SnakeGame
    TowerGame towerGame;

    // Set the game up
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new instance of the towerGame class
        towerGame = new TowerGame(this, size);

        // Make towerGame the view of the Activity
        setContentView(towerGame);
    }

    // Start the thread in towerGame
    @Override
    protected void onResume() {
        super.onResume();
        towerGame.startThread();
    }

    // Stop the thread in towerGame
    @Override
    protected void onPause() {
        super.onPause();
        towerGame.stopThread();
    }
}
