package book.pojo;

/**
 * @author fulihua
 * @date 2026/10/17 7:50 PM
 * @description
 */
public class Book {
    private Integer id;
    private String bookName;
    private String author;
    private Double price;
    private Integer saleCount;
    private Integer bookCount;
    private String bookImg;
    private Integer status;

    public Book() {
    }

    public Book(Integer id, String bookName, String author, Double price, Integer saleCount, Integer bookCount, String bookImg, Integer status) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
        this.saleCount = saleCount;
        this.bookCount = bookCount;
        this.bookImg = bookImg;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", saleCount=" + saleCount +
                ", bookCount=" + bookCount +
                ", bookImg='" + bookImg + '\'' +
                ", status=" + status +
                '}';
    }
}