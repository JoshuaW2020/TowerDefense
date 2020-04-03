package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Bullet {

    private float size;
    private PointF location;
    private int heading;
    private int speed;
    private int damage;

    //private MoveableObjectType type;
    //private MovementStrategyFactory movementStrategyFactory;
    //private MovementStrategy movementStrategy;

    Bullet() {
        //super(MoveableObjectType.Bullet);
        create();
    }

    private void create() {

    }

    public void spawn() {

    }

    //For moving the moveable object
    public void move(long fps) {

    }

    public void draw(Canvas canvas, Paint paint) {

    }
}
