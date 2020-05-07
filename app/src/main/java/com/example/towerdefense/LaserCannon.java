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

import static com.example.towerdefense.FixedObjectType.Cannon;
import static com.example.towerdefense.MoveableObjectType.Laser;

public class LaserCannon extends FixedGameObject {

    private float size;
    private Point location;
    private Rect hitBox;
    private int shotSpeed;
    private int reloading;
    private Bitmap bitmap;
    private RectF rangeBox;
    private int range;
    private FixedObjectType towerType;

    private MoveableGameObject bullet = null;

    MoveableObjectFactory bulletFactory;



    LaserCannon(Context context, float blockSize, Point screenSize) {

        // Size is 4 times the normal "block" size ie.80 pixels
        this.size = blockSize * 3;

        bulletFactory = new MoveableObjectFactory(context, blockSize, screenSize);

        towerType = Cannon;

        create(context);
    }

    private void create(Context context) {

        //Long shot speed
        shotSpeed = 200;
        reloading = 0;

        //Huge range
        range = 1200;

        //Assign bitmap/scale design
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int)size, (int)size, false);

        hitBox = new Rect();
        rangeBox = new RectF();

    }

    public void shotCheck(RectF enemy) {

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
        //Now create/spawn a bullet of tower type - if bullet doesn't already exist
        if (bullet == null) {
            bullet = bulletFactory.build(Laser);
            bullet.spawn(new PointF(location.x - 10, location.y - 10));
            bullet.markTarget(new PointF(enemy.centerX(), enemy.centerY()));

            reloading = 0;
        }
    }

    public MoveableGameObject getBullet() { return bullet; }

    public void moveBullets(long fps, RectF enemy) {

        if (bullet != null) {


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
        canvas.drawBitmap(bitmap, location.x, location.y, paint);

        if (bullet != null)
            bullet.draw(canvas, paint);
    }

    public FixedObjectType getTowerType() { return towerType; }

}
