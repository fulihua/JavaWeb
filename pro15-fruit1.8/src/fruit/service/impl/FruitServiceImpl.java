package fruit.service.impl;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import fruit.service.FruitService;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/2 1:01 PM
 * @description
 */
public class FruitServiceImpl implements FruitService {
    private FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        return fruitDao.getFruitList(keyword, pageNo);
    }

    @Override
    public Integer getPageCount(String keyword) {
        Integer totalCount = fruitDao.getTotalCount(keyword);
        return (totalCount+5-1)/5;
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDao.addFruit(fruit);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDao.updateFruit(fruit);
    }

    @Override
    public void delFruit(Integer id) {
        fruitDao.delFruit(id);
    }

    @Override
    public Fruit getFruitById(Integer id) {
       return fruitDao.getFruitById(id);
    }
}