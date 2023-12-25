package com.hysea.entity;

import com.hysea.core.Fraction;

import java.awt.*;
import java.util.Arrays;

public class Line extends CanBeSelectedObject{

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
        if(DimensionType.D2.equals(getDimensionType())){
            buildParametricEquationByPoints();
            buildSlopeExpression();
            buildCommonEquation();
        }
    }

    private DimensionType dimensionType;

    private Point[] points;

    private ParametricEquation parametricEquation;

    private SlopeExpression slopeExpression;

    private CommonEquation commonEquation;

    /**
     * 2D直线方程斜率式
     */
    public static class SlopeExpression{

        private Fraction k;

        private Fraction b;

        public Fraction getK() {
            return k;
        }

        public void setK(Fraction k) {
            this.k = k;
        }

        public Fraction getB() {
            return b;
        }

        public void setB(Fraction b) {
            this.b = b;
        }
    }

    /**
     * 2D,3D直线方程参数式
     * x = mt + x0
     * y = nt + y0
     * z = pt + z0
     */
    public static class ParametricEquation {
        private DimensionType dimensionType;

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

        public DimensionType getDimensionType() {
            return dimensionType;
        }

        public void setDimensionType(DimensionType dimensionType) {
            this.dimensionType = dimensionType;
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

    public static class CommonEquation{
        private Fraction a;

        private Fraction b;

        private Fraction c;

        public Fraction getA() {
            return a;
        }

        public void setA(Fraction a) {
            this.a = a;
        }

        public Fraction getB() {
            return b;
        }

        public void setB(Fraction b) {
            this.b = b;
        }

        public Fraction getC() {
            return c;
        }

        public void setC(Fraction c) {
            this.c = c;
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

    public CommonEquation getCommonEquation() {
        return commonEquation;
    }

    public void setCommonEquation(CommonEquation commonEquation) {
        this.commonEquation = commonEquation;
    }

    public SlopeExpression getSlopeExpression() {
        return slopeExpression;
    }

    public void setSlopeExpression(SlopeExpression slopeExpression) {
        this.slopeExpression = slopeExpression;
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
                "dimensionType=" + dimensionType +
                ", points=" + Arrays.toString(points) +
                ", parametricEquation=" + parametricEquation +
                ", slopeExpression=" + slopeExpression +
                ", commonEquation=" + commonEquation +
                ", isBeSelected=" + isBeSelected +
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

    /**
     * 通过this.points计算两点式
     * 如果getDimensionType为2D，则只设置m,n,x0,y0
     */
    public void buildParametricEquationByPoints(){
        if (DimensionType.D2.equals(getDimensionType())) {
            Point[] points = getPoints();

            Fraction x0 = points[0].getX();
            Fraction y0 = points[0].getY();
            Fraction x1 = points[1].getX();
            Fraction y1 = points[1].getY();

            // Calculate m and x0
            Fraction m = x1.subtract(x0);

            // Calculate n and y0
            Fraction n = y1.subtract(y0);

            // Set parametric equation
            ParametricEquation equation = new ParametricEquation();
            equation.setM(m);
            equation.setN(n);
            equation.setX0(x0);
            equation.setY0(y0);
            equation.setDimensionType(DimensionType.D2);

            setParametricEquation(equation);
        } else {
            throw new RuntimeException("Unsupported dimension type for this method.");
        }
    }

    public void buildSlopeExpression(){
        if (DimensionType.D2.equals(getDimensionType())) {
            Point[] points = getPoints();

            Fraction x0 = points[0].getX();
            Fraction y0 = points[0].getY();
            Fraction x1 = points[1].getX();
            Fraction y1 = points[1].getY();

            Fraction k = y1.subtract(y0).divide(x1.subtract(x0));

            Fraction b = y0.subtract(x0.multiply(k));

            SlopeExpression slopeExpression = new SlopeExpression();
            slopeExpression.setK(k);
            slopeExpression.setB(b);

            setSlopeExpression(slopeExpression);
        } else {
            throw new RuntimeException("Unsupported dimension type for this method.");
        }
    }

    /**
     * A = Y2 - Y1
     * B = X1 - X2
     * C = X2*Y1 - X1*Y2
     */
    public void buildCommonEquation(){
        if (DimensionType.D2.equals(getDimensionType())) {
            CommonEquation commonEquation = new CommonEquation();

            Fraction a = points[1].getY().subtract(points[0].getY());
            Fraction b = points[0].getX().subtract(points[1].getX());
            Fraction c = points[1].getX().multiply(points[0].getY()).subtract(points[0].getX().multiply(points[1].getY()));

            commonEquation.setA(a);
            commonEquation.setB(b);
            commonEquation.setC(c);

            setCommonEquation(commonEquation);
        }
    }
}
