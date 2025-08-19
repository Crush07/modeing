package com.hysea.canvas;

import com.hysea.core.Calculator;
import com.hysea.core.Fraction;
import com.hysea.entity.*;
import com.hysea.entity.Point;
import com.hysea.entity.shape.Cube;
import com.hysea.entity.shape.Cylinder;
import com.hysea.entity.shape.Sphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CanvasPanel extends JPanel {

    private Viewpoint viewpoint;
    private CoordinateSystem coordinateSystem;

    List<Line> lineArray = new ArrayList<>();

    public CanvasPanel() {
        // 初始化画布，设置视点等
        viewpoint = new Viewpoint();
        coordinateSystem = new CoordinateSystem(0,0,0);

        drawCoordinateSystem();
        drawEntity();

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

        Graphics2D g2d = (Graphics2D) g;
        
        // 启用抗锯齿和高质量渲染
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        // 绘制渐变背景
        drawGradientBackground(g2d);
        
        // 绘制网格
        drawGrid(g2d);
        
        // 绘制坐标轴
        // drawCoordinateAxes(g2d);

        // 绘制3D线条
        for (Line line : lineArray) {
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

    }

    public void drawEntity(){
//        double radius = 100;
//        double height = 200;
//        Point center = new Point(0,0,100);
//        int subdivision = 4;
//        Line[] tempLineArray = createCylinder(radius, height, center, subdivision);
//        this.lineArray.addAll(Arrays.stream(tempLineArray).collect(Collectors.toList()));

//        double length = 100;
//        double width = 100;
//        double height = 200;
//        Point center = new Point(0,0,100);
//        Line[] tempLineArray = createCube(length, width, height, center);
//        this.lineArray.addAll(Arrays.stream(tempLineArray).collect(Collectors.toList()));
    }

    private Line[] createCylinder(double radius, double height, Point center, int subdivision) {
        return new Cylinder(radius, height, center, subdivision).create().getLines();
    }

    private Line[] createCube(double length, double width, double height, Point center) {
        return new Cube(length, width, height, center).create().getLines();
    }
    
    private Line[] createSphere(double radius, Point center, int subdivision) {
        return new Sphere(radius, center, subdivision).create().getLines();
    }
    
    public void addSphere(double radius, Point center, int subdivision) {
        Line[] sphereLines = createSphere(radius, center, subdivision);
        this.lineArray.addAll(Arrays.stream(sphereLines).filter(line -> line != null).collect(Collectors.toList()));
        repaint();
    }
    
    public void addCube(double length, double width, double height, Point center) {
        Line[] cubeLines = createCube(length, width, height, center);
        this.lineArray.addAll(Arrays.stream(cubeLines).filter(line -> line != null).collect(Collectors.toList()));
        repaint();
    }
    
    public void addCylinder(double radius, double height, Point center, int subdivision) {
        Line[] cylinderLines = createCylinder(radius, height, center, subdivision);
        this.lineArray.addAll(Arrays.stream(cylinderLines).filter(line -> line != null).collect(Collectors.toList()));
        repaint();
    }


    public void selectLine(Point mousePoint, List<Line> lineArray){
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
    
    /**
     * 绘制渐变背景
     */
    private void drawGradientBackground(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        
        // 创建从上到下的渐变
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(45, 45, 48),
            0, height, new Color(30, 30, 32)
        );
        
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }
    
    /**
     * 绘制网格
     */
    private void drawGrid(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        
        // 设置网格线样式
        g2d.setStroke(new BasicStroke(0.5f));
        g2d.setColor(new Color(80, 80, 85, 100)); // 半透明网格线
        
        int gridSize = 20; // 网格大小
        
        // 绘制垂直网格线
        for (int x = centerX % gridSize; x < width; x += gridSize) {
            g2d.drawLine(x, 0, x, height);
        }
        
        // 绘制水平网格线
        for (int y = centerY % gridSize; y < height; y += gridSize) {
            g2d.drawLine(0, y, width, y);
        }
    }
    
    /**
     * 绘制坐标轴
     */
    private void drawCoordinateAxes(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        
        // 设置坐标轴样式
        g2d.setStroke(new BasicStroke(2.0f));
        
        // X轴 (红色)
        g2d.setColor(new Color(255, 100, 100));
        g2d.drawLine(centerX - 100, centerY, centerX + 100, centerY);
        // X轴箭头
        g2d.drawLine(centerX + 95, centerY - 5, centerX + 100, centerY);
        g2d.drawLine(centerX + 95, centerY + 5, centerX + 100, centerY);
        // X轴标签
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("X", centerX + 105, centerY + 5);
        
        // Y轴 (绿色)
        g2d.setColor(new Color(100, 255, 100));
        g2d.drawLine(centerX, centerY - 100, centerX, centerY + 100);
        // Y轴箭头
        g2d.drawLine(centerX - 5, centerY - 95, centerX, centerY - 100);
        g2d.drawLine(centerX + 5, centerY - 95, centerX, centerY - 100);
        // Y轴标签
        g2d.drawString("Y", centerX + 5, centerY - 105);
        
        // Z轴 (蓝色) - 斜向表示深度
        g2d.setColor(new Color(100, 100, 255));
        int zEndX = centerX - 70;
        int zEndY = centerY + 70;
        g2d.drawLine(centerX, centerY, zEndX, zEndY);
        // Z轴箭头
        g2d.drawLine(zEndX + 5, zEndY - 5, zEndX, zEndY);
        g2d.drawLine(zEndX + 5, zEndY, zEndX, zEndY);
        // Z轴标签
        g2d.drawString("Z", zEndX - 10, zEndY + 15);
        
        // 绘制原点
        g2d.setColor(Color.WHITE);
        g2d.fillOval(centerX - 3, centerY - 3, 6, 6);
        g2d.setColor(new Color(60, 63, 65));
        g2d.drawOval(centerX - 3, centerY - 3, 6, 6);
    }
}
