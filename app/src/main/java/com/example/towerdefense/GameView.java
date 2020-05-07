package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView {
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private float defaultTextSize;
    private Bitmap background;

    GameView(SurfaceView surfaceHolder, Context context, Point screenSize) {
        this.surfaceHolder = surfaceHolder.getHolder();
        paint = new Paint();
        defaultTextSize = paint.getTextSize();

        //Assign bitmap/scale design
        this.background = BitmapFactory.decodeResource(context.getResources(), R.drawable.moon_map);
        this.background = Bitmap.createScaledBitmap(background, screenSize.x, screenSize.y, false);
    }

    public void draw(GameWorld gameWorld, UserInterface UI, GameState gameState) {

        //Draw the screen
        if (surfaceHolder.getSurface().isValid()) {

            //Draw background - simple for now (grey) - always want to display map background
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(background, 0, 0, paint);

            //If playing game then draw everything without a main message
            if (!gameState.getGameOver()) {

                //Draw all the game world objects
                gameWorld.drawObjects(canvas, paint);

                //Draw the UI
                UI.draw(canvas, paint, gameState);

            }
            //if first game then display initial message / tap to play
            else if (gameState.getGameOver() && gameState.getFirstGame()) {

                //change text size
                paint.setTextSize(400f);

                gameWorld.drawObjects(canvas, paint);
                UI.draw(canvas, paint, gameState);

                // Draw main screen
                UI.drawFirstGame(canvas, paint);

                //change text size back
                paint.setTextSize(defaultTextSize);
            }
            // if game is over - display game over screen/ tap to restart
            else if (gameState.getGameOver()) {

                //change text size
                paint.setTextSize(400f);

                gameWorld.drawObjects(canvas, paint);
                UI.draw(canvas, paint, gameState);

                //Draw main screen
                UI.drawGameOver(canvas, paint);

                //change text size back
                paint.setTextSize(defaultTextSize);
            }
            //If game is paused
            else if (gameState.getPaused()) {

                gameWorld.drawObjects(canvas, paint);
                UI.draw(canvas, paint, gameState);

                //Draw main screen
                UI.drawPaused(canvas, paint);

            }

            // Always want to post graphics
            surfaceHolder.unlockCanvasAndPost(canvas);
        }


    }
}
