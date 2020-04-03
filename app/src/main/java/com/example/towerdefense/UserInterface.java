package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
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
        textSize = screenSize.x / 25; // 50 is arbitrary

    }

    public void draw(Canvas canvas, Paint paint, GameState gameState) {
        //Draw the UI block on the top of screen
        //Draw rectangle on top of screen
        paint.setColor(Color.argb(100, 0, 0, 0));
        canvas.drawRect(0, 0, screenWidth, screenHeight / 8, paint);

        //Draw the UI Text
        paint.setTextSize(textSize / 2.0f);
        paint.setColor(Color.argb(255, 255, 255, 255));
        canvas.drawText("Wave:" + gameState.getWave() + "  " + "Level:" + gameState.getLevel(), textSize, textSize / 2, paint);
        canvas.drawText("Base HP: " + gameState.getHP(), textSize, textSize * 1.25f, paint);

    }

}
