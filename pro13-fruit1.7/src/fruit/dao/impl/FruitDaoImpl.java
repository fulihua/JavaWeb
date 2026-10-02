package fruit.dao.impl;

import fruit.dao.FruitDao;
import fruit.pojo.Fruit;
import fruit.dao.base.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FruitDaoImpl extends BaseDao<Fruit> implements FruitDao {

    @Override
    public List<Fruit> getFruitList() {
        return executeQuery("select * from t_fruit");
    }

    @Override
    public List<Fruit> getFruitList(Integer pageNo){
        return executeQuery("select * from t_fruit limit ?,5",(pageNo-1)*5);
    }
    @Override
    public List<Fruit> getFruitList(String keyword,Integer pageNo){
        return executeQuery("select * from t_fruit where fname like ? or remark like ? limit ?,5","%"+keyword+"%","%"+keyword+"%",(pageNo-1)*5);
    }

    @Override
    public Fruit getFruitById(Integer id) {
        return load("select * from t_fruit where id = ? ",id);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        executeUpdate("update t_fruit set price = ?,fcount=?,remark=?where id=?",fruit.getPrice(),fruit.getFcount(),fruit.getRemark(),fruit.getId());
    }
    @Override
    public void delFruit(Integer id){
        executeUpdate("delete from t_fruit where id = ? ",id);
    }
    @Override
    public void addFruit(Fruit fruit){
        executeUpdate("insert into t_fruit values(0,?,?,?,?)",fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark());
    }
    @Override
    public Integer getTotalCount(){
    return getTotalCount("");
    }
    @Override
    public Integer getTotalCount(String keyword){
        String sql = "select count(*) from t_fruit where fname like ? or remark like ?";
        List<Object[]> list = executeComplexQuery(sql,"%"+keyword+"%","%"+keyword+"%");
        return ((Long)list.get(0)[0]).intValue();
    }
}

