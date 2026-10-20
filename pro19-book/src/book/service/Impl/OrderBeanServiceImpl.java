package book.service.Impl;

import book.dao.OrderBeanDao;
import book.pojo.OrderBean;
import book.pojo.User;
import book.service.OrderBeanService;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/17 8:33 PM
 * @description
 */
public class OrderBeanServiceImpl implements OrderBeanService{
    private OrderBeanDao orderBeanDao;
    @Override
    public void addOrderBean(OrderBean orderBean) {
        orderBeanDao.addOrderBean(orderBean);
    }

    @Override
    public List<OrderBean> getOrderBeanList(User user) {
        return orderBeanDao.getOrderBeanList(user);
    }
}