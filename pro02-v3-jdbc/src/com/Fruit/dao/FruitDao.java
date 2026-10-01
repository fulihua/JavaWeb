package com.Fruit.dao;

import com.Fruit.pojo.Fruit;

import java.util.List;


/**
 * @author fulihua
 * @date 2026/9/5 1:58 PM
 * @description 水果统计表练习
 */
public interface FruitDao {
    //获取所有的库存记录
    List<Fruit> getFruitList();
    //添加指定的库存记录
    void addFruit(Fruit fruit);
    //删除指定的库存记录
    void delFruit(Integer id);
    //获取指定的库存记录
    Fruit getFruit(Integer id);
    Fruit getFruit(String fname);
    //修改指定的库存记录
    void updateFruit(Fruit fruit);
}
//DAO: Data Access Object