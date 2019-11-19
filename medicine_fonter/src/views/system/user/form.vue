<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增用户' : '编辑用户'" append-to-body width="570px">
    <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="66px">
        <el-form-item label="账号" prop="username">
            <el-input :disabled="!isAdd" v-model="form.username"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
        <el-input v-model.trim="form.name"/>
      </el-form-item>
        <el-form-item label="状态" prop="enabled">
            <el-radio v-for="item in enabledTypeOptions" :key="item.id" v-model="form.enabled" :label="item.key" >{{item.display_name}}</el-radio>
        </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model.number="form.phone" />
      </el-form-item>
        <el-form-item label="性别" prop="enabled">
            <el-radio v-for="item in sexOptions" :key="item.id" v-model="form.sex" :label="item.key" >{{item.display_name}}</el-radio>
        </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item required label="部门">
        <treeselect v-model="deptId" :options="depts" style="width: 178px" placeholder="选择部门" @select="selectFun" />
      </el-form-item>
      <el-form-item required label="岗位">
        <el-select v-model="jobId" style="width: 178px" placeholder="请先选择部门">
          <el-option
            v-for="(item, index) in jobs"
            :key="item.name + index"
            :label="item.name"
            :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item required label="角色">
        <el-select v-model="roleIds" style="width: 450px;" multiple placeholder="请选择">
          <el-option
            v-for="(item, index) in roles"
            :disabled="level !== 1 && item.level <= level"
            :key="item.name + index"
            :label="item.name"
            :value="item.id"/>
        </el-select>
      </el-form-item>
       <el-form-item style="margin-bottom: 0px;" label="地址">
           <el-input
               type="textarea"
               style="width: 450px;"
               :rows="2"
               maxlength="90"
               show-word-limit
               placeholder="请输入具体地址"
               v-model="form.address"
               clearable>
           </el-input>
       </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>

  import { add, edit } from '@/api/user'
  import { getDepts } from '@/api/dept'
  import { getAll, getLevel } from '@/api/role'
  import { validateUsername } from '@/utils/validator'
  import { getAllJob } from '@/api/job'
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  export default {
    components: { Treeselect },
    props: {
      isAdd: {
        type: Boolean,
        required: true
      },
    },
    data() {
      const validPhone = (rule, value, callback) => {
        if (!value) {
          callback(new Error('请输入电话号码'))
        } else if (!this.isvalidPhone(value)) {
          callback(new Error('请输入正确的11位手机号码'))
        } else {
          callback()
        }
      }
      return {
        dialog: false, loading: false, form: { name: '', username: '', email: '', sex : '0', enabled: false, address: '', roles: [], job: { id: '' }, dept: { id: '' }, phone: null },
        roleIds: [], roles: [], depts: [], deptId: null, jobId: null, jobs: [], level: 3,
          sexOptions: [
              { key: '0', display_name: '男' },
              { key: '1', display_name: '女' }
          ],
          enabledTypeOptions: [
              { key: true, display_name: '激活' },
              { key: false, display_name: '锁定' }
          ],
        rules: {
            username: [
                {required: true, trigger: 'blur', message: '用户名不能为空'},
                {validator: validateUsername, trigger: 'blur'},
            ],
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 2, max: 14, message: '长度在 2 到 14 个字符', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
          ],
          phone: [
            { required: true, trigger: 'blur', validator: validPhone }
          ],

        }
      }
    },
    methods: {
      cancel() {
        this.resetForm()
      },
      doSubmit() {
        this.form.dept.id = this.deptId
        this.form.job.id = this.jobId
        this.$refs['form'].validate((valid) => {
          if (valid) {
            if (this.deptId === null || this.deptId === undefined) {
              this.$message({
                message: '部门不能为空',
                type: 'warning'
              })
            } else if (this.jobId === null) {
              this.$message({
                message: '岗位不能为空',
                type: 'warning'
              })
            } else if (this.roleIds.length === 0) {
              this.$message({
                message: '角色不能为空',
                type: 'warning'
              })
            } else {
              this.loading = true
              this.form.roles = []
              const _this = this
              this.roleIds.forEach(function(data, index) {
                const role = { id: data }
                _this.form.roles.push(role)
              })
              if (this.isAdd) {
                this.doAdd()
              } else this.doEdit()
            }
          } else {
            return false
          }
        })
      },
      doAdd() {
        add(this.form).then(res => {
          this.resetForm()
          this.$notify({
            title: '添加成功',
            message: '默认密码：abc123456',
            type: 'success',
            duration: 2500
          })
          this.loading = false
          this.$parent.init()
        }).catch(err => {
          this.loading = false
          console.log(err.response.data.message)
        })
      },
      doEdit() {
        edit(this.form).then(res => {
          this.resetForm()
          this.$notify({
            title: '修改成功',
            type: 'success',
            duration: 2500
          })
          this.loading = false
          this.$parent.init()
        }).catch(err => {
          this.loading = false
          console.log(err.response.data.message)
        })
      },
      resetForm() {
        this.dialog = false
        this.$refs['form'].resetFields()
        this.deptId = null
        this.jobId = null
        this.roleIds = []
        this.form = { username: '', email: '', sex: '0', enabled: false, roles: [], job: { id: '' }, dept: { id: '' }, phone: null }
      },
      getRoles() {
        getAll().then(res => {
          this.roles = res.content
        }).catch(err => {
          console.log(err.response.data.message)
        })
      },
      getJobs(id) {
        getAllJob(id).then(res => {
          this.jobs = res.content
        }).catch(err => {
          console.log(err.response.data.message)
        })
      },
      getDepts() {
        getDepts().then(res => {
          this.depts = res.content
        })
      },
      isvalidPhone(str) {
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        return reg.test(str)
      },
      selectFun(node, instanceId) {
        this.getJobs(node.id)
      },
      getRoleLevel() {
        getLevel().then(res => {
          this.level = res.level
        }).catch(err => {
          console.log(err.response.data.message)
        })
      }
    }
  }
</script>

<style scoped>

</style>
