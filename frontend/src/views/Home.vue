<template>
  <div class="home-container">
    <!-- 欢迎横幅 -->
    <div class="welcome-banner liquid-glass">
      <div class="welcome-text">
        <h2>欢迎回来，{{ user.username || '同学' }}</h2>
        <p>感谢你为校园流浪猫付出的每一份温暖</p>
      </div>
      <div class="welcome-icon">
        <el-icon :size="48"><Star /></el-icon>
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stat-row">
      <el-col :span="6" v-for="(item, idx) in statCards" :key="idx">
        <div class="stat-card liquid-glass liquid-glass-hover">
          <div class="stat-icon" :style="{ background: item.gradient }">
            <el-icon :size="26"><component :is="item.icon" /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ item.value }}</div>
            <div class="stat-label">{{ item.label }}</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 互动小猫 -->
    <div class="interactive-cat-section">
      <div class="cat-track" ref="catTrackRef">
        <div
          class="cat-container"
          :style="{ transform: `translateX(${catX}px) scaleX(${catDirection})` }"
          @click="handleCatClick"
        >
          <!-- 内联 SVG 猫咪，不依赖外部资源 -->
          <svg class="cat-svg" viewBox="0 0 120 100" xmlns="http://www.w3.org/2000/svg">
              <!-- 尾巴 -->
              <path d="M95 55 Q110 40 105 25" stroke="#F4A460" stroke-width="6" fill="none" stroke-linecap="round">
                <animateTransform attributeName="transform" type="rotate" values="-5 95 55;10 95 55;-5 95 55" dur="0.8s" repeatCount="indefinite"/>
              </path>
              <!-- 身体 -->
              <ellipse cx="60" cy="62" rx="30" ry="20" fill="#F4A460"/>
              <!-- 花纹 -->
              <ellipse cx="50" cy="55" rx="8" ry="4" fill="#D2892E" opacity="0.5"/>
              <ellipse cx="70" cy="58" rx="6" ry="3" fill="#D2892E" opacity="0.5"/>
              <!-- 头 -->
              <circle cx="35" cy="40" r="22" fill="#F4A460"/>
              <!-- 左耳 -->
              <polygon points="18,22 14,4 30,18" fill="#F4A460"/>
              <polygon points="20,20 17,8 28,18" fill="#FFB6C1"/>
              <!-- 右耳 -->
              <polygon points="48,18 52,4 56,22" fill="#F4A460"/>
              <polygon points="49,18 52,8 54,20" fill="#FFB6C1"/>
              <!-- 眼睛 -->
              <ellipse cx="27" cy="38" rx="4" ry="5" fill="#2E8B57"/>
              <ellipse cx="27" cy="38" rx="2" ry="4.5" fill="#111"/>
              <circle cx="26" cy="36" r="1.2" fill="#FFF"/>
              <ellipse cx="43" cy="38" rx="4" ry="5" fill="#2E8B57"/>
              <ellipse cx="43" cy="38" rx="2" ry="4.5" fill="#111"/>
              <circle cx="42" cy="36" r="1.2" fill="#FFF"/>
              <!-- 鼻子 -->
              <polygon points="35,46 33,49 37,49" fill="#FF69B4"/>
              <!-- 嘴 -->
              <path d="M35 49 Q31 53 28 51" stroke="#555" stroke-width="1" fill="none"/>
              <path d="M35 49 Q39 53 42 51" stroke="#555" stroke-width="1" fill="none"/>
              <!-- 胡须 -->
              <line x1="10" y1="42" x2="24" y2="45" stroke="#888" stroke-width="0.8"/>
              <line x1="8" y1="48" x2="24" y2="48" stroke="#888" stroke-width="0.8"/>
              <line x1="46" y1="45" x2="60" y2="42" stroke="#888" stroke-width="0.8"/>
              <line x1="46" y1="48" x2="62" y2="48" stroke="#888" stroke-width="0.8"/>
              <!-- 前腿 -->
              <rect x="40" y="76" width="8" height="16" rx="4" fill="#F4A460">
                <animateTransform attributeName="transform" type="translate" values="0,0;0,-4;0,0" dur="0.4s" repeatCount="indefinite"/>
              </rect>
              <rect x="52" y="76" width="8" height="16" rx="4" fill="#E8973E">
                <animateTransform attributeName="transform" type="translate" values="0,-4;0,0;0,-4" dur="0.4s" repeatCount="indefinite"/>
              </rect>
              <!-- 后腿 -->
              <rect x="68" y="76" width="8" height="16" rx="4" fill="#F4A460">
                <animateTransform attributeName="transform" type="translate" values="0,-4;0,0;0,-4" dur="0.4s" repeatCount="indefinite"/>
              </rect>
              <rect x="80" y="76" width="8" height="16" rx="4" fill="#E8973E">
                <animateTransform attributeName="transform" type="translate" values="0,0;0,-4;0,0" dur="0.4s" repeatCount="indefinite"/>
              </rect>
              <!-- 爪子 -->
              <ellipse cx="44" cy="92" rx="5" ry="2.5" fill="#FFDAB9"/>
              <ellipse cx="56" cy="92" rx="5" ry="2.5" fill="#FFDAB9"/>
              <ellipse cx="72" cy="92" rx="5" ry="2.5" fill="#FFDAB9"/>
              <ellipse cx="84" cy="92" rx="5" ry="2.5" fill="#FFDAB9"/>
            </svg>
          <div v-if="showBubble" class="speech-bubble">
            <div class="bubble-content">{{ currentMessage }}</div>
            <div class="bubble-arrow"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- 排行榜 + 最近记录 -->
    <el-row :gutter="20" class="chart-row">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <el-icon class="header-icon"><Trophy /></el-icon>
              <span>投喂次数 TOP5</span>
            </div>
          </template>
          <div ref="catRankingRef" style="height: 320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <el-icon class="header-icon"><User /></el-icon>
              <span>志愿者贡献榜</span>
            </div>
          </template>
          <div ref="volunteerRankingRef" style="height: 320px"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 地点分布 + 最近记录 -->
    <el-row :gutter="20" class="chart-row">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <el-icon class="header-icon"><Location /></el-icon>
              <span>猫咪地点分布</span>
            </div>
          </template>
          <div ref="locationChartRef" style="height: 320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <el-icon class="header-icon"><Clock /></el-icon>
              <span>最近投喂记录</span>
            </div>
          </template>
          <el-table :data="recentFeeds" style="width: 100%">
            <el-table-column prop="catName" label="猫咪" width="100"></el-table-column>
            <el-table-column prop="realName" label="投喂人" width="100"></el-table-column>
            <el-table-column prop="foodType" label="食物类型" width="100">
              <template #default="scope">
                <el-tag size="small" effect="light">{{ scope.row.foodType }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="feedTime" label="投喂时间"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, nextTick, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { Star, Document, User, Calendar, PieChart, Clock, Trophy, Location } from '@element-plus/icons-vue'
import { getCatList } from '../api/cat'
import { getFeedList } from '../api/feed'
import { getVolunteerList } from '../api/volunteer'
import { getActivityList } from '../api/activity'
import { getCatRanking, getVolunteerRanking, getLocationDistribution } from '../api/statistics'

const user = JSON.parse(localStorage.getItem('user') || '{}')

const stats = ref({ catCount: 0, feedCount: 0, volunteerCount: 0, activityCount: 0 })
const recentFeeds = ref([])
const catRankingRef = ref(null)
const volunteerRankingRef = ref(null)
const locationChartRef = ref(null)

// 互动小猫相关
const showBubble = ref(false)
const currentMessage = ref('')
const catTrackRef = ref(null)
const catX = ref(0)
const catDirection = ref(1) // 1 = 向右, -1 = 向左
let bubbleTimer = null
let animationFrameId = null
const catSpeed = 1.5 // 像素/帧

const animateCat = () => {
  if (!catTrackRef.value) {
    animationFrameId = requestAnimationFrame(animateCat)
    return
  }

  const trackWidth = catTrackRef.value.offsetWidth
  const catWidth = 60

  catX.value += catSpeed * catDirection.value

  if (catX.value >= trackWidth - catWidth) {
    catDirection.value = -1
  } else if (catX.value <= 0) {
    catDirection.value = 1
  }

  animationFrameId = requestAnimationFrame(animateCat)
}

const catMessages = [
  '喵~ 今天投喂了吗？',
  '我饿了，有好吃的吗？',
  '谢谢你来看我！',
  '校园里的猫咪都很可爱哦~',
  '记得按时投喂哦！',
  '今天天气真好，适合散步~',
  '我是校园的小守护者！',
  '有鱼干吗？我想吃鱼干~',
  '摸摸我，我会呼噜呼噜~',
  '我是最可爱的流浪猫！'
]

const handleCatClick = () => {
  // 随机选择一条消息
  const randomIndex = Math.floor(Math.random() * catMessages.length)
  currentMessage.value = catMessages[randomIndex]
  showBubble.value = true

  // 清除之前的定时器
  if (bubbleTimer) {
    clearTimeout(bubbleTimer)
  }

  // 3秒后隐藏气泡
  bubbleTimer = setTimeout(() => {
    showBubble.value = false
  }, 3000)
}

const statCards = computed(() => [
  { label: '流浪猫数量', value: stats.value.catCount, icon: Star, gradient: 'linear-gradient(135deg, #E8A33D, #C8842A)' },
  { label: '投喂记录数', value: stats.value.feedCount, icon: Document, gradient: 'linear-gradient(135deg, #7FB069, #5E8F4F)' },
  { label: '志愿者数量', value: stats.value.volunteerCount, icon: User, gradient: 'linear-gradient(135deg, #F5A89E, #E07A6E)' },
  { label: '活动数量', value: stats.value.activityCount, icon: Calendar, gradient: 'linear-gradient(135deg, #F5C77E, #E8A33D)' }
])

onMounted(async () => {
  // 启动猫咪动画
  await nextTick()
  console.log('启动猫咪动画，catTrackRef:', catTrackRef.value)
  animateCat()

  try {
    const [catRes, feedRes, volunteerRes, activityRes, catRankingRes, volunteerRankingRes, locationRes] = await Promise.all([
      getCatList(), getFeedList(), getVolunteerList(), getActivityList(),
      getCatRanking(5), getVolunteerRanking(5), getLocationDistribution()
    ])

    stats.value.catCount = catRes.data.length
    stats.value.feedCount = feedRes.data.length
    stats.value.volunteerCount = volunteerRes.data.length
    stats.value.activityCount = activityRes.data.length
    recentFeeds.value = feedRes.data.slice(0, 6)

    // 猫咪投喂排行柱状图
    const catRankingChart = echarts.init(catRankingRef.value)
    catRankingChart.setOption({
      tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
      grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
      xAxis: {
        type: 'category',
        data: catRankingRes.data.map(item => item.catName),
        axisLine: { lineStyle: { color: '#8A7F75' } },
        axisLabel: { color: '#4A4039', rotate: 30 }
      },
      yAxis: {
        type: 'value',
        axisLine: { lineStyle: { color: '#8A7F75' } },
        axisLabel: { color: '#4A4039' },
        splitLine: { lineStyle: { color: 'rgba(138, 127, 117, 0.1)' } }
      },
      series: [{
        type: 'bar',
        data: catRankingRes.data.map(item => item.count),
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#E8A33D' },
            { offset: 1, color: '#C8842A' }
          ]),
          borderRadius: [8, 8, 0, 0]
        }
      }]
    })

    // 志愿者贡献榜横向柱状图
    const volunteerRankingChart = echarts.init(volunteerRankingRef.value)
    volunteerRankingChart.setOption({
      tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
      grid: { left: '3%', right: '10%', bottom: '3%', containLabel: true },
      xAxis: {
        type: 'value',
        axisLine: { lineStyle: { color: '#8A7F75' } },
        axisLabel: { color: '#4A4039' },
        splitLine: { lineStyle: { color: 'rgba(138, 127, 117, 0.1)' } }
      },
      yAxis: {
        type: 'category',
        data: volunteerRankingRes.data.map(item => item.name).reverse(),
        axisLine: { lineStyle: { color: '#8A7F75' } },
        axisLabel: { color: '#4A4039' }
      },
      series: [{
        type: 'bar',
        data: volunteerRankingRes.data.map(item => item.serviceHours).reverse(),
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: '#7FB069' },
            { offset: 1, color: '#5E8F4F' }
          ]),
          borderRadius: [0, 8, 8, 0]
        }
      }]
    })

    // 地点分布饼图
    const locationChart = echarts.init(locationChartRef.value)
    locationChart.setOption({
      tooltip: { trigger: 'item' },
      legend: { orient: 'vertical', left: 'left', textStyle: { color: '#4A4039' } },
      color: ['#E8A33D', '#7FB069', '#F5A89E', '#8A7F75', '#F5C77E', '#9B59B6'],
      series: [{
        type: 'pie',
        radius: ['35%', '65%'],
        center: ['62%', '50%'],
        avoidLabelOverlap: false,
        itemStyle: { borderRadius: 10, borderColor: 'rgba(255,255,255,0.5)', borderWidth: 2 },
        label: { show: true, color: '#4A4039' },
        data: locationRes.data.map(item => ({ name: item.location, value: item.count })),
        emphasis: {
          itemStyle: { shadowBlur: 20, shadowOffsetX: 0, shadowColor: 'rgba(232, 163, 61, 0.4)' }
        }
      }]
    })
  } catch (error) {
    console.error(error)
  }
})
</script>

<style scoped>
.home-container {
  padding: 0;
}

.welcome-banner {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px 32px;
  margin-bottom: 20px;
  background: linear-gradient(135deg, #E8A33D 0%, #D4942A 100%);
  border: 2px solid rgba(0, 0, 0, 0.1);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  transition: all 0.3s ease;
}

.welcome-text h2 {
  margin: 0;
  font-size: 24px;
  font-weight: 700;
  color: #FFFFFF;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  line-height: 1.4;
}

.welcome-text p {
  margin: 8px 0 0;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.95);
  line-height: 1.5;
}

.welcome-icon {
  color: #FFFFFF;
  opacity: 0.9;
}

.stat-row {
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 22px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #FFFFFF;
  margin-right: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  transition: all 0.3s ease;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  color: #333;
  line-height: 1;
}

.stat-label {
  font-size: 13px;
  color: #666;
  margin-top: 6px;
  font-weight: 500;
}

.chart-row {
  margin-bottom: 20px !important;
}

.card-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
  color: #333;
  font-size: 16px;
}

.header-icon {
  color: #E8A33D;
}

/* 互动小猫样式 */
.interactive-cat-section {
  margin-bottom: 20px;
  padding: 30px 20px;
  position: relative;
}

.cat-track {
  width: 100%;
  height: 120px;
  position: relative;
  background: linear-gradient(180deg, transparent 0%, rgba(232, 163, 61, 0.05) 100%);
  border-radius: 12px;
}

.cat-container {
  position: absolute;
  left: 0;
  bottom: 10px;
  cursor: pointer;
  z-index: 10;
  will-change: transform;
}

.cat-sprite {
  position: relative;
  width: 80px;
  height: 66px;
  transition: transform 0.2s ease;
}

.cat-container:hover .cat-sprite {
  transform: scale(1.1);
}

.cat-walking {
  animation: cat-move 15s linear infinite;
}

@keyframes cat-move {
  0% {
    left: -100px;
  }
  100% {
    left: calc(100% + 100px);
  }
}

.cat-wrapper {
  position: relative;
  width: 80px;
  height: 80px;
}

.cat-svg {
  width: 60px;
  height: 50px;
  filter: drop-shadow(0 2px 4px rgba(0,0,0,0.2));
}

/* SVG 猫咪动画 */
.cat-svg .cat-tail {
  animation: tail-wag 1s ease-in-out infinite;
  transform-origin: 70px 65px;
}

@keyframes tail-wag {
  0%, 100% { transform: rotate(-15deg); }
  50% { transform: rotate(15deg); }
}

.cat-svg .cat-leg-1 {
  animation: leg-walk 0.5s ease-in-out infinite;
}
.cat-svg .cat-leg-2 {
  animation: leg-walk 0.5s ease-in-out infinite 0.25s;
}
.cat-svg .cat-leg-3 {
  animation: leg-walk 0.5s ease-in-out infinite 0.125s;
}

@keyframes leg-walk {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-3px); }
}

.cat-svg .cat-eye-left,
.cat-svg .cat-eye-right {
  animation: blink 3s ease-in-out infinite;
  transform-origin: center;
}

@keyframes blink {
  0%, 90%, 100% { transform: scaleY(1); }
  95% { transform: scaleY(0.1); }
}

.cat-svg .cat-head {
  animation: head-bob 2s ease-in-out infinite;
  transform-origin: 50px 40px;
}

@keyframes head-bob {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-2px); }
}

/* 对话气泡 */
.speech-bubble {
  position: absolute;
  top: -80px;
  left: 50%;
  transform: translateX(-50%);
  background: #fff;
  border: 2px solid #E8A33D;
  border-radius: 16px;
  padding: 12px 16px;
  min-width: 180px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  animation: bubble-appear 0.3s ease-out;
  z-index: 20;
}

.bubble-content {
  color: #333;
  font-size: 14px;
  font-weight: 500;
  text-align: center;
  line-height: 1.4;
}

.bubble-arrow {
  position: absolute;
  bottom: -8px;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 0;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-top: 8px solid #fff;
}

.bubble-arrow::before {
  content: '';
  position: absolute;
  top: -10px;
  left: -8px;
  width: 0;
  height: 0;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-top: 8px solid #E8A33D;
}

@keyframes bubble-appear {
  from {
    opacity: 0;
    transform: translateX(-50%) translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateX(-50%) translateY(0);
  }
}
</style>
