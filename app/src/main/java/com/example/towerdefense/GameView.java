package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView {
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    GameView(SurfaceView surfaceHolder) {
        this.surfaceHolder = surfaceHolder.getHolder();
        paint = new Paint();
    }

    public void draw(GameWorld gameWorld) {

        //Draw the screen
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            //Draw all the game world objects
            gameWorld.drawObjects(canvas, paint);


        }


    }
}
