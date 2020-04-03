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

    public void draw(GameWorld gameWorld, UserInterface UI, GameState gameState) {

        //Draw the screen
        if (surfaceHolder.getSurface().isValid()) {

            //Draw background - simple for now (grey)
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 160, 160, 160));

            //Draw all the game world objects
            gameWorld.drawObjects(canvas, paint);

            //Draw the UI
            UI.draw(canvas, paint, gameState);



            surfaceHolder.unlockCanvasAndPost(canvas);
        }


    }

    public void drawGameOver(GameWorld gameWorld, UserInterface UI, GameState gameState) {

        //Draw screen w/ objects/UI still
        draw(gameWorld, UI, gameState);

        //Now draw game over text

    }
}
