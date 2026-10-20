package book.dao;

import book.pojo.OrderBean;
import book.pojo.User;

import java.util.List;

public interface OrderBeanDao {
     void addOrderBean(OrderBean orderBean);

    List<OrderBean> getOrderBeanList(User user);
}
