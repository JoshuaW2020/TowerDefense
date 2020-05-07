package com.example.towerdefense;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    //The basic stats variables
    private int wave = 0;
    private int level = 0;
    // Each enemy that passes the defenses "damages" for 1 hp
    private int baseHP = 20;

    //Money
    private static int money = 10;
    private int[] towerCost = {10, 25, 50, 50, 150};


    //Tower being added
    private static FixedObjectType towerType = null;
    private static Point towerPlacement = null;

    //First time starting game?
    private boolean firstGame = true;

    //The actual game state variables
    private static volatile boolean paused = true;
    //private static volatile boolean waveWon = false;
    private static volatile boolean gameOver = true;
    private static volatile boolean threadRunning = false;
    private static volatile boolean addingTower = false;
    private static volatile boolean placedTower = false;


    public void startNewGame() {
        wave = 0;
        level = 1;
        baseHP = 20;
        money = 1000; // changed to 1000 for testing purposes

        firstGame = false;
        resume();
    }

    public boolean getFirstGame() { return firstGame; }

    private void endGame() {
        gameOver = true;
        paused = true;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
        gameOver = false;
    }

    public boolean getPaused() {
        return paused;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void addMoney(int enemyWorth) {
        money += enemyWorth;
    }

    public void loseHP() {
        baseHP--;
        if (baseHP == 0) {
            endGame();
        }
    }

    public FixedObjectType getTowerType() { return towerType; }

    public Point getTowerPlacement() { return towerPlacement; }

    public void addTower(FixedObjectType tower) {

        //Check if player has enough money to purchase tower
        switch (tower) {
            case Turret: {
                if (money >= towerCost[0]) {
                    addingTower = true;
                    towerType = tower;
                }
                break;
            }
            case Cannon: {
                if (money >= towerCost[1]) {
                    addingTower = true;
                    towerType = tower;
                }
                break;
            }
            case Emplacement: {
                if (money >= towerCost[2]) {
                    addingTower = true;
                    towerType = tower;
                }
                break;
            }
            case Rockets: {
                if (money >= towerCost[3]) {
                    addingTower = true;
                    towerType = tower;
                }
                break;
            }
            case Blackholes: {
                if (money >= towerCost[4]) {
                    addingTower = true;
                    towerType = tower;
                }
                break;
            }
        }

    }

    public void addedTower() {
        //placed tower, now take away money = cost of tower
        switch (towerType) {
            case Turret:
                money -= towerCost[0]; break;
            case Cannon:
                money -= towerCost[1]; break;
            case Emplacement:
                money -= towerCost[2]; break;
            case Rockets:
                money -= towerCost[3]; break;
            case Blackholes:
                money -= towerCost[4]; break;
            default: //do nothing
                break;
        }

        addingTower = false;
        towerType = null;
        placedTower = false;
        towerPlacement = null;
    }

    public void refundTower(FixedObjectType towerType) {

        switch (towerType) {
            case Turret:
                money += towerCost[0]; break;
            case Cannon:
                money += towerCost[1]; break;
            case Emplacement:
                money += towerCost[2]; break;
            case Rockets:
                money += towerCost[3]; break;
            case Blackholes:
                money += towerCost[4]; break;
            default: //do nothing
                break;
        }
    }

    public boolean getAddingTower() { return addingTower; }

    public boolean getPlacedTower() { return placedTower; }

    public void placeTower(Point placement) {
        placedTower = true;
        towerPlacement = placement;
    }

    public int getHP() {
        return baseHP;
    }

    public int getMoney() { return money; }

    public int getWave() {
        return wave;
    }

    public void nextWave() {
        wave++;
    }

    public void resetWaves() {
        wave = 0;
    }

    public int getLevel() {
        return level;
    }

    public void nextLevel() {
        level++;
    }

    public boolean getThreadRunning() {
        return threadRunning;
    }

    public void startThread() {
        threadRunning = true;
    }

    public void stopEverything() {
        paused = true;
        threadRunning = false;
    }



}
