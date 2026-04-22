<template>
  <div style="max-width: 800px; margin: 40px auto; font-family: 'Microsoft JhengHei', sans-serif; border: 1px solid #ccc; padding: 25px; border-radius: 12px; background: #ffffff; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
    <h2 style="color: #2c3e50; border-left: 5px solid #009973; padding-left: 15px;"> 系統管理後台</h2>
    <p style="color: #666; font-size: 0.9rem;">在此管理投票項目，您可以新增、修改或刪除項目。</p>
    
    <table style="width: 100%; border-collapse: collapse; margin-top: 20px; text-align: left;">
      <thead>
        <tr style="background: #f8f9fa; border-bottom: 2px solid #dee2e6;">
          <th style="padding: 12px; width: 60px;">序號</th>
          <th style="padding: 12px;">投票項目名稱</th>
          <th style="padding: 12px; width: 180px;">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in items" :key="item.itemId" style="border-bottom: 1px solid #eee;">
          <td style="padding: 12px; font-weight: bold; color: #999;">{{ index + 1 }}</td>
          <td style="padding: 12px;">
            <span v-if="editingId !== item.itemId">{{ item.itemName }}</span>
            <input v-else v-model="editName" style="padding: 5px; width: 80%;" />
          </td>
          <td style="padding: 12px;">
            <div v-if="editingId !== item.itemId">
              <button @click="startEdit(item)" style="color: #007bff; background: none; border: none; cursor: pointer; margin-right: 10px;">修改</button>
              <button @click="deleteItem(item.itemId)" style="color: #dc3545; background: none; border: none; cursor: pointer;">刪除</button>
            </div>
            <div v-else>
              <button @click="saveEdit(item.itemId)" style="color: #28a745; background: none; border: none; cursor: pointer; margin-right: 10px;">儲存</button>
              <button @click="editingId = null" style="color: #6c757d; background: none; border: none; cursor: pointer;">取消</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <div style="margin-top: 30px; padding: 20px; background: #f1f3f5; border-radius: 8px;">
      <h4 style="margin-top: 0;">➕ 新增投票項目</h4>
      <input v-model="newItemName" placeholder="請輸入新項目名稱 (如: 鍵盤)" style="padding: 10px; width: 60%; border: 1px solid #ccc; border-radius: 4px;" />
      <button @click="addItem" style="background: #009973; color: white; border: none; padding: 10px 20px; margin-left: 10px; cursor: pointer; border-radius: 4px; font-weight: bold;">確認新增</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const items = ref([])
const newItemName = ref('')
const editingId = ref(null) // 紀錄目前正在編輯哪一列
const editName = ref('')    // 紀錄編輯中的暫存名稱

// 1. 取得所有項目
const loadItems = async () => {
  try {
    const res = await fetch('http://localhost:8080/api/vote/items')
    items.value = await res.json()
  } catch (e) {
    alert('無法連線至後端伺服器')
  }
}

// 2. 新增項目
const addItem = async () => {
  if (!newItemName.value.trim()) return alert('名稱不能為空')
  await fetch('http://localhost:8080/api/vote/items', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ itemName: newItemName.value })
  })
  newItemName.value = ''
  loadItems()
}

// 3. 刪除項目
const deleteItem = async (id) => {
  if (!confirm('刪除項目會連同相關投票紀錄一併刪除，確定嗎？')) return
  await fetch(`http://localhost:8080/api/vote/items/${id}`, { method: 'DELETE' })
  loadItems()
}

// 4. 開始編輯
const startEdit = (item) => {
  editingId.value = item.itemId
  editName.value = item.itemName
}

// 5. 儲存修改 (PUT)
const saveEdit = async (id) => {
  if (!editName.value.trim()) return
  await fetch(`http://localhost:8080/api/vote/items/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ itemName: editName.value })
  })
  editingId.value = null
  loadItems()
}

onMounted(loadItems)
</script>