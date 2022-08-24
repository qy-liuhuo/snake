package com.mht.snake.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.mht.snake.model.GameStage;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.Random;

public class Game {


    //分数
    private Integer score;
    //难度
    private Integer levelDifficulty;
    //地图大小
    private Integer mapSize;
    //游戏阶段
    private GameStage gameStage;
    //蛇
    private Snake snake;
    //食物
    private FoodNode food;
    //随机数生成器
    private Random random;
    /**
     * 构造方法
     * @param levelDifficulty 难度
     * @param mapSize 地图大小
     */
    public Game(Integer levelDifficulty, Integer mapSize) throws IOException {
        this.score=0;
        this.levelDifficulty=levelDifficulty;
        this.mapSize=mapSize;
        this.gameStage = GameStage.Begin;
        //创建随机数生成器
        random = new Random();
        //实例化蛇
        snake=new Snake();
        snake.snakeInit();
        //实例化食物
        generateFood();
    }
    public Game()
    {
    }
    /**
     * 恢复游戏
     */
    public void ResetGame()
    {
        this.score=0;
        this.gameStage = GameStage.GOING;
        //创建随机数生成器
        random = new Random();
        //实例化蛇
        snake=new Snake();
        snake.snakeInit();
        //实例化食物
        generateFood();
    }
    /**
     * 随机生成食物对象，保证在地图内且不与蛇身体冲突
     * @return
     */
    private void generateFood()
    {
        Integer x,y;
        do {
            x=random.nextInt(mapSize);
            y=random.nextInt(mapSize);
        }while (snake.judgeConflict(x,y));
        food=new FoodNode(x,y);
    }


    public Boolean goAhead()
    {
       snake.move();
        /**
         * 吃到了食物
         */
       if(snake.getHead().equals(food))
       {
            //生成新的食物
            generateFood();
            //加分
            score++;
       }
       else
       {
           //没吃到食物
           snake.removeTail();
           Node head=snake.getHead();
           //游戏结束
           if(snake.ifSuicide()||head.getX()>=mapSize||head.getX()<0||head.getY()>=mapSize||head.getY()<0||judgeFull())
           {
               gameStage=GameStage.END;
               return false;
           }
       }
       return true;
    }

    /**
     * 判断是否满了
     * @return
     */
    public Boolean judgeFull()
    {
        if(this.snake.getSize()==mapSize*mapSize)
        {
            return true;
        }
        return false;
    }
    public void saveGame() throws IOException {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"/../game.json";
        //获取资源路径
        //String path = getClass().getResource("game.json").getPath();
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),"UTF-8");
        String json= JSON.toJSONString(this);
        osw.write(json);
        osw.flush();//清空缓冲区，强制输出数据
        osw.close();//关闭输出流
        this.gameStage=GameStage.END;
    }
    public Game readGame() throws IOException {
        BufferedReader reader = null;
        String laststr = "";
        Game newGame;
        try {
            String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"/../game.json";
            InputStream inputStream =new FileInputStream(path);
            //设置字符编码为UTF-8，避免读取中文乱码
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            // 通过BufferedReader进行读取
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
            }
            //关闭BufferedReader
            reader.close();
            newGame=JSON.parseObject(laststr, Game.class);
        } catch (IOException e) {
            System.out.println("存档异常，开始新游戏");
            newGame=new Game(1,10);
        } finally {
            if (reader != null) {
                try {
                    //不管执行是否出现异常，必须确保关闭BufferedReader
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return newGame;
    }
    /**
     * 设置方向
     * @param d
     */
    public void setDirection(Direction d)
    {
        snake.setDirection(d);
    }
    public Integer getMapSize() {
        return mapSize;
    }
    public GameStage getGameStage() {
        return gameStage;
    }
    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }
    public Snake getSnake() {
        return snake;
    }
    public FoodNode getFood() {
        return food;
    }
    public Integer getLevelDifficulty() {
        return levelDifficulty;
    }
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }

    public void setLevelDifficulty(Integer levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }

    public void setMapSize(Integer mapSize) {
        this.mapSize = mapSize;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setFood(FoodNode food) {
        this.food = food;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
