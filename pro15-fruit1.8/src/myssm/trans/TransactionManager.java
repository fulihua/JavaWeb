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
    public static void begin() {
        try {
            System.out.println("开启事务...");
            Connection conn = ConnUtil.getConn();
            conn.setAutoCommit(false);
            System.out.println("-----------" + conn.hashCode() + "-----------");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    public static void commit(){
        try{
            System.out.println("提交事务...");
            Connection conn = ConnUtil.getConn();
            conn.commit();
            System.out.println("-----------"+conn.hashCode()+"-----------");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
      }
      public static void rollback(){
          try {
              System.out.println("回滚事务....");
              Connection conn = ConnUtil.getConn();
              conn.rollback();
              System.out.println("-----------"+conn.hashCode()+"-----------");
          } catch (SQLException e) {
              e.printStackTrace();
              throw new RuntimeException(e.getMessage());
          }
      }
    }



