package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Enemy extends MoveableGameObject{

    private int health;
    private int speed;
    private int resistance;
    private int heading;
    private int size;                       //size of enemy on screen
    private Bitmap enemyImage;
    private Point location = new Point();   // current location on screen

    private Enemy(enemyBuilder builder) {
     this.enemyImage = builder.enemyImage;
     this.health = builder.health;
     this.speed = builder.speed;
     this.resistance = builder.resistance;
     this.size = builder.size;
    }

    private void move() {

    }

    Point getLocation() { return location; }

    void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(enemyImage, location.x * size, location.y * size, paint);
    }



    //Builder function to build the specific type of enemy
    public static class enemyBuilder {
        private int health;
        private int speed;
        private int resistance;
        private int size;                       //size of enemy on screen
        private Bitmap enemyImage;

        public enemyBuilder (int size, int health, int speed) {
            this.size = size;
            this.health = health;
            this.speed = speed;
        }

        public enemyBuilder resistance(int resistance) {
            this.resistance = resistance;
            return this;
        }

        public Enemy build (Context context) {

            this.enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.triangleEnemy);
            this.enemyImage = Bitmap.createScaledBitmap(enemyImage, size, size, false);

            Enemy newEnemy = new Enemy(this);
            return newEnemy;
        }
    }
}
