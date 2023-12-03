package com.hysea.canvas;

import com.hysea.entity.CoordinateSystem;
import com.hysea.entity.Viewpoint;

import javax.swing.*;
import java.awt.*;

public class CanvasPanel extends JPanel {
    private Viewpoint viewpoint;

    public CanvasPanel() {
        // 初始化画布，设置视点等
        this.viewpoint = new Viewpoint(new CoordinateSystem(0, 0, 0));
    }

    public void setViewpoint(Viewpoint viewpoint) {
        this.viewpoint = viewpoint;
        // 视点发生变化，触发重新绘制
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 获取面板的中心点坐标
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // 在中心点画一个点
        g.setColor(Color.RED);
        g.fillRect(centerX, centerY, 5, 5);
    }

    public void drawCoordinateSystem(Graphics g){



    }
}
