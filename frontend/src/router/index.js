import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('../views/Layout.vue'),
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: 'Home',
        component: () => import('../views/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: '/cat',
        name: 'Cat',
        component: () => import('../views/Cat.vue'),
        meta: { title: '流浪猫管理' }
      },
      {
        path: '/feed',
        name: 'Feed',
        component: () => import('../views/Feed.vue'),
        meta: { title: '投喂记录' }
      },
      {
        path: '/volunteer',
        name: 'Volunteer',
        component: () => import('../views/Volunteer.vue'),
        meta: { title: '志愿者管理' }
      },
      {
        path: '/activity',
        name: 'Activity',
        component: () => import('../views/Activity.vue'),
        meta: { title: '活动管理' }
      },
      {
        path: '/adoption',
        name: 'Adoption',
        component: () => import('../views/Adoption.vue'),
        meta: { title: '领养管理' }
      },
      {
        path: '/user',
        name: 'User',
        component: () => import('../views/User.vue'),
        meta: { title: '用户管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
