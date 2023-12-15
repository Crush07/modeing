package com.hysea.entity;

import com.hysea.core.Fraction;

import java.awt.*;

public class Line {

    public Line() {
    }

    public Line(Point[] points) {
        this.dimensionType = DimensionType.D2;
        for (Point point : points) {
            if(!DimensionType.D2.equals(point.getDimensionType())){
                this.dimensionType = DimensionType.D3;
            }
        }
        this.points = points;
    }

    private DimensionType dimensionType;

    private Point[] points;

    private ParametricEquation parametricEquation;

    /**
     * 直线方程参数式
     * x = mt + x0
     * y = nt + y0
     * z = pt + z0
     */
    public static class ParametricEquation {

        private Fraction m;

        private Fraction n;

        private Fraction p;

        private Fraction x0;

        private Fraction y0;

        private Fraction z0;

        public Fraction getM() {
            return m;
        }

        public void setM(Fraction m) {
            this.m = m;
        }

        public Fraction getN() {
            return n;
        }

        public void setN(Fraction n) {
            this.n = n;
        }

        public Fraction getP() {
            return p;
        }

        public void setP(Fraction p) {
            this.p = p;
        }

        public Fraction getX0() {
            return x0;
        }

        public void setX0(Fraction x0) {
            this.x0 = x0;
        }

        public Fraction getY0() {
            return y0;
        }

        public void setY0(Fraction y0) {
            this.y0 = y0;
        }

        public Fraction getZ0() {
            return z0;
        }

        public void setZ0(Fraction z0) {
            this.z0 = z0;
        }

        @Override
        public String toString() {
            return "ParametricEquation{" +
                    "m=" + m +
                    ", n=" + n +
                    ", p=" + p +
                    ", x0=" + x0 +
                    ", y0=" + y0 +
                    ", z0=" + z0 +
                    '}';
        }
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public ParametricEquation getParametricEquation() {
        return parametricEquation;
    }

    public void setParametricEquation(ParametricEquation parametricEquation) {
        this.parametricEquation = parametricEquation;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    @Override
    public String toString() {
        return "Line{" +
                "parametricEquation=" + parametricEquation +
                '}';
    }

    public void draw(Graphics g){
        if (DimensionType.D2.equals(getDimensionType())){
            Point[] points = getPoints();
            g.drawLine((int)points[0].getX().getValue(),
                    (int)points[0].getY().getValue(),
                    (int)points[1].getX().getValue(),
                    (int)points[1].getY().getValue());
        }else{
            throw new RuntimeException();
        }
    }
}
