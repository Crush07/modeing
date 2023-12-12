package com.hysea.entity;

public class Line {

    public Line() {
    }

    public Line(Point[] points) {
        this.points = points;
    }

    private Point[] points;

    private ParametricEquation parametricEquation;

    /**
     * 直线方程参数式
     * x = mt + x0
     * y = nt + y0
     * z = pt + z0
     */
    public static class ParametricEquation {

        private double m;

        private double n;

        private double p;

        private double x0;

        private double y0;

        private double z0;

        public double getM() {
            return m;
        }

        public void setM(double m) {
            this.m = m;
        }

        public double getN() {
            return n;
        }

        public void setN(double n) {
            this.n = n;
        }

        public double getP() {
            return p;
        }

        public void setP(double p) {
            this.p = p;
        }

        public double getX0() {
            return x0;
        }

        public void setX0(double x0) {
            this.x0 = x0;
        }

        public double getY0() {
            return y0;
        }

        public void setY0(double y0) {
            this.y0 = y0;
        }

        public double getZ0() {
            return z0;
        }

        public void setZ0(double z0) {
            this.z0 = z0;
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
}
