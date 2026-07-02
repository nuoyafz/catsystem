<template>
  <div class="cat-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <div class="card-title">
            <el-icon class="card-title-icon"><Star /></el-icon>
            <span>流浪猫管理</span>
          </div>
          <el-button type="primary" @click="handleAdd" :icon="Plus">添加猫咪</el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="猫咪名字">
          <el-input v-model="searchForm.name" placeholder="请输入名字"></el-input>
        </el-form-item>
        <el-form-item label="健康状况">
          <el-select v-model="searchForm.healthStatus" placeholder="请选择" clearable>
            <el-option label="健康" value="健康"></el-option>
            <el-option label="生病" value="生病"></el-option>
            <el-option label="受伤" value="受伤"></el-option>
            <el-option label="康复中" value="康复中"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格 -->
      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="70"></el-table-column>
        <el-table-column label="照片" width="80">
          <template #default="scope">
            <img v-if="scope.row.photoUrl" :src="scope.row.photoUrl" class="cat-avatar" :alt="scope.row.name" />
            <div v-else class="cat-avatar cat-avatar-placeholder"></div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名字" width="90"></el-table-column>
        <el-table-column prop="breed" label="品种" width="110"></el-table-column>
        <el-table-column prop="color" label="毛色" width="90"></el-table-column>
        <el-table-column prop="gender" label="性别" width="70"></el-table-column>
        <el-table-column prop="age" label="年龄" width="70"></el-table-column>
        <el-table-column prop="healthStatus" label="健康状况" width="100">
          <template #default="scope">
            <el-tag :type="getHealthTag(scope.row.healthStatus)">
              {{ scope.row.healthStatus }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="常出没地点"></el-table-column>
        <el-table-column prop="sterilized" label="绝育" width="70">
          <template #default="scope">
            {{ scope.row.sterilized === 1 ? '是' : '否' }}
          </template>
        </el-table-column>
        <el-table-column label="标签" width="200">
          <template #default="scope">
            <div class="tag-list">
              <el-tag 
                v-for="tag in scope.row.tags" 
                :key="tag.id" 
                :color="tag.color" 
                size="small"
                style="margin-right: 4px; margin-bottom: 4px;"
              >
                {{ tag.name }}
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="猫咪名字" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="品种" prop="breed">
          <el-input v-model="form.breed"></el-input>
        </el-form-item>
        <el-form-item label="毛色" prop="color">
          <el-input v-model="form.color"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="公">公</el-radio>
            <el-radio label="母">母</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="0" :max="30"></el-input-number>
        </el-form-item>
        <el-form-item label="健康状况" prop="healthStatus">
          <el-select v-model="form.healthStatus">
            <el-option label="健康" value="健康"></el-option>
            <el-option label="生病" value="生病"></el-option>
            <el-option label="受伤" value="受伤"></el-option>
            <el-option label="康复中" value="康复中"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="常出没地点" prop="location">
          <el-input v-model="form.location"></el-input>
        </el-form-item>
        <el-form-item label="是否绝育" prop="sterilized">
          <el-radio-group v-model="form.sterilized">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="照片URL" prop="photoUrl">
          <el-input v-model="form.photoUrl" placeholder="请输入猫咪照片URL"></el-input>
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-select v-model="form.tags" multiple placeholder="请选择标签" style="width: 100%">
            <el-option 
              v-for="tag in allTags" 
              :key="tag.id" 
              :label="tag.name" 
              :value="tag"
            >
              <span style="float: left">{{ tag.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ tag.description }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3"></el-input>
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
import { Star, Plus } from '@element-plus/icons-vue'
import { getCatPage, addCat, updateCat, deleteCat } from '../api/cat'
import { getAllTags } from '../api/catTag'

const searchForm = reactive({ name: '', healthStatus: '' })
const tableData = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('添加猫咪')
const formRef = ref(null)
const allTags = ref([])

const form = reactive({
  id: null,
  name: '',
  breed: '',
  color: '',
  gender: '公',
  age: 1,
  healthStatus: '健康',
  location: '',
  sterilized: 0,
  photoUrl: '',
  tags: [],
  description: ''
})

const rules = {
  name: [{ required: true, message: '请输入猫咪名字', trigger: 'blur' }],
  healthStatus: [{ required: true, message: '请选择健康状况', trigger: 'change' }]
}

const getHealthTag = (status) => {
  const map = { '健康': 'success', '生病': 'danger', '受伤': 'warning', '康复中': 'info' }
  return map[status] || 'info'
}

const loadData = async () => {
  try {
    const res = await getCatPage({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      name: searchForm.name,
      healthStatus: searchForm.healthStatus
    })
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const loadTags = async () => {
  try {
    const res = await getAllTags()
    allTags.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const resetSearch = () => {
  searchForm.name = ''
  searchForm.healthStatus = ''
  pageNum.value = 1
  loadData()
}

const handleAdd = () => {
  dialogTitle.value = '添加猫咪'
  Object.keys(form).forEach(key => {
    form[key] = key === 'gender' ? '公' : key === 'age' ? 1 : key === 'healthStatus' ? '健康' : key === 'sterilized' ? 0 : key === 'id' ? null : key === 'tags' ? [] : ''
  })
  loadTags()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑猫咪'
  Object.keys(row).forEach(key => {
    if (key in form) form[key] = row[key]
  })
  loadTags()
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除这只猫咪吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteCat(row.id)
      ElMessage.success('删除成功')
      loadData()
    })
    .catch(() => {})
}

const handleSubmit = async () => {
  await formRef.value.validate()
  try {
    if (form.id) {
      await updateCat(form)
      ElMessage.success('更新成功')
    } else {
      await addCat(form)
      ElMessage.success('添加成功')
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

.cat-avatar {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  object-fit: cover;
  border: 2px solid rgba(255, 255, 255, 0.5);
  box-shadow: 0 2px 8px rgba(160, 120, 60, 0.15);
}

.cat-avatar-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(232, 163, 61, 0.15);
  font-size: 24px;
}

.tag-list {
  display: flex;
  flex-wrap: wrap;
  gap: 2px;
}
</style>
