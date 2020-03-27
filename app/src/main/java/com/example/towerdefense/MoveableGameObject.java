package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class MoveableGameObject {

    private int size;
    private Point location;
    private int heading;
    private int speed;


    //For moving the moveable object
    private void move() {}

    //For getting location of object, can move after creation
    abstract Point getLocation();

    // Every object needs to draw itself
    abstract void draw(Canvas canvas, Paint paint);
}
