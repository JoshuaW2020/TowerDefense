package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

public class DirectMovementStrategy implements MovementStrategy{

    float screenHeight;
    float screenWidth;

    DirectMovementStrategy(Point screenSize) {
        this.screenHeight = screenSize.y;
        this.screenWidth = screenSize.x;
    }

    public int getHeading(PointF curLocation, PointF target) {
        int heading = (int) Math.toDegrees(Math.atan2(target.y - curLocation.y, target.x - curLocation.x));

        heading += 90;

        //Log.w("heading: ", "heading: " + heading);

        return heading;
    }

    @Override
    public PointF move(PointF curLocation, int heading, int speed, long fps) {

        heading -= 90;  //axis correction

        PointF newLocation = curLocation;

        float horizVelocity = (float) (Math.cos(Math.toRadians(heading)));
        float vertVelocity = (float) (Math.sin(Math.toRadians(heading)));

        newLocation.x += horizVelocity * speed / fps;
        newLocation.y += vertVelocity * speed / fps;

        //Log.w("new location", " x: " + newLocation.x + " y: " + newLocation.y);

        return newLocation;
    }

}
