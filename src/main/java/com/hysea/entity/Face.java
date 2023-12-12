package com.hysea.entity;

public class Face {

    public Face(Line[] lines) {
        this.lines = lines;
    }

    private Line[] lines;

    private CommonEquation commonEquation;

    /**
     * 平面方程一般式
     * Ax + By + Cz + D = 0
     */
    public static class CommonEquation{

        private double A;

        private double B;

        private double C;

        private double D;

        public double getA() {
            return A;
        }

        public void setA(double a) {
            A = a;
        }

        public double getB() {
            return B;
        }

        public void setB(double b) {
            B = b;
        }

        public double getC() {
            return C;
        }

        public void setC(double c) {
            C = c;
        }

        public double getD() {
            return D;
        }

        public void setD(double d) {
            D = d;
        }
    }

    public Line[] getLines() {
        return lines;
    }

    public void setLines(Line[] lines) {
        this.lines = lines;
    }

    public CommonEquation getCommonEquation() {
        return commonEquation;
    }

    public void setCommonEquation(CommonEquation commonEquation) {
        this.commonEquation = commonEquation;
    }
}