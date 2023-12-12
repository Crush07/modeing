package com.hysea.entity;

public class Viewpoint {

    //视点位置
    private Point point;

    //缩放
    private double scale;

    //视线向量
    private Vector vector;

    //视平面x向量
    private Vector vectorX;

    //视平面y向量
    private Vector vectorY;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Vector getVectorX() {
        return vectorX;
    }

    public void setVectorX(Vector vectorX) {
        this.vectorX = vectorX;
    }

    public Vector getVectorY() {
        return vectorY;
    }

    public void setVectorY(Vector vectorY) {
        this.vectorY = vectorY;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }
}