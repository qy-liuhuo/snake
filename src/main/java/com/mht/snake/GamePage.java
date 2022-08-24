package com.mht.snake;

import com.mht.snake.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

/**
 * 游戏界面控制器
 */
public class GamePage extends Application {
    //游戏对象
    private Game game;
    //画板
    private Canvas canvas;
    //当前窗体
    private Stage stage;
    private Pane pane;

    /**
     * 设置游戏并开始
     * @param game
     */
    public void setGame(Game game) throws IOException {
        this.game = game;
        game.setGameStage(GameStage.GOING);
        //添加键盘点击事件
        addKeyboardFun();
        //新建游戏线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    game.goAhead();
                    //游戏结束，弹窗，需要去父线程操作
                    if(game.getGameStage()==GameStage.END)
                    {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("游戏结束");
                                alert.setHeaderText(null);
                                ScoreRecord s=new ScoreRecord();
                                //存记录，获得排名
                                int rank=1;
                                try {
                                    s.insertRecord(game.getScore());
                                    rank=s.getRank(game.getScore());
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                alert.setContentText("本局得分："+game.getScore()+", 排名"+rank);
                                ButtonType returnButton = new ButtonType("主菜单");
                                ButtonType newGameButton = new ButtonType("新游戏");
                                alert.getButtonTypes().setAll(returnButton, newGameButton);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == returnButton){
                                    stage.close();
                                    try {
                                        new MainApp().start(new Stage());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else if (result.get() == newGameButton) {
                                    game.ResetGame();
                                }

                            }
                        });
                        while(game.getGameStage()==GameStage.END){
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                break;
                            }
                        };
                    }
                    //游戏暂停
                    while(game.getGameStage()== GameStage.STOP)
                    {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    //画图
                    drawSnake();
                    //设置速度
                    try {
                        Thread.sleep(600-100*game.getLevelDifficulty());
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }).start();
    }

    /**
     * 绘制
     */
    private void drawSnake()
    {
        GraphicsContext context=canvas.getGraphicsContext2D();
        context.clearRect(0,0,800,800);
        Random rand=new Random();
        double size=800/game.getMapSize();
        for (Node i:game.getSnake().getBody()) {
            context.setFill(i.getColor());
            context.fillRect(i.getX()*size,i.getY()*size,size,size);
        }
        Node food=game.getFood();
        context.setFill(food.getColor());
        context.fillRect(food.getX()*size, food.getY()*size,size,size);
    }

    /**
     * 添加键盘点击事件
     */
    private void addKeyboardFun(){
        pane.requestFocus();
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().getName().equals(KeyCode.RIGHT.getName())) {
                    game.setDirection(Direction.RIGHT);
                }
                if (keyEvent.getCode().getName().equals(KeyCode.LEFT.getName())) {
                    game.setDirection(Direction.LEFT);
                }
                if (keyEvent.getCode().getName().equals(KeyCode.DOWN.getName())) {
                    game.setDirection(Direction.DOWN);
                }
                if (keyEvent.getCode().getName().equals(KeyCode.UP.getName())) {
                    game.setDirection(Direction.UP);
                }
                if (keyEvent.getCode().getName().equals(KeyCode.SPACE.getName())) {
                    if(game.getGameStage()==GameStage.GOING)
                    {
                        game.setGameStage(GameStage.STOP);
                    }
                    else if(game.getGameStage()==GameStage.STOP)
                    {
                        game.setGameStage(GameStage.GOING);
                    }
                }
                //保存游戏
                if(keyEvent.getCode().getName().equals(KeyCode.ENTER.getName()))
                {
                    try {
                        game.saveGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 界面初始化
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        pane = new Pane();
        canvas = new Canvas(800, 800);
        pane.getChildren().add(canvas);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("贪吃蛇");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        stage=primaryStage;
    }

}
