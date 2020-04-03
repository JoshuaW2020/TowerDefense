package com.example.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import static com.example.towerdefense.MoveableObjectType.Plasma;

public class PlasmaBullet extends MoveableGameObject {

    private float size;
    private PointF location;
    private int heading;
    private int speed;
    private int damage;


    PlasmaBullet(Context context, float blockSize, Point screenSize) {

        super(Plasma);
        this.size = blockSize / 2;  //Half the regular block size

        create();
    }

    private void create() {

    }

    public void spawn(Point location) {

    }

    //For moving the moveable object
    public void move(long fps) {

    }

    public void draw(Canvas canvas, Paint paint) {

    }
}
