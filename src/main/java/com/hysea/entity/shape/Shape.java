package com.hysea.entity.shape;

import com.hysea.entity.Line;

public abstract class Shape {

    private Line[] lines;

    public Line[] getLines() {
        return lines;
    }

    public void setLines(Line[] lines) {
        this.lines = lines;
    }

    public abstract Shape create();
}
