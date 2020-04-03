package com.example.towerdefense;

import android.view.MotionEvent;

public interface InputObserver {

    void input(MotionEvent event, GameState gameState);

}
