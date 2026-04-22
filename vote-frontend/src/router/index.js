import { createRouter, createWebHistory } from 'vue-router'
import UserVote from '../views/UserVote.vue'
import AdminManage from '../views/AdminManage.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: UserVote },
    { path: '/admin', component: AdminManage }
  ]
})

export default router