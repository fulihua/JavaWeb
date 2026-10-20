package book.dao.Impl;

import book.dao.OrderBeanDao;
import book.pojo.OrderBean;
import book.pojo.User;
import myssm.base.BaseDao;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/17 8:35 PM
 * @description
 */
public class OrderBeanDaoImpl extends BaseDao<OrderBean> implements OrderBeanDao {
    @Override
    public void addOrderBean(OrderBean orderBean) {
        Integer id = executeUpdate("insert into t_order values(0,?,?,?,?,?,?)",orderBean.getOrderNo(),orderBean.getOrderDate(),orderBean.getOrderMoney(),orderBean.getOrderCount(),orderBean.getOrderStatus(),orderBean.getUser().getId());
        //这里的orderBean.getUser().getId()，不是创建User对象！只是拿到用户的主键数字，把这个数字存入订单表的外键字段！我们的order表里面的user类型是Integer类型的，只是存了user的id，并非把整个user对象都存进去了！！！
        orderBean.setId(id);//这里数据库自动生成主键id，数据库把id值生成出来之后，Java内存里的orderbean对象还不知道这个id！
        //orderBean.setId(id);就是把数据库返回的自增主键回填到我们的java对象里面
    }

    @Override
    public List<OrderBean> getOrderBeanList(User user) {
        return executeQuery("select * from t_order where user = ? order by orderDate desc",user.getId());
    }
}