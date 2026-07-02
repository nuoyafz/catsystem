-- 系统升级脚本：添加标签系统、领养管理、统计数据支持

USE cat_feeding;

-- 1. 猫咪标签表
CREATE TABLE IF NOT EXISTS t_cat_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称',
    color VARCHAR(20) NOT NULL DEFAULT '#409EFF' COMMENT '标签颜色（十六进制）',
    icon VARCHAR(50) COMMENT '图标（可选）',
    description VARCHAR(200) COMMENT '标签描述',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='猫咪标签表';

-- 2. 猫咪-标签关联表
CREATE TABLE IF NOT EXISTS t_cat_tag_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    cat_id BIGINT NOT NULL COMMENT '猫咪ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_cat_tag (cat_id, tag_id),
    FOREIGN KEY (cat_id) REFERENCES t_cat(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES t_cat_tag(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='猫咪-标签关联表';

-- 3. 领养申请表
CREATE TABLE IF NOT EXISTS t_adoption (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '领养ID',
    cat_id BIGINT NOT NULL COMMENT '猫咪ID',
    applicant_name VARCHAR(50) NOT NULL COMMENT '申请人姓名',
    applicant_phone VARCHAR(20) NOT NULL COMMENT '联系电话',
    applicant_email VARCHAR(100) COMMENT '邮箱',
    applicant_address VARCHAR(255) COMMENT '居住地址',
    reason TEXT NOT NULL COMMENT '领养理由',
    experience TEXT COMMENT '养猫经验',
    status VARCHAR(20) NOT NULL DEFAULT '待审核' COMMENT '状态：待审核/已通过/已拒绝/已完成',
    reviewer_id BIGINT COMMENT '审核人ID',
    review_comment TEXT COMMENT '审核意见',
    review_time DATETIME COMMENT '审核时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (cat_id) REFERENCES t_cat(id) ON DELETE CASCADE,
    FOREIGN KEY (reviewer_id) REFERENCES t_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='领养申请表';

-- 4. 在 t_cat 表增加领养状态字段
ALTER TABLE t_cat ADD COLUMN adoption_status VARCHAR(20) NOT NULL DEFAULT '可领养' COMMENT '领养状态：可领养/申请中/已领养/不可领养';

-- 5. 插入默认标签数据
INSERT INTO t_cat_tag (name, color, icon, description) VALUES
('已绝育', '#67C23A', 'CircleCheck', '已完成绝育手术'),
('已打疫苗', '#409EFF', 'FirstAidKit', '已接种狂犬疫苗'),
('性格亲人', '#E6A23C', 'Star', '性格温顺，喜欢与人互动'),
('怕生', '#909399', 'Warning', '性格胆小，需要耐心接近'),
('需要医疗', '#F56C6C', 'FirstAidKit', '需要医疗救助'),
('待领养', '#9B59B6', 'House', '适合被领养'),
('校园明星', '#FF6B9D', 'Trophy', '在校园内很受欢迎'),
('已归档', '#95A5A6', 'Folder', '已离开校园或去世');

-- 6. 为现有猫咪随机分配一些标签
INSERT INTO t_cat_tag_relation (cat_id, tag_id) VALUES
(1, 3), -- 橘座 - 性格亲人
(1, 7), -- 橘座 - 校园明星
(2, 4), -- 小黑 - 怕生
(3, 3), -- 花花 - 性格亲人
(3, 7), -- 花花 - 校园明星
(4, 3), -- 大白 - 性格亲人
(5, 4), -- 灰灰 - 怕生
(6, 5), -- 咪咪 - 需要医疗
(7, 3), -- 球球 - 性格亲人
(8, 3), -- 点点 - 性格亲人
(9, 5), -- 虎子 - 需要医疗
(10, 3); -- 雪球 - 性格亲人
