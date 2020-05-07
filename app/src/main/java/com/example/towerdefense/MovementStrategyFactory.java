package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.PointF;

public class MovementStrategyFactory {

    private final MovementStrategy directMovementStrategy;

    MovementStrategyFactory(Point screenSize) {

        directMovementStrategy = new DirectMovementStrategy(screenSize);
    }

    public MovementStrategy getStrategy(MoveableObjectType type) {
        switch (type) {
            case Drone: return directMovementStrategy;
            case Soldier: return null;
            case Behemoth: return null;
            case Plasma: return directMovementStrategy;
            case Laser: return directMovementStrategy;
            case Rocket: return null;
            default : return null;
        }
    }


}
