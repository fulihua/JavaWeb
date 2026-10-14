package myssm;

import org.xml.sax.SAXException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author fulihua
 * @date 2026/10/11 9:08 PM
 * @description
 */
public class TransManager {
   private static ThreadLocal<Connection> tl = new ThreadLocal<>();

   public static void begin(){
       try{
           System.out.println("开启事务...");
           Connection conn = ConnUtil.getConnection();
           tl.set(conn);
           conn.setAutoCommit(false);
           System.out.println("-----"+conn.hashCode()+"-----");
       }catch (SQLException e){
           e.printStackTrace();
           throw new RuntimeException(e.getMessage());
       }
   }

   public static void commit() {
       Connection conn = tl.get();
       if (conn == null) {
           return;
       }
       try {
           System.out.println("提交事务...");
           conn.commit();
           System.out.println("-----" + conn.hashCode() + "-----");
       } catch (SQLException e) {
           e.printStackTrace();
           throw new RuntimeException(e.getMessage());
       }
   }

   public static void rollback(){
       Connection conn = tl.get();
       if(conn==null){return;}
       try {
           System.out.println("回滚事务...");
           conn.rollback();
           System.out.println("-----"+conn.hashCode()+"-----");
       }catch (SQLException e){
           e.printStackTrace();
           throw new RuntimeException(e.getMessage());
       }
   }

   public static void close(){
       Connection conn = tl.get();
       if(conn!=null){
           try{
              if(!conn.isClosed()){
                   ConnUtil.close(conn);
               }
           }catch (SQLException e){
               e.printStackTrace();
               throw new RuntimeException(e.getMessage());
           }
       }
       tl.remove();
   }

   public static Connection getConnection(){
       return tl.get();
   }

}