# 付丽华的 JavaWeb 学习与实践仓库

你好！这里是白城师范学院软件工程专业 25 级学生付丽华的 JavaWeb 学习与实践代码仓库。

本仓库记录了我从 JDBC、Servlet 基础，到 Filter/Listener 进阶，再到独立完成完整 Web 项目的学习轨迹。仓库中的 `proXX` 文件夹代表了我不同阶段的知识点练习与项目迭代。

## 🚀 核心项目导航

### 1. BookStore 图书商城系统（核心项目）
- **所在目录**：`pro19-book` （及 `pro14-filter-listener` 部分代码）
- **技术栈**：Java + Servlet + Filter + Listener + MySQL + Tomcat + 手写简易 MVC
- **项目描述**：
  - 独立手写简易 `DispatcherServlet` 分发器，利用 Java 反射实现控制器方法调用与请求路由。
  - 自定义 `UserFilter` 实现白名单放行与 Session 登录鉴权，区分登录与未登录用户权限。
  - 实现图书展示、加入购物车、购物车数量更新、购物车持久化、事务控制及异常回滚。
  - 排查并修复了 Session 偶发丢失、空指针、Git 分支冲突、远程推送连接等开发常见问题。

### 2. 水果库存管理系统（JDBC 进阶实战）
- **所在目录**：`pro02-jdbc` 至 `pro15-fruit1.8`（记录了完整的迭代过程），以及 `FruitDemo/src`、`lib_myssm0.3`（lib_myssm0.3里面是我手写的myssm,但是由于某些语句写错了，便没有再进行迭代，每个pro里面都有写myssm文件夹，以此来代替lib_myssm0.3压缩包）
- **技术栈**：Java + JDBC + MySQL
- **项目描述**：从最基础的 JDBC 连接，逐步迭代到封装 DAO 层、使用连接池、分离业务逻辑，理解数据库连接流程与资源关闭。

### 3. QQZone 简易空间项目
- **所在目录**：`pro17-qqzone`
- **技术栈**：JavaWeb + Servlet + MySQL
- **项目描述**：仿照 QQ 空间实现基础动态发布、浏览展示功能，练习 Web 页面跳转、请求参数接收与前后端数据交互。

### 4. 微信校园活动报名小程序（开发中）
- **所在目录**：相关半成品代码散落于近期迭代中（`pro12` - `pro18` 的提交记录中均有体现）
- **技术栈**：微信原生小程序 + Java 后端
- **项目描述**：面向校园场景，实现活动发布、学生报名、信息收集。目前正在学习前后端通信，为后续 REST 接口开发打下基础。

## 📚 其他练习模块
- `pro09-servlet`：Servlet 基础练习，理解生命周期与请求处理。
- `pro14-filter-listener`：过滤器与监听器实战练习，为 BookStore 做准备。
- `pro18-myselfDemo`：微信校园活动报名小程序（半成品）。

## 📧 联系方式
- **邮箱**：2536999541@qq.com
- **GitHub**：[fulihua](https://github.com/fulihua)
- 持续学习中，期待在寒假获得一份真实的 Java 开发实习机会，积累工业界经验！
