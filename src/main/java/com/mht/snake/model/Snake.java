package com.mht.snake.model;

import javafx.scene.paint.Color;

import java.util.LinkedList;

public class Snake {


    //移动方向
    private Direction direction;
    //身体
    private LinkedList<SnakeNode> body=new LinkedList<>();

    /**
     * 设置移动方向
     * @param d 移动方向
     */
    public void setDirection(Direction d) {
        //不能直接掉头
        if((d==Direction.DOWN&&direction==Direction.UP)||(d==Direction.UP&&direction==Direction.DOWN)){
            return;
        }
        if((d==Direction.RIGHT&&direction==Direction.LEFT)||(d==Direction.LEFT&&direction==Direction.RIGHT)){
            return;
        }
        this.direction = d;
    }

    /**
     * 构造方法
     */
    public Snake()
    {

    }
    public void snakeInit()
    {
        //初始化三个节点
        SnakeNode head=new SnakeNode(2,0);
        shiftToHead(head);
        body.add(head);
        body.add(new SnakeNode(1,0));
        body.add(new SnakeNode(0,0));
        //初始化方向
        this.direction=Direction.RIGHT;
    }
    /**
     * 蛇移动
     */
    public void move()
    {
        Integer headX=body.getFirst().getX();
        Integer headY=body.getFirst().getY();
        SnakeNode newHead;
        switch (direction)
        {
            case UP:
                newHead=new SnakeNode(headX,headY-1);
                break;
            case DOWN:
                newHead=new SnakeNode(headX,headY+1);
                break;
            case LEFT:
                newHead=new SnakeNode(headX-1,headY);
                break;
            default:
                newHead=new SnakeNode(headX+1,headY);
                break;
        }
        shiftToHead(newHead);
        shiftToTail(body.getFirst());
        //沿着前进方向增加节点
        body.addFirst(newHead);
    }

    /**
     * 判断(x,y)是否与蛇身体冲突
     * @param x
     * @param y
     * @return
     */
    public Boolean judgeConflict(Integer x,Integer y)
    {
        for (SnakeNode node:body){
            if(node.getX()==x&&node.getY()==y)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断蛇是否“自杀”
     * @return
     */
    public Boolean ifSuicide()
    {
        SnakeNode head=getHead();
        for (SnakeNode node:body){
            if(node==head)
            {
                continue;
            }
            if(node.equals(head))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * 转换为头结点
     */
    public void shiftToHead(SnakeNode snakeNode)
    {
        snakeNode.setColor(Color.rgb(243, 156, 18));
    }

    /**
     * 转换为普通身体节点
     */
    public void shiftToTail(SnakeNode snakeNode)
    {
        snakeNode.setColor(Color.rgb(46, 204, 113));
    }
    /**
     * 删除尾结点
     */
    public void removeTail()
    {
        body.removeLast();
    }
    /**
     * 获得头结点
     * @return
     */
    public SnakeNode getHead()
    {
        return body.getFirst();
    }
    public LinkedList<SnakeNode> getBody() {
        return body;
    }
    public Integer getSize()
    {
        return body.size();
    }
    public Direction getDirection() {
        return direction;
    }

    public void setBody(LinkedList<SnakeNode> body) {
        this.body = body;
    }
}
