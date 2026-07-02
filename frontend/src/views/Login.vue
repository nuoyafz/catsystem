<template>
  <div class="lg-login">
    <div class="lg-login-card liquid-glass">
      <div class="lg-login-head">
        <div class="lg-login-mark">
          <el-icon :size="28"><Star /></el-icon>
        </div>
        <h1 class="lg-login-title">校园流浪猫<br />投喂记录系统</h1>
        <p class="lg-login-sub">记录每一次温暖投喂，守护校园流浪猫</p>
      </div>

      <el-form :model="form" :rules="rules" ref="formRef" label-position="top" class="lg-login-form">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" size="large" :prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" size="large" show-password :prefix-icon="Lock" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" size="large" class="lg-login-btn">登 录</el-button>
        </el-form-item>
        <div class="lg-login-foot">
          没有账号？
          <el-button type="text" @click="showRegister = true">去注册</el-button>
        </div>
      </el-form>

      <div class="lg-login-hint">
        <el-icon><InfoFilled /></el-icon>
        默认管理员：admin / 123456
      </div>
    </div>

    <!-- 注册对话框 -->
    <el-dialog v-model="showRegister" title="用户注册" width="440px">
      <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" show-password></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="registerForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="registerForm.email"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRegister = false">取消</el-button>
        <el-button type="primary" @click="handleRegister">注册</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, InfoFilled, Star } from '@element-plus/icons-vue'
import { login, register } from '../api/user'

const router = useRouter()
const formRef = ref(null)
const registerFormRef = ref(null)
const showRegister = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const registerForm = reactive({
  username: '',
  password: '',
  phone: '',
  email: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const registerRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  try {
    const res = await login(form)
    localStorage.setItem('token', res.data.token)
    localStorage.setItem('user', JSON.stringify(res.data.user))
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    console.error(error)
  }
}

const handleRegister = async () => {
  await registerFormRef.value.validate()
  try {
    await register(registerForm)
    ElMessage.success('注册成功，请登录')
    showRegister.value = false
  } catch (error) {
    console.error(error)
  }
}
</script>

<style scoped>
.lg-login {
  width: 100%;
  min-height: 100dvh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 24px;
}

.lg-login-card {
  width: 420px;
  padding: 44px 40px 36px;
  border-radius: 0;
}

.lg-login-head {
  text-align: center;
  margin-bottom: 32px;
}

.lg-login-mark {
  width: 64px;
  height: 64px;
  margin: 0 auto 18px;
  border-radius: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--mc-primary);
  color: var(--mc-white);
  border: 3px solid var(--mc-dark);
  box-shadow:
    inset -4px -4px 0 0 var(--mc-primary-dark),
    inset 4px 4px 0 0 var(--mc-grass),
    0 8px 0 0 var(--mc-dark);
  image-rendering: pixelated;
}

.lg-login-title {
  font-size: 22px;
  font-weight: 700;
  color: var(--mc-text);
  line-height: 1.6;
  margin: 0;
  font-family: 'Press Start 2P', 'Microsoft YaHei', cursive;
}

.lg-login-sub {
  margin-top: 10px;
  font-size: 12px;
  color: var(--mc-text-light);
  letter-spacing: 0.5px;
  font-family: 'Press Start 2P', 'Microsoft YaHei', cursive;
  line-height: 1.6;
}

.lg-login-form :deep(.el-form-item__label) {
  font-weight: 700;
  color: var(--mc-text);
  font-family: 'Press Start 2P', cursive;
  font-size: 10px;
}

.lg-login-btn {
  width: 100%;
  height: 46px;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 4px;
  font-family: 'Press Start 2P', cursive;
}

.lg-login-foot {
  text-align: center;
  font-size: 10px;
  color: var(--mc-text-light);
  font-family: 'Press Start 2P', cursive;
}

.lg-login-hint {
  margin-top: 20px;
  padding: 10px 14px;
  border-radius: 0;
  background: var(--mc-info);
  border: 2px solid var(--mc-dark);
  color: var(--mc-white);
  font-size: 9px;
  display: flex;
  align-items: center;
  gap: 6px;
  justify-content: center;
  box-shadow:
    inset -2px -2px 0 0 #0288D1,
    inset 2px 2px 0 0 #81D4FA;
  font-family: 'Press Start 2P', cursive;
}
</style>
