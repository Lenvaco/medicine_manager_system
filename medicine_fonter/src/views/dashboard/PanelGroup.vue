<template>
    <el-row :gutter="40" class="panel-group">
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-people">
                    <svg-icon icon-class="purchaseCount" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                    <div class="card-panel-text">采购成交额</div>
                    <count-to :start-val="0" :end-val="count.purchasePrice" :duration="2600" class="card-panel-num" />
                </div>
            </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-message">
                    <svg-icon icon-class="supplierCount" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                    <div class="card-panel-text">合作供应商</div>
                    <count-to :start-val="0" :end-val="count.supplierCount" :duration="3000" class="card-panel-num" />
                </div>
            </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-money">
                    <svg-icon icon-class="saleCount" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                    <div class="card-panel-text">销售成交额</div>
                    <count-to :start-val="0" :end-val="count.salePrice" :duration="3200" class="card-panel-num" />
                </div>
            </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-shopping">
                    <svg-icon icon-class="customerCount" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                    <div class="card-panel-text">顾客数</div>
                    <count-to :start-val="0" :end-val="count.customerCount" :duration="3600" class="card-panel-num" />
                </div>
            </div>
        </el-col>
    </el-row>
</template>

<script>
    import CountTo from 'vue-count-to'
    import { getCountData } from '@/api/dashboard'
    export default {
        components: {
            CountTo
        },
        data() {
            return {
                count: { purchasePrice: 0, supplierCount: 0, salePrice: 0, customerCount: 0 }
            }
        },
        mounted() {
            getCountData().then(res => {
                console.log(res)
                this.count.purchasePrice = res.purchasePrice
                this.count.supplierCount = res.supplierCount
                this.count.salePrice = res.salePrice
                this.count.customerCount = res.customerCount
            })
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .panel-group {
        margin-top: 18px;
        .card-panel-col{
            margin-bottom: 32px;
        }
        .card-panel {
            height: 108px;
            font-size: 12px;
            position: relative;
            overflow: hidden;
            color: #666;
            background: #fff;
            box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
            border-color: rgba(0, 0, 0, .05);
            .icon-people {
                color: #40c9c6;
            }
            .icon-message {
                color: #36a3f7;
            }
            .icon-money {
                color: #f4516c;
            }
            .icon-shopping {
                color: #34bfa3
            }
            .card-panel-icon-wrapper {
                float: left;
                margin: 14px 0 0 14px;
                padding: 16px;
                transition: all 0.38s ease-out;
                border-radius: 6px;
            }
            .card-panel-icon {
                float: left;
                font-size: 48px;
            }
            .card-panel-description {
                float: right;
                font-weight: bold;
                margin: 26px;
                margin-left: 0px;
                .card-panel-text {
                    line-height: 18px;
                    color: rgba(0, 0, 0, 0.45);
                    font-size: 16px;
                    margin-bottom: 12px;
                }
                .card-panel-num {
                    font-size: 20px;
                }
            }
        }
    }
</style>
