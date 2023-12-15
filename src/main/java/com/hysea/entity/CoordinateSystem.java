package com.hysea.entity;

public class CoordinateSystem {

    private double x;

    private double y;

    private double z;

    //坐标轴长度
    private double length;

    public CoordinateSystem(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        length = 500;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
