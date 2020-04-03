package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class UserInterface {

    private int textSize;
    private int screenHeight;
    private int screenWidth;

    private ArrayList<Rect> buyButtons;


    UserInterface(Point screenSize) {
        //Set the screen height/width/text size
        screenHeight = screenSize.y;
        screenWidth = screenSize.x;
        textSize = screenSize.x / 25; // 50 is arbitrary

        createBuyButtons();
    }

    private void createBuyButtons() {
        int statsPadding = textSize * 8;
        int buttonWidth = screenWidth / 14;
        int buttonHeight = screenHeight / 9;
        int buttonPadding = screenWidth / 100;

        Rect buyPlasmaTurret = new Rect(statsPadding, 15, statsPadding + buttonWidth, buttonHeight);

        buyButtons = new ArrayList();
        buyButtons.add(buyPlasmaTurret);
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
        canvas.drawText("Money: " + gameState.getMoney(), textSize * 4f, textSize * 1.25f, paint);

        drawButtons(canvas, paint);

    }

    private void drawButtons(Canvas canvas, Paint paint) {
        //Set custom color for buttons
        paint.setColor(Color.argb(100, 255, 255, 255));

        for(Rect button : buyButtons) {
            canvas.drawRect(button, paint);
        }

        //Set colors back
        paint.setColor(Color.argb(255, 255, 255, 255));
    }

    public ArrayList<Rect> getBuyButtons() {
        return buyButtons;
    }

}
