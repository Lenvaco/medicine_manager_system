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
    console.log(data)
    return request({
        url: 'api/medicine/' + data.id,
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

