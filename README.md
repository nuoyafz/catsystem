<<<<<<< HEAD
# 🐱 校园流浪猫投喂记录系统

> 一个基于 Spring Boot + Vue 3 的校园流浪猫投喂管理平台，用技术守护每一只校园猫咪。

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-6DB33F?logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.3.4-4FC08D?logo=vue.js)](https://vuejs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## ✨ 功能特性

### 📊 数据可视化首页
- 实时统计卡片：猫咪数量、投喂记录、志愿者、活动
- 猫咪健康状况分布饼图
- 投喂次数 TOP5 排行榜
- 志愿者贡献榜
- 猫咪地点分布图
- 最近投喂记录
- 互动小猫动画

### 🐾 流浪猫管理
- 猫咪档案录入（名字、品种、毛色、性别、健康状况等）
- 绝育状态标记
- 按名字/健康状况搜索
- 照片上传与管理

### 🍖 投喂记录
- 记录每次投喂（猫咪、时间、食物类型、投喂量、地点）
- 关联猫咪和投喂人信息
- 支持按条件筛选和分页

### 👥 志愿者管理
- 志愿者信息登记（姓名、学号、院系、联系方式）
- 服务时长自动统计
- 志愿者信息维护

### 📅 活动管理
- 活动发布（标题、描述、时间、地点、最大人数）
- 活动状态管理（报名中/进行中/已结束/已取消）
- 在线报名与人数统计

### 🔐 用户系统
- JWT 身份认证
- 角色权限控制（管理员/普通用户）
- 用户注册、登录、个人信息管理

## 🛠️ 技术栈

### 后端
| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.7.18 | 核心框架 |
| MyBatis-Plus | 3.5.3.1 | ORM 框架 |
| MySQL | 8.0 | 数据库 |
| JWT (jjwt) | 0.9.1 | 身份认证 |
| Hutool | 5.8.20 | 工具类库 |
| Lombok | - | 代码简化 |

### 前端
| 技术 | 版本 | 说明 |
|------|------|------|
| Vue 3 | 3.3.4 | 渐进式框架 |
| Element Plus | 2.3.14 | UI 组件库 |
| Vue Router | 4.2.4 | 路由管理 |
| Pinia | 2.1.6 | 状态管理 |
| Axios | 1.5.0 | HTTP 客户端 |
| ECharts | 5.4.3 | 数据可视化 |
| Vite | 4.4.9 | 构建工具 |

## 📁 项目结构

```
cat-feeding-system/
├── backend/                          # 后端 Spring Boot 项目
│   ├── src/main/java/com/catfeeding/
│   │   ├── entity/                   # 实体类
│   │   ├── mapper/                   # MyBatis Mapper 接口
│   │   ├── service/                  # Service 层
│   │   ├── controller/               # Controller 层
│   │   ├── config/                   # 配置类（CORS、JWT、MyBatis等）
│   │   ├── common/                   # 通用类（Result、分页等）
│   │   └── utils/                    # 工具类
│   ├── src/main/resources/
│   │   ├── application.yml           # 配置文件
│   │   └── mapper/                   # MyBatis XML 映射文件
│   └── pom.xml
├── frontend/                         # 前端 Vue3 项目
│   ├── src/
│   │   ├── api/                      # API 接口封装
│   │   ├── views/                    # 页面组件
│   │   ├── router/                   # 路由配置
│   │   ├── store/                    # Pinia 状态管理
│   │   └── styles/                   # 主题样式
│   ├── package.json
│   └── vite.config.js
├── sql/                              # 数据库脚本
│   └── create.sql                    # 建表语句
└── README.md
```

## 🗄️ 数据库设计

系统包含 7 张核心数据表：

| 表名 | 说明 | 主要字段 |
|------|------|----------|
| `t_user` | 用户表 | username, password, role, status |
| `t_cat` | 流浪猫信息表 | name, breed, color, health_status, location, sterilized |
| `t_feed_record` | 投喂记录表 | user_id, cat_id, feed_time, food_type, amount, location |
| `t_volunteer` | 志愿者信息表 | user_id, real_name, student_id, department, service_hours |
| `t_activity` | 志愿活动表 | title, description, location, start_time, end_time, status |
| `t_activity_signup` | 活动报名表 | activity_id, user_id, signup_time, hours |
| `t_operation_log` | 操作日志表 | user_id, operation, method, ip |

## 🚀 快速开始

### 环境要求
- JDK 8+
- Maven 3.6+
- Node.js 16+
- MySQL 8.0+

### 1. 数据库初始化

```bash
# 登录 MySQL
mysql -u root -p

# 执行建表脚本（自动创建 cat_feeding 数据库）
source sql/create.sql
```

### 2. 后端启动

```bash
cd backend

# 修改数据库配置（如需要）
# 编辑 src/main/resources/application.yml

# 编译并运行
mvn clean install
mvn spring-boot:run
```

后端服务启动在 `http://localhost:8080`

### 3. 前端启动

```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务启动在 `http://localhost:5173`

### 4. 访问系统

打开浏览器访问 `http://localhost:5173`

**默认管理员账号：**
- 用户名：`admin`
- 密码：`123456`

## 📸 截图

> 这里可以添加项目截图

## 👥 团队成员

| 姓名 | 角色 | 负责模块 |
|------|------|----------|
| 方舟 | 组长 | 用户管理、系统管理、后端开发 |
| 邢锦涛 | 成员 | 流浪猫管理、投喂记录、前端开发 |
| 崔嘉兵 | 成员 | 志愿者管理、活动管理、测试 |

## 📚 课程信息

- **课程名称**：Web应用开发课程设计
- **课程代码**：08160234
- **学分**：2
- **时间**：17-18周

## 📝 许可证

本项目为课程设计作业，仅供学习使用。

---

<p align="center">用代码温暖每一只校园猫咪 🐾</p>
=======
# catsystem
>>>>>>> c89e5c6f4b3f4eacd1e8295b476a0adfda8eae9e
