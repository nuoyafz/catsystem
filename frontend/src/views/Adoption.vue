<template>
  <div class="adoption-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <div class="card-title">
            <el-icon class="card-title-icon"><House /></el-icon>
            <span>领养管理</span>
          </div>
          <el-button type="primary" @click="handleAdd" :icon="Plus">申请领养</el-button>
        </div>
      </template>

      <!-- 表格 -->
      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="70"></el-table-column>
        <el-table-column label="猫咪" width="150">
          <template #default="scope">
            <div class="cat-info">
              <img v-if="scope.row.catPhoto" :src="scope.row.catPhoto" class="cat-mini-avatar" />
              <span>{{ scope.row.catName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="applicantName" label="申请人" width="100"></el-table-column>
        <el-table-column prop="applicantPhone" label="联系电话" width="120"></el-table-column>
        <el-table-column prop="applicantAddress" label="居住地址"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusTag(scope.row.status)">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reviewerName" label="审核人" width="100"></el-table-column>
        <el-table-column prop="createTime" label="申请时间" width="160"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button 
              v-if="scope.row.status === '待审核'" 
              type="success" 
              size="small" 
              @click="handleReview(scope.row, '已通过')"
            >
              通过
            </el-button>
            <el-button 
              v-if="scope.row.status === '待审核'" 
              type="danger" 
              size="small" 
              @click="handleReview(scope.row, '已拒绝')"
            >
              拒绝
            </el-button>
            <el-button type="primary" size="small" @click="handleView(scope.row)">查看</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 申请领养对话框 -->
    <el-dialog v-model="dialogVisible" title="申请领养" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="选择猫咪" prop="catId">
          <el-select v-model="form.catId" placeholder="请选择要领养的猫咪">
            <el-option 
              v-for="cat in availableCats" 
              :key="cat.id" 
              :label="cat.name" 
              :value="cat.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请人姓名" prop="applicantName">
          <el-input v-model="form.applicantName"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="applicantPhone">
          <el-input v-model="form.applicantPhone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="applicantEmail">
          <el-input v-model="form.applicantEmail"></el-input>
        </el-form-item>
        <el-form-item label="居住地址" prop="applicantAddress">
          <el-input v-model="form.applicantAddress" type="textarea" :rows="2"></el-input>
        </el-form-item>
        <el-form-item label="领养理由" prop="reason">
          <el-input v-model="form.reason" type="textarea" :rows="3"></el-input>
        </el-form-item>
        <el-form-item label="养猫经验" prop="experience">
          <el-input v-model="form.experience" type="textarea" :rows="3"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交申请</el-button>
      </template>
    </el-dialog>

    <!-- 查看详情对话框 -->
    <el-dialog v-model="viewDialogVisible" title="申请详情" width="600px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="猫咪">{{ viewData.catName }}</el-descriptions-item>
        <el-descriptions-item label="申请人">{{ viewData.applicantName }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ viewData.applicantPhone }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ viewData.applicantEmail }}</el-descriptions-item>
        <el-descriptions-item label="居住地址">{{ viewData.applicantAddress }}</el-descriptions-item>
        <el-descriptions-item label="领养理由">{{ viewData.reason }}</el-descriptions-item>
        <el-descriptions-item label="养猫经验">{{ viewData.experience }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusTag(viewData.status)">{{ viewData.status }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="审核人">{{ viewData.reviewerName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="审核意见">{{ viewData.reviewComment || '-' }}</el-descriptions-item>
        <el-descriptions-item label="申请时间">{{ viewData.createTime }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { House, Plus } from '@element-plus/icons-vue'
import { getAllAdoptions, applyAdoption, deleteAdoption, reviewAdoption } from '../api/adoption'
import { getCatList } from '../api/cat'

const tableData = ref([])
const availableCats = ref([])
const dialogVisible = ref(false)
const viewDialogVisible = ref(false)
const formRef = ref(null)
const viewData = ref({})

const form = reactive({
  catId: null,
  applicantName: '',
  applicantPhone: '',
  applicantEmail: '',
  applicantAddress: '',
  reason: '',
  experience: ''
})

const rules = {
  catId: [{ required: true, message: '请选择猫咪', trigger: 'change' }],
  applicantName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  applicantPhone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  reason: [{ required: true, message: '请输入领养理由', trigger: 'blur' }]
}

const getStatusTag = (status) => {
  const map = { '待审核': 'warning', '已通过': 'success', '已拒绝': 'danger', '已完成': 'info' }
  return map[status] || 'info'
}

const loadData = async () => {
  try {
    const res = await getAllAdoptions()
    tableData.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const loadAvailableCats = async () => {
  try {
    const res = await getCatList()
    availableCats.value = res.data.filter(cat => cat.adoptionStatus === '可领养')
  } catch (error) {
    console.error(error)
  }
}

const handleAdd = () => {
  Object.keys(form).forEach(key => {
    form[key] = key === 'catId' ? null : ''
  })
  loadAvailableCats()
  dialogVisible.value = true
}

const handleView = (row) => {
  viewData.value = row
  viewDialogVisible.value = true
}

const handleReview = (row, status) => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  ElMessageBox.prompt('请输入审核意见', '审核', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /\S+/,
    inputErrorMessage: '审核意见不能为空'
  }).then(async ({ value }) => {
    await reviewAdoption(row.id, {
      status,
      reviewerId: user.id,
      reviewComment: value
    })
    ElMessage.success('审核成功')
    loadData()
  }).catch(() => {})
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除这条申请吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteAdoption(row.id)
      ElMessage.success('删除成功')
      loadData()
    })
    .catch(() => {})
}

const handleSubmit = async () => {
  await formRef.value.validate()
  try {
    await applyAdoption(form)
    ElMessage.success('申请提交成功')
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

.cat-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.cat-mini-avatar {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  object-fit: cover;
}
</style>
