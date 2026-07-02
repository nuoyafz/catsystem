<template>
  <div class="volunteer-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <div class="card-title">
            <el-icon class="card-title-icon"><UserFilled /></el-icon>
            <span>志愿者管理</span>
          </div>
          <el-button type="primary" @click="handleAdd" :icon="Plus">添加志愿者</el-button>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="username" label="用户名" width="120"></el-table-column>
        <el-table-column prop="realName" label="真实姓名" width="120"></el-table-column>
        <el-table-column prop="studentId" label="学号" width="120"></el-table-column>
        <el-table-column prop="department" label="院系"></el-table-column>
        <el-table-column prop="phone" label="联系电话" width="130"></el-table-column>
        <el-table-column prop="serviceHours" label="服务时长(h)" width="120">
          <template #default="scope">
            <el-tag type="success">{{ scope.row.serviceHours }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="joinTime" label="加入时间" width="180"></el-table-column>
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
        <el-form-item label="用户ID" prop="userId">
          <el-input-number v-model="form.userId" :min="1"></el-input-number>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="form.realName"></el-input>
        </el-form-item>
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="form.studentId"></el-input>
        </el-form-item>
        <el-form-item label="院系" prop="department">
          <el-input v-model="form.department"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
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
import { UserFilled, Plus } from '@element-plus/icons-vue'
import { getVolunteerList, addVolunteer, updateVolunteer, deleteVolunteer } from '../api/volunteer'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('添加志愿者')
const formRef = ref(null)

const form = reactive({
  id: null,
  userId: null,
  realName: '',
  studentId: '',
  department: '',
  phone: ''
})

const rules = {
  userId: [{ required: true, message: '请输入用户ID', trigger: 'blur' }],
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }]
}

const loadData = async () => {
  try {
    const res = await getVolunteerList()
    tableData.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleAdd = () => {
  dialogTitle.value = '添加志愿者'
  Object.keys(form).forEach(key => form[key] = key === 'id' ? null : '')
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑志愿者'
  Object.keys(row).forEach(key => {
    if (key in form) form[key] = row[key]
  })
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该志愿者吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteVolunteer(row.id)
      ElMessage.success('删除成功')
      loadData()
    })
    .catch(() => {})
}

const handleSubmit = async () => {
  await formRef.value.validate()
  try {
    if (form.id) {
      await updateVolunteer(form)
      ElMessage.success('更新成功')
    } else {
      await addVolunteer(form)
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
  color: var(--ios-green);
}
</style>
