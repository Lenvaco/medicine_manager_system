<template>

    <div :style="{height:height,width:width}">
        <ve-pie  :data="chartData" :settings="chartSettings"></ve-pie>
    </div>
</template>

<script>
    import { getMedicineData } from '@/api/dashboard'

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

            return {
                chartData: {
                    columns: ['name',  'saleCount'],
                    rows: [
                    ]
                },
                chartSettings: {
                    labelMap:{
                        name: '药品名',
                        saleCount: '比例'
                    },

                }

            }
        },
        mounted() {

            getMedicineData().then(res => {
                this.chartData.rows = res
            })
        },
        methods: {
        }
    }
</script>
