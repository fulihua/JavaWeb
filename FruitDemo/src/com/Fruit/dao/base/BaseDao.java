package com.Fruit.dao.base;

import com.alibaba.druid.pool.DruidDataSource;
import com.Fruit.util.*;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/9/6 6:04 PM
 * @description
 */
public abstract class BaseDao<T> {

    protected Connection conn = null;
    protected PreparedStatement psmt = null;
    protected ResultSet rs = null;

    private String DRIVER;
    private String URL;
    private String USER;
    private String PWD;

    private DruidDataSource dataSource;

    private String entityClassName ;


    public BaseDao() {
        loadProperties();
        initDataSource();

        ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();//1.拿到当前类的【带泛型的父类类型】，强转为ParameterizedType
        /*
       问：
        1.ParameterizedType:参数化类型（带泛型的类型）,比如BaseDao<Fruit>,这不是普通的类，是带泛型参数的类型对象，专门用来拿到尖括号<>里面写的Fruit
        2.为啥要强转？
        答：如果不强转，原代码应该是：Type genericSuper = this.getClass().getGenericSuperclass();
        1. **`Type` 是父接口**，下面有好多种实现：
           - `Class`：普通类，比如`Fruit.class`（不带泛型）
           - `ParameterizedType`：参数化类型，`BaseDao<Fruit>`（带 <> 泛型）
           - `TypeVariable`：泛型变量，`T`
           - `WildcardType`：通配符 `?`
           - `GenericArrayType`：泛型数组

`       getGenericSuperclass()` 的返回值写死定义：**返回 Type**。
        编译器只知道：返回的东西属于 Type，不知道里面到底是哪一种子类
        强转的目的：
        告诉编译器：**我确定，这里拿到的这个 Type，实际就是 ParameterizedType**，你把它当成 ParameterizedType 来用，这样我才能调用`getActualTypeArguments()`这个方法。
        > 如果不强转：变量类型是`Type`，Type 接口里**没有`getActualTypeArguments()`这个方法**，代码直接红报错。
         */
        Type[] types = parameterizedType.getActualTypeArguments();//翻译：**获取真实的类型参数**
        /*
        返回 `Type[]`，数组里面就是`<>`里写的东西。
        例子：
        - `BaseDao<Fruit>` → 数组长度 = 1，`types[0] = Fruit`
        - `Map<String,Integer>` → 数组长度 = 2，`types[0]=String，types[1]=Integer`
         */
        entityClassName = types[0].getTypeName();
        //因为当前BaseDao<T>后面只有一个泛型位置，所以此处我们使用的是[0]
        //types [0] 拿到 Fruit，`getTypeName()`得到完整包名`com.Fruit.pojo.Fruit`
    }

    /*
    反射相关内容：

    当你写 `new FruitDaoImpl()` 的时候：

1. `new FruitDaoImpl()` → 会执行子类构造，子类默认会调用父类`BaseDao`的构造方法，**这里的`this`就是刚 new 出来的 FruitDaoImpl 实例**
2. `this.getClass()` → 获取`FruitDaoImpl`的 Class 图纸
3. `.getGenericSuperclass()`：获取**带泛型信息的父类**
   - 如果用普通`getSuperclass()`，拿到的只是`BaseDao.class`，会丢掉`<Fruit>`这个泛型信息
   - `getGenericSuperclass()` 拿到：`BaseDao<Fruit>`（保留泛型！）
4. 强制转成`ParameterizedType`（带泛型参数的类型）
5. `.getActualTypeArguments()`：取出父类上**真实的泛型参数**，放到 Type 数组
   - `BaseDao<Fruit>`，泛型就 1 个，数组`types`里面只有 1 个元素，就是 Fruit 这个类型
6. `types[0].getTypeName()`：拿到 Fruit 的完整类名字符串，例如`com.atguigu.fruit.pojo.Fruit`，赋值给`entityClassName`

>
> 后面`load()`方法就拿这个`entityClassName`传给 ClassUtil，用`Class.forName`拿到 Fruit 图纸、反射创建 Fruit 对象。
     */


    private void initDataSource() {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PWD);
    }

    private void loadProperties() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");//`getClass()` 需要实体对象！而 `this` 就是那个对象！
        Properties properties = new Properties();
        try {
            properties.load(inputStream);

            DRIVER = properties.getProperty("driver");
            URL = properties.getProperty("url");
            USER = properties.getProperty("user");
            PWD = properties.getProperty("pwd");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConn() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void close(ResultSet rs, PreparedStatement psmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (psmt != null) {
                psmt.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //填充参数
    private void setParams(PreparedStatement psmt,Object...params) throws SQLException{
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i + 1, params[i]);
            }
        }

    }

    //执行更新，如果是删除和修改，则返回影响行数
    //如果是insert操作，而且数据库的主键是自增列，那么我们返回自增列的值
    protected int executeUpdate(String sql, Object... params) {
        sql = sql.trim().toUpperCase();
        //insertFlag代表当前的SQL是否是一个insert语句
        boolean insetFlag = sql.startsWith("INSERT INTO");
        conn = getConn();
        try {
            if (insetFlag) {
                psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//1. `psmt`是`PreparedStatement`对象，作用：保存 SQL、占位符，发给数据库。**它不是数据库返回的主键 id**。
                //当你执行`psmt.executeUpdate()`，MySQL 执行 insert，**数据库自动生成自增 id，这个 id 单独放在一张小小的虚拟结果表里，不会自动放到 psmt 里**。
            } else {
                psmt = conn.prepareStatement(sql);
            }
          setParams(psmt,params);
            int count = psmt.executeUpdate();
            //**不管是 INSERT / UPDATE / DELETE，都必须调用 executeUpdate ()！**
            //`executeUpdate()`的核心功能：**把 SQL 发给 MySQL，执行这条 SQL**
            if (insetFlag) {
                rs = psmt.getGeneratedKeys();
                //`rs`：`getGeneratedKeys()`返回的**主键结果集**
                if (rs.next()) {
                    return ((Long) rs.getLong(1)).intValue();//遍历得到主键结果集里的所有数据
                    /*
                    - `rs.getLong(1)`：读取这张表第 1 列的值。MySQL 自增主键底层返回类型是 Long（长整型）。
                    - `(Long)`：强制类型转换。
                    - `.intValue()`：把 Long 转成 int，用来 return。
                    只有**对象 Long**，才能调用`.intValue()`这个方法。
                    原始基本类型`long`不能调用方法！
                    - `.intValue()`
                        - Long 包装类自带的方法：把 Long 里面存的数字，取出转为`int`。
                     */
                }
            } else {
                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, psmt, conn);
        }
        return 0;
    }

    /*
    这个executeQuery方法：
    由于：
    > rs = 临时通道，一边连着数据库，一边拿一行行原始数据；
> Fruit 实体对象 = 把通道里的数据复制出来，存成独立的 Java 对象。通道 (rs/conn) 关掉之后，对象还在，随便使用。

所以这个方法的目的：
    ## 总结这个方法的完整目标

>
> 查询数据库拿到数据 → 在内存创建实体对象 → 将数据库读取到的值填充进对象 → 将对象存入 List 集合，把 List 返回出去给上层代码使用。
> 本质从头到尾就是**查询**。反射赋值，只是把查到的数据装进 Java 对象，不是更新数据库。
     */

    protected List<T> executeQuery(String sql, Object...params){
        List<T> list = new ArrayList<>();
        conn = getConn();
        try{
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();
            //执行查询 SQL 语句，把查询出来的结果封装成`ResultSet`（结果集 rs）
            //`executeQuery()`：**专门给 SELECT 查询用的方法**，只能执行查询语句，不能执行增删改（insert/update/delete）

            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取列的数量
            int columnCount = rsmd.getColumnCount();
            if(rs.next()){
                Class entityClass = ClassUtil.getClass(entityClassName);
                T instance = (T)ClassUtil.createInstance(entityClassName);
                for(int i = 1;i<=columnCount;i++){
                    //获取列名，其实我们特意将列名和属性名保持一致，就是为了此处的反射赋值
                    String columnName = rsmd.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    ClassUtil.setProperty(instance,columnName,columnValue);
                }
                list.add(instance);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs,psmt,conn);
        }
        return list;
    }

    protected T load(String sql,Object...params){
        conn = getConn();
        try{
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取列的数量
            int columnCount = rsmd.getColumnCount();
            if(rs.next()){
                Class entityClass = ClassUtil.getClass(entityClassName);
                T instance = (T)ClassUtil.createInstance(entityClassName);
                for(int i = 1;i<=columnCount;i++){
                    //获取列名，其实我们特意将列名和属性名保持一致，就是为了此处的反射赋值
                    String columnName = rsmd.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    ClassUtil.setProperty(instance,columnName,columnValue);
                }
                return instance;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs,psmt,conn);
        }
        return null;
    }
        }

/*
怎么通过父类信息拿到类的 Field 属性？
结合你 BaseDao 代码的完整链路，一步一步拆开：
//1. 获取子类的Class对象 FruitDaoImpl.class
Class<?> subClass = this.getClass();`getClass()` 需要实体对象！而`this` 就是那个对象！
//2. 获取【带泛型的父类】BaseDao<Fruit>
ParameterizedType pt = (ParameterizedType) subClass.getGenericSuperclass();
//3. 取出父类泛型里面真实类型：Fruit
Type realEntityType = pt.getActualTypeArguments()[0];
//4. 转成Class对象！重点！！
Class<?> entityClass = (Class<?>) realEntityType;
***注意：把`realEntityType`（泛型里面拿到的 Type）强转为`Class<?>`，就是拿到**Fruit 类的图纸**。
//5. 现在entityClass就是 Fruit.class，就可以拿Field了！
Field field = entityClass.getDeclaredField("fname");


我们拆解这一串链路：

1. `this.getClass()` → FruitDaoImpl 对象 → 获取 `FruitDaoImpl.class`（子类图纸）
2. `.getGenericSuperclass()` → 获取带泛型的父类：`BaseDao<Fruit>`，类型是 ParameterizedType
3. `.getActualTypeArguments()[0]` → 取出`<>`里面的类型：`Fruit`，它的类型是`Type`（realEntityType）
4. `(Class<?>) realEntityType`：强转，把这个 Type 转为`Fruit.class`（Fruit 的图纸）


### 区分两个图纸，千万别混

- `subClass` = `FruitDaoImpl.class`：DAO 实现类的图纸（负责数据库操作）
- `entityClass` = `Fruit.class`：实体类图纸（Fruit，存放水果数据）

拿到`Fruit.class`这张图纸之后，你才能做这些操作：

1. `entityClass.newInstance()`：按照图纸 new 出 Fruit 实物对象
2. `entityClass.getDeclaredField()`：从图纸找到 fname/fprice 这些属性 Field
3. 拿到 Field 之后，就可以给 new 出来的 Fruit 实例赋值
 */



/*
***总结：

1. **反射理解**
你的理解基本正确：反射可以**只读**地从子类信息（Class 对象 / 类名字符串）读取父类、父类泛型这类类元信息；后续拿到实体类的`Field`，可以绕过`private`权限，**对实例对象的成员变量读写赋值**。

>
> 小修正：不是靠包名主动搜索，包名字符串只是加载 Class 的一种方式，真正读取元数据靠 Class 对象。

2. **`Class<?> subClass = this.getClass();`**`getClass()`确实需要实例对象；代码写在实例方法里，**`this`就是已经 new 好的子类实例（FruitDaoImpl 对象）**，由调用方法时创建，所以可以直接调用`getClass()`拿到子类的 Class 图纸。
3. **`Class<?> entityClass = (Class<?>) realEntityType;`**
✅ 理解正确：这行就是拿到实体类（Fruit）的 Class，也就是**Fruit 类的图纸**。
4. **通过父类泛型信息获取 Field 完整链路**`this（子类实例）` → `getClass()`得到子类图纸 → 获取带泛型的父类`BaseDao<Fruit>` → 提取泛型参数`Fruit` → 强转得到 Fruit 的 Class 图纸 → 使用`getDeclaredField()`拿到 Fruit 的属性 Field。

>
> 关键点：不是从父类 BaseDao 里面找 Field，是解析出泛型对应的实体类 Fruit，**用 Fruit 自己的 Class 图纸拿它的属性**。
 */