# 信用风险管理系统 (Credit Risk Platform)

基于 Spring Cloud 架构的分布式信用风险管理系统，提供用户管理、权限控制、交易处理和纠纷裁决等功能。

## 项目概述

信用风险管理系统是一个基于微服务架构的企业级应用，旨在为金融机构提供全面的信用风险管理解决方案。系统采用现代化的技术栈，具备高可用性、可扩展性和安全性。

### 核心功能

- **用户认证与授权** - 基于JWT的认证机制和RBAC权限控制
- **信用评估** - 用户信用评级和风险评估
- **交易管理** - 交易记录、审批流程和风险监控
- **纠纷处理** - 纠纷申报、仲裁裁决和结果跟踪
- **系统监控** - 服务健康检查、日志追踪和性能监控

## 项目架构

本项目采用微服务架构，包含以下核心模块：

### 服务模块

- **credit-gateway** - 网关服务，统一入口、路由管理和鉴权过滤
- **credit-auth** - 认证中心服务，负责用户登录、JWT Token签发和验证
- **credit-user** - 用户与信用服务，提供用户管理、权限控制等功能
- **credit-trade** - 交易服务，处理交易相关业务
- **credit-dispute** - 纠纷裁决服务，处理纠纷和仲裁业务

### 公共模块

- **credit-common** - 公共模块，包含通用工具类和实体定义

## 技术栈

### 后端技术
- **Java 17** - 编程语言
- **Spring Boot 3.2.4** - 应用框架
- **Spring Cloud 2023.0.1** - 微服务框架
- **Spring Cloud Alibaba 2023.0.1.0** - 微服务组件
- **MyBatis Plus 3.5.10.1** - ORM 框架
- **MySQL 8.0.33** - 数据库
- **Redis** - 缓存
- **Nacos** - 服务注册与发现、配置中心

### 开发工具
- **Maven 3.6+** - 项目构建工具
- **Lombok 1.18.30** - 代码简化工具
- **JJWT 0.12.5** - JWT令牌处理

## 快速开始

### 环境要求

#### 必需环境
- **JDK 17+** - Java 开发环境
- **Maven 3.6+** - 项目构建工具
- **MySQL 8.0+** - 数据库服务
- **Redis 6.0+** - 缓存服务

#### 可选环境
- **Nacos 2.2+** - 服务注册与配置中心（推荐用于生产环境）
- **Docker** - 容器化部署（可选）

### 构建项目

使用项目提供的 Maven 配置文件确保依赖正确下载：

```bash
# 构建所有模块
./mvnw clean install -s maven-settings.xml

# 构建单个模块（如 credit-user）
cd credit-user
../mvnw clean install -s ../maven-settings.xml
```

### 服务配置

1. **修改数据库连接**：编辑各模块的 `application.yml` 文件
2. **配置 Redis**：设置 Redis 连接信息
3. **配置 Nacos**：设置服务注册中心地址

### 启动服务

#### 开发环境启动

```bash
# 启动认证服务
cd credit-auth
../mvnw spring-boot:run -s ../maven-settings.xml

# 启动用户服务（新终端）
cd credit-user
../mvnw spring-boot:run -s ../maven-settings.xml

# 启动网关服务（新终端）
cd credit-gateway
../mvnw spring-boot:run -s ../maven-settings.xml
```

## 开发指南

### 项目结构
```
credit-risk-platform/
├── credit-common/          # 公共模块
├── credit-auth/           # 认证服务
├── credit-user/           # 用户服务
├── credit-trade/          # 交易服务
├── credit-dispute/        # 纠纷服务
├── credit-gateway/        # 网关服务
└── pom.xml               # 父工程配置
```

### 代码规范

1. **命名规范**
   - 包名：全小写，使用公司域名倒序
   - 类名：大驼峰命名法
   - 方法名：小驼峰命名法
   - 常量：全大写，下划线分隔

2. **代码风格**
   - 使用 Lombok 简化代码
   - 遵循阿里巴巴 Java 开发规范
   - 统一使用 4 个空格缩进

3. **API 设计原则**
   - RESTful 风格
   - 统一响应格式
   - 适当的 HTTP 状态码
   - 完整的错误处理

### 测试规范

1. **单元测试**：每个服务类应有对应的单元测试
2. **集成测试**：测试服务间的集成调用
3. **API 测试**：使用 Postman 或 Swagger 进行接口测试

## 贡献指南

### 开发流程

1. **Fork 项目**：从主仓库 Fork 到个人账户
2. **创建分支**：基于 `develop` 分支创建功能分支
3. **开发功能**：在功能分支上实现新功能或修复 bug
4. **提交代码**：遵循约定式提交规范
5. **创建 PR**：向主仓库发起 Pull Request
6. **代码审查**：等待项目维护者审查代码
7. **合并代码**：审查通过后合并到主分支

### 提交规范

使用约定式提交格式：
```
<类型>[可选的作用域]: <描述>

[可选的正文]

[可选的脚注]
```

**类型说明：**
- `feat`：新功能
- `fix`：修复 bug
- `docs`：文档更新
- `style`：代码格式调整
- `refactor`：代码重构
- `test`：测试相关
- `chore`：构建过程或辅助工具变动

### 分支管理

- `main`：主分支，用于生产环境发布
- `develop`：开发分支，集成所有功能
- `feature/*`：功能分支，开发新功能
- `bugfix/*`：修复分支，修复 bug
- `release/*`：发布分支，准备版本发布

## 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

## 联系方式

- **项目维护者**：重庆交通大学计算机学院
- **邮箱**：contact@cqjtu.edu.cn
- **项目地址**：https://github.com/cqjtu/credit-risk-platform

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


### 生产环境配置

- 使用 Nacos 进行配置管理
- 配置数据库连接池参数
- 启用 Redis 集群模式
- 配置日志收集和监控

## 许可证

本项目采用 MIT 许可证。

## 联系方式

- 项目维护者：wjf
- 邮箱：17671220626@139.com
- 项目地址：https://github.com/cqjtu/credit-risk-platform