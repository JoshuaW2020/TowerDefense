package com.example.towerdefense;

import android.util.Log;
import android.view.MotionEvent;

public class InputController implements InputObserver {

    public InputController(GameBroadcaster broadcaster) {
        broadcaster.addObserver(this);
    }

    @Override
    public void input(MotionEvent event, GameState gameState) {

        //Find out where there was a tap
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);

        int eventType = event.getAction() & MotionEvent.ACTION_MASK;

        //Player has lifted finger indicating a complete motion event
        if (eventType == MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP) {

            //If game is over - start new game
            if (gameState.getGameOver()) {

                gameState.startNewGame();
            }
        }
    }

}
