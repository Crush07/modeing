package com.hysea.entity;

public class Point {
    CoordinateSystem coordinates;

    public Point(double x, double y, double z) {
        this.coordinates = new CoordinateSystem(x, y, z);
    }
}