<template>
  <div class="app-container">
      <!--form 组件-->
      <userForm ref="form" :is-add="isAdd"/>
      <el-row :gutter="20">
          <!--部门数据-->
          <el-col :xs="9" :sm="6" :md="4" :lg="4" :xl="4">
              <div class="head-container">
                  <el-input v-model="deptName" clearable placeholder="输入部门搜索" prefix-icon="el-icon-search" style="width: 100%;" class="filter-item" @input="getDeptDatas"/>
              </div>
              <el-tree :data="depts" :props="defaultProps" :expand-on-click-node="false" default-expand-all @node-click="handleNodeClick"/>
          </el-col>
          <!--用户数据-->
          <el-col :xs="15" :sm="18" :md="20" :lg="20" :xl="20">
        <!--工具栏-->
        <div class="head-container">
        <!-- 搜索 -->
            <el-input v-model="query.blurry" clearable placeholder="输入用户名或者邮箱搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
            <el-select v-model="query.enabled" clearable placeholder="状态" class="filter-item" style="width: 90px" @change="toQuery">
                <el-option v-for="item in enabledTypeOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
            </el-select>
          <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
          <!-- 新增 -->
          <div v-permission="['ADMIN','USER_ALL','USER_CREATE']" style="display: inline-block;margin: 0px 2px;">
            <el-button
              class="filter-item"
              size="mini"
              type="primary"
              icon="el-icon-plus"
              @click="add">新增</el-button>
          </div>
          <!-- 导出 -->
          <div style="display: inline-block;">
            <el-button
              v-permission="['ADMIN','USER_ALL','USER_SELECT']"
              :loading="downloadLoading"
              size="mini"
              class="filter-item"
              type="warning"
              icon="el-icon-download"
              @click="download">导出</el-button>
          </div>
        </div>
        <!--表格渲染-->
        <el-table v-loading="loading" :data="data" size="small" style="width: 100%;" max-width="250">
          <af-table-column prop="id" fixed label="编号"/>
          <af-table-column prop="name" label="姓名"/>
          <af-table-column prop="username" label="用户名"/>
          <af-table-column prop="phone" label="电话"/>
          <af-table-column label="性别">
              <template slot-scope="scope">
                 <span v-if="scope.row.sex == '1'">女</span>
                 <span v-else>男</span>
              </template>
          </af-table-column>
          <af-table-column :show-overflow-tooltip="true" prop="email" label="邮箱"/>

            <af-table-column  label="状态" align="center">
                <template slot-scope="scope">
                    <div v-for="item in enabledTypeOptions" :key="item.id">
                        <el-tag v-if="scope.row.enabled === item.key" :type="scope.row.enabled ? '' : 'info'">{{ item.display_name }}</el-tag>
                    </div>
                </template>
            </af-table-column>
          <af-table-column label="职位">
            <template slot-scope="scope">
              <div>{{ scope.row.dept.name }} / {{ scope.row.job.name }}</div>
            </template>
          </af-table-column>
          <af-table-column prop="address" label="地址"></af-table-column>

          <af-table-column :show-overflow-tooltip="true" prop="createTime" label="创建日期">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </af-table-column>
          <af-table-column fixed v-if="checkPermission(['ADMIN','USER_ALL','USER_EDIT','USER_DELETE'])" label="操作" width="125" align="center" fixed="right">
            <template slot-scope="scope">
              <el-button v-permission="['ADMIN','USER_ALL','USER_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"/>
              <el-popover
                v-permission="['ADMIN','USER_ALL','USER_DELETE']"
                :ref="scope.row.id"
                placement="top"
                width="180">
                <p>确定删除本条数据吗？</p>
                <div style="text-align: right; margin: 0">
                  <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                  <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
                </div>
                <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
              </el-popover>
            </template>
          </af-table-column>
        </el-table>
        <!--分页组件-->
        <el-pagination
          :total="total"
          :current-page="page"
          style="margin-top: 8px;"
          layout="total, prev, pager, next, sizes"
          @size-change="sizeChange"
          @current-change="pageChange"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import checkPermission from '@/utils/permission'
  import initData from '@/mixin/initData'
  import { del, downloadUser } from '@/api/user'
  import { parseTime, downloadFile } from '@/utils/index'
  import { getDepts } from '@/api/dept'
  import userForm from './form'
  export default {
    name: 'User',
    components: { userForm },
    mixins: [initData],
    data() {
      return {
        height: document.documentElement.clientHeight - 180 + 'px;', isAdd: false,
        delLoading: false, deptName: '', depts: [], deptId: null,
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        downloadLoading: false,
        sexOptions: [
          { key: '0', display_name: '男' },
          { key: '1', display_name: '女' }
        ],
        enabledTypeOptions: [
          { key: true, display_name: '激活' },
          { key: false, display_name: '锁定' }
        ]
      }
    },
    created() {
      this.getDeptDatas()
      this.$nextTick(() => {
        this.init()
        // 加载数据字典
        // this.getDict('user_status')
      })
    },
    mounted: function() {
      const that = this
      window.onresize = function temp() {
        that.height = document.documentElement.clientHeight - 180 + 'px;'
      }
    },
    methods: {
      parseTime,
      checkPermission,
      beforeInit() {
        this.url = 'api/users'
        const query = this.query
        const blurry = query.blurry
        this.params = { page: this.page, size: this.size, deptId: this.deptId }
        if (blurry) { this.params['blurry'] = blurry }
          const enabled = query.enabled
          if (enabled !== '' && enabled !== null) { this.params['enabled'] = enabled }
        return true
      },
      subDelete(id) {
        this.delLoading = true
        del(id).then(res => {
          this.delLoading = false
          this.$refs[id].doClose()
          this.dleChangePage()
          this.init()
          this.$notify({
            title: '删除成功',
            type: 'success',
            duration: 2500
          })
        }).catch(err => {
          this.delLoading = false
          this.$refs[id].doClose()
          console.log(err.response.data.message)
        })
      },
      getDeptDatas() {
        const sort = 'id'
        const params = { sort: sort }
        if (this.deptName) { params['name'] = this.deptName }
        getDepts(params).then(res => {
          this.depts = res.content
        })
      },
      handleNodeClick(data) {
        if (data.pid === 0) {
          this.deptId = null
        } else {
          this.deptId = data.id
        }
        this.init()
      },
      add() {
        this.isAdd = true
        this.$refs.form.getDepts()
        this.$refs.form.getRoles()
        this.$refs.form.getRoleLevel()
        this.$refs.form.dialog = true
      },
      // 导出
      download() {
        this.downloadLoading = true
        downloadUser().then(result => {
          downloadFile(result, '用户列表', 'xlsx')
          this.downloadLoading = false
        }).catch(() => {
          this.downloadLoading = false
        })
      },
      // 数据转换
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => {
          if (j === 'createTime' || j === 'lastPasswordResetTime') {
            return parseTime(v[j])
          } else if (j === 'enabled') {
            return parseTime(v[j]) ? '启用' : '禁用'
          } else {
            return v[j]
          }
        }))
      },
      edit(data) {
          console.log(data)
        this.isAdd = false
        const _this = this.$refs.form
        _this.getRoles()
        _this.getDepts()
        _this.getRoleLevel()
        _this.roleIds = []
        _this.form = { id: data.id, username: data.username,name: data.name, phone: data.phone, email: data.email, sex: data.sex, enabled: data.enabled, address: data.address, roles: [], dept: { id: data.dept.id }, job: { id: data.job.id }}
        data.roles.forEach(function(data, index) {
          _this.roleIds.push(data.id)
        })
        _this.deptId = data.dept.id
        _this.jobId = data.job.id
        _this.getJobs(_this.deptId)
        _this.dialog = true
      }
    }
  }
</script>

<style scoped>
</style>
