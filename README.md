# 电影购票系统

一个基于Vue3+SpringBoot3的电影管理系统

## 技术栈

**前端**：Vue3 + [Element-Plus](https://element-plus.org/zh-CN/component/overview.html) + Axios

**后端**：SpringBoot3 + Mybatis-plus + MySQL

文件管理：阿里云OSS

## 亮点

使用了面向模板的编程



## 权限管理

一个系统的关键在于权限管理

本系统分为四种权限：权限由大到小

- 系统管理员
- 影院管理员
- 用户
- 公共

这里的设计是登录认证时下发JWT令牌，令牌载荷payload

```json
{
    userId="1234";
    role="cinema_admin"  //或"system_admin" 或"user"
    cinemaId="112111"    //这里仅在role="cinema_admin"时存在
}
```

**需要系统管理员权限的功能模块**

- 电影管理
- 影院管理
- 评论管理（暂未实现）

**需要影院管理员权限的模块**

- 影厅管理（含座位管理）

- 排片管理
- 订单管理

**需要用户权限的模块**

- 订单管理
- 评论管理（暂未实现）

对于这三种不同的权限，设计思路如下：

#### 公共权限

不进行请求拦截

当访问电影信息、影院信息、排片信息、座位信息时

只能访问status为特定值的信息，即可用状态的信息

只有这里对status进行设置

#### User权限

使用拦截器方案

每个请求进入拦截器时，判断是否为用户，是用户则放行

对于订单信息时：查询的所有订单信息均为对应用户Id的信息

对于创建订单时，检查`seatId`对应`ScreeningHall`和`scheduleId`对应`ScreeningHall`是否一致

#### Cinema_Admin权限

使用拦截器方案 + 权限管理方案

对于任意请求，都需要携带`Query`参数`cinemaId`

进入拦截器时，拦截器获取所有请求的`cinemaId`，与`payload`里的参数进行对比，对比通过后放行

对于所有信息的获取和创建，都需要对传入的数据进行合格性检测，例如：

- 对于Schedule的获取，需要知道获取的schedule的`cinemaId`与payload是否一致等

- 对于含有`cinemaId`的修改，需要知道修改的值与`Query`参数的`cinemaId`是否一致

**这里采取的方案是使用切面类，对于需要判断的地方打上`@CinemaIdCheck`的注释，切面类自动获取对应类的`cinemaId`的值（使用反射获取`getCinemaId`方法），同时获取该类的`mapper`层对象，直接进行查询是否有该数据存在数据库**

#### System_Admin权限

使用拦截器方案



### 还未完成的功能

1. 微信登陆
2. 支付系统（下单后转为未支付状态，扫码支付后转为支付状态）
3. 评论系统



### 需要缓存的数据

1. 电影信息（Status正常）

2. 影院信息

3. ScheduleSeat信息



### 需要WebSocket

1. 审核
2. 取消订单