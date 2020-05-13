package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import static com.example.towerdefense.FixedObjectType.Rockets;
import static com.example.towerdefense.MoveableObjectType.Rocket;

public class AntimatterRockets extends FixedGameObject {

    private float size;
    private Point location;
    private Rect hitBox;
    private int shotSpeed;
    private int reloading;
    private Bitmap bitmap;
    private Bitmap rotatedBitmap;
    private RectF rangeBox;
    private int range;
    private FixedObjectType towerType;

    private MoveableGameObject bullet = null;

    MoveableObjectFactory bulletFactory;



    AntimatterRockets(Context context, float blockSize, Point screenSize) {

        // Size is 4 times the normal "block" size ie.80 pixels
        this.size = blockSize * 3;

        bulletFactory = new MoveableObjectFactory(context, blockSize, screenSize);

        towerType = Rockets;

        create(context);
    }

    private void create(Context context) {

        //Medium shot speed
        shotSpeed = 100;
        reloading = 0;

        //Medium range
        range = 750;

        //Assign bitmap/scale design
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket_turret);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int)size, (int)size, false);

        hitBox = new Rect();
        rangeBox = new RectF();

    }


    public void shotCheck(RectF enemy) {

        //Calculate the angle to enemy - similar to heading
        int angle = (int) Math.toDegrees(Math.atan2(enemy.centerY() - location.y, enemy.centerX() - location.x + 10));

        angle += 90;

        //Rotate turret to look at enemy
        Matrix matrix = new Matrix();
        matrix.setTranslate(-size/2, -size/2);
        matrix.postRotate(angle);
        matrix.postTranslate(bitmap.getWidth()/2, bitmap.getHeight()/2);
        rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        //Increase reload and check if tower can shoot
        reloading++;

        if (reloading >= shotSpeed) {
            //If enemy in range
            //shoot at it
            if (enemy != null) {
                if (RectF.intersects(rangeBox, enemy)) {
                    shoot(enemy);
                }
            }
        }
    }

    private void shoot(RectF enemy) {
        //Now create/spawn a bullet of tower type - if bullet doesnt already exist
        if (bullet == null) {
            bullet = bulletFactory.build(Rocket);
            bullet.spawn(new PointF(hitBox.centerX(), hitBox.centerY()));
            bullet.markTarget(new PointF(enemy.centerX(), enemy.centerY()));

            reloading = 0;
        }
    }

    public MoveableGameObject getBullet() { return bullet; }

    public void moveBullets(long fps, RectF enemy) {

        if (bullet != null) {

            bullet.markTarget(new PointF(enemy.centerX(), enemy.centerY()));

            bullet.move(fps);

            //if bullet outside of range, delete it
            if (!rangeBox.contains(bullet.getHitBox())) {
                deleteBullet();
            }
        }

    }

    public void deleteBullet() {
        bullet = null;
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
        //Draw the bullet
        if (bullet != null)
            bullet.draw(canvas, paint);

        //Draw the tower
        canvas.drawBitmap(rotatedBitmap, location.x, location.y, paint);
    }

    public FixedObjectType getTowerType() { return towerType; }

}
