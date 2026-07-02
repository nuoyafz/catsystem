-- 校园流浪猫投喂记录系统 数据库建表脚本
-- 数据库：MySQL 8.0+

CREATE DATABASE IF NOT EXISTS cat_feeding DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE cat_feeding;

-- 用户表
CREATE TABLE t_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码（MD5加密）',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像URL',
    role VARCHAR(20) NOT NULL DEFAULT 'user' COMMENT '角色：admin-管理员，user-普通用户',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 流浪猫信息表
CREATE TABLE t_cat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '猫咪ID',
    name VARCHAR(50) NOT NULL COMMENT '猫咪名字',
    breed VARCHAR(50) COMMENT '品种',
    color VARCHAR(50) COMMENT '毛色',
    gender VARCHAR(10) COMMENT '性别：公/母',
    age INT COMMENT '年龄（岁）',
    health_status VARCHAR(20) NOT NULL DEFAULT '健康' COMMENT '健康状况：健康/生病/受伤/康复中',
    photo_url VARCHAR(255) COMMENT '照片URL',
    location VARCHAR(100) COMMENT '常出没地点',
    description TEXT COMMENT '描述信息',
    sterilized TINYINT NOT NULL DEFAULT 0 COMMENT '是否绝育：0-否，1-是',
    create_by BIGINT COMMENT '创建人ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流浪猫信息表';

-- 投喂记录表
CREATE TABLE t_feed_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    user_id BIGINT NOT NULL COMMENT '投喂用户ID',
    cat_id BIGINT NOT NULL COMMENT '猫咪ID',
    feed_time DATETIME NOT NULL COMMENT '投喂时间',
    food_type VARCHAR(50) NOT NULL COMMENT '食物类型：猫粮/罐头/零食/其他',
    amount VARCHAR(20) COMMENT '投喂量：少量/适量/多量',
    location VARCHAR(100) COMMENT '投喂地点',
    note TEXT COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE,
    FOREIGN KEY (cat_id) REFERENCES t_cat(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='投喂记录表';

-- 志愿者信息表
CREATE TABLE t_volunteer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '志愿者ID',
    user_id BIGINT NOT NULL UNIQUE COMMENT '关联用户ID',
    real_name VARCHAR(50) COMMENT '真实姓名',
    student_id VARCHAR(20) COMMENT '学号',
    department VARCHAR(50) COMMENT '院系',
    phone VARCHAR(20) COMMENT '联系电话',
    service_hours DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '服务时长（小时）',
    join_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='志愿者信息表';

-- 志愿活动表
CREATE TABLE t_activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '活动ID',
    title VARCHAR(100) NOT NULL COMMENT '活动标题',
    description TEXT COMMENT '活动描述',
    location VARCHAR(100) COMMENT '活动地点',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    max_participants INT DEFAULT 50 COMMENT '最大参与人数',
    current_participants INT NOT NULL DEFAULT 0 COMMENT '当前参与人数',
    status VARCHAR(20) NOT NULL DEFAULT '报名中' COMMENT '状态：报名中/进行中/已结束/已取消',
    create_by BIGINT COMMENT '创建人ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='志愿活动表';

-- 活动报名表
CREATE TABLE t_activity_signup (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '报名ID',
    activity_id BIGINT NOT NULL COMMENT '活动ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    signup_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
    hours DECIMAL(10,2) DEFAULT 0 COMMENT '本次服务时长',
    UNIQUE KEY uk_activity_user (activity_id, user_id),
    FOREIGN KEY (activity_id) REFERENCES t_activity(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动报名表';

-- 操作日志表
CREATE TABLE t_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    user_id BIGINT COMMENT '操作用户ID',
    username VARCHAR(50) COMMENT '操作用户名',
    operation VARCHAR(200) NOT NULL COMMENT '操作内容',
    method VARCHAR(200) COMMENT '请求方法',
    ip VARCHAR(50) COMMENT 'IP地址',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';
