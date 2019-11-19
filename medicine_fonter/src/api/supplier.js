import request from '@/utils/request'

export function add(data) {
    return request({
        url: 'api/supplier',
        method: 'post',
        data
    })
}
export function downloadSupplier() {
    return request({
        url: 'api/supplier/download',
        method: 'get',
        responseType: 'blob'
    })
}
export function edit(data) {
    return request({
        url: 'api/supplier',
        method: 'put',
        data
    })
}
export function del(id) {
    return request({
        url: 'api/supplier/' + id,
        method: 'delete'
    })
}

