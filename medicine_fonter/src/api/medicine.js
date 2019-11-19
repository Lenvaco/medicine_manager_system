import request from '@/utils/request'

export function add(data) {
    return request({
        url: 'api/medicine',
        method: 'post',
        data
    })
}
export function downloadMedicine() {
    return request({
        url: 'api/medicine/download',
        method: 'get',
        responseType: 'blob'
    })
}
export function edit(data) {
    return request({
        url: 'api/medicine',
        method: 'put',
        data
    })
}
export function del(id) {
    return request({
        url: 'api/medicine/' + id,
        method: 'delete'
    })
}

