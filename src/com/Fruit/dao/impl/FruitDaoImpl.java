package com.Fruit.dao.impl;

import com.Fruit.dao.FruitDao;
import com.Fruit.pojo.Fruit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/9/5 1:59 PM
 * @description
 */
public class FruitDaoImpl implements FruitDao {
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql:///fruitdb?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private final String USER = "root";
    private final String PWD = "abcd1234";

    Connection conn = null;
    PreparedStatement psmt = null;//预编译的SQL执行对象，是Statement的升级版，用来执行带参数的SQL语句
    ResultSet rs = null;//ResultSet是JDBC中用来存放数据库查询结果集的对象

    @Override
    public List<Fruit> getFruitList() {
        List<Fruit> fruitList = new ArrayList<>();
        try {
            //1.加载驱动
            Class.forName(DRIVER);
            //2.通过驱动管理器获取连接对象
            conn = DriverManager.getConnection(URL, USER, PWD);
            //3.编写SQL语句
            String sql = "select * from t_fruit";
            //4.编写预处理命令对象
            psmt = conn.prepareStatement(sql);
            //5.执行查询
            rs = psmt.executeQuery();
            //6.解析结果集
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String fname = rs.getString(2);
                Integer fprice = rs.getInt(3);
                Integer fcount = rs.getInt(4);
                String remark = rs.getString(5);

                Fruit fruit = new Fruit(id, fname, fprice, fcount, remark);
                fruitList.add(fruit);
            }
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();//print:打印  StackTrace:堆栈信息
            //作用：在控制台打印完整的报错信息
        }finally{
            try{
                if(rs != null){
                    rs.close();//1. 如果 rs 不为 null，但它**已经关闭**，再次调用`.close()`，**一般不会报错**（很多驱动做了防护，重复关闭无异常）。
                }
                if(psmt != null){
                    psmt.close();//1. 如果 rs 不为 null，但它**已经关闭**，再次调用`.close()`，**一般不会报错**（很多驱动做了防护，重复关闭无异常）。
                }
                if(conn!=null && !conn.isClosed()){
                    conn.close();//1. 如果连接对象不为 null，但是**已经关闭**，再调用`conn.close()`，**部分 MySQL 驱动会抛出 SQLException 异常**。
                    //所以多加一层`!conn.isClosed()`，避免重复关闭连接触发异常。
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return fruitList;
    }

    @Override //作用：告诉编译器：**下面这个方法，是重写父类 / 实现接口里的方法**。
    //让编译器帮你检查：方法名、返回值、参数列表，是不是和接口 / 父类一模一样。
    public void addFruit(Fruit fruit){
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);//1. `DriverManager`：JDBC 提供的驱动管理工具类，专门用来获取数据库连接
            String sql = "insert into t_fruit values(0,?,?,?,?)";
            psmt = conn.prepareStatement(sql);
            //填充参数
            psmt.setString(1,fruit.getFname());
            psmt.setInt(2,fruit.getFprice());
            psmt.setInt(3,fruit.getFcount());
            psmt.setString(4,fruit.getRemark());
            int count = psmt.executeUpdate();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(rs != null) {
                    rs.close();
                }
                if(psmt != null) {
                    psmt.close();
                }
                if(conn!=null && !conn.isClosed()){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void delFruit(Integer id){
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);//1. `DriverManager`：JDBC 提供的驱动管理工具类，专门用来获取数据库连接
            String sql = "delete from t_fruit where id = ?";
            psmt = conn.prepareStatement(sql);
            //填充参数
             psmt.setInt(1,id);
            int count = psmt.executeUpdate();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(rs != null) {
                    rs.close();
                }
                if(psmt != null) {
                    psmt.close();
                }
                if(conn!=null && !conn.isClosed()){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public Fruit getFruit(Integer id){
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);//1. `DriverManager`：JDBC 提供的驱动管理工具类，专门用来获取数据库连接
            String sql = "select * from t_fruit where id = ?";
            psmt = conn.prepareStatement(sql);
            //填充参数
            psmt.setInt(1,id);
            rs = psmt.executeQuery();//**执行查询类 SQL（SELECT），返回查询结果，放到 ResultSet 里面**
            if (rs.next()) {
                String fname = rs.getString(2);
                Integer fprice = rs.getInt(3);
                Integer fcount = rs.getInt(4);
                String remark = rs.getString(5);

                return new Fruit(id , fname , fprice , fcount , remark);}

            }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(rs != null) {
                    rs.close();
                }
                if(psmt != null) {
                    psmt.close();
                }
                if(conn!=null && !conn.isClosed()){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Fruit getFruit(String fname){
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);//1. `DriverManager`：JDBC 提供的驱动管理工具类，专门用来获取数据库连接
            String sql = "select * from t_fruit where fname = ?";
            psmt = conn.prepareStatement(sql);
            //填充参数
            psmt.setString(1,fname);//1 代表：SQL 里**第 1 个 `?` 占位符**

            //setXxx (占位序号，值)
            //序号必须和 SQL 中`?`的位置一一对应，不能随便乱写。

            rs = psmt.executeQuery();//**执行查询类 SQL（SELECT），返回查询结果，放到 ResultSet 里面**
            if (rs.next()) {
                /*
                简单总结：
`select *` → 列顺序 = **数据库建表的字段顺序**；
自己手写 select 列 1, 列 2 → 列顺序 = **你写的先后顺序**。
                 */
                Integer id = rs.getInt(1);
                Integer fprice = rs.getInt(3);
                Integer fcount = rs.getInt(4);
                String remark = rs.getString(5);

                return new Fruit(id , fname , fprice , fcount , remark );}

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(rs != null) {
                    rs.close();
                }
                if(psmt != null) {
                    psmt.close();
                }
                if(conn!=null && !conn.isClosed()){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void updateFruit(Fruit fruit){
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);//1. `DriverManager`：JDBC 提供的驱动管理工具类，专门用来获取数据库连接
            String sql = "update t_fruit set fcount = ? where id = ? ";
            psmt = conn.prepareStatement(sql);
            //填充参数
            psmt.setInt(1,fruit.getFcount());
            psmt.setInt(2,fruit.getId());
            int count = psmt.executeUpdate();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(rs != null) {
                    rs.close();
                }
                if(psmt != null) {
                    psmt.close();
                }
                if(conn!=null && !conn.isClosed()){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
