package com.mht.snake;
import com.mht.snake.model.Game;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * 游戏主菜单控制器
 */
public class MainApp extends Application {
    @FXML
    private Slider level;
    @FXML
    private Slider mapSize;
    /**
     * 开始按钮点击事件
     * @throws IOException
     */
    @FXML
    protected void startGame() throws IOException {
        //System.out.println("game start!!");
        //新建game对象
        Game game =new Game((int)level.getValue(),(int)mapSize.getValue());
        Stage stage=(Stage)level.getScene().getWindow();
        stage.close();
        GamePage gamePage= new GamePage();
        gamePage.start(new Stage());
        //传递游戏对象
        gamePage.setGame(game);
    }
    /**
     * 恢复游戏
     * @throws IOException
     */
    @FXML
    protected void restoreGame() throws IOException {
        //System.out.println("game start!!");
        //新建game对象
        Game game =new Game().readGame();
        Stage stage=(Stage)level.getScene().getWindow();
        stage.close();
        GamePage gamePage= new GamePage();
        gamePage.start(new Stage());
        //传递游戏对象
        gamePage.setGame(game);
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        primaryStage.setTitle("贪吃蛇");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}