package fruit.pojo;

/**
 * @author fulihua
 * @date 2026/9/28 7:12 PM
 * @description
 */
public class Fruit {
    private Integer id;
    private String fname;
    private Integer price;
    private Integer fcount;
    private String remark;

    public Fruit() {}

    public Fruit(Integer id, Integer price, Integer fcount, String remark) {
        this.id = id;
        this.price = price;
        this.fcount = fcount;
        this.remark = remark;
    }

    public Fruit(String fname, Integer price, Integer fcount, String remark) {
        this.fname = fname;
        this.price = price;
        this.fcount = fcount;
        this.remark = remark;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setFcount(Integer fcount) {
        this.fcount = fcount;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFname() {
        return fname;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getFcount() {
        return fcount;
    }

    public String getRemark() {
        return remark;
    }
}