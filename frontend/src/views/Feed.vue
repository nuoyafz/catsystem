<template>
  <div class="feed-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <div class="card-title">
            <el-icon class="card-title-icon"><Document /></el-icon>
            <span>投喂记录</span>
          </div>
          <el-button type="primary" @click="handleAdd" :icon="Plus">添加记录</el-button>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="catName" label="猫咪" width="100"></el-table-column>
        <el-table-column prop="realName" label="投喂人" width="100"></el-table-column>
        <el-table-column prop="feedTime" label="投喂时间" width="180"></el-table-column>
        <el-table-column prop="foodType" label="食物类型" width="100">
          <template #default="scope">
            <el-tag>{{ scope.row.foodType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="投喂量" width="100"></el-table-column>
        <el-table-column prop="location" label="投喂地点"></el-table-column>
        <el-table-column prop="note" label="备注"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="猫咪" prop="catId">
          <el-select v-model="form.catId" placeholder="请选择猫咪">
            <el-option v-for="cat in catList" :key="cat.id" :label="cat.name" :value="cat.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="投喂时间" prop="feedTime">
          <el-date-picker v-model="form.feedTime" type="datetime" placeholder="选择时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="食物类型" prop="foodType">
          <el-select v-model="form.foodType">
            <el-option label="猫粮" value="猫粮"></el-option>
            <el-option label="罐头" value="罐头"></el-option>
            <el-option label="零食" value="零食"></el-option>
            <el-option label="其他" value="其他"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="投喂量" prop="amount">
          <el-select v-model="form.amount">
            <el-option label="少量" value="少量"></el-option>
            <el-option label="适量" value="适量"></el-option>
            <el-option label="多量" value="多量"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="投喂地点" prop="location">
          <el-input v-model="form.location"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.note" type="textarea"></el-input>
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
import { getFeedList, addFeed, updateFeed, deleteFeed } from '../api/feed'
import { getCatList } from '../api/cat'

const tableData = ref([])
const catList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('添加投喂记录')
const formRef = ref(null)
const user = JSON.parse(localStorage.getItem('user') || '{}')

const form = reactive({
  id: null,
  userId: user.id,
  catId: null,
  feedTime: '',
  foodType: '猫粮',
  amount: '适量',
  location: '',
  note: ''
})

const rules = {
  catId: [{ required: true, message: '请选择猫咪', trigger: 'change' }],
  feedTime: [{ required: true, message: '请选择投喂时间', trigger: 'change' }],
  foodType: [{ required: true, message: '请选择食物类型', trigger: 'change' }]
}

const loadData = async () => {
  try {
    const res = await getFeedList()
    tableData.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const loadCatList = async () => {
  try {
    const res = await getCatList()
    catList.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleAdd = () => {
  dialogTitle.value = '添加投喂记录'
  form.id = null
  form.catId = null
  form.feedTime = ''
  form.foodType = '猫粮'
  form.amount = '适量'
  form.location = ''
  form.note = ''
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑投喂记录'
  Object.keys(row).forEach(key => {
    if (key in form) form[key] = row[key]
  })
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除这条记录吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteFeed(row.id)
      ElMessage.success('删除成功')
      loadData()
    })
    .catch(() => {})
}

const handleSubmit = async () => {
  await formRef.value.validate()
  try {
    if (form.id) {
      await updateFeed(form)
      ElMessage.success('更新成功')
    } else {
      await addFeed(form)
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
  loadCatList()
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
</style>
