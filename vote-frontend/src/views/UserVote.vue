<template>
  <div style="max-width: 600px; margin: 40px auto; font-family: sans-serif; border: 1px solid #ddd; padding: 20px; border-radius: 8px;">
    <h2 style="color: #009973;">E.SUN BANK 玉山銀行 - 我要投票</h2>
    <hr />
    <div v-for="item in items" :key="item.itemId" style="margin: 10px 0;">
      <input type="checkbox" :id="'v-'+item.itemId" :value="item.itemId" v-model="selectedItemIds">
      <label :for="'v-'+item.itemId"> {{ item.itemName }} (目前票數: {{ item.voteCount }})</label>
    </div>
    <div style="margin-top: 20px;">
      <input v-model="voterName" placeholder="請輸入您的姓名" style="padding: 8px; width: 200px;" />
      <button @click="submitVote" style="background: #009973; color: white; border: none; padding: 8px 20px; cursor: pointer; border-radius: 4px;">提交投票</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const items = ref([])
const selectedItemIds = ref([])
const voterName = ref('')

const loadItems = async () => {
  const res = await fetch('http://localhost:8080/api/vote/items')
  items.value = await res.json()
}

const submitVote = async () => {
  if (!voterName.value || selectedItemIds.value.length === 0) return alert('請填寫完整')
  await fetch('http://localhost:8080/api/vote/cast', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ voterName: voterName.value, itemIds: selectedItemIds.value })
  })
  alert('投票成功！')
  voterName.value = ''; selectedItemIds.value = []; loadItems()
}

onMounted(loadItems)
</script>