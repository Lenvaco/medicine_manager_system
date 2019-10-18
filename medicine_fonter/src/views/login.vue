<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" label-position="left" label-width="0px" class="login-form">
      <h3 class="title">医药管理系统</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user-login" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码" @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="captcha">
        <el-input v-model="loginForm.captcha" auto-complete="off" placeholder="验证码" style="width: 63%" @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="captcha" class="el-input__icon input-icon"/>
        </el-input>
        <div class="login-code">
          <img :src="captchaUrl" @click="getCaptcha">
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住我</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="primary" style="width:100%;" @click.native.prevent="handleLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>正在登录...</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import { getCaptchaImg } from '@/api/login'
  import { validateUsername, validatePassword } from '@/utils/validator'
  import Config from '@/config'
  import  crypt from '@/utils/crypt'
  import Cookies from 'js-cookie'
  export default {
      name: 'Login',
      data() {
        return {
          captchaUrl: '',
          loginForm: {
            username: '',
            password: '',
            captcha: '',
            rememberMe: false,
            uuid: ''
          },
          loginRules: {
            username: [
              {required: true, trigger: 'blur', message: '用户名不能为空'},
              {validator: validateUsername, trigger: 'blur'},
            ],
            password: [
              {required: true, trigger: 'blur', message: '密码不能为空'},
              {validator: validatePassword, trigger: 'blur'},
            ],
            captcha: [{required: true, trigger: 'change', message: '验证码不能为空'}]
          },
          loading: false,
          redirect: undefined
        }
      },
      watch: {
        $route: {
          handler: function (route) {
            this.redirect = route.query && route.query.redirect
          },
          immediate: true
        }
      },
      created() {
        this.getCookie()
        this.getCaptcha();
      },
      methods: {
        getCaptcha() {
          getCaptchaImg().then(res => {
            this.loginForm.uuid = res.uuid
            this.captchaUrl = 'data:image/gif;base64,' + res.img
          })
        },
        getCookie() {
          let user = Cookies.get('SYSTEM_USER')
          if(!(user === undefined)) {
            user = crypt.decrypt(user).split('&')
            this.loginForm = {
              username: user[0],
              password: user[1],
              captcha: '',
            }
          }
        },
        handleLogin() {
          this.$refs.loginForm.validate(valid => {
            const user = {
              username: this.loginForm.username,
              password: this.loginForm.password,
              uuid: this.loginForm.uuid,
              captcha: this.loginForm.captcha,
              rememberMe: this.loginForm.rememberMe
            }
            if (valid) {
              this.loading = true
              if(user.rememberMe) {
                Cookies.set('SYSTEM_USER', crypt.encrypt(user.username + '&' + user.password), {expires: Config.passCookieExpires})
              } else {
                Cookies.remove('SYSTEM_USER')
              }
              this.$store.dispatch('login', user).then(() => {
                this.loading = false
                this.$router.push({path: this.redirect || '/'})
              }).catch(() => {
                this.loading = false
                this.getCaptcha()
              })
            } else {
              return false
            }
          })
        }
      },
    }
</script>

<style rel="stylesheet/scss" lang="scss">
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    height: 100%;
    width: 100%;
    background-image:url(	'../assets/image/login_bg.jpg');
    background-size: cover;
  }
  .title {
    margin: 0px auto 30px auto;
    text-align: center;
    color: #707070;
  }

  .login-form {
    border-radius: 8px;
    box-shadow: 3px 3px 5px  gray;
    background: #ffffff;
    width: 350px;
    padding: 25px 25px 5px 25px;
    .el-input {
      height: 38px;
      input {
        height: 38px;
      }
    }
    .input-icon{
      height: 39px;width: 14px;margin-left: 2px;
    }
  }
  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .login-code {
    width: 33%;
    display: inline-block;
    height: 38px;
    float: right;
    img{
      width: 100%;
      height: 100%;
      cursor: pointer;
      vertical-align:middle
    }
  }
</style>

