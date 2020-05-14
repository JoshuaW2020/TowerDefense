package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import static com.example.towerdefense.MoveableObjectType.Behemoth;
import static com.example.towerdefense.MoveableObjectType.Drone;
import static com.example.towerdefense.MoveableObjectType.Soldier;

//Will be holding the game world objects
public class GameWorld {

    //Context for object creation
    Context context;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 75;        //Make 75 blocks wide - arbitrary
    private float NumBlocksHigh;
    Point screenSize;
    float blockSize;

    private FixedGameObjectFactory fixedObjectFactory;

    private ArrayList<MoveableGameObject> enemies;

    //enemy path
    private ArrayList<Rect> paths;

    private ArrayList<FixedGameObject> towers;

    private ArrayList<Rect> base_parts;
    private ArrayList<Bitmap> base_bitmap;

    GameWorld(Context context, Point screenSize) {
        this.context = context;

        // Work out how many pixels each block is
        float blockSize = screenSize.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        NumBlocksHigh = screenSize.y / blockSize;
        this.screenSize = screenSize;
        this.blockSize = blockSize;

        //Initialize the Array lists of objects
        enemies = new ArrayList<>();

        towers = new ArrayList();

        base_parts = new ArrayList();
        base_bitmap = new ArrayList();

        //initialize the tower factory
        fixedObjectFactory = new FixedGameObjectFactory(context, blockSize, screenSize);

    }

    //Create and place tower into game world
    public void addTower(FixedObjectType towerType, Point location, GameState gameState) {

        //Create the tower
        towers.add(fixedObjectFactory.build(towerType));

        //Spawn tower where placed
        towers.get(towers.size() - 1).spawn(screenSize, location);

        //If tower intersects with path, delete tower
        for (Rect path : paths) {
            if (towers.get(towers.size() - 1).getHitBox().intersect(path)) {
                //Refund for tower
                gameState.refundTower(towers.get(towers.size() - 1).getTowerType());
                //Now remove tower
                towers.remove(towers.size() - 1);

                //Tower was unable to be placed, deleted tower, now break out of checking loop - not great, but works
                break;
                }
        }
    }

    public void addEnemies(GameState gameState, Level level) {
        enemies.clear();

        enemies = level.getWaveEnemies(gameState);

        paths = level.getPath();

        //Also add base walls
        Rect base1 = new Rect(screenSize.x - 200, paths.get(2).top - 220, screenSize.x, paths.get(2).top - 20);
        Rect base2 = new Rect(screenSize.x - 200, paths.get(2).bottom + 20, screenSize.x, paths.get(2).bottom + 220);

        //Assign bitmap/scale design
        Bitmap base1Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.base_wall);
        base1Bitmap = Bitmap.createScaledBitmap(base1Bitmap, 200, 200, false);

        Bitmap base2Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.base_wall);
        base2Bitmap = Bitmap.createScaledBitmap(base2Bitmap, 200, 200, false);

        base_parts.add(base1);
        base_parts.add(base2);
        base_bitmap.add(base1Bitmap);
        base_bitmap.add(base2Bitmap);

    }

    public void moveEnemies(long fps) {
        //Move all enemies
        for (int i = 0; i < enemies.size(); i++) {

            //Move first enemy no matter what
            if (i == 0)
                enemies.get(i).move(fps);
            //Move drones
            else if (enemies.get(i).getEnemyType() == Drone) {
                //If the earlier enemy is no longer intersecting the next enemy then allow next enemy to move (only if enemy before is of the same type)
                if (enemies.get(i - 1).getEnemyType() != Drone)
                    enemies.get(i).move(fps);
                else if (!RectF.intersects(enemies.get(i - 1).getHitBox(), enemies.get(i).getHitBox()) && enemies.get(i - 1).getEnemyType() == Drone)
                    enemies.get(i).move(fps);

            }
            //Move soldiers
            else if (enemies.get(i).getEnemyType() == Soldier) {
                //If the earlier enemy is no longer intersecting the next enemy then allow next enemy to move (only if enemy before is of the same type)
                if (enemies.get(i - 1).getEnemyType() != Soldier)
                    enemies.get(i).move(fps);
                else if (!RectF.intersects(enemies.get(i - 1).getHitBox(), enemies.get(i).getHitBox()) && enemies.get(i - 1).getEnemyType() == Soldier)
                    enemies.get(i).move(fps);
            }
            //Move behemoths
            else if (enemies.get(i).getEnemyType() == Behemoth) {
                //If the earlier enemy is no longer intersecting the next enemy then allow next enemy to move (only if enemy before is of the same type)
                if (enemies.get(i - 1).getEnemyType() != Behemoth)
                    enemies.get(i).move(fps);
                else if (!RectF.intersects(enemies.get(i - 1).getHitBox(), enemies.get(i).getHitBox()) && enemies.get(i - 1).getEnemyType() == Behemoth)
                    enemies.get(i).move(fps);
            }

        }


    }

    public void checkEnemies(GameState gameState, Level level) {
        //check all enemies to see bullet collisions/deaths and if they made it to the objective/base

        for (int i = 0; i < enemies.size(); i++) {
            for (FixedGameObject tower : towers) {
                if (tower.getBullet() != null) {
                    if (enemies.get(i).bulletCollision(tower.getBullet())) {
                        tower.deleteBullet();
                    }
                }
            }

            //checks if enemy is dead
            if (enemies.get(i).getDead()) {
                gameState.addMoney(enemies.get(i).getWorth());
                enemies.remove(i);
            }
        }

        for (int i = 0; i < enemies.size(); i++) {

            //check if enemy made it to first objective
            if (level.getObjective(0).contains( (int) enemies.get(i).getHitBox().centerX(), (int) enemies.get(i).getHitBox().centerY())) {
                //check how many objectives there are - if > 1 -> set next target
                if (level.getNumOfObjectives() > 1) {
                    enemies.get(i).markTarget(level.getObjectivePoint(1));
                }
                //Checks if enemy made it to base ie.right side of map
                else if (enemies.get(i).getHitBox().left >= screenSize.x) {
                    gameState.loseHP();

                    // and delete the enemy that made it
                    enemies.remove(i);
                }
            }

            //check if enemy made it to second objective
            if (level.getObjective(1).contains( (int) enemies.get(i).getHitBox().left, (int) enemies.get(i).getHitBox().top)) {
                //check how many objectives there are - if > 2 -> set next target
                if (level.getNumOfObjectives() > 2) {
                    enemies.get(i).markTarget(level.getObjectivePoint(2));
                }
                //Checks if enemy made it to base ie.right side of map
                else if (enemies.get(i).getHitBox().left >= screenSize.x) {
                    gameState.loseHP();

                    // and delete the enemy that made it
                    enemies.remove(i);
                }
            }

            //check if enemy made it to third objective
            if (level.getObjective(2).contains( (int) enemies.get(i).getHitBox().centerX(), (int) enemies.get(i).getHitBox().centerY())) {
                //check how many objectives there are - if > 2 -> set next target
                if (level.getNumOfObjectives() > 3) {
                    enemies.get(i).markTarget(level.getObjectivePoint(3));
                }
                //Checks if enemy made it to base ie.right side of map
                else if (enemies.get(i).getHitBox().left >= screenSize.x) {
                    gameState.loseHP();

                    // and delete the enemy that made it
                    enemies.remove(i);
                }
            }

        }

    }

    public int getEnemies() {
        return enemies.size();
    }

    public void towersShoot() {

        if (enemies.size() >= 1) {
            for (FixedGameObject tower : towers) {
                tower.shotCheck(enemies.get(0).getHitBox());
            }
        }
    }

    public void moveBullets(long fps) {

        if (!enemies.isEmpty()) {
            for (FixedGameObject tower : towers) {
                tower.moveBullets(fps, enemies.get(0).getHitBox());
            }
        }

    }

    public void drawObjects(Canvas canvas, Paint paint) {

        paint.setColor(Color.argb(100, 240, 255, 255));

        //Draw the path
        if (paths != null) {
            for (Rect path : paths) {
                canvas.drawRect(path, paint);
            }
        }



        paint.setColor(Color.argb(255, 160, 160, 160));

        //Draw the different enemies
        for (MoveableGameObject enemy : enemies) {
            enemy.draw(canvas, paint);
        }

        //Now draw the towers / bullets
        for (FixedGameObject tower : towers) {
            tower.draw(canvas, paint);
        }

        //Draw the base walls
        for (int i = 0; i < base_parts.size(); i++) {
            canvas.drawBitmap(base_bitmap.get(i), base_parts.get(i).left, base_parts.get(i).top, paint);
        }

    }

}


