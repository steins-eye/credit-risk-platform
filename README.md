# 信用风险管理系统 (Credit Risk Platform)

基于 Spring Cloud 架构的分布式信用风险管理系统，提供用户管理、权限控制、交易处理和纠纷裁决等功能。

## 项目架构

本项目采用微服务架构，包含以下核心模块：

### 服务模块

- **credit-gateway** - 网关服务，统一入口和路由管理
- **credit-user** - 用户与信用服务，提供用户管理、权限控制等功能
- **credit-trade** - 交易服务，处理交易相关业务
- **credit-dispute** - 纠纷裁决服务，处理纠纷和仲裁业务

### 公共模块

- **credit-common** - 公共模块，包含通用工具类和实体定义

## 技术栈

- **Java 17** - 编程语言
- **Spring Boot 3.x** - 应用框架
- **Spring Cloud** - 微服务框架
- **MyBatis Plus** - ORM 框架
- **MySQL** - 数据库
- **Redis** - 缓存
- **Nacos** - 服务注册与发现、配置中心

## 数据库设计

系统采用标准的 RBAC (基于角色的访问控制) 权限模型，包含以下核心表：

### 权限资源表 (sys_permission)
存储具体的菜单、按钮或 API 路径

### 角色表 (sys_role)
角色定义，如 Arbitrator、Admin 等

### 用户表 (sys_user)
用户基础信息表

### 用户-角色关联表 (sys_user_role)
多对多关系，用户与角色关联

### 角色-权限关联表 (sys_role_permission)
多对多关系，角色与权限关联

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 构建项目

```bash
# 构建所有模块
./mvnw clean install -s maven-settings.xml

# 构建单个模块（如 credit-user）
cd credit-user
./mvnw clean install -s ../maven-settings.xml
```

### 配置说明

1. 修改各模块的 `application.yml` 文件中的数据库连接信息
2. 确保 Redis 和 MySQL 服务正常运行
3. 配置 Nacos 服务地址（如果需要）

### 启动服务

```bash
# 启动 credit-user 服务
cd credit-user
../mvnw spring-boot:run -s ../maven-settings.xml
```

## API 接口

### 用户管理接口

- `GET /api/user` - 获取用户列表
- `GET /api/user/{userId}` - 根据ID获取用户信息
- `POST /api/user` - 创建用户
- `PUT /api/user/{userId}` - 更新用户信息
- `DELETE /api/user/{userId}` - 删除用户

### 角色管理接口

- `GET /api/role` - 获取角色列表
- `GET /api/role/{roleId}` - 根据ID获取角色信息
- `POST /api/role` - 创建角色
- `PUT /api/role/{roleId}` - 更新角色信息
- `DELETE /api/role/{roleId}` - 删除角色

### 权限管理接口

- `GET /api/permission` - 获取权限列表
- `GET /api/permission/{permissionId}` - 根据ID获取权限信息
- `POST /api/permission` - 创建权限
- `PUT /api/permission/{permissionId}` - 更新权限信息
- `DELETE /api/permission/{permissionId}` - 删除权限

## 项目结构

```
credit-risk-platform/
├── credit-common/          # 公共模块
│   ├── src/main/java/cn/edu/cqjtu/cs/credit/common/
│   │   └── entity/         # 公共实体类
│   └── pom.xml
├── credit-user/            # 用户服务
│   ├── src/main/java/cn/edu/cqjtu/cs/credit/user/
│   │   ├── entity/         # 实体类
│   │   ├── mapper/         # MyBatis Mapper
│   │   ├── service/        # 业务逻辑层
│   │   └── controller/     # 控制器层
│   ├── src/main/resources/
│   │   └── application.yml # 配置文件
│   └── pom.xml
├── credit-trade/           # 交易服务
├── credit-dispute/         # 纠纷裁决服务
├── credit-gateway/         # 网关服务
├── maven-settings.xml      # Maven 配置文件
└── README.md              # 项目说明文档
```

## 开发规范

### 代码规范

- 使用统一的返回结果对象 `Result<T>`
- 遵循 RESTful API 设计规范
- 使用 MyBatis Plus 进行数据库操作
- 实体类使用 Lombok 注解简化代码

### 数据库规范

- 所有表名使用小写字母和下划线分隔
- 主键字段使用 `xxx_id` 命名
- 时间字段使用 `create_time`、`update_time` 等标准命名

## 部署说明

### Docker 部署

每个模块都提供了 Dockerfile，可以方便地进行容器化部署：

```bash
# 构建镜像
cd credit-user
docker build -t credit-user:latest .

# 运行容器
docker run -d -p 8081:8081 credit-user:latest
```

### 生产环境配置

- 使用 Nacos 进行配置管理
- 配置数据库连接池参数
- 启用 Redis 集群模式
- 配置日志收集和监控

## 贡献指南

1. Fork 本项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证。

## 联系方式

- 项目维护者：wjf
- 邮箱：17671220626@139.com
- 项目地址：https://github.com/cqjtu/credit-risk-platform

## 更新日志

### v1.0.0 (2026-03-23)
- 初始化项目结构
- 实现用户服务基础功能
- 完成 RBAC 权限模型设计
- 提供完整的 API 接口文档