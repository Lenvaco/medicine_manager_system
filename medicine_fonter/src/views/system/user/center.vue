<template>
  <div class="app-containe ">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="8" :lg="6" :xl="5" style="margin-bottom: 10px; width: 100%">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div style="text-align: center">
              <div class="avatar-img">
                <img :src="avatar"   class="avatar">
              </div>
            </div>
            <ul class="user-info">
              <li><svg-icon icon-class="user" /> 用户名称 <div class="user-right">{{ user.name }}</div></li>
              <li><svg-icon icon-class="phone" /> 手机号码 <div class="user-right">{{ user.phone }}</div></li>
              <li><svg-icon icon-class="email" /> 用户邮箱 <div class="user-right">{{ user.email }}</div></li>
              <li><svg-icon icon-class="role" /> 部门岗位 <div class="user-right"> {{ user.deptName }} / {{ user.jobName }}</div></li>
              <li><svg-icon icon-class="date" /> 创建日期 <div class="user-right">{{ parseTime(user.createTime) }}</div></li>
              <li>
                <svg-icon icon-class="security" /> 安全设置
                <div class="user-right">
                  <a @click="$refs.pass.dialog = true">修改密码</a>
                </div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
    </el-row>
      <updatePass ref="pass"/>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { regEmail } from '@/utils/index'
  import updatePass from './center/updatePass'
  import { getToken } from '@/utils/auth'
  import store from '@/store'
  import { parseTime } from '@/utils/index'
  import Avatar from '@/assets/avatar/avatar.png'
  export default {
    name: 'Center',
    components: { updatePass },
    data() {
      return {
        avatar: Avatar,
        headers: {
          'Authorization': 'Bearer ' + getToken()
        }
      }
    },
    computed: {
      ...mapGetters([
        'user',
      ])
    },
    created() {
      store.dispatch('GetInfo').then(() => {})
    },
    methods: {
      parseTime,
      formatEmail(mail) {
        return regEmail(mail)
      },


    }
  }
</script>

<style rel="stylesheet/scss" lang="scss">
  .avatar-img{
    display: inline-block;
    line-height: 120px;
    text-align: center
  }

  .avatar {
    width: 120px;
    height: 120px;
    display: block;
    border-radius: 50%
  }
  .user-info {
    padding-left: 0px;
    list-style: none;
    li{
      border-bottom: 1px solid #F0F3F4;
      border-top: 1px solid #F0F3F4;
      padding: 11px 0px;
      font-size: 13px;
    }
    .user-right {
      float: right;

      a{
        color: #317EF3;
      }
    }
  }
</style>
