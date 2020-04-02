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
        MoveableGameObject object= null;
        switch (type) {
            case Drone:
                object = new CyborgDrone(context, blockSize, screenSize);
                break;

            case Soldier:
                ;
                break;

            case Behemoth:
                ;
                break;

            case Bullet:
                ;
                break;

            default:
                //Exception: no compatible type found
                break;
        }
        return object;
    }
}
