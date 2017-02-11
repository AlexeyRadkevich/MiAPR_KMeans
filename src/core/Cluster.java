package core;

import java.awt.*;
import java.util.ArrayList;

public class Cluster {

    public ArrayList<Point> points;
    public Point centroid;

    public Cluster() {
        this.points = new ArrayList<>();
        this.centroid = null;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public Point getCentroid() {
        return centroid;
    }

    public void addPoint(Point point) {
        points.add(point);
    }


    public ArrayList<Point> getPoints() {
        return points;
    }

    public void clear() {
        points.clear();
    }
}