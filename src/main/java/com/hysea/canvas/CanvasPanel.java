package com.hysea.canvas;

import com.hysea.entity.CoordinateSystem;
import com.hysea.entity.Point;
import com.hysea.entity.Vector;
import com.hysea.entity.Viewpoint;

import javax.swing.*;
import java.awt.*;

public class CanvasPanel extends JPanel {

    private Viewpoint viewpoint;
    private CoordinateSystem coordinateSystem;

    public CanvasPanel() {
        // 初始化画布，设置视点等
        viewpoint = new Viewpoint();
        viewpoint.setPoint(new Point(3,3,3));
        viewpoint.setScale(1);
        viewpoint.setVector(new Vector(-3,-3,-3));
        coordinateSystem = new CoordinateSystem(0,0,0);

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

    public void drawCoordinateSystem(Graphics g){

        // 获取面板的中心点坐标
        int minX = 0,minY = 0;
        int maxX = getWidth();
        int maxY = getHeight();
        int centerX = (maxX + minY) / 2;
        int centerY = (maxY + minY) / 2;

        // 红色画笔
        g.setColor(Color.RED);

        //x轴
        g.drawLine(centerX,centerY,maxX,centerY);

        //y轴
        g.drawLine(centerX,centerY,centerX,minY);

        //z轴
        g.drawLine(centerX,centerY,centerX,centerY);

    }
}
