package com.hysea.entity.shape;

import com.hysea.core.Fraction;
import com.hysea.entity.Line;
import com.hysea.entity.Point;

public class Cylinder extends Shape{

    private double radius;

    private double height;

    private Point center;

    int subdivision;

    public Cylinder(double radius, double height, Point center, int subdivision) {
        this.radius = radius;
        this.height = height;
        this.center = center;
        this.subdivision = subdivision;
    }

    @Override
    public Shape create() {
        setLines(new Line[subdivision * 5]);

        Point centerOfTop = new Point(center.getX(), center.getY(), center.getZ().add(new Fraction(height).divide(2)));
        for (int i = 0; i < subdivision; i++) {
            double theta1 = 2.0 * Math.PI * i / subdivision;
            double theta2 = 2.0 * Math.PI * (i + 1) / subdivision;

            // 计算细分线的起点和终点
            Point p1 = new Point(centerOfTop.getX().add(radius * Math.cos(theta1)), centerOfTop.getY().add(radius * Math.sin(theta1)), centerOfTop.getZ());
            Point p2 = new Point(centerOfTop.getX().add(radius * Math.cos(theta2)), centerOfTop.getY().add(radius * Math.sin(theta2)), centerOfTop.getZ());

            Line line = new Line(new Point[]{p1, p2});

            getLines()[i + (subdivision * 0)] = line;

            Line line1 = new Line(new Point[]{centerOfTop, p1});

            getLines()[i + (subdivision * 1)] = line1;
        }

        Point centerOfBottom = new Point(center.getX(), center.getY(), center.getZ().subtract(new Fraction(height).divide(2)));
        for (int i = 0; i < subdivision; i++) {
            double theta1 = 2.0 * Math.PI * i / subdivision;
            double theta2 = 2.0 * Math.PI * (i + 1) / subdivision;

            // 计算细分线的起点和终点
            Point p1 = new Point(centerOfBottom.getX().add(radius * Math.cos(theta1)), centerOfBottom.getY().add(radius * Math.sin(theta1)), centerOfBottom.getZ());
            Point p2 = new Point(centerOfBottom.getX().add(radius * Math.cos(theta2)), centerOfBottom.getY().add(radius * Math.sin(theta2)), centerOfBottom.getZ());

            Line line = new Line(new Point[]{p1, p2});

            getLines()[i + (subdivision * 2)] = line;

            Line line1 = new Line(new Point[]{centerOfBottom, p1});

            getLines()[i + (subdivision * 3)] = line1;

            Line line2 = new Line(new Point[]{p1, getLines()[i].getPoints()[0]});

            getLines()[i + (subdivision * 4)] = line2;
        }
        return this;
    }
}
