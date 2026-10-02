package fruit.service;

import fruit.pojo.Fruit;

import java.util.List;

public interface FruitService {

    //根据查询关键字检索指定页的数据
    List<Fruit> getFruitList(String keyword,Integer pageNo);

    //查询总页数
    Integer getPageCount(String keyword);

    //添加新库存
    void addFruit(Fruit fruit);

    //修改指定库存
    void updateFruit(Fruit fruit);

    //删除指定库存
    void delFruit(Integer id);

    //查询指定的库存
    Fruit getFruitById(Integer id);
}
