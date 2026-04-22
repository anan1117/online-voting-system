package com.esun.vote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.esun.vote.model.VoteItem;
import com.esun.vote.repository.VoteRepository;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<VoteItem> getAllItems() {
        return voteRepository.findAllWithCount();
    }

    @Transactional
    public void vote(String voterName, List<Integer> itemIds) {
        // 如果名字是空的就拋出異常
        if (voterName == null || voterName.trim().isEmpty()) {
            throw new IllegalArgumentException("投票人姓名不得為空");
        }
        
        // 可多選，跑迴圈執行投票
        for (Integer itemId : itemIds) {
            voteRepository.castVote(voterName, itemId);
        }
    }

    public void createNewItem(String itemName) {
        voteRepository.addItem(itemName);
    }
    
    public void updateItemName(Integer id, String newName) {
        voteRepository.updateItem(id, newName);
    }

    public void removeItem(Integer id) {
        voteRepository.deleteItem(id);
    }
}