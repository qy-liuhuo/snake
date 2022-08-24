package com.mht.snake.model;

import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * 节点类
 */
public class Node{


    //坐标
    private Integer x,y;
    //颜色
    private Color color;
    public Node(int x,int y)
    {
        this.x=x;
        this.y=y;
    }
    public Node(int x,int y,Color c)
    {
        this.x=x;
        this.y=y;
        this.color=c;
    }
    public Integer getX() {
        return x;
    }
    public Integer getY()
    {
        return y;
    }
    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }

    /**
     * 重写比较方法
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
       Node a=(Node) obj;
       return (this.x==a.getX())&&(this.y==a.getY());
    }
    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
