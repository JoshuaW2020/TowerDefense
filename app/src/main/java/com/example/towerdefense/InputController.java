package com.example.towerdefense;

import android.view.MotionEvent;

public class InputController implements InputObserver {

    public InputController(GameBroadcaster broadcaster) {
        broadcaster.addObserver(this);
    }

    @Override
    public void input(MotionEvent event) {

        //Find out where there was a tap
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);


    }

}
