package core;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class KMeans {

    public ArrayList<Point> points;
    public ArrayList<Cluster> clusters;

    private int num_points;
    private int num_clusters;

    public KMeans(int num_points, int num_clusters) {
        this.num_points = num_points;
        this.num_clusters = num_clusters;
        points = getRandomPoints();
        clusters = getRandomClusters();
        search();
    }

    private double getDistance(Point point, Point centroid) {
        double x = centroid.getX() - point.getX();
        double y = centroid.getY() - point.getY();
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void search() {
        boolean finish = false;

        do {

            clearClusters();
            ArrayList<Point> lastCentroids = getCentroids();
            assignCluster();
            calculateCentroids();
            ArrayList<Point> currentCentroids = getCentroids();
            double distance = 0;
            for (int i = 0; i < lastCentroids.size(); i++) {
                distance += getDistance(lastCentroids.get(i), currentCentroids.get(i));
            }
            if (distance == 0) {
                finish = true;
            }

        } while (!finish);
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private ArrayList<Point> getCentroids() {
        ArrayList<Point> centroids = new ArrayList(num_clusters);
        for (Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            Point point = new Point(aux.x, aux.y);
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster() {
        int cluster = 0;
        double distance = 0;
        for (Point point : points) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < num_clusters; i++) {
                Cluster c = clusters.get(i);
                distance = getDistance(point, c.getCentroid());
                if (distance < min) {
                    min = distance;
                    cluster = i;
                }
            }
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            ArrayList<Point> list = cluster.getPoints();
            int n_points = list.size();

            for (Point point : list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Point centroid = cluster.getCentroid();
            if (n_points > 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.x = (int) newX;
                centroid.y = (int) newY;
            }
        }
    }


    private ArrayList<Cluster> getRandomClusters() {
        ArrayList<Cluster> res = new ArrayList<Cluster>();
        for (int i = 0; i < num_clusters; i++) {
            Cluster cluster = new Cluster();
            Point centroid = points.get(i);
            cluster.setCentroid(centroid);
            res.add(cluster);
            points.remove(i);
        }
        return res;
    }

    private ArrayList<Point> getRandomPoints() {
        Random rand = new Random();
        ArrayList<Point> res = new ArrayList<Point>(num_points);
        for (int i = 0; i < num_points; i++) {
            int x = rand.nextInt(640);
            int y = rand.nextInt(480);
            res.add(new Point(x, y));
        }
        return res;
    }

}