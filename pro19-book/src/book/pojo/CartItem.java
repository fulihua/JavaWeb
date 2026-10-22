package book.pojo;

/**
 * @author fulihua
 * @date 2026/10/17 7:53 PM
 * @description
 */
public class CartItem {
    private Integer id;
    private Book book;
    private String bookName;
    private Double price;
    private Integer buyCount;
    private User user;

    public CartItem() {
    }

    public CartItem(Integer id) {
        this.id = id;
    }

    public CartItem(Integer id, Book book, String bookName, Double price, Integer buyCount, User user) {
        this.id = id;
        this.book = book;
        this.bookName = bookName;
        this.price = price;
        this.buyCount = buyCount;
        this.user = user;
    }

    public CartItem(Book book, String bookName, Double price, Integer buyCount, User user) {
        this.book = book;
        this.bookName = bookName;
        this.price = price;
        this.buyCount = buyCount;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", book=" + book +
                ", bookName='" + bookName + '\'' +
                ", price=" + price +
                ", buyCount=" + buyCount +
                ", user=" + user +
                '}';
    }
}