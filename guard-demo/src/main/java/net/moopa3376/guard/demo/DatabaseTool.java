package net.moopa3376.guard.demo;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class DatabaseTool
{
    public static Connection connection = null;
    public static String url = "jdbc:mysql://106.14.159.9:3306/eat";
    public static String username = "root";
    public static String password = "Lxflhz8888";
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static ResultSet query(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public static void close(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
