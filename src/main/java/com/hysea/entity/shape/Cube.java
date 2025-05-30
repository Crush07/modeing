
package com.hysea.entity.shape;

import com.hysea.core.Fraction;
import com.hysea.entity.Line;
import com.hysea.entity.Point;

public class Cube extends Shape{

    private double length;

    private double width;

    private double height;

    private Point center;

    public Cube(double length, double width, double height, Point center) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.center = center;
    }

    @Override
    public Shape create() {
        setLines(new Line[12]);

        Point centerOfTop = new Point(center.getX(), center.getY(), center.getZ().add(new Fraction(height).divide(2)));

        int[][] operationSymbol = new int[][] {
                {1,-1},
                {-1,-1},
                {-1,1},
                {1,1},
        };

        for (int i = 0; i < operationSymbol.length; i++) {
            int startIndex = i % operationSymbol.length;
            int endIndex = (i + 1) % operationSymbol.length;

            Point startPoint = new Point(new Fraction(width).divide(2).multiply(operationSymbol[startIndex][0]).add(center.getX()), new Fraction(length).divide(2).multiply(operationSymbol[startIndex][1]).add(center.getY()), centerOfTop.getZ());
            Point endPoint = new Point(new Fraction(width).divide(2).multiply(operationSymbol[endIndex][0]).add(center.getX()), new Fraction(length).divide(2).multiply(operationSymbol[endIndex][1]).add(center.getY()), centerOfTop.getZ());

            Line line = new Line(new Point[]{startPoint, endPoint});
            getLines()[i + (operationSymbol.length * 0)] = line;
        }

        Point centerOfBottom = new Point(center.getX(), center.getY(), center.getZ().subtract(new Fraction(height).divide(2)));

        for (int i = 0; i < operationSymbol.length; i++) {
            int startIndex = i % operationSymbol.length;
            int endIndex = (i + 1) % operationSymbol.length;

            Point startPoint = new Point(new Fraction(width).divide(2).multiply(operationSymbol[startIndex][0]).add(center.getX()), new Fraction(length).divide(2).multiply(operationSymbol[startIndex][1]).add(center.getY()), centerOfBottom.getZ());
            Point endPoint = new Point(new Fraction(width).divide(2).multiply(operationSymbol[endIndex][0]).add(center.getX()), new Fraction(length).divide(2).multiply(operationSymbol[endIndex][1]).add(center.getY()), centerOfBottom.getZ());

            Line line = new Line(new Point[]{startPoint, endPoint});
            getLines()[i + (operationSymbol.length * 1)] = line;
        }

        for (int i = 0; i < operationSymbol.length; i++) {
            Point startPoint = new Point(new Fraction(width).divide(2).multiply(operationSymbol[i][0]).add(center.getX()), new Fraction(length).divide(2).multiply(operationSymbol[i][1]).add(center.getY()), centerOfTop.getZ());
            Point endPoint = new Point(new Fraction(width).divide(2).multiply(operationSymbol[i][0]).add(center.getX()), new Fraction(length).divide(2).multiply(operationSymbol[i][1]).add(center.getY()), centerOfBottom.getZ());

            Line line = new Line(new Point[]{startPoint, endPoint});
            getLines()[i + (operationSymbol.length * 2)] = line;
        }

        return this;
    }
}
