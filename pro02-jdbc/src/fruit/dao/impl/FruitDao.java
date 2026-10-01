package fruit.dao.impl;
import fruit.pojo.*;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/9/26 7:55 PM
 * @description
 */
public interface FruitDao {
    //获取所有的库存记录
    List<Fruit> getFruitList();
    //添加新库存
    void addFruit(Fruit fruit);
    //删除指定的库存记录
    void delFruit(Integer id);
    //获取指定的库存记录
    Fruit getFruit(Integer id);
    Fruit getFruit(String fname);
    //修改指定的库存记录
    void updateFruit(Fruit fruit);
}