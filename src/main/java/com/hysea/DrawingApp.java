package com.hysea;

import com.hysea.canvas.CanvasPanel;
import com.hysea.entity.Point;
import com.hysea.manager.ObjectManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class DrawingApp {
    private static JPanel objectListPanel;
    private static ObjectManager objectManager;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 设置现代化的Look and Feel
            try {
                // 设置全局字体
                Font font = new Font("Microsoft YaHei", Font.PLAIN, 12);
                UIManager.put("Button.font", font);
                UIManager.put("Label.font", font);
                UIManager.put("Menu.font", font);
                UIManager.put("MenuItem.font", font);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            JFrame frame = new JFrame("3D建模应用程序");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null); // 居中显示
            frame.setMinimumSize(new Dimension(800, 600));

            // 创建右边的画布
            CanvasPanel canvasPanel = new CanvasPanel();
            canvasPanel.setBackground(new Color(45, 45, 48)); // 深色背景
            
            // 创建顶部工具栏
            JToolBar toolBar = createModernToolBar(canvasPanel);

            // 初始化对象管理器
            objectManager = ObjectManager.getInstance();
            
            // 创建左边的对象栏
            JPanel objectPanel = createModernSidePanel();

            // 创建状态栏
            JPanel statusBar = createStatusBar();
            
            // 创建主窗口布局
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, objectPanel, canvasPanel);
            splitPane.setDividerLocation(250);
            splitPane.setDividerSize(3);
            splitPane.setBorder(null);
            splitPane.setBackground(new Color(60, 63, 65));
            
            // 设置主窗口背景色
            frame.getContentPane().setBackground(new Color(60, 63, 65));
            
            // 将组件添加到主窗口
            frame.setLayout(new BorderLayout());
            frame.add(toolBar, BorderLayout.NORTH);
            frame.add(splitPane, BorderLayout.CENTER);
            frame.add(statusBar, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }
    
    private static JToolBar createModernToolBar(CanvasPanel canvasPanel) {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(60, 63, 65));
        toolBar.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        // 创建现代化按钮
        JButton newBtn = createModernButton("新建", new Color(0, 120, 215));
        JButton openBtn = createModernButton("打开", new Color(16, 124, 16));
        JButton saveBtn = createModernButton("保存", new Color(255, 140, 0));
        
        toolBar.add(newBtn);
        toolBar.addSeparator();
        toolBar.add(openBtn);
        toolBar.add(saveBtn);
        toolBar.addSeparator();
        
        // 添加3D工具按钮
        JButton cubeBtn = createModernButton("立方体", new Color(106, 90, 205));
        JButton cylinderBtn = createModernButton("圆柱体", new Color(106, 90, 205));
        JButton sphereBtn = createModernButton("球体", new Color(106, 90, 205));
        
        // 为立方体按钮添加点击事件
        cubeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在画布中心创建一个立方体
                Point center = new Point(0, 0, 0);
                double length = 100, width = 100, height = 100;
                canvasPanel.addCube(length, width, height, center);
                
                // 添加到对象管理器
                objectManager.addObject("立方体", null, new Color(255, 100, 100));
            }
        });
        
        // 为圆柱体按钮添加点击事件
        cylinderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在画布中心创建一个圆柱体
                Point center = new Point(0, 0, 100);
                double radius = 50;
                double height = 200;
                int subdivision = 16;
                canvasPanel.addCylinder(radius, height, center, subdivision);
                
                // 添加到对象管理器
                objectManager.addObject("圆柱体", null, new Color(100, 255, 100));
            }
        });
        
        // 为球体按钮添加点击事件
        sphereBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在画布中心创建一个球体
                Point center = new Point(0, 0, 0);
                double radius = 50;
                int subdivision = 24;
                canvasPanel.addSphere(radius, center, subdivision);
                
                // 添加到对象管理器
                objectManager.addObject("球体", null, new Color(100, 100, 255));
            }
        });
        
        toolBar.add(cubeBtn);
        toolBar.add(cylinderBtn);
        toolBar.add(sphereBtn);
        
        return toolBar;
    }
    
    private static JButton createModernButton(String text, Color accentColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(80, 35));
        button.setBackground(new Color(70, 73, 75));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // 添加悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(accentColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 73, 75));
            }
        });
        
        return button;
    }
    
    private static JPanel createModernSidePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(37, 37, 38));
        panel.setPreferredSize(new Dimension(250, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(60, 63, 65)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        // 添加标题
        JLabel titleLabel = new JLabel("对象管理器");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        // 创建对象列表区域
        objectListPanel = new JPanel();
        objectListPanel.setLayout(new BoxLayout(objectListPanel, BoxLayout.Y_AXIS));
        objectListPanel.setBackground(new Color(37, 37, 38));
        
        JScrollPane scrollPane = new JScrollPane(objectListPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(37, 37, 38));
        scrollPane.getViewport().setBackground(new Color(37, 37, 38));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // 设置对象管理器监听器
        objectManager.addListener(new ObjectManager.ObjectManagerListener() {
            @Override
            public void onObjectAdded(ObjectManager.SceneObject obj) {
                SwingUtilities.invokeLater(() -> {
                    JPanel objectItem = createObjectItem(obj.getName(), obj.getColor());
                    objectListPanel.add(objectItem);
                    objectListPanel.revalidate();
                    objectListPanel.repaint();
                });
            }
            
            @Override
            public void onObjectRemoved(ObjectManager.SceneObject obj) {
                SwingUtilities.invokeLater(() -> {
                    // 查找并移除对应的UI组件
                    Component[] components = objectListPanel.getComponents();
                    for (Component comp : components) {
                        if (comp instanceof JPanel) {
                            JPanel item = (JPanel) comp;
                            Component[] itemComponents = item.getComponents();
                            for (Component itemComp : itemComponents) {
                                if (itemComp instanceof JLabel) {
                                    JLabel label = (JLabel) itemComp;
                                    if (obj.getName().equals(label.getText())) {
                                        objectListPanel.remove(item);
                                        objectListPanel.revalidate();
                                        objectListPanel.repaint();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        
        return panel;
    }
    
    private static JPanel createObjectItem(String name, Color color) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(new Color(45, 45, 48));
        item.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 63, 65), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        // 颜色指示器
        JPanel colorIndicator = new JPanel();
        colorIndicator.setBackground(color);
        colorIndicator.setPreferredSize(new Dimension(12, 12));
        colorIndicator.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        
        item.add(colorIndicator, BorderLayout.WEST);
        item.add(nameLabel, BorderLayout.CENTER);
        
        // 添加悬停效果
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                item.setBackground(new Color(55, 55, 58));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                item.setBackground(new Color(45, 45, 48));
            }
        });
        
        return item;
    }
    
    private static JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(37, 37, 38));
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(60, 63, 65)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        statusBar.setPreferredSize(new Dimension(0, 25));
        
        JLabel statusLabel = new JLabel("就绪");
        statusLabel.setForeground(Color.LIGHT_GRAY);
        statusLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        
        JLabel coordLabel = new JLabel("坐标: (0, 0, 0)");
        coordLabel.setForeground(Color.LIGHT_GRAY);
        coordLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 11));
        
        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(coordLabel, BorderLayout.EAST);
        
        return statusBar;
    }
}