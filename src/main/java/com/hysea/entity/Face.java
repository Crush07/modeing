package com.hysea.entity;

import com.hysea.core.Fraction;

public class Face {

    public Face() {
    }

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

        private Fraction A;

        private Fraction B;

        private Fraction C;

        private Fraction D;

        public Fraction getA() {
            return A;
        }

        public void setA(Fraction a) {
            A = a;
        }

        public Fraction getB() {
            return B;
        }

        public void setB(Fraction b) {
            B = b;
        }

        public Fraction getC() {
            return C;
        }

        public void setC(Fraction c) {
            C = c;
        }

        public Fraction getD() {
            return D;
        }

        public void setD(Fraction d) {
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