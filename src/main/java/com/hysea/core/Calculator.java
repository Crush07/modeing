package com.hysea.core;

import com.hysea.entity.*;

import java.util.List;

public class Calculator {

    /**
     * 根据直线参数式方程和平面一般式方程计算交汇点
     * @param line 线
     * @param face 面
     * @return 交汇点
     */
    public static Point getPointByParametricEquationOfLineAndCommonEquationOfFace(Line line, Face face) {

        //TODO 判断线是否在面内，抛出无穷解异常

        //TODO 判断线是否和面平行，抛出无解异常

        Line.ParametricEquation parametricEquation = line.getParametricEquation();
        Face.CommonEquation commonEquation = face.getCommonEquation();

        Fraction t = new Fraction(-1).multiply(
                commonEquation.getA().multiply(parametricEquation.getX0())
                        .add(commonEquation.getB().multiply(parametricEquation.getY0()))
                        .add(commonEquation.getC().multiply(parametricEquation.getZ0()))
                        .add(commonEquation.getD())
        ).divide(
                commonEquation.getA().multiply(parametricEquation.getM())
                        .add(commonEquation.getB().multiply(parametricEquation.getN()))
                        .add(commonEquation.getC().multiply(parametricEquation.getP()))
        );

        Point point = new Point(
                parametricEquation.getM().multiply(t).add(parametricEquation.getX0()),
                parametricEquation.getN().multiply(t).add(parametricEquation.getY0()),
                parametricEquation.getP().multiply(t).add(parametricEquation.getZ0())
        );

        return point;
    }

    /**
     * 求过点P且与向量平行直线的参数方程
     * @param vector 向量
     * @param point 点
     * @return 直线
     */
    public static Line getLineByVectorAndPoint(Vector vector, Point point){
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

    /**
     * 求存在点且与方向向量垂直的面
     * @param normalVector 法向量
     * @param point 点
     * @return 平面
     */
    public static Face getFaceByNormalVectorAndPoint(Vector normalVector, Point point){

        Face face = new Face();
        face.setCommonEquation(new Face.CommonEquation());
        Face.CommonEquation commonEquation = face.getCommonEquation();
        commonEquation.setA(normalVector.getX());
        commonEquation.setB(normalVector.getY());
        commonEquation.setC(normalVector.getZ());
        commonEquation.setD(normalVector.getX().multiply(point.getX())
                .add(normalVector.getY().multiply(point.getY()))
                .add(normalVector.getZ().multiply(point.getZ())));

        return face;
    }

    /**
     * 已知2个向量v1，v2，一个起点a，一个终点d，d - a为向量v3，a*v1 + b*v2 = v3，求a，b
     * @param vectorX
     * @param vectorY
     * @param point
     * @param orignPoint
     * @return
     */
    public static Point getPointInRectangularCoordinateSystemByVectorXAndVectorYAndPointToOriginPoint(Vector vectorX, Vector vectorY, Point point, Point orignPoint){

        Vector v3 = new Vector(
                orignPoint.getX().subtract(point.getX()),
                orignPoint.getY().subtract(point.getY()),
                orignPoint.getZ().subtract(point.getZ())
        );

        double a,b;
//        a * vectorX.getX() + b * vectorY.getX() = v3.getX();
//        a * vectorX.getY() + b * vectorY.getY() = v3.getY();
//        a * vectorX.getZ() + b * vectorY.getZ() = v3.getZ();
        //求a,b
        double[][] coefficients = {
                {vectorX.getX().getValue(), vectorY.getX().getValue()},
                {vectorX.getY().getValue(), vectorY.getY().getValue()},
                {vectorX.getZ().getValue(), vectorY.getZ().getValue()}
        };

        double[] constants = {v3.getX().getValue(), v3.getY().getValue(), v3.getZ().getValue()};

        double[] solutions = solveSystem(coefficients, constants);
        a = solutions[0];
        b = solutions[1];
        Point res = new Point(a,b,0);
        res.setDimensionType(DimensionType.D2);

        return res;
    }

    /**
     * 使用高斯消元法等方法求解线性方程组
     * 省略具体实现，可以使用第三方库或手动实现
     * 返回一个包含解的数组
     * https://blog.csdn.net/lzyws739307453/article/details/89816311 可参考
     * @param coefficients
     * @param constants
     * @return
     */
    private static double[] solveSystem(double[][] coefficients, double[] constants) {

        double[] solution = new double[2];

        //构建增广矩阵
        double[][] augmentedMatrix = new double[2][3];
        for (int i = 0; i < coefficients.length - 1; i++) {
            for (int j = 0; j < coefficients[i].length; j++) {
                augmentedMatrix[i][j] = coefficients[i][j];
            }
            augmentedMatrix[i][coefficients[i].length] = constants[i];
        }
        //转换为简化行阶梯式
        double[][] simple = reduceToRowEchelonForm(augmentedMatrix);

        //向后替换算法
        solution[1] = simple[1][2] / simple[1][1];
        solution[0] = (simple[0][2] - simple[0][1] * solution[1]) / simple[0][0];

        return solution;
    }

    /**
     * 增广矩阵转换为简化行阶梯形式
     * @param augmentedMatrix 增广矩阵
     * @return 简化行阶梯形式
     */
    public static double[][] reduceToRowEchelonForm(double[][] augmentedMatrix) {
        int rowCount = augmentedMatrix.length;
        int colCount = augmentedMatrix[0].length;

        int lead = 0; // 主导列

        for (int r = 0; r < rowCount; r++) {
            if (lead >= colCount) {
                break;
            }

            int i = r;
            while (augmentedMatrix[i][lead] == 0) {
                i++;
                if (i == rowCount) {
                    i = r;
                    lead++;
                    if (colCount == lead) {
                        break;
                    }
                }
            }

            // 交换行
            double[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[r];
            augmentedMatrix[r] = temp;

            // 将主导列的元素变为1
            double lv = augmentedMatrix[r][lead];
            for (int j = 0; j < colCount; j++) {
                augmentedMatrix[r][j] /= lv;
            }

            // 消元操作，将其他行的主导列元素变为0
            for (int k = 0; k < rowCount; k++) {
                if (k != r) {
                    double factor = augmentedMatrix[k][lead];
                    for (int j = 0; j < colCount; j++) {
                        augmentedMatrix[k][j] -= factor * augmentedMatrix[r][j];
                    }
                }
            }

            lead++;
        }

        return augmentedMatrix;
    }

    /**
     * 求两条线的交点，要求line1不能延长(x0,y0)一端，求得两条直线的交点
     * @param line1 根据parametricEquation参数式的延长直线，不能延长(x0,y0)一端
     * @param line2 根据slopeExpression斜率式的无限长直线
     * @return 交点
     */
    public static Point getPointByParametricEquationOfLine1AndSlopeExpressionOfLine2(Line line1, Line line2){

        Fraction x = new Fraction(0);
        Fraction y = new Fraction(0);
        //实现逻辑
        Line.ParametricEquation parametricEquation = line1.getParametricEquation();
        Line.SlopeExpression slopeExpression = line2.getSlopeExpression();

        //求t
        //t=(k*x0+b-y0)/(n-k*m)
        //只要n-k*m != 0就有解
        if(parametricEquation.getN().subtract(slopeExpression.getK().multiply(parametricEquation.getM())).getValue() != 0){
            Fraction t = slopeExpression.getK().multiply(parametricEquation.getX0()).add(slopeExpression.getB()).subtract(parametricEquation.getY0())
                    .divide(parametricEquation.getN().subtract(slopeExpression.getK().multiply(parametricEquation.getM())));
            //t < 0 说明是延长(x0,y0)一端的交点，不符合函数要求
            if(t.getValue() < 0){
                return null;
            }else {
                x = t.multiply(parametricEquation.getM()).add(parametricEquation.getX0());
                y = t.multiply(parametricEquation.getN()).add(parametricEquation.getY0());
            }
        }else {
            //平行
            return null;
        }

        return new Point(x,y);
    }

    /**
     * 求两条线的交点，要求line1不能延长(x0,y0)一端，求得两条直线的交点
     * @param line1 根据parametricEquation参数式的延长直线，不能延长(x0,y0)一端
     * @param line2 根据slopeExpression斜率式的无限长直线
     * @return 交点
     */
    public static Point getPointByParametricEquationOfLine1AndCommonEquationOfLine2(Line line1, Line line2){

        Fraction x = new Fraction(0);
        Fraction y = new Fraction(0);
        //实现逻辑
        Line.ParametricEquation parametricEquation = line1.getParametricEquation();
        Line.CommonEquation commonEquation = line2.getCommonEquation();

        if(commonEquation.getA().multiply(parametricEquation.getM())
                .add(commonEquation.getB().multiply(parametricEquation.getN())).getValue() != 0){
            Fraction t = commonEquation.getC()
                    .add(commonEquation.getA().multiply(parametricEquation.getX0()))
                    .add(commonEquation.getB().multiply(parametricEquation.getY0())).multiply(-1)
                    .divide(commonEquation.getA().multiply(parametricEquation.getM())
                            .add(commonEquation.getB().multiply(parametricEquation.getN())).getValue());
            //t < 0 说明是延长(x0,y0)一端的交点，不符合函数要求
            if(t.getValue() < 0){
                return null;
            }else {
                x = t.multiply(parametricEquation.getM()).add(parametricEquation.getX0());
                y = t.multiply(parametricEquation.getN()).add(parametricEquation.getY0());
            }
        }else{
            return null;
        }

        return new Point(x,y);
    }

    public static boolean isPoint2DInLine2D(Point point, Line line){
        Line.ParametricEquation parametricEquation = line.getParametricEquation();

//        if(point.getY().getValue() >= Math.min(line.getPoints()[0].getY().getValue(),line.getPoints()[1].getY().getValue())
//                && point.getY().getValue() <= Math.max(line.getPoints()[0].getY().getValue(),line.getPoints()[1].getY().getValue())){
//            if(parametricEquation.getM().getValue() != 0){
//                Fraction t = point.getX().subtract(parametricEquation.getX0()).divide(parametricEquation.getM());
//
//                //如果点在线段内则一定符合t >= 0 && t <= 1
//                if(t.getValue() >= 0 && t.getValue() <= 1){
//                    //代入y = y0 + nt，验证等式，如果等式成立，则点在线段内
//                    if(t.multiply(parametricEquation.getN()).add(parametricEquation.getY0()).getValue() >= point.getY().getValue() - 1
//                            && t.multiply(parametricEquation.getN()).add(parametricEquation.getY0()).getValue() <= point.getY().getValue() + 1){
//                        return true;
//                    }
//                }
//            }else{
//                if(point.getX().getValue() == line.getPoints()[0].getX().getValue()){
//                    return true;
//                }
//            }
//        }
//        return false;

        //向量叉积，求两个向量相乘得到的四边形面积，如果为0，说明是一条线
        if(point.getY().getValue() >= Math.min(line.getPoints()[0].getY().getValue(),line.getPoints()[1].getY().getValue())
                && point.getY().getValue() <= Math.max(line.getPoints()[0].getY().getValue(),line.getPoints()[1].getY().getValue())
                && point.getX().getValue() >= Math.min(line.getPoints()[0].getX().getValue(),line.getPoints()[1].getX().getValue())
                && point.getX().getValue() <= Math.max(line.getPoints()[0].getX().getValue(),line.getPoints()[1].getX().getValue())
                && point.getX().subtract(line.getPoints()[0].getX()).multiply(line.getPoints()[1].getY().subtract(line.getPoints()[0].getY())).getValue()
                == point.getY().subtract(line.getPoints()[0].getY()).multiply(line.getPoints()[1].getX().subtract(line.getPoints()[0].getX())).getValue()
        ){
            return true;
        }else{
            return false;
        }

    }

}
