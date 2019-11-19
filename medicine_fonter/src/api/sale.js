import request from '@/utils/request'

export function add(data) {
    return request({
        url: 'api/sale',
        method: 'post',
        data
    })
}
export function downloadSupplier() {
    return request({
        url: 'api/sale/download',
        method: 'get',
        responseType: 'blob'
    })
}
export function edit(data) {
    return request({
        url: 'api/sale',
        method: 'put',
        data
    })
}
export function del(id) {
    return request({
        url: 'api/sale/' + id,
        method: 'delete'
    })
}

