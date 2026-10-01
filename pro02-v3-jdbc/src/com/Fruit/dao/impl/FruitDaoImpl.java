package com.Fruit.dao.impl;

import com.Fruit.dao.FruitDao;
import com.Fruit.dao.base.BaseDao;
import com.Fruit.pojo.Fruit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/9/5 1:59 PM
 * @description
 */
public class FruitDaoImpl extends BaseDao<Fruit> implements FruitDao {

    @Override
    public List<Fruit> getFruitList() {

        String sql = "select * from t_fruit";
        return super.executeQuery(sql);
    }

    @Override //作用：告诉编译器：**下面这个方法，是重写父类 / 实现接口里的方法**。
    //让编译器帮你检查：方法名、返回值、参数列表，是不是和接口 / 父类一模一样。
    public void addFruit(Fruit fruit){
        String sql = "insert into t_fruit values(0,?,?,?,?)";
        executeUpdate(sql,fruit.getFname(),fruit.getFprice(),fruit.getFcount(),fruit.getRemark());
    }
    @Override
    public void delFruit(Integer id){
        String sql = "delete from t_fruit where id = ?";
        executeUpdate(sql,id);
    }

    @Override
    public Fruit getFruit(Integer id){
            return super.load("select * from t_fruit where id = ?", id);
        }
    @Override
    public Fruit getFruit(String fname){
            return super.load("select * from t_fruit where fname = ?", fname);
        }

    @Override
    public void updateFruit(Fruit fruit){
           String sql = "update t_fruit set fcount = ? where id = ? ";
           executeUpdate(sql,fruit.getFcount(),fruit.getId());
    }
}
