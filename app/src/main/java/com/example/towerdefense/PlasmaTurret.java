package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class PlasmaTurret extends FixedGameObject{

    private float size;
    private Point location;
    private Rect hitBox;
    private int shotSpeed;
    private int reloading;
    private Bitmap bitmap;
    private RectF rangeBox;
    private int range;



    PlasmaTurret(Context context, float blockSize) {

        // Size is 4 times the normal "block" size ie.80 pixels
        this.size = blockSize * 4;

        create(context);
    }

    private void create(Context context) {

        shotSpeed = 200;
        reloading = 0;

        range = 300;

        //Assign bitmap/scale design
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int)size, (int)size, false);

        hitBox = new Rect();
        rangeBox = new RectF();

    }

    public void shotCheck() {

        //Increase reload and check if tower can shoot
        reloading++;

        if (reloading == shotSpeed) {
            //If enemy in range
            //shoot at it


        }
    }

    private void shoot() {

    }

    Rect getHitBox() { return hitBox; }

    Point getLocation() { return location; }

    public boolean spawn(Point screenSize, Point placement) {

        location = placement;

        hitBox.set(placement.x, placement.y, placement.x + (int)size, placement.y + (int)size);

        rangeBox.set(placement.x - range, placement.y - range, placement.x + range, placement.y + range);

        return false;
    }

    void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, location.x, location.y, paint);
    }

}
