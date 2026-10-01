package fruit.dao.impl;

import fruit.pojo.Fruit;

import javax.xml.transform.Result;
import java.awt.desktop.UserSessionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/9/26 8:10 PM
 * @description
 */
public class FruitDaoImpl implements FruitDao{
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql:///fruitdb?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private final String USER = "root";
    private final String PWD = "1123456";

    Connection conn = null;
    PreparedStatement psmt = null;//PreparedStatement是JDBC里用来执行预编译SQL的对象,防止SQL注入，比Statment安全
    ResultSet rs = null;//ResultSet是JDBC里存查询结果集的对象，用来装MySQL查出来的多行数据（select返回的表数据）
    /*
    注意：
    ### 一句话区分

- ✅ SELECT 查询 → 拿到数据表 → `executeQuery()` → ResultSet
- ✅ INSERT / UPDATE / DELETE 增删改 → 拿到影响行数 → `executeUpdate()` → int 数字，没有 ResultSet
     */

    @Override
    public List<Fruit> getFruitList(){
        List<Fruit> fruitList = new ArrayList<>();
        try{
            //1.加载驱动
            Class.forName(DRIVER);
            //2.通过驱动管理器获取连接对象
            conn = DriverManager.getConnection(URL,USER,PWD);
            //3.编写SQL语句
            String sql = "select *from t_fruit";
            //4.创建预处理命令对象
            psmt = conn.prepareStatement(sql);
            //5.执行查询
            rs = psmt.executeQuery();
            //6.解析结果集
            while(rs.next()){
                Integer id = rs.getInt(1);
                String fname = rs.getString(2);
                Integer price = rs.getInt(3);
                Integer fcount = rs.getInt(4);
                String remark = rs.getString(5);
                Fruit fruit = new Fruit(id,fname,price,fcount,remark);
                fruitList.add(fruit);
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(rs != null){
                    rs.close();
                }
                if(psmt != null){
                    psmt.close();
                }
                if(conn != null && !conn.isClosed()){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return fruitList;
    }

    @Override
    public void addFruit(Fruit fruit){
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);
            String sql = "insert into t_fruit values(0,?,?,?,?)";
            psmt = conn.prepareStatement(sql);
            //填充参数
            psmt.setString(1,fruit.getFname());
            psmt.setInt(2,fruit.getPrice());
            psmt.setInt(3,fruit.getFcount());
            psmt.setString(4, fruit.getRemark());
            int count = psmt.executeUpdate();
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(rs!=null){
                    rs.close();
                }
                if(psmt!=null){
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
            Class.forName(DRIVER);//拿驱动图纸进 JVM，登记驱动
            conn = DriverManager.getConnection(URL,USER,PWD);//打通通往 MySQL 的通道 conn
            String sql = "delete from t_fruit where id = ?";
            psmt = conn.prepareStatement(sql);//提交 SQL 纸条，拿到任务处理器 psmt
            //填充参数
            psmt.setInt(1,id);
            int count = psmt.executeUpdate();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(rs!=null){
                    rs.close();
                }
                if(psmt!=null){
                    psmt.close();
                }
                if(conn!=null&&!conn.isClosed()){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public Fruit getFruit(Integer id) {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "select * from t_fruit where id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            rs = psmt.executeQuery();
            if (rs.next()) {
                String fname = rs.getString(2);
                Integer price = rs.getInt(3);
                Integer fcount = rs.getInt(4);
                String remark = rs.getString(5);

                return new Fruit(id, fname, price, fcount, remark);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psmt != null) {
                    psmt.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Fruit getFruit(String fname){
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "select * from t_fruit where id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, fname);
            rs = psmt.executeQuery();
            if (rs.next()) {
                Integer id = rs.getInt(1);
                Integer price = rs.getInt(3);
                Integer fcount = rs.getInt(4);
                String remark = rs.getString(5);

                return new Fruit(id, fname, price, fcount, remark);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psmt != null) {
                    psmt.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void updateFruit(Fruit fruit){
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);
            String sql = "update into t_fruit set fcount = ? where id = ?";
            psmt = conn.prepareStatement(sql);
            //填充参数
            psmt.setInt(1,fruit.getFcount());
            psmt.setInt(2, fruit.getId());
            int count = psmt.executeUpdate();
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(rs!=null){
                    rs.close();
                }
                if(psmt!=null){
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