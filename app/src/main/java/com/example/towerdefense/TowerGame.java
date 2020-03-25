package com.example.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TowerGame extends SurfaceView implements Runnable {

    // Objects for the game loop/thread
    private Thread thread = null;
    // Control pausing between updates
    private long nextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean playing = false;
    private volatile boolean paused = true;

    // Objects for drawing
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;


    public TowerGame(Context context, Point size) {

        super(context);


    }

    // Handles the game loop
    @Override
    public void run() {
        while (playing) {
            if (!paused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

            draw();
        }
    }

    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime = System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }

    public void update() {

    }

    public void draw() {

    }

    // Stop the thread
    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }


    // Start the thread
    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }
}
