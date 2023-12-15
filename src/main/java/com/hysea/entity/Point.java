package com.hysea.entity;

import com.hysea.core.Fraction;

public class Point {

    private DimensionType dimensionType;

    private Fraction x;

    private Fraction y;

    private Fraction z;

    public Point(Fraction x, Fraction y) {
        this.x = x;
        this.y = y;
        this.z = new Fraction(0);
        this.dimensionType = DimensionType.D2;
    }

    public Point(double x, double y) {
        this.x = new Fraction(x);
        this.y = new Fraction(y);
        this.z = new Fraction(0);
        this.dimensionType = DimensionType.D2;
    }

    public Point(Fraction x, Fraction y, Fraction z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double x, double y, double z) {
        this.x = new Fraction(x);
        this.y = new Fraction(y);
        this.z = new Fraction(z);
    }

    public Fraction getX() {
        return x;
    }

    public void setX(Fraction x) {
        this.x = x;
    }

    public Fraction getY() {
        return y;
    }

    public void setY(Fraction y) {
        this.y = y;
    }

    public Fraction getZ() {
        return z;
    }

    public void setZ(Fraction z) {
        this.z = z;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    @Override
    public String toString() {
        return "Point{" +
                "dimensionType=" + dimensionType +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}