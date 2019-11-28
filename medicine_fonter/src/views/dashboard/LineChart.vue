<template>

    <div :style="{height:height,width:width}">
        <el-button type="primary" @click="changeType">切换图表类型</el-button>
        <ve-chart height="350px" width="97%" :data="chartData" :settings="chartSettings"></ve-chart>
    </div>
</template>

<script>
    import { debounce } from '@/utils'
    import { getSaleData } from '@/api/dashboard'

    export default {
        props: {
            width: {
                type: String,
                default: '100%'
            },
            height: {
                type: String,
                default: '350px'
            },
        },
        data() {
            this.typeArr = ['line', 'histogram']
            this.index = 0
            return {
                chartData: {
                    columns: ['date', 'saleCount'],
                    rows: [
                    ]
                },
                chartSettings: {
                    labelMap:{
                        date: '年月',
                        saleCount: '月销售量'
                    },
                    type: this.typeArr[this.index],
                }

            }
        },
        mounted() {

            getSaleData().then(res => {
                this.chartData.rows = res
            })
        },
        methods: {
            changeType(){
                this.index++
                if (this.index >= this.typeArr.length) { this.index = 0 }
                this.chartSettings.type = this.typeArr[this.index]
            }
        }
    }
</script>
