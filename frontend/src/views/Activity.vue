<template>
  <div class="activity-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <div class="card-title">
            <el-icon class="card-title-icon"><Calendar /></el-icon>
            <span>活动管理</span>
          </div>
          <el-button type="primary" @click="handleAdd" :icon="Plus">发布活动</el-button>
        </div>
      </template>

      <!-- 状态筛选 -->
      <el-form :inline="true" class="search-form">
        <el-form-item label="活动状态">
          <el-select v-model="statusFilter" placeholder="全部" clearable @change="loadData">
            <el-option label="报名中" value="报名中"></el-option>
            <el-option label="进行中" value="进行中"></el-option>
            <el-option label="已结束" value="已结束"></el-option>
            <el-option label="已取消" value="已取消"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="title" label="活动标题" width="200"></el-table-column>
        <el-table-column prop="location" label="活动地点" width="150"></el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="180"></el-table-column>
        <el-table-column prop="endTime" label="结束时间" width="180"></el-table-column>
        <el-table-column label="报名人数" width="120">
          <template #default="scope">
            {{ scope.row.currentParticipants }} / {{ scope.row.maxParticipants }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusTag(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="活动标题" prop="title">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="活动描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3"></el-input>
        </el-form-item>
        <el-form-item label="活动地点" prop="location">
          <el-input v-model="form.location"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择开始时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker v-model="form.endTime" type="datetime" placeholder="选择结束时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="最大人数" prop="maxParticipants">
          <el-input-number v-model="form.maxParticipants" :min="1" :max="200"></el-input-number>
        </el-form-item>
        <el-form-item label="活动状态" prop="status">
          <el-select v-model="form.status">
            <el-option label="报名中" value="报名中"></el-option>
            <el-option label="进行中" value="进行中"></el-option>
            <el-option label="已结束" value="已结束"></el-option>
            <el-option label="已取消" value="已取消"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Calendar, Plus } from '@element-plus/icons-vue'
import { getActivityList, addActivity, updateActivity, deleteActivity } from '../api/activity'

const tableData = ref([])
const statusFilter = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('发布活动')
const formRef = ref(null)
const user = JSON.parse(localStorage.getItem('user') || '{}')

const form = reactive({
  id: null,
  title: '',
  description: '',
  location: '',
  startTime: '',
  endTime: '',
  maxParticipants: 50,
  currentParticipants: 0,
  status: '报名中',
  createBy: user.id
})

const rules = {
  title: [{ required: true, message: '请输入活动标题', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  endTime: [{ required: true, message: '请选择结束时间', trigger: 'change' }]
}

const getStatusTag = (status) => {
  const map = { '报名中': 'success', '进行中': 'primary', '已结束': 'info', '已取消': 'danger' }
  return map[status] || 'info'
}

const loadData = async () => {
  try {
    const res = await getActivityList({ status: statusFilter.value || undefined })
    tableData.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleAdd = () => {
  dialogTitle.value = '发布活动'
  form.id = null
  form.title = ''
  form.description = ''
  form.location = ''
  form.startTime = ''
  form.endTime = ''
  form.maxParticipants = 50
  form.currentParticipants = 0
  form.status = '报名中'
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑活动'
  Object.keys(row).forEach(key => {
    if (key in form) form[key] = row[key]
  })
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该活动吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteActivity(row.id)
      ElMessage.success('删除成功')
      loadData()
    })
    .catch(() => {})
}

const handleSubmit = async () => {
  await formRef.value.validate()
  try {
    if (form.id) {
      await updateActivity(form)
      ElMessage.success('更新成功')
    } else {
      await addActivity(form)
      ElMessage.success('发布成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
  color: var(--ios-label);
}

.card-title-icon {
  color: var(--ios-blue);
}

.search-form {
  margin-bottom: 20px;
  padding: 16px;
  border-radius: var(--lg-radius-sm);
  background: rgba(255, 255, 255, 0.3);
}
</style>
