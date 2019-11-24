import request from '@/utils/request'

export function add(data) {
    return request({
        url: 'api/customer',
        method: 'post',
        data
    })
}
export function downloadCustomer() {
    return request({
        url: 'api/customer/download',
        method: 'get',
        responseType: 'blob'
    })
}
export function edit(data) {
    console.log(data)
    return request({
        url: 'api/customer/' + data.id,
        method: 'put',
        data
    })
}
export function del(id) {
    return request({
        url: 'api/customer/' + id,
        method: 'delete'
    })
}

