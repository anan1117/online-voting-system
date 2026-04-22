-- ==========================================
-- 1. DDL (Data Definition Language) - 建立結構
-- ==========================================

-- 刪除舊表 
DROP TABLE IF EXISTS VOTE_RECORD;
DROP TABLE IF EXISTS VOTE_ITEM;

-- 建立投票項目表
CREATE TABLE VOTE_ITEM (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(100) NOT NULL
);

-- 建立投票紀錄表
CREATE TABLE VOTE_RECORD (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    voter_name VARCHAR(50) NOT NULL,
    item_id INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES VOTE_ITEM(item_id)
);

-- ==========================================
-- 2. DML (Data Manipulation Language) - 初始資料
-- ==========================================

-- 插入投票項目
INSERT INTO VOTE_ITEM (item_id, item_name) VALUES (1, '電腦');
INSERT INTO VOTE_ITEM (item_id, item_name) VALUES (2, '滑鼠');
