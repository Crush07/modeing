package com.hysea.entity;

import com.hysea.core.Fraction;

public class Vector {

    private Fraction x;

    private Fraction y;

    private Fraction z;

    public Vector(Fraction x, Fraction y, Fraction z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Vector(double x, double y, double z) {
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

    /**
     * 向量相加
     * @param vector 被加向量
     * @return
     */
    public Vector add(Vector vector){
        return new Vector(this.getX().add(vector.getX()),
                this.getY().add(vector.getY()),
                this.getZ().add(vector.getZ()));
    }

    /**
     * 向量的长度
     * @return
     */
    public Fraction getSize(){
        return new Fraction(Math.sqrt(this.getX().multiply(this.getX())
                .add(this.getY().multiply(this.getY()))
                .add(this.getZ().multiply(this.getZ())).getValue()));
    }

    public void setSize(Fraction size){
        Fraction scale = size.divide(getSize());
        this.setX(this.getX().multiply(scale));
        this.setY(this.getY().multiply(scale));
        this.setZ(this.getZ().multiply(scale));
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
