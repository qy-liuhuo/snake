package com.mht.snake.model;
import java.sql.*;

public class ScoreRecord {
    /**
     * 插入记录
     * @param s 得分
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void insertRecord(Integer s) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://8.142.79.40:3306/snake","snake","2Bs4maa6ynjjejhi");
        String sql = "insert into score(score,time) values(?,?)";
        PreparedStatement statement = connection.prepareCall(sql);
        java.sql.Date sd;
        java.util.Date ud;
        //initialize the ud such as
        ud = new java.util.Date();
        sd = new java.sql.Date(ud.getTime());
        statement.setInt(1,s);
        statement.setDate(2, sd);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    /**
     * 获得排名
     * @param s 得分
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Integer getRank(int s) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        Integer rank=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://8.142.79.40:3306/snake","snake","2Bs4maa6ynjjejhi");
            stmt = conn.createStatement();
            String sql = "SELECT count(*) FROM score where score>"+s;
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                rank=rs.getInt(1);
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return  rank+1;
    }
}
