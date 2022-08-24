module com.mht.snake {
    requires javafx.controls;
    requires javafx.fxml;

    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;
    requires fastjson;

    opens com.mht.snake to javafx.fxml;
    exports com.mht.snake;
    exports com.mht.snake.model;
    opens com.mht.snake.model to javafx.fxml;
}