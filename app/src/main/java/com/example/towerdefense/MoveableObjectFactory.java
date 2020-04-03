package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;

public class MoveableObjectFactory {

    private Context context;
    private float blockSize;
    private Point screenSize;

    MoveableObjectFactory(Context context, float blockSize, Point screenSize) {
        this.context = context;
        this.blockSize = blockSize;
        this.screenSize = screenSize;
    }

    public MoveableGameObject build(MoveableObjectType type) {
        switch (type) {
            case Drone:
                return new CyborgDrone(context, blockSize, screenSize);

            case Soldier:
                ;
                break;

            case Behemoth:
                ;
                break;

            case Plasma:
                ;
                break;

            default:
                //Exception: no compatible type found
                return null;
        }
        return null;
    }
}
