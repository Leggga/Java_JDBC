package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtils {
   public static void rollBackImmediately(Connection con){
        if ( con != null){
            try {
                con.rollback();
            } catch (SQLException e) {
                System.out.println("Error! The connection didn't rollback");
            }
        }
    }

   public static void closeResource(Statement statement){
        if ( statement != null){
            try {
                statement.close();
            } catch (Exception e) {
                System.out.println("Error! A statement didn't close");
            }
        }
    }

   public static void closeResource(Connection conn){
        if ( conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("Error! A connection didn't close");
            }
        }
    }
}
