package com.hysea.entity;

public class Viewpoint {

    //视点位置
    private Point point;

    //缩放
    private int scale;

    //方向向量
    private Vector vector;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }
}