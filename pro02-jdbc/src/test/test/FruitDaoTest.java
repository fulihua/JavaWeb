package test.test;

import fruit.dao.impl.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import org.testng.annotations.Test;

public class FruitDaoTest {

    private FruitDao fruitDao = new FruitDaoImpl();

    @Test
    public void test01(){
        fruitDao.getFruitList().forEach(System.out::println);
    }

    @Test
    public void test02(){
        Fruit fruit = new Fruit(0,"火龙果",3,35,"火龙果真好吃");
        fruitDao.addFruit(fruit);
    }

    @Test
    public void test03(){
        fruitDao.delFruit(30);
    }

    @Test
    public void test04(){
        System.out.println(fruitDao.getFruit(30));
    }

    @Test
    public void test05(){
        System.out.println(fruitDao.getFruit("火龙果"));
    }

    @Test
    public void test06(){
        Fruit fruit= fruitDao.getFruit("火龙果");
        fruit.setFcount(999);
        fruitDao.updateFruit(fruit);
    }
}
