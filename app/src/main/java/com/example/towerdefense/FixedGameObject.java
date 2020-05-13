package com.example.towerdefense;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class FixedGameObject {

    private float size;
    private Point location;
    private Rect hitBox;
    private int shotSpeed;
    private int reloading;


    abstract void shotCheck(RectF enemy);

    private void shoot(RectF enemy) {
        //shoot a bullet

    }

    abstract MoveableGameObject getBullet();

    abstract void moveBullets(long fps, RectF enemy);

    abstract void deleteBullet();

    //For getting location of object - can't update location after creation
    abstract Point getLocation();

    //For retrieving hit box of tower
    abstract Rect getHitBox();

    abstract boolean spawn(Point screen, Point placement);

    // Every object needs to draw itself
    abstract void draw(Canvas canvas, Paint paint);

    abstract FixedObjectType getTowerType();


}
