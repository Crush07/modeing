package com.hysea;

import com.hysea.canvas.CanvasPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class DrawingApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Drawing Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // 创建顶部工具栏
            JToolBar toolBar = new JToolBar();
            JButton tool1 = new JButton("Tool 1");
            JButton tool2 = new JButton("Tool 2");

//            MouseAdapter mouseAdapter = new MouseAdapter();

            toolBar.add(tool1);
            toolBar.add(tool2);

            // 创建左边的对象栏
            JPanel objectPanel = new JPanel();
            objectPanel.setBackground(Color.LIGHT_GRAY);
            objectPanel.setPreferredSize(new Dimension(200, 0));

            // 创建右边的画布
            CanvasPanel canvasPanel = new CanvasPanel();
            canvasPanel.setBackground(Color.WHITE);

            // 创建主窗口布局
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, objectPanel, canvasPanel);
            splitPane.setDividerLocation(200);

            // 将工具栏和分割面板添加到主窗口
            frame.setLayout(new BorderLayout());
            frame.add(toolBar, BorderLayout.NORTH);
            frame.add(splitPane, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}