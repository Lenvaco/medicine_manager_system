import request from '@/utils/request'

export function login(username, password, captcha, uuid) {
  return request({
    url: 'auth/login',
    method: 'post',
    data: {
      username,
      password,
      captcha,
      uuid
    }
  })
}

export function getInfo() {
  return request({
    url: 'auth/info',
    method: 'get'
  })
}

export function getCaptchaImg() {
  return request({
    url: 'auth/captcha',
    method: 'get'
  })
}
