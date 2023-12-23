package com.hysea.canvas;

import com.hysea.core.Calculator;
import com.hysea.core.Fraction;
import com.hysea.entity.*;
import com.hysea.entity.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Calendar;

public class CanvasPanel extends JPanel {

    private Viewpoint viewpoint;
    private CoordinateSystem coordinateSystem;

    public CanvasPanel() {
        // 初始化画布，设置视点等
        viewpoint = new Viewpoint();
        coordinateSystem = new CoordinateSystem(0,0,0);

        //MouseMotionAdapter的mouseDragged监听事件才管用
        MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {

            Integer lastX = null;
            Integer lastY = null;

            @Override
            public void mouseDragged(MouseEvent e) {

                int x = e.getX();
                int y = e.getY();

                //第一下记录位置，不变换
                if(lastX != null && lastY != null){
//                System.out.println((x - lastX) + " " + (y - lastY));

                    if((x - lastX) != 0){
                        Fraction xOfVector = viewpoint.getVector().getX();
                        Fraction yOfVector = viewpoint.getVector().getY();
                        Fraction zOfVector = viewpoint.getVector().getZ();

                        Fraction xOfVectorX = viewpoint.getVectorX().getX();
                        Fraction yOfVectorX = viewpoint.getVectorX().getY();
                        Fraction zOfVectorX = viewpoint.getVectorX().getZ();

                        Fraction delta = new Fraction(x - lastX).multiply(0.01);

                        // 更新viewpoint的向量
                        viewpoint.getVector().setX(viewpoint.getVector().getX().add(delta.multiply(xOfVectorX)));
                        viewpoint.getVector().setY(viewpoint.getVector().getY().add(delta.multiply(yOfVectorX)));
                        viewpoint.getVector().setZ(viewpoint.getVector().getZ().add(delta.multiply(zOfVectorX)));
                        viewpoint.getVector().setSize(new Fraction(1));

                        // 更新viewpoint的VectorX
                        viewpoint.getVectorX().setX(viewpoint.getVectorX().getX().subtract(delta.multiply(xOfVector)));
                        viewpoint.getVectorX().setY(viewpoint.getVectorX().getY().subtract(delta.multiply(yOfVector)));
                        viewpoint.getVectorX().setZ(viewpoint.getVectorX().getZ().subtract(delta.multiply(zOfVector)));
                        viewpoint.getVectorX().setSize(new Fraction(1));

                    }

                    if((y - lastY) != 0){
                        Fraction xOfVector = viewpoint.getVector().getX();
                        Fraction yOfVector = viewpoint.getVector().getY();
                        Fraction zOfVector = viewpoint.getVector().getZ();

                        Fraction xOfVectorY = viewpoint.getVectorY().getX();
                        Fraction yOfVectorY = viewpoint.getVectorY().getY();
                        Fraction zOfVectorY = viewpoint.getVectorY().getZ();

                        Fraction delta = new Fraction(y - lastY).multiply(0.01);

                        // 更新viewpoint的向量
                        viewpoint.getVector().setX(viewpoint.getVector().getX().add(delta.multiply(xOfVectorY)));
                        viewpoint.getVector().setY(viewpoint.getVector().getY().add(delta.multiply(yOfVectorY)));
                        viewpoint.getVector().setZ(viewpoint.getVector().getZ().add(delta.multiply(zOfVectorY)));
                        viewpoint.getVector().setSize(new Fraction(1));

                        // 更新viewpoint的VectorX
                        viewpoint.getVectorY().setX(viewpoint.getVectorY().getX().subtract(delta.multiply(xOfVector)));
                        viewpoint.getVectorY().setY(viewpoint.getVectorY().getY().subtract(delta.multiply(yOfVector)));
                        viewpoint.getVectorY().setZ(viewpoint.getVectorY().getZ().subtract(delta.multiply(zOfVector)));
                        viewpoint.getVectorY().setSize(new Fraction(1));
                    }
                }

                lastX = x;
                lastY = y;


                repaint();
            }

        };

        this.addMouseMotionListener(mouseMotionAdapter);

    }

    public void setViewpoint(Viewpoint viewpoint) {
        this.viewpoint = viewpoint;
        // 视点发生变化，触发重新绘制
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawCoordinateSystem(g);
    }

    public void drawLine(Graphics g,Line line){
        // 获取面板的中心点坐标
        int minX = 0,minY = 0;
        int maxX = getWidth();
        int maxY = getHeight();
        int centerX = (maxX + minY) / 2;
        int centerY = (maxY + minY) / 2;

        //视平面
        Face faceByNormalVectorAndPoint = Calculator.getFaceByNormalVectorAndPoint(viewpoint.getVector(), viewpoint.getPoint());

        //穿过topX与视线向量平行的直线
        Line lineByVectorAndPoint1 = Calculator.getLineByVectorAndPoint(viewpoint.getVector(), line.getPoints()[0]);
        //穿过topY与视线向量平行的直线
        Line lineByVectorAndPoint2 = Calculator.getLineByVectorAndPoint(viewpoint.getVector(), line.getPoints()[1]);

        //投影到视平面上的topX
        Point pointByParametricEquationOfLineAndCommonEquationOfFace1 = Calculator.getPointByParametricEquationOfLineAndCommonEquationOfFace(lineByVectorAndPoint1, faceByNormalVectorAndPoint);
        //投影到视平面上的topY
        Point pointByParametricEquationOfLineAndCommonEquationOfFace2 = Calculator.getPointByParametricEquationOfLineAndCommonEquationOfFace(lineByVectorAndPoint2, faceByNormalVectorAndPoint);

        //转换成视平面上的直角坐标系坐标
        Point map1 = Calculator.getPointInRectangularCoordinateSystemByVectorXAndVectorYAndPointToOriginPoint(viewpoint.getVectorX(), viewpoint.getVectorY(), pointByParametricEquationOfLineAndCommonEquationOfFace1, viewpoint.getPoint());
        Point map2 = Calculator.getPointInRectangularCoordinateSystemByVectorXAndVectorYAndPointToOriginPoint(viewpoint.getVectorX(), viewpoint.getVectorY(), pointByParametricEquationOfLineAndCommonEquationOfFace2, viewpoint.getPoint());

        Point top1Point = new Point(centerX + (int)map1.getX().getValue(),centerY + (int)map1.getY().getValue());
        Point top2Point = new Point(centerX + (int)map2.getX().getValue(),centerY + (int)map2.getY().getValue());
        Line l = new Line(new Point[]{top1Point,top2Point});
        l.draw(g);

    }

    public void drawCoordinateSystem(Graphics g){
        // 获取面板的中心点坐标
        int minX = 0,minY = 0;
        int maxX = getWidth();
        int maxY = getHeight();
        int centerX = (maxX + minY) / 2;
        int centerY = (maxY + minY) / 2;

        Point topX = new Point(coordinateSystem.getX() + coordinateSystem.getLength(), 0, 0);
        Point topY = new Point(0,coordinateSystem.getY() + coordinateSystem.getLength(), 0);
        Point topZ = new Point(0,0,coordinateSystem.getZ() + coordinateSystem.getLength());
        Point topOrigin = new Point(0,0,0);

        //视平面
        Face faceByNormalVectorAndPoint = Calculator.getFaceByNormalVectorAndPoint(viewpoint.getVector(), viewpoint.getPoint());

        //穿过topX与视线向量平行的直线
        Line lineByVectorAndPointX = Calculator.getLineByVectorAndPoint(viewpoint.getVector(), topX);
        System.out.println(lineByVectorAndPointX);
        //穿过topY与视线向量平行的直线
        Line lineByVectorAndPointY = Calculator.getLineByVectorAndPoint(viewpoint.getVector(), topY);
        System.out.println(lineByVectorAndPointY);
        //穿过topZ与视线向量平行的直线
        Line lineByVectorAndPointZ = Calculator.getLineByVectorAndPoint(viewpoint.getVector(), topZ);
        System.out.println(lineByVectorAndPointZ);
        //穿过topO与视线向量平行的直线
        Line lineByVectorAndPointOrigin = Calculator.getLineByVectorAndPoint(viewpoint.getVector(), topOrigin);
        System.out.println(lineByVectorAndPointOrigin);

        //投影到视平面上的topX
        Point pointByParametricEquationOfLineAndCommonEquationOfFaceX = Calculator.getPointByParametricEquationOfLineAndCommonEquationOfFace(lineByVectorAndPointX, faceByNormalVectorAndPoint);
        //投影到视平面上的topY
        Point pointByParametricEquationOfLineAndCommonEquationOfFaceY = Calculator.getPointByParametricEquationOfLineAndCommonEquationOfFace(lineByVectorAndPointY, faceByNormalVectorAndPoint);
        //投影到视平面上的topZ
        Point pointByParametricEquationOfLineAndCommonEquationOfFaceZ = Calculator.getPointByParametricEquationOfLineAndCommonEquationOfFace(lineByVectorAndPointZ, faceByNormalVectorAndPoint);
        //投影到视平面上的topO
        Point pointByParametricEquationOfLineAndCommonEquationOfFaceOrigin = Calculator.getPointByParametricEquationOfLineAndCommonEquationOfFace(lineByVectorAndPointOrigin, faceByNormalVectorAndPoint);

        //转换成视平面上的直角坐标系坐标
        Point mapX = Calculator.getPointInRectangularCoordinateSystemByVectorXAndVectorYAndPointToOriginPoint(viewpoint.getVectorX(), viewpoint.getVectorY(), pointByParametricEquationOfLineAndCommonEquationOfFaceX, viewpoint.getPoint());
        Point mapY = Calculator.getPointInRectangularCoordinateSystemByVectorXAndVectorYAndPointToOriginPoint(viewpoint.getVectorX(), viewpoint.getVectorY(), pointByParametricEquationOfLineAndCommonEquationOfFaceY, viewpoint.getPoint());
        Point mapZ = Calculator.getPointInRectangularCoordinateSystemByVectorXAndVectorYAndPointToOriginPoint(viewpoint.getVectorX(), viewpoint.getVectorY(), pointByParametricEquationOfLineAndCommonEquationOfFaceZ, viewpoint.getPoint());
        Point mapOrigin = Calculator.getPointInRectangularCoordinateSystemByVectorXAndVectorYAndPointToOriginPoint(viewpoint.getVectorX(), viewpoint.getVectorY(), pointByParametricEquationOfLineAndCommonEquationOfFaceOrigin, viewpoint.getPoint());

        //映射到视平面的原点直角坐标系坐标
        int mapCenterX = centerX + (int)mapOrigin.getX().getValue();
        int mapCenterY = centerY + (int)mapOrigin.getY().getValue();

        g.setColor(new Color(29,55,56));
        g.fillRect(0,0,getWidth(),getHeight());

        // 白色画笔
        g.setColor(Color.WHITE);

        Point originPoint = new Point(mapCenterX, mapCenterY);

        Line topLine = new Line(new Point[]{
                new Point(new Fraction(minX),new Fraction(minY)),
                new Point(new Fraction(maxX),new Fraction(minY))
        });

        Line leftLine = new Line(new Point[]{
                new Point(new Fraction(minX),new Fraction(minY)),
                new Point(new Fraction(minX),new Fraction(maxY))
        });

        Line bottomLine = new Line(new Point[]{
                new Point(new Fraction(minX),new Fraction(maxY)),
                new Point(new Fraction(maxX),new Fraction(maxY))
        });

        Line rightLine = new Line(new Point[]{
                new Point(new Fraction(maxX),new Fraction(minY)),
                new Point(new Fraction(maxX),new Fraction(maxY))
        });


        Point topXPoint = new Point(centerX + (int)mapX.getX().getValue(),centerY + (int)mapX.getY().getValue());
        Line xAxle = new Line(new Point[]{originPoint,topXPoint});
        Point xAxlePoint = Calculator.getPointByParametricEquationOfLine1AndSlopeExpressionOfLine2(xAxle, topLine);
        if(xAxlePoint == null){
            xAxlePoint = Calculator.getPointByParametricEquationOfLine1AndCommonEquationOfLine2(xAxle, leftLine);
        }
        if(xAxlePoint == null){
            xAxlePoint = Calculator.getPointByParametricEquationOfLine1AndCommonEquationOfLine2(xAxle, bottomLine);
        }
        if(xAxlePoint == null){
            xAxlePoint = Calculator.getPointByParametricEquationOfLine1AndCommonEquationOfLine2(xAxle, rightLine);
        }
        System.out.println("xAxlePoint"+xAxlePoint);
        if(xAxlePoint != null){
            xAxle.getPoints()[1] = xAxlePoint;
        }
        xAxle.draw(g);

        Point topYPoint = new Point(centerX + (int)mapY.getX().getValue(),centerY + (int)mapY.getY().getValue());
        Line yAxle = new Line(new Point[]{originPoint,topYPoint});
        Point yAxlePoint = Calculator.getPointByParametricEquationOfLine1AndSlopeExpressionOfLine2(yAxle, topLine);
        if(yAxlePoint == null){
            yAxlePoint = Calculator.getPointByParametricEquationOfLine1AndCommonEquationOfLine2(yAxle, leftLine);
        }
        if(yAxlePoint == null){
            yAxlePoint = Calculator.getPointByParametricEquationOfLine1AndCommonEquationOfLine2(yAxle, bottomLine);
        }
        if(yAxlePoint == null){
            yAxlePoint = Calculator.getPointByParametricEquationOfLine1AndCommonEquationOfLine2(yAxle, rightLine);
        }
        System.out.println("yAxlePoint"+yAxlePoint);
        if(yAxlePoint != null){
            yAxle.getPoints()[1] = yAxlePoint;
        }
        yAxle.draw(g);

        Point topZPoint = new Point(centerX + (int)mapZ.getX().getValue(),centerY + (int)mapZ.getY().getValue());
        Line zAxle = new Line(new Point[]{originPoint,topZPoint});
        Point zAxlePoint = Calculator.getPointByParametricEquationOfLine1AndSlopeExpressionOfLine2(zAxle, topLine);
        if(zAxlePoint == null){
            zAxlePoint = Calculator.getPointByParametricEquationOfLine1AndCommonEquationOfLine2(zAxle, leftLine);
        }
        if(zAxlePoint == null){
            zAxlePoint = Calculator.getPointByParametricEquationOfLine1AndCommonEquationOfLine2(zAxle, bottomLine);
        }
        if(zAxlePoint == null){
            zAxlePoint = Calculator.getPointByParametricEquationOfLine1AndCommonEquationOfLine2(zAxle, rightLine);
        }
        System.out.println("zAxlePoint"+zAxlePoint);
        if(zAxlePoint != null){
            zAxle.getPoints()[1] = zAxlePoint;
        }
        zAxle.draw(g);

        Line line = new Line(new Point[]{new Point(0, 0, 0), new Point(300, 300, 300)});
        drawLine(g,line);
    }
}
