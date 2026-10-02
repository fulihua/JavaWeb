package fruit.dao;

import fruit.pojo.Fruit;

import java.util.List;

public interface FruitDao {
    @Deprecated
    List<Fruit> getFruitList();
    @Deprecated
    List<Fruit> getFruitList(Integer pageNo);//默认每页显示5条记录
    List<Fruit> getFruitList(String keyword,Integer pageNO);//默认每页显示5条记录
    //List<Fruit> getFruitList(Integer pageNo,Integer pageSize);
    Fruit getFruitById(Integer id);
    void updateFruit(Fruit fruit);
    void delFruit(Integer id);
    void addFruit(Fruit fruit);
    //查询总记录条数
    @Deprecated
    Integer getTotalCount();
    Integer getTotalCount(String keyword);
}

/*
Method Overload
一个类内部同名方法之间的关系，需要满足两个条件：
1.方法名相同
2.参数签名不同。参数签名包含两个部分：个数，类型

Method Override
父子类之间同名方法的关系，需要满足如下条件：
1.父子方法必须长的"一模一样"
2.访问修饰符不能缩小（子类必须要发扬光大父类的方法）
3.子类方法不能抛出比父类方法更大的异常（子类不能给父类捅娄子）
 */