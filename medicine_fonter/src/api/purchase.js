import request from '@/utils/request'

export function add(data) {
    return request({
        url: 'api/purchase',
        method: 'post',
        data
    })
}
export function downloadPurchaseRecord() {
    return request({
        url: 'api/purchase/download',
        method: 'get',
        responseType: 'blob'
    })
}
export function edit(data) {
    return request({
        url: 'api/purchase/' + data.id,
        method: 'put',
        data
    })
}
export function del(id) {
    return request({
        url: 'api/purchase/' + id,
        method: 'delete'
    })
}

