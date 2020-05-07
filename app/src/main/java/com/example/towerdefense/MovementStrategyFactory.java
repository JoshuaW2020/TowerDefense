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
            case Soldier: return directMovementStrategy;
            case Behemoth: return directMovementStrategy;
            case Plasma: return directMovementStrategy;
            case Laser: return directMovementStrategy;
            case Rocket: return directMovementStrategy;
            default : return null;
        }
    }


}
