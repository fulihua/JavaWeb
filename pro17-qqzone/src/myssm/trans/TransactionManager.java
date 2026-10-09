package myssm.trans;

import myssm.util.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author fulihua
 * @date 2026/10/4 5:18 PM
 * @description
 */
public class TransactionManager {
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();
    public static void begin() {
        try {
            System.out.println("开启事务...");
            Connection conn = ConnUtil.getConn();
            conn.setAutoCommit(false);
            tl.set(conn); // 存入当前线程
            System.out.println("-----------" + conn.hashCode() + "-----------");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    public static void commit(){
        Connection conn = tl.get();
        if(conn == null){
            return;
        }
        try{
            System.out.println("提交事务...");
            conn.commit();
            System.out.println("-----------"+conn.hashCode()+"-----------");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            close();
        }
      }
      public static void rollback(){
          Connection conn = tl.get();
          if(conn == null){
              return;
          }
          try {
              System.out.println("回滚事务....");
              conn.rollback();
              System.out.println("-----------"+conn.hashCode()+"-----------");
          } catch (SQLException e) {
              e.printStackTrace();
              throw new RuntimeException(e.getMessage());
          }finally {
              close();
          }
      }

    public static void close(){
        Connection conn = tl.get();
        if(conn != null){
            try {
                if(!conn.isClosed()){ // 判断：只有没关闭才关闭
                    ConnUtil.closeConn(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        tl.remove();
    }


    // 给BaseDao用：获取当前线程绑定的连接
    public static Connection getConnection(){
        return tl.get();
    }
}





