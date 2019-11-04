import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/users',
    method: 'post',
    data
  })
}
export function downloadUser() {
  return request({
    url: 'api/users/download',
    method: 'get',
    responseType: 'blob'
  })
}

export function del(id) {
  return request({
    url: 'api/users/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/users',
    method: 'put',
    data
  })
}

export function changePsd(user) {
  const data = {
    oldPassword: user.oldPass,
    newPassword: user.newPass
  }
  return request({
    url: 'api/users/changePsd',
    method: 'post',
    data
  })
}

export function updateEmail(code, data) {
  return request({
    url: 'api/users/updateEmail/' + code,
    method: 'post',
    data
  })
}

