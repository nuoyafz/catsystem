<template>
  <el-container class="lg-layout">
    <!-- 液态玻璃侧边栏 -->
    <el-aside width="220px" class="lg-aside">
      <div class="lg-logo">
        <div class="lg-logo-mark">
          <img src="/cat.png" alt="Logo" class="lg-logo-img" />
        </div>
        <div class="lg-logo-text">
          <div class="lg-logo-title">流浪猫</div>
          <div class="lg-logo-sub">投喂记录系统</div>
        </div>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        class="lg-menu"
      >
        <el-menu-item index="/home">
          <el-icon><HomeFilled /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-menu-item index="/cat">
          <el-icon><Star /></el-icon>
          <span>流浪猫管理</span>
        </el-menu-item>
        <el-menu-item index="/feed">
          <el-icon><Document /></el-icon>
          <span>投喂记录</span>
        </el-menu-item>
        <el-menu-item index="/volunteer">
          <el-icon><User /></el-icon>
          <span>志愿者管理</span>
        </el-menu-item>
        <el-menu-item index="/activity">
          <el-icon><Calendar /></el-icon>
          <span>活动管理</span>
        </el-menu-item>
        <el-menu-item index="/adoption">
          <el-icon><House /></el-icon>
          <span>领养管理</span>
        </el-menu-item>
        <el-menu-item index="/user" v-if="user.role === 'admin'">
          <el-icon><Setting /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <!-- 液态玻璃顶栏 -->
      <el-header class="lg-header">
        <div class="lg-header-inner">
          <div class="lg-breadcrumb">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>{{ currentTitle }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="lg-user">
            <button class="theme-toggle" @click="toggleTheme" :title="`切换到${themeNames[currentTheme]}`">
              <span class="theme-icon">{{ themeIcons[currentTheme] }}</span>
            </button>
            <div class="lg-user-avatar">{{ user.username ? user.username.charAt(0).toUpperCase() : 'U' }}</div>
            <el-dropdown @command="handleCommand">
              <span class="lg-dropdown-link">
                {{ user.username }}
                <el-icon class="el-icon--right"><arrow-down /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                  <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </el-header>

      <!-- 主内容区 -->
      <el-main class="lg-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed, onMounted, onUnmounted, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()

const user = JSON.parse(localStorage.getItem('user') || '{}')

const activeMenu = computed(() => route.path)
const currentTitle = computed(() => route.meta.title || '首页')

const currentTheme = ref(localStorage.getItem('theme') || 'minecraft')

const themeNames = {
  minecraft: '像素风格',
  vibrant: '活力主题',
  warm: '暖阳猫舍'
}

const themeIcons = {
  minecraft: '⛏️',
  vibrant: '🎨',
  warm: '🌞'
}

const toggleTheme = () => {
  const themes = ['minecraft', 'vibrant', 'warm']
  const currentIndex = themes.indexOf(currentTheme.value)
  const nextIndex = (currentIndex + 1) % themes.length
  const newTheme = themes[nextIndex]
  
  currentTheme.value = newTheme
  localStorage.setItem('theme', newTheme)
  document.documentElement.setAttribute('data-theme', newTheme)
  document.body.setAttribute('data-theme', newTheme)
  ElMessage.success(`已切换到${themeNames[newTheme]}主题`)
}

const handleCommand = (command) => {
  if (command === 'logout') {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    ElMessage.success('已退出登录')
    router.push('/login')
  } else if (command === 'profile') {
    ElMessage.info('个人信息功能开发中')
  }
}

onMounted(() => {
  document.addEventListener('mousemove', handleMouseTrack)
  const savedTheme = localStorage.getItem('theme') || 'minecraft'
  currentTheme.value = savedTheme
  document.documentElement.setAttribute('data-theme', savedTheme)
  document.body.setAttribute('data-theme', savedTheme)
})

onUnmounted(() => {
  document.removeEventListener('mousemove', handleMouseTrack)
})

function handleMouseTrack(e) {
  const els = document.querySelectorAll('.liquid-glass')
  els.forEach(el => {
    const rect = el.getBoundingClientRect()
    const x = ((e.clientX - rect.left) / rect.width) * 100
    const y = ((e.clientY - rect.top) / rect.height) * 100
    el.style.setProperty('--mouse-x', `${x}%`)
    el.style.setProperty('--mouse-y', `${y}%`)
  })
}
</script>

<style scoped>
.lg-layout {
  height: 100dvh;
}

/* 侧边栏 - Minecraft 像素风格 */
.lg-aside {
  position: relative;
  background: var(--mc-wood);
  border-right: 4px solid var(--mc-dark);
  box-shadow:
    inset -4px -4px 0 0 var(--mc-wood-dark),
    inset 4px 4px 0 0 #BCAAA4,
    4px 0 0 0 var(--mc-dark);
  overflow-y: auto;
  overflow-x: hidden;
  image-rendering: pixelated;
}

.lg-logo {
  height: 72px;
  display: flex;
  align-items: center;
  padding: 0 20px;
  gap: 12px;
  border-bottom: 4px solid var(--mc-dark);
  background: var(--mc-wood-dark);
}

.lg-logo-mark {
  width: 38px;
  height: 38px;
  border-radius: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--mc-primary);
  border: 3px solid var(--mc-dark);
  box-shadow:
    inset -3px -3px 0 0 var(--mc-primary-dark),
    inset 3px 3px 0 0 var(--mc-grass);
  overflow: hidden;
  image-rendering: pixelated;
}

.lg-logo-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  image-rendering: pixelated;
}

.lg-logo-title {
  color: var(--mc-white);
  font-size: 16px;
  font-weight: 700;
  line-height: 1.4;
  font-family: 'Press Start 2P', 'Microsoft YaHei', cursive;
  text-shadow: 2px 2px 0 var(--mc-dark);
}

.lg-logo-sub {
  color: var(--mc-light);
  font-size: 11px;
  letter-spacing: 0.5px;
  margin-top: 2px;
  font-family: 'Press Start 2P', 'Microsoft YaHei', cursive;
  text-shadow: 1px 1px 0 var(--mc-dark);
  line-height: 1.4;
}

.lg-menu {
  padding: 12px 0;
}

/* 顶栏 - Minecraft 像素风格 */
.lg-header {
  height: 64px;
  background: var(--mc-stone);
  border-bottom: 4px solid var(--mc-dark);
  box-shadow:
    inset -4px -4px 0 0 var(--mc-stone-dark),
    inset 4px 4px 0 0 var(--mc-light),
    0 4px 0 0 var(--mc-dark);
  position: relative;
  z-index: 10;
  image-rendering: pixelated;
}

.lg-header-inner {
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
}

.lg-user {
  display: flex;
  align-items: center;
  gap: 10px;
}

.theme-toggle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 2px solid var(--vb-border, var(--mc-stone));
  background: var(--vb-surface, var(--mc-white));
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.theme-toggle:hover {
  transform: scale(1.1) rotate(15deg);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.theme-icon {
  font-size: 20px;
  line-height: 1;
}

.lg-user-avatar {
  width: 34px;
  height: 34px;
  border-radius: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--mc-primary);
  border: 3px solid var(--mc-dark);
  color: var(--mc-white);
  font-weight: 700;
  font-size: 12px;
  box-shadow:
    inset -2px -2px 0 0 var(--mc-primary-dark),
    inset 2px 2px 0 0 var(--mc-grass);
  font-family: 'Press Start 2P', cursive;
  image-rendering: pixelated;
}

.lg-dropdown-link {
  cursor: pointer;
  display: flex;
  align-items: center;
  color: var(--mc-text);
  font-weight: 700;
  font-family: 'Press Start 2P', cursive;
  font-size: 10px;
}

/* 主内容区 - 透明，让背景透出 */
.lg-main {
  background: transparent;
  padding: 24px;
  overflow-y: auto;
}
</style>
