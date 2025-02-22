package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import static com.example.towerdefense.FixedObjectType.Rockets;
import static com.example.towerdefense.FixedObjectType.Cannon;
import static com.example.towerdefense.FixedObjectType.Turret;

public class InputController implements InputObserver {

    public InputController(GameBroadcaster broadcaster) {
        broadcaster.addObserver(this);
    }

    @Override
    public void input(MotionEvent event, GameState gameState, ArrayList<Rect> buttons) {

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
            else if (!gameState.getGameOver()) {

                //If player tapped to buy plasma tower - button 1
                if (buttons.get(0).contains(x, y)) {
                    gameState.addTower(Turret);
                }
                //If player tapped to buy Laser tower - button 2
                else if (buttons.get(1).contains(x, y)) {
                    gameState.addTower(Cannon);
                }
                //If player tapped to buy Rockets tower - button 3
                else if (buttons.get(2).contains(x, y)) {
                    gameState.addTower(Rockets);
                }
                //If player is placing tower - can't be on the path
                else if (gameState.getAddingTower()) {
                    Point placement = new Point(x, y);
                    gameState.placeTower(placement);
                }
                // Pause button - will change button number - last button
                else if (buttons.get(3).contains(x, y)) {

                    if (!gameState.getPaused()) {
                        gameState.pause();
                    }
                    else
                        gameState.resume();
                }

            }

        }
    }

}
