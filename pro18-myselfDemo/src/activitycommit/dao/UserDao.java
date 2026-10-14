package activitycommit.dao;

import activitycommit.pojo.User;
import myssm.BaseDao;
import myssm.TransManager;
import org.xml.sax.SAXException;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/12 9:28 AM
 * @description
 */
public class UserDao extends BaseDao<User> {
    public List<User> selectAll(){
        String sql = "select id,name,age,major,gender,password,create_time from user";
        return executeQuery(sql);
    }

    public User selectById(Integer id){
        String sql = "select id,name,age,major,gender,password,create_time from user where id = ?";
        return load(sql,id);
    }

    public int insertUser(User user){
        String sql = "insert into user(name,age,major,gender,password,create_time) values (?,?,?,?,?,?)";
        return executeUpdate(sql,user.getName(),user.getAge(),user.getMajor(),user.getGender(),user.getPassword(),new java.util.Date());
    }

    public User load(Integer id) {
        Connection conn = TransManager.getConnection();
        String sql = "select * from user where id = ?";
        PreparedStatement psmt = null;
        ResultSet rs = null;
        User user = null;
        try{
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,id);
            rs = psmt.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                user.setMajor(rs.getString("major"));
                user.setGender(rs.getString("gender"));
                user.setCreateTime(rs.getTimestamp("create_time"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs,psmt,conn);
        }
        return user;
    }

}