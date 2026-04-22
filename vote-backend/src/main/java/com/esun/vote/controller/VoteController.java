package com.esun.vote.controller;

import org.springframework.web.bind.annotation.*;
import com.esun.vote.model.VoteItem;
import com.esun.vote.service.VoteService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vote")
@CrossOrigin(origins = "*")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    // 1. 取得所有投票項目 
    @GetMapping("/items")
    public List<VoteItem> getItems() {
        return voteService.getAllItems();
    }

    // 2. 進行投票 
    @PostMapping("/cast")
    public String castVote(@RequestBody Map<String, Object> payload) {
        String voterName = (String) payload.get("voterName");
        List<Integer> itemIds = (List<Integer>) payload.get("itemIds");
        
        voteService.vote(voterName, itemIds);
        return "投票成功！";
    }

    // 3. 新增投票項目 
    @PostMapping("/items")
    public String addItem(@RequestBody Map<String, String> payload) {
        voteService.createNewItem(payload.get("itemName"));
        return "新增項目成功！";
    }
    
 // 4. 更新投票項目 
    @PutMapping("/items/{id}")
    public String updateItem(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        voteService.updateItemName(id, payload.get("itemName"));
        return "更新成功";
    }

    // 5. 刪除投票項目 
    @DeleteMapping("/items/{id}")
    public String deleteItem(@PathVariable Integer id) {
        voteService.removeItem(id);
        return "刪除成功";
    }
}