package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public abstract class FixedGameObject {

    private int size;
    private Point location;




    //For getting location of object - can't update location after creation
    abstract Point getLocation();

    // Every object needs to draw itself
    abstract void draw(Canvas canvas, Paint paint);


}
