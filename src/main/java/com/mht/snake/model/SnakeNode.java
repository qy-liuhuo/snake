package com.mht.snake.model;

import javafx.scene.paint.Color;

/**
 * 蛇节点 ，继承node
 */
public class SnakeNode extends Node {
    public SnakeNode(int x, int y) {
        super(x, y, Color.rgb(46, 204, 113));
    }
}
