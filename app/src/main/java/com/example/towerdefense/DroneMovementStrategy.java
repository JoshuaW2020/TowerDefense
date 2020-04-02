package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.PointF;

public class DroneMovementStrategy implements MovementStrategy {

    float screenHeight;
    float screenWidth;

    DroneMovementStrategy(Point screenSize) {
        this.screenHeight = screenSize.y;
        this.screenWidth = screenSize.x;
    }

    @Override
    public PointF move(PointF curLocation, int heading, int speed, int fps) {

        PointF newLocation = curLocation;

        //Actually move the object according to heading and speed
        //If heading is due North
        if (heading >= 0 && heading < 45) {
            newLocation.y -= speed / fps;
        }
        //If heading is NorthEast
        else if (heading >= 45 && heading < 90) {
            newLocation.y -= speed / fps;
            newLocation.x += speed / fps;
        }
        //If heading is due East
        else if (heading >= 90 && heading < 135) {
            newLocation.x += speed / fps;
        }
        //If heading is SouthEast
        else if (heading >= 135 && heading < 180) {
            newLocation.y += speed / fps;
            newLocation.x += speed / fps;
        }
        //If heading is due South
        else if (heading >= 180 && heading < 225) {
            newLocation.y += speed / fps;
        }
        //If heading is SouthWest
        else if (heading >= 225 && heading < 270) {
            newLocation.y += speed / fps;
            newLocation.x -= speed / fps;
        }
        //If heading is due West
        else if (heading >= 270 && heading < 315) {
            newLocation.x -= speed / fps;
        }
        //If heading is NorthWest
        else if (heading >= 315 && heading < 360) {
            newLocation.y -= speed / fps;
            newLocation.x -= speed / fps;
        }

        return newLocation;
    }



}
