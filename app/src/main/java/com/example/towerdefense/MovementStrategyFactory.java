package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.PointF;

public class MovementStrategyFactory {

    private final MovementStrategy droneMovementStrategy;

    MovementStrategyFactory(Point screenSize) {

        droneMovementStrategy = new DroneMovementStrategy(screenSize);
    }

    public MovementStrategy getStrategy(MoveableObjectType type) {
        switch (type) {
            case Drone: return droneMovementStrategy;
            case Soldier: return null;
            case Behemoth: return null;
            case Bullet: return null;
            default : return null;
        }
    }


}
