package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import static com.example.towerdefense.MoveableObjectType.Drone;

public class CyborgDrone extends MoveableGameObject {

    private int health;
    private int speed;
    private int resistance;
    private int heading;
    private float size;                   //size of enemy on screen - a specific block/square size
    private Bitmap enemyImage;
    private PointF location = new PointF();   // current location on screen
    private MoveableObjectType objectType;

    //Movement strategy
    private MovementStrategyFactory movementStrategyFactory;
    private MovementStrategy movementStrategy;

    CyborgDrone(Context context, float blockSize, Point screenSize) {
        super(Drone);
        this.size = blockSize;
        create(context, screenSize);
    }

    private void create(Context context, Point screenSize) {
        health = 10;
        speed = 2;         //will make speed a part of the movement strategy later
        resistance = 0;     //should also make part of a strategy based on previous wave's resistance/tower damage

        //Assign bitmap/scale design
        this.enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.triangleEnemy);
        this.enemyImage = Bitmap.createScaledBitmap(enemyImage, (int)size, (int)size, false);

        //Type of object
        objectType = Drone;

        //Assign movement strategy
        movementStrategyFactory = new MovementStrategyFactory(screenSize);
        movementStrategy = movementStrategyFactory.getStrategy(objectType);

    }

    public boolean bulletCollision(PointF bulletLocation) {

        if(location == bulletLocation) {
            return true;
        }
        return false;
    }

    public void move(int fps) {
        location = movementStrategy.move(location, heading, speed, fps);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(enemyImage, location.x * size, location.y * size, paint);
    }

}
