src/main/java/cn/edu/cqjtu/cs/credit/user/
├── controller/             # 接口层（User/Role/Permission 的 CRUD）
├── service/                # 业务逻辑层
│   ├── impl/
├── mapper/                 # MyBatis-Plus Mapper 接口
├── entity/                 # 对应的 5 张表的实体类
│   └── vo/                 # 返回给前端的视图对象（如 UserVO，隐藏密码）
└── CreditUserApplication.java