package com.Fruit.pojo;

/**
 * @author fulihua
 * @date 2026/9/5 2:19 PM
 * @description
 */
public class Fruit {
    private Integer id;
    private String fname;
    private Integer fprice;
    private Integer fcount;
    private String remark;

    public Fruit() {}

    public Fruit(Integer id, String fname, Integer fprice, Integer fcount, String remark) {
        this.id = id;
        this.fname = fname;
        this.fprice = fprice;
        this.fcount = fcount;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public Integer getFprice() {
        return fprice;
    }

    public Integer getFcount() {
        return fcount;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFprice(Integer fprice) {
        this.fprice = fprice;
    }

    public void setFcount(Integer fcount) {
        this.fcount = fcount;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", fprice=" + fprice +
                ", fcount=" + fcount +
                ", remark='" + remark + '\'' +
                '}';
    }
}