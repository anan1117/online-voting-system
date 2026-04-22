-- 1. 刪除舊表
DROP TABLE IF EXISTS VOTE_RECORD;
DROP TABLE IF EXISTS VOTE_ITEM;

-- 2. 建立投票項目表
CREATE TABLE VOTE_ITEM (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(100) NOT NULL
);

-- 3. 建立投票紀錄表
CREATE TABLE VOTE_RECORD (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    voter_name VARCHAR(50) NOT NULL,
    item_id INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES VOTE_ITEM(item_id)
);

-- 4. 插入初始資料
INSERT INTO VOTE_ITEM (item_name) VALUES ('電腦');
INSERT INTO VOTE_ITEM (item_name) VALUES ('滑鼠');