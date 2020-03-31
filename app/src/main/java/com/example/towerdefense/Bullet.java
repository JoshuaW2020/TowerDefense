package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Bullet extends MoveableGameObject{

    private int size;
    private Point location;
    private int heading;
    private int speed;

    Bullet() {
        super(MoveableObjectType.Bullet);
        create();
    }

    private void create() {

    }

    //For moving the moveable object
    public void move() {

    }

    public void draw(Canvas canvas, Paint paint) {

    }
}
