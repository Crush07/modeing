package com.hysea.entity.shape;

import com.hysea.core.Fraction;
import com.hysea.entity.Line;
import com.hysea.entity.Point;

public class Sphere extends Shape{

    private double radius;
    private Point center;
    private int subdivision;

    public Sphere(double radius, Point center, int subdivision) {
        this.radius = radius;
        this.center = center;
        this.subdivision = subdivision;
    }

    @Override
    public Shape create() {
        // 经线数量: subdivision * subdivision (包括极点连接)
        // 纬线数量: (subdivision - 1) * subdivision (不包括极点)
        int totalLines = subdivision * subdivision + (subdivision - 1) * subdivision;
        setLines(new Line[totalLines]);

        // 生成经线（从北极到南极的线）
        int lineIndex = 0;
        for (int i = 0; i < subdivision; i++) {
            double longitude = 2.0 * Math.PI * i / subdivision;
            
            for (int j = 0; j < subdivision; j++) {
                double latitude1 = Math.PI * j / subdivision - Math.PI / 2;
                double latitude2 = Math.PI * (j + 1) / subdivision - Math.PI / 2;
                
                // 计算球面上的点
                Point p1 = new Point(
                    center.getX().add(new Fraction(radius * Math.cos(latitude1) * Math.cos(longitude))),
                    center.getY().add(new Fraction(radius * Math.cos(latitude1) * Math.sin(longitude))),
                    center.getZ().add(new Fraction(radius * Math.sin(latitude1)))
                );
                
                Point p2 = new Point(
                    center.getX().add(new Fraction(radius * Math.cos(latitude2) * Math.cos(longitude))),
                    center.getY().add(new Fraction(radius * Math.cos(latitude2) * Math.sin(longitude))),
                    center.getZ().add(new Fraction(radius * Math.sin(latitude2)))
                );
                
                Line line = new Line(new Point[]{p1, p2});
                getLines()[lineIndex++] = line;
            }
        }
        
        // 生成纬线（水平圆圈）
        for (int j = 1; j < subdivision; j++) { // 跳过北极点，包括南极点
            double latitude = Math.PI * j / subdivision - Math.PI / 2;
            
            for (int i = 0; i < subdivision; i++) {
                double longitude1 = 2.0 * Math.PI * i / subdivision;
                double longitude2 = 2.0 * Math.PI * ((i + 1) % subdivision) / subdivision;
                
                Point p1 = new Point(
                    center.getX().add(new Fraction(radius * Math.cos(latitude) * Math.cos(longitude1))),
                    center.getY().add(new Fraction(radius * Math.cos(latitude) * Math.sin(longitude1))),
                    center.getZ().add(new Fraction(radius * Math.sin(latitude)))
                );
                
                Point p2 = new Point(
                    center.getX().add(new Fraction(radius * Math.cos(latitude) * Math.cos(longitude2))),
                    center.getY().add(new Fraction(radius * Math.cos(latitude) * Math.sin(longitude2))),
                    center.getZ().add(new Fraction(radius * Math.sin(latitude)))
                );
                
                Line line = new Line(new Point[]{p1, p2});
                getLines()[lineIndex++] = line;
            }
        }
        
        return this;
    }
}
