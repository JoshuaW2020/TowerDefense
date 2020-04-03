package com.example.towerdefense;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

public interface InputObserver {

    void input(MotionEvent event, GameState gameState, ArrayList<Rect> buttons);

}
