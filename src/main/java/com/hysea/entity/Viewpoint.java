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

    /**
     * 初始化视点
     */
    public Viewpoint() {

        //视点在z轴上
        this.point = new Point(0,0,20);

        //缩放为1
        this.scale = 1;

        //视线向量从视点指向空间坐标系原点
        this.vector = new Vector(0,0,-1);

        //视平面x向量，x轴正方向
        this.vectorX = new Vector(1,0,0);

        //视平面y向量，y轴正方向
        this.vectorY = new Vector(0,1,0);


//        //视线向量从视点指向空间坐标系原点
//        this.vector = new Vector(0,-0.5,-1);
//
//        //视平面x向量，x轴正方向
//        this.vectorX = new Vector(1,0,0);
//
//        //视平面y向量，y轴正方向
//        this.vectorY = new Vector(0,1,0.5);

    }

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