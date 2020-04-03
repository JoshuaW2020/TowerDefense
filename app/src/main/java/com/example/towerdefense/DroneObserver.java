package com.example.towerdefense;

import android.graphics.RectF;

public class DroneObserver extends EnemyObserver{

    private MoveableGameObject enemyObject;

    DroneObserver(MoveableGameObject enemy) {
        this.enemyObject = enemy;
    }

    public RectF getHitBox() {
        return enemyObject.getHitBox();
    }

}
