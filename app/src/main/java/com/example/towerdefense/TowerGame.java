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

// Acts as the top level controller calling on GameWorld for the objects and telling things what to do
class TowerGame extends SurfaceView implements Runnable {

    // Objects for the game loop/thread
    private Thread thread = null;
    // Control pausing between updates
    private long nextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean playing = false;
    private volatile boolean paused = true;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 100;
    private int NumBlocksHigh;
    private Point screenSize;

    // Objects for drawing
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    //World/Object declaration
    GameWorld gameWorld;


    public TowerGame(Context context, Point screenSize) {

        super(context);

        // Work out how many pixels each block is
        int blockSize = screenSize.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        NumBlocksHigh = screenSize.y / blockSize;
        this.screenSize = screenSize;

        gameWorld = new GameWorld(context, screenSize);


    }

    // Handles the game loop
    @Override
    public void run() {
        while (playing) {
            if (!paused) {
                // Update 30 times a second
                if (updateRequired()) {
                    update();
                }
            }

            draw();
        }
    }

    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 30 frames per second
        final long TARGET_FPS = 30;
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
