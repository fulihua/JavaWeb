package myssm.ioc;

import fruit.util.ClassUtil;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author fulihua
 * @date 2026/10/2 5:53 PM
 * @description
 */

/*
    对应的xml代码：
    <bean id="fruitDao" class="fruit.dao.impl.FruitDaoImpl"/>

<bean id="fruitService" class="fruit.service.impl.FruitServiceImpl">
    <property name="fruitDao" ref="fruitDao"/>
</bean>

 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    private Map<String ,Object> beanMap = new HashMap<>();

    @Override
    public Object getBean(String id){return beanMap.get(id);}

    public ClassPathXmlApplicationContext(){
        try{
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("beans.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            //第一步：取出所有的bean节点，得到bean实例，保存到beanMap容器中
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for(int i=0;i<beanNodeList.getLength();i++){
                Node beanNode = beanNodeList.item(i);
                //如果是元素节点，则强制转换成Element，因为我们想要获取它的内部属性
                if(beanNode.getNodeType()==Node.ELEMENT_NODE){
                    Element beamElement = (Element)beanNode;
                    //获取id属性值
                    String id = beamElement.getAttribute("id");
                    //获取class属性值
                    String className = beamElement.getAttribute("class");
                    Object beanInstance = ClassUtil.createInstance(className);
                    //将这个bean实例保存到map容器中去
                    beanMap.put(id,beanInstance);
                }
            }
            //第二步：组装bean实例之间的依赖关系
            for(int i=0;i<beanNodeList.getLength();i++){
                Node beanNode = beanNodeList.item(i);
                if(beanNode.getNodeType()==Node.ELEMENT_NODE){
                    Element beanElement = (Element)beanNode;
                    String id = beanElement.getAttribute("id");
                    Object bean = beanMap.get(id);

                    //开始解析bean元素内部的property子元素
                  NodeList beanChildNodeList = beanElement.getChildNodes();
                  for(int j=0;j<beanChildNodeList.getLength();j++){
                      Node beanChildNode = beanChildNodeList.item(j);
                      //如果你是property元素节点
                      if(beanChildNode.getNodeType()==Node.ELEMENT_NODE&&"property".equalsIgnoreCase((beanChildNode.getNodeName()))){
                          //这个节点是标签，不是空白换行
                          Element propertyElement = (Element)beanChildNode;
                          String propertyName = propertyElement.getAttribute("name");
                          String propertyRef = propertyElement.getAttribute("ref");
                          //将propertyRef对应的实例取出来，然后赋值给id对应的bean实例的propertyName属性
                          Object refObj = beanMap.get(propertyRef);
                          //将refObj赋值给bean的propertyName属性
                          ClassUtil.setProperty(bean,propertyName,refObj);
                      }
                  }
                }
            }

        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }catch (SAXException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

/*

# ClassPathXmlApplicationContext 核心知识点总结（简易IOC容器）

>
> 这个就是老师写的极简版SpringIOC，**分两大步：①实例化所有Bean ②装配依赖（set注入）**

## 整体功能

读取项目类路径下 `beans.xml`，解析里面的`<bean>`和`<property>`标签，自动创建对象、自动完成依赖注入；把所有对象存在`beanMap`，后续调用`getBean("id")`取出对象使用。

## 一、成员变量 & 接口

```
private Map<String,Object> beanMap = new HashMap<>();
```

1. `beanMap`：**IOC容器本体**，HashMap
   - key：xml里bean的`id`（字符串）
   - value：反射创建出来的对象实例
2. 实现`BeanFactory`接口，接口只定义一个方法：`Object getBean(String id)`
   - `getBean`：根据id从beanMap取出已经创建+注入完成的对象

## 二、构造方法 `ClassPathXmlApplicationContext()`

>
> new这个容器对象的时候，自动执行全部解析、创建、注入逻辑！

### 1. 读取xml文件（DOM解析XML）

```
InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("beans.xml") ;
```

- `类加载器.getResourceAsStream`：读取**resources下的beans.xml**，拿到文件流
- DOM解析XML相关类：
  - `DocumentBuilderFactory`：工厂，用来造文档构建器
  - `DocumentBuilder`：xml解析器
  - `Document`：代表整个xml文档（文档树）
- 把xml文档加载成一颗DOM树，节点（Node）对应xml标签

### 2. 第一轮循环：实例化所有bean（创建全部对象，存入beanMap）

```
NodeList beanNodeList = document.getElementsByTagName("bean");
for( 遍历所有<bean>标签 ){
    Element beanElement = (Element)beanNode;
    String id = beanElement.getAttribute("id");
    String className = beanElement.getAttribute("class");
    Object beanInstance = ClassUtil.createInstance(className);
    beanMap.put(id,beanInstance);
}
```

知识点：

1. `getElementsByTagName("bean")`：拿到xml中全部`<bean>`节点
2. `Node / Element`：
   - Node：所有节点（文本、注释、标签都算Node）
   - Element：**标签节点**，只有它才能用`getAttribute("属性名")`拿id、class属性
   - 判断`getNodeType()==Node.ELEMENT_NODE`：过滤掉空白文本、注释节点，只处理标签
3. `getAttribute("id")` / `getAttribute("class")`：读取标签上的属性
4. `ClassUtil.createInstance(className)`：**反射**，根据全类名，调用无参构造 new 对象
5. `beanMap.put(id,beanInstance)`：把新建好的对象存入容器Map

✅ 第一轮结束：**所有bean全部new完成，放进beanMap。但是此时对象之间的依赖还没赋值！**

>
> 例如：fruitService对象已经创建，但是里面fruitDao属性还是null。

### 3. 第二轮循环：解析`<property>`，完成依赖注入（setter注入）

```
再次遍历所有<bean>标签：
    取出当前bean对象 Object bean = beanMap.get(id);
    遍历这个bean内部所有子节点：
        如果子节点是 <property>标签：
            String propertyName = propertyElement.getAttribute("name");
            String propertyRef = propertyElement.getAttribute("ref");
            Object refObj = beanMap.get(propertyRef);
            ClassUtil.setProperty(bean,propertyName,refObj);
```

知识点：

1. 拿到`<property name="fruitDao" ref="fruitDao"/>`的两个属性
   - `name`：Java类里面属性名，用来找到set方法（fruitDao → setFruitDao()）
   - `ref`：引用的bean的id，去beanMap找到对应的对象（fruitDao）
2. `Object refObj = beanMap.get(propertyRef)`：从容器拿出被依赖的对象（fruitDao实例）
3. `ClassUtil.setProperty(bean,propertyName,refObj)`
   - 底层**反射**，根据propertyName拼接set方法名，找到setXxx方法，执行`method.invoke()`
   - 等价手动写：`bean.setFruitDao(refObj)`，完成依赖注入>
   > 👉 这一步就是自动执行了你之前疑惑的`fruitservice.setFruitDao(fruitdao);`！

✅ 第二轮结束：所有对象的依赖全部装配完毕。

## 三、为什么要分两轮循环？【重点考点】

>
> 不能一边创建bean一边注入！
> 如果先创建fruitService，此时fruitDao还没实例化，beanMap里面没有fruitDao，`beanMap.get("fruitDao")`拿到null，空指针。

- 第一轮：**全部实例化，全部放进map**（不管依赖）
- 第二轮：**统一做依赖注入**
保证所有被依赖的对象，在注入的时候已经存在于beanMap。

## 四、用到的核心Java技术清单

1. **XML DOM解析**：Document、Element、Node、NodeList，读取xml配置
2. **类加载器读取资源文件** `classLoader.getResourceAsStream()`
3. **反射**（封装在ClassUtil）
   - 根据全类名创建实例
   - 根据属性名找到setter方法，反射调用方法完成赋值
4. **集合HashMap** 作为IOC容器存放bean
5. 接口编程：BeanFactory接口，定义getBean规范
6. 异常处理：解析xml可能抛出的3种异常：ParserConfigurationException、SAXException、IOException

## 五、工作流程一句话浓缩

1. 加载并解析beans.xml
2. 第一轮遍历所有bean标签，反射new所有对象，存入beanMap
3. 第二轮遍历bean内部property标签，从map取出引用对象，反射调用set方法完成注入
4. 外部调用`getBean("id")`直接拿到装配完成的对象使用

## 六、和你之前Servlet代码对比

- 你老代码：`new FruitServiceImpl()`，service内部自己new dao，不需要这个IOC类
- 使用IOC：删掉service内部new dao的代码；IOC自动new dao、new service，自动调用setFruitDao注入。

## 七、常见坑

1. 实体类必须有无参构造，否则`ClassUtil.createInstance`反射创建对象失败
2. 需要注入的属性，**必须写对应的setXxx方法**，否则`ClassUtil.setProperty`找不到方法报错
3. xml中ref的值必须和某个bean的id完全一致，否则get拿到null
4. 标签区分大小写，property不能写错
5. 要把beans.xml放在resources目录，类加载器才能读到

 */

/*
自己精华总结：
*下面这三行等价 IOC 的核心逻辑：
FruitDao fruitdao = new FruitDaoImpl();
FruitServiceImpl fruitservice = new FruitServiceImpl();
fruitservice.setFruitDao(fruitdao);


*上面三行代码的解释（也是IOC这整个文件在做的事情：
FruitDao创建一个FruitDaoImpl的实例，
FruitService创建一个FruitServiceImpl的实例，
然后在FruitServiceImpl里面将FruitDaoImpl的实例赋值给FruitServiceImpl的实例中的fruitDao对象！！！
 */