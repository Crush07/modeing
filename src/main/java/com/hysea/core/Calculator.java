package com.hysea.core;

import com.hysea.entity.Face;
import com.hysea.entity.Line;
import com.hysea.entity.Point;
import com.hysea.entity.Vector;

import java.util.List;

public class Calculator {

    /**
     * 根据直线参数式方程和平面一般式方程计算交汇点
     * @param line 线
     * @param face 面
     * @return 交汇点
     */
    Point getPointByParametricEquationOfLineAndCommonEquationOfFace(Line line, Face face) {

        //TODO 判断线是否在面内，抛出无穷解异常

        //TODO 判断线是否和面平行，抛出无解异常

        Line.ParametricEquation parametricEquation = line.getParametricEquation();
        Face.CommonEquation commonEquation = face.getCommonEquation();

        double t = -1 * (
                commonEquation.getA() * parametricEquation.getX0()
                        + commonEquation.getB() * parametricEquation.getY0()
                        + commonEquation.getC() * parametricEquation.getZ0()
                        + commonEquation.getD()
                ) / (commonEquation.getA() * parametricEquation.getM()
                + commonEquation.getB() * parametricEquation.getN()
                + commonEquation.getC() * parametricEquation.getP());

        Point point = new Point(parametricEquation.getM() * t + parametricEquation.getX0(),
                parametricEquation.getN() * t + parametricEquation.getY0(),
                parametricEquation.getP() * t + parametricEquation.getZ0());

        return point;
    }

    /**
     * 求过点P且与向量平行直线的参数方程
     * @param vector 向量
     * @param point 点
     * @return 直线
     */
    Line getLineByVectorAndPoint(Vector vector, Point point){
        Line line = new Line();
        line.setParametricEquation(new Line.ParametricEquation());
        Line.ParametricEquation parametricEquation = line.getParametricEquation();
        parametricEquation.setM(vector.getX());
        parametricEquation.setN(vector.getY());
        parametricEquation.setP(vector.getZ());
        parametricEquation.setX0(point.getX());
        parametricEquation.setY0(point.getY());
        parametricEquation.setZ0(point.getZ());

        return line;
    }

}
