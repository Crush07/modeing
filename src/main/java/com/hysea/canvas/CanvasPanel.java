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

    Line[] lineArray = new Line[120];

    public CanvasPanel() {
        // 初始化画布，设置视点等
        viewpoint = new Viewpoint();
        coordinateSystem = new CoordinateSystem(0,0,0);

        drawCoordinateSystem();

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

            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = new Point(new Fraction(e.getX()), new Fraction(e.getY()));
                selectLine(point,lineArray);
                repaint();
            }
        };

        this.addMouseMotionListener(mouseMotionAdapter);

//        this.addMouseListener(new MouseAdapter(){
//
//        });

    }

    public void setViewpoint(Viewpoint viewpoint) {
        this.viewpoint = viewpoint;
        // 视点发生变化，触发重新绘制
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(29,55,56));
        g.fillRect(0,0,getWidth(),getHeight());

        // 白色画笔
        g.setColor(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;

        // 启用抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Line line : lineArray) {
            System.out.println(line);
            drawLine(g2d,line);
        }
    }

    public void drawLine(Graphics2D g,Line line){

        Color originColor = g.getColor();
        if(line.isBeSelected()){
            g.setColor(Color.WHITE);
        }

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

        g.setColor(originColor);
    }

    public boolean isSelected(Point mousePoint, Line line){
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

        return Calculator.isPoint2DInLine2D(mousePoint,l);

    }

    public void drawCoordinateSystem(){

        double radius = 100;

        Point center = new Point(0, 0, 0);
        for (int i = 0; i < 24; i++) {
            double theta1 = 2.0 * Math.PI * i / 24;
            double theta2 = 2.0 * Math.PI * (i + 1) / 24;

            // 计算细分线的起点和终点
            Point p1 = new Point(center.getX().add(radius * Math.cos(theta1)), center.getY().add(radius * Math.sin(theta1)), center.getZ());
            Point p2 = new Point(center.getX().add(radius * Math.cos(theta2)), center.getY().add(radius * Math.sin(theta2)), center.getZ());

            Line line = new Line(new Point[]{p1, p2});

            lineArray[i] = line;

            Line line1 = new Line(new Point[]{center, p1});

            lineArray[i + 24] = line1;
        }

        center = new Point(0, 0, 200);
        for (int i = 0; i < 24; i++) {
            double theta1 = 2.0 * Math.PI * i / 24;
            double theta2 = 2.0 * Math.PI * (i + 1) / 24;

            // 计算细分线的起点和终点
            Point p1 = new Point(center.getX().add(radius * Math.cos(theta1)), center.getY().add(radius * Math.sin(theta1)), center.getZ());
            Point p2 = new Point(center.getX().add(radius * Math.cos(theta2)), center.getY().add(radius * Math.sin(theta2)), center.getZ());

            Line line = new Line(new Point[]{p1, p2});

            lineArray[i + 48] = line;

            Line line1 = new Line(new Point[]{center, p1});

            lineArray[i + 72] = line1;

            Line line2 = new Line(new Point[]{p1, lineArray[i].getPoints()[0]});

            lineArray[i + 96] = line2;
        }
    }


    public void selectLine(Point mousePoint, Line[] lineArray){
        for (Line line : lineArray) {
            if(isSelected(mousePoint,line)){
                line.setBeSelected(true);
            }else{
                line.setBeSelected(false);
            }
        }
    }

    public void drawFutureLine(Graphics g){

    }
}
