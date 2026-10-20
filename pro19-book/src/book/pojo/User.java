package book.pojo;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/17 7:45 PM
 * @description
 */
public class User {
    private Integer id;
    private String uname;
    private String pwd;
    private String email;
    private Integer role;
    private Cart cart;
    private List<OrderBean> orderBeanList;

    public User() {
    }

    public User(Integer id, String uname, String pwd, String email, Integer role) {
        this.id = id;
        this.uname = uname;
        this.pwd = pwd;
        this.email = email;
        this.role = role;
    }

    public User(String name, String pwd, String email) {
        this.uname = name;
        this.pwd = pwd;
        this.email = email;
        this.role = 0;//0表示普通用户，1表示管理员
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return uname;
    }

    public void setName(String name) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", cart=" + cart +
                '}';
    }

    public void setOrderBeanList(List<OrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
    }
}