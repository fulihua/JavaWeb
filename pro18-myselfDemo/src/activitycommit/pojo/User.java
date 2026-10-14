package activitycommit.pojo;

import java.util.Date;

/**
 * @author fulihua
 * @date 2026/10/11 7:39 PM
 * @description
 */
public class User {
    private String name;
    private Integer age;
    private String major;
    private String gender;
    private String password;

    private Integer id;

    private Date createTime;

    public User(String name, Integer age, String major, String gender, String password, Integer id, Date create_time) {
        this.name = name;
        this.age = age;
        this.major = major;
        this.gender = gender;
        this.password = password;
        this.id = id;
        this.createTime = createTime;
    }

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", major='" + major + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", create_time=" + createTime +
                '}';
    }
}