package com.example.towerdefense;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Bullet extends MoveableGameObject {

    private float size;
    private PointF location;
    private int heading;
    private int speed;
    private int damage;

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
