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

        Rect pauseB = new Rect(screenWidth - buttonWidth - buttonPadding, 15, screenWidth - buttonPadding, buttonHeight);

        buyButtons = new ArrayList();
        buyButtons.add(buyPlasmaTurret);
        buyButtons.add(pauseB);
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

        //Draw main message based on game state

    }

    public void drawGameOver(Canvas canvas, Paint paint) {

        //Pick paint - dark red
        paint.setColor(Color.argb(255, 102, 0, 0));

        //Draw the text
        canvas.drawText("GAME OVER", screenWidth / 2, screenHeight / 2, paint);

        paint.setColor(Color.argb(255, 255, 255, 255));
    }

    public void drawFirstGame(Canvas canvas, Paint paint) {

        //pick paint - dark green
        paint.setColor(Color.argb(255, 0, 102, 0));

        //draw the text
        canvas.drawText("Welcome to the Space Defense Force, Commander", screenWidth / 4, screenHeight / 2, paint);

        //Set paint back
        paint.setColor(Color.argb(255, 255, 255, 255));
    }

    public void drawPaused(Canvas canvas, Paint paint) {

        //pick paint
        paint.setColor(Color.argb(255, 255, 255, 0));

        //Draw the text
        canvas.drawText("PAUSED", (screenWidth / 2) - 10, (screenHeight / 2) + 20, paint);

        //Set paint back
        paint.setColor(Color.argb(255, 255, 255, 255));

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
