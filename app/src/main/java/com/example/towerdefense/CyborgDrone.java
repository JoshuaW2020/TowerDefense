package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class CyborgDrone extends MoveableGameObject{

    private int health;
    private int speed;
    private int resistance;
    private int heading;
    private int size;                   //size of enemy on screen - a specific block/square size
    private Bitmap enemyImage;
    private Point location = new Point();   // current location on screen

    CyborgDrone(Context context, int blockSize) {
        super(MoveableObjectType.Drone);
        this.size = blockSize;
        create(context);
    }

    private void create(Context context) {
        health = 10;
        speed = 20;         //will make speed a part of the movement strategy later
        resistance = 0;     //should also make part of a strategy based on previous wave's resistance/tower damage

        //Assign bitmap/scale design
        this.enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.triangleEnemy);
        this.enemyImage = Bitmap.createScaledBitmap(enemyImage, size, size, false);


    }

    public void move() {

    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(enemyImage, location.x * size, location.y * size, paint);
    }

}
