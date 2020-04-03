package com.example.towerdefense;

import android.graphics.PointF;

public interface MovementStrategy {

    PointF move(PointF curLocation, int heading, int speed, long fps);

}
