import request from '@/utils/request'

export function getCountData() {
    return request({
        url: 'api/chart/count',
        method: 'get'
    })
}
export function getSaleData() {
    return request({
        url: 'api/chart/sale',
        method: 'get'
    })
}
export function getMedicineData() {
    return request({
        url: 'api/chart/medicine',
        method: 'get'
    })
}

