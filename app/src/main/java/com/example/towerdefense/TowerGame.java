package com.example.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Acts as the top level controller calling on GameWorld for the objects and telling things what to do
class TowerGame extends SurfaceView implements Runnable, GameBroadcaster {

    // Objects for the game loop/thread
    private Thread thread = null;
    // Control pausing between updates
    private long nextFrameTime;
    private long fps;


    // Is the game currently playing and or paused?
    private volatile boolean playing = false;
    private volatile boolean paused = true;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 100;
    private float NumBlocksHigh;
    private Point screenSize;

    // Game View class declaration for viewing everything
    GameView gameView;
    //Game User interface display
    UserInterface userInterface;

    //World/Object declaration
    GameWorld gameWorld;

    //The GameState class
    GameState gameState;

    Level level;

    //An array list for all input observations made by UI/screen
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    InputController inputController;


    public TowerGame(Context context, Point screenSize) {

        super(context);

        gameView = new GameView(this);

        // Work out how many pixels each block is
        float blockSize = screenSize.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        NumBlocksHigh = screenSize.y / blockSize;
        this.screenSize = screenSize;

        gameWorld = new GameWorld(context, screenSize);

        inputController = new InputController(this);

        userInterface = new UserInterface(screenSize);

        //Initialize the game state
        gameState = new GameState();

        //Start the initial game thread
        //startThread();

        //Level - will add later
        level = new Level(context, screenSize);




    }

    // Handles the game loop
    @Override
    public void run() {
        while (gameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();


            if (!gameState.getPaused()) {

                //The call to update the game objects
                update();

            }

            //If game is over and paused
            if (gameState.getPaused() && gameState.getGameOver()) {

                //Draw game over screen
                gameView.drawGameOver(gameWorld, userInterface, gameState);

            }else {

                draw();
            }

            // Measure the frames per second in the usual way
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame >= 1) {
                final int MILLIS_IN_SECOND = 1000;
                fps = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    public void update() {

        if (gameWorld.getEnemies() == 0 && gameState.getHP() > 0) {

            gameState.nextWave();

            if (gameState.getWave() >= 5) {
                //Increase level
            }

            //Spawn next wave of enemies
            gameWorld.addDrones(gameState, level);
            //gameWorld.addSoldiers(gameState, level);



        }

        //Update everything in gameWorld
        gameWorld.moveEnemies(fps);
        gameWorld.towersShoot(fps);

        //Check if any enemies are dead, if so, delete them
        gameWorld.checkEnemies(gameState);

    }

    public void draw() {
        //Draw everything

        gameView.draw(gameWorld, userInterface, gameState);


    }

    public void addObserver(InputObserver observer) {

        inputObservers.add(observer);

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        for (InputObserver observer : inputObservers) {
            observer.input(motionEvent, gameState);
        }


        return true;
    }


    public void stopThread() {
        gameState.stopEverything();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("Exception","stopThread()" + e.getMessage());
        }
    }

    public void startThread() {
        gameState.startThread();
        playing = true;

        thread = new Thread(this);
        thread.start();
    }

    // Old and Unused
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

    //Old method - unused
    // Stop the thread
    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    //old method - unused
    // Start the thread
    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }
}
