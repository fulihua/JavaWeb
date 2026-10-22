package book.pojo;

/**
 * @author fulihua
 * @date 2026/10/17 7:59 PM
 * @description
 */
public class OrderDetail {
    private Integer id;
    private Book book;
    private String bookName;
    private Double price;
    private Integer buyCount;
    private OrderBean orderBean;

    public OrderDetail() {
    }

    public OrderDetail(Integer id) {
        this.id = id;
    }

    public OrderDetail(Integer id, Book book, String bookName, Double price, Integer buyCount, OrderBean orderBean) {
        this.id = id;
        this.book = book;
        this.bookName = bookName;
        this.price = price;
        this.buyCount = buyCount;
        this.orderBean = orderBean;
    }

    public OrderDetail(Book book, String bookName, Double price, Integer buyCount, OrderBean orderBean) {
        this.book = book;
        this.bookName = bookName;
        this.price = price;
        this.buyCount = buyCount;
        this.orderBean = orderBean;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", book=" + book +
                ", bookName='" + bookName + '\'' +
                ", price=" + price +
                ", buyCount=" + buyCount +
                ", orderBean=" + orderBean +
                '}';
    }
}