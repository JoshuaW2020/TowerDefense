package com.example.towerdefense;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private Context context;
    private Point screenSize;
    private float blockSize;

    private ArrayList<Rect> path;
    private ArrayList<Rect> objectives;

    Map(Context context, Point screenSize, float blockSize) {

        this.context = context;
        this.screenSize = screenSize;
        this.blockSize = blockSize * 2;

        path = new ArrayList();
        objectives = new ArrayList();

        //For now just create the path here
        Rect path1 = new Rect(-20, screenSize.y / 2, (screenSize.x / 2) + (int) this.blockSize, screenSize.y / 2 + (int) this.blockSize);
        Rect objective1 = new Rect(path1.right - (int) blockSize, path1.top, path1.right, path1.bottom);

        Rect path2 = new Rect(path1.right - (int) this.blockSize, screenSize.y / 2, path1.right, screenSize.y / 2 + 300 + (int) this.blockSize);
        Rect objective2 = new Rect(path2.left, path2.bottom - (int) this.blockSize, path2.right, path2.bottom);

        Rect path3 = new Rect(path2.left, path2.bottom - (int) this.blockSize, screenSize.x + (int) this.blockSize, path2.bottom);
        Rect objective3 = new Rect(path3.right - (int) this.blockSize, path3.top, path3.right, path3.bottom);

        path.add(path1);
        objectives.add(objective1);

        path.add(path2);
        objectives.add(objective2);

        path.add(path3);
        objectives.add(objective3);
    }

    public ArrayList<Rect> getPath() { return path; }

    public Rect getObjective(int i) { return objectives.get(i); }

    public PointF getSpawnPoint() { return new PointF(path.get(0).left, path.get(0).top); }

    public int getNumOfObjectives() { return objectives.size(); }

    public PointF getObjectivePoint(int i) { return new PointF(objectives.get(i).centerX() - (this.blockSize / 2), objectives.get(i).centerY() - (this.blockSize / 2)); }

}


