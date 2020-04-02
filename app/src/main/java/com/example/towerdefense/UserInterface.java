package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class UserInterface {

    private int textSize;
    private int screenHeight;
    private int screenWidth;


    UserInterface(Point screenSize) {
        //Set the screen height/width/text size
        screenHeight = screenSize.y;
        screenWidth = screenSize.x;
        textSize = screenSize.x / 50; // 50 is arbitrary

    }

    public void draw(Canvas canvas, Paint paint) {

    }

}
