package com.esun.vote.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.esun.vote.model.VoteItem;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VoteRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall castVoteCall;

    public VoteRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        // 準備呼叫名為 CAST_VOTE 的預存程序
        this.castVoteCall = new SimpleJdbcCall(dataSource).withProcedureName("CAST_VOTE");
    }

    /**
     * 查詢所有投票項目與目前的累計票數
     */
    public List<VoteItem> findAllWithCount() {
        String sql = "SELECT i.item_id, i.item_name, COUNT(r.record_id) as vote_count " +
                     "FROM VOTE_ITEM i LEFT JOIN VOTE_RECORD r ON i.item_id = r.item_id " +
                     "GROUP BY i.item_id, i.item_name";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(VoteItem.class));
    }

    /**
     * 執行投票（呼叫 Stored Procedure）
     */
//    public void castVote(String voterName, Integer itemId) {
//        Map<String, Object> inParams = new HashMap<>();
//        inParams.put("p_voter_name", voterName);
//        inParams.put("p_item_id", itemId);
//        castVoteCall.execute(inParams);
//    }
    
    public void castVote(String voterName, Integer itemId) {
        // 改用標準 SQL 插入，這同樣能透過 PreparedStatement 防止 SQL Injection
        String sql = "INSERT INTO VOTE_RECORD (voter_name, item_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, voterName, itemId);
    }

    /**
     * 新增投票項目
     */
    public void addItem(String itemName) {
        String sql = "INSERT INTO VOTE_ITEM (item_name) VALUES (?)";
        jdbcTemplate.update(sql, itemName);
    }
    
    public void updateItem(Integer id, String name) {
        jdbcTemplate.update("UPDATE VOTE_ITEM SET item_name = ? WHERE item_id = ?", name, id);
    }

    public void deleteItem(Integer id) {
     // 1. 先刪除該項目的所有投票紀錄 (避免外鍵衝突)
        jdbcTemplate.update("DELETE FROM VOTE_RECORD WHERE item_id = ?", id);
        
        // 2. 再刪除該項目本身
        jdbcTemplate.update("DELETE FROM VOTE_ITEM WHERE item_id = ?", id);
    }
}