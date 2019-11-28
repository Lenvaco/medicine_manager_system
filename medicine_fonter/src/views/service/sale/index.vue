<template>
    <div class="app-container">
        <!--工具栏-->
        <div class="head-container">
            <!-- 搜索 -->
            <el-input v-model="query.value" clearable placeholder="输入药品名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
            <el-date-picker
                v-model="query.date"
                type="datetimerange"
                range-separator="至"
                class="el-range-editor--small demonstration"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="height: 30.5px;"
                start-placeholder="销售起始时间"
                end-placeholder="销售结束时间"/>
            <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
            <!-- 新增 -->
            <div v-permission="['ADMIN','SALE_ALL','SALE_CREATE']" style="display: inline-block;margin: 0px 2px;">
                <el-button
                    class="filter-item"
                    size="mini"
                    type="primary"
                    icon="el-icon-plus"
                    @click="add">新增</el-button>
            </div>
            <!-- 导出 -->
            <div style="display: inline-block;">
                <el-button
                    v-permission="['ADMIN','SALE_ALL','SALE_SELECT']"
                    :loading="downloadLoading"
                    size="mini"
                    class="filter-item"
                    type="warning"
                    icon="el-icon-download"
                    @click="download">导出</el-button>
            </div>
        </div>
        <!--表单组件-->
        <saleForm ref="form" :is-add="isAdd"/>
        <!--表格渲染-->
        <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
            <el-table-column prop="id" width="160px" label="销售编号"/>
            <el-table-column label="药品名" >
                <template slot-scope="scope">
                    <span>{{scope.row.medicine.name}}</span>
                </template>
            </el-table-column>
            <el-table-column label="顾客名" >
                <template slot-scope="scope">
                    <span>{{scope.row.customer.name}}</span>
                </template>
            </el-table-column>
            <el-table-column label="销售员" >
                <template slot-scope="scope">
                    <span>{{scope.row.user.name}}</span>
                </template>
            </el-table-column>
            <el-table-column prop="saleCount" label="数目" />
            <el-table-column prop="salePrice" label="单价" />
            <el-table-column prop="sumPrice" label="总价" />
            <el-table-column :show-overflow-tooltip="true" label="销售时间">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.saleTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column v-if="checkPermission(['ADMIN','SALE_ALL','SALE_EDIT','SALE_DELETE'])" label="操作" width="130px" align="center" fixed="right">
                <template slot-scope="scope">
                    <el-button v-permission="['ADMIN','SALE_ALL','SALE_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"/>
                    <el-popover
                        v-permission="['ADMIN','SALE_ALL','SALE_DELETE']"
                        :ref="scope.row.id"
                        placement="top"
                        width="180">
                        <p>确定删除本条数据吗？</p>
                        <div style="text-align: right; margin: 0">
                            <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                            <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
                        </div>
                        <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
                    </el-popover>
                </template>
            </el-table-column>
        </el-table>
        <!--分页组件-->
        <el-pagination
            :total="total"
            :current-page="page"
            style="margin-top: 8px;"
            layout="total, prev, pager, next, sizes"
            @size-change="sizeChange"
            @current-change="pageChange"/>
    </div>
</template>

<script>
    import checkPermission from '@/utils/permission'
    import initData from '@/mixin/initData'
    import { del, downloadSaleRecord } from '@/api/sale'
    import { parseTime, downloadFile } from '@/utils/index'
    import saleForm from './form'
    export default {
        name: 'Sale',
        components: { saleForm },
        mixins: [initData],
        data() {
            return {
                delLoading: false,
                downloadLoading: false,
                isAdd: false,
            }
        },
        created() {
            this.$nextTick(() => {
                this.init()
            })
        },
        methods: {
            parseTime,
            checkPermission,
            beforeInit() {
                this.url = 'api/sale'
                // const sort = 'sort,asc'
                this.params = { page: this.page, size: this.size }
                const query = this.query
                const value = query.value
                // const enabled = query.enabled
                if (value) { this.params['name'] = value }
                if (query.date) {
                    this.params['startTime'] = query.date[0]
                    this.params['endTime'] = query.date[1]
                }
                return true
            },
            subDelete(id) {
                this.delLoading = true
                del(id).then(res => {
                    this.delLoading = false
                    this.$refs[id].doClose()
                    this.dleChangePage()
                    this.init()
                    this.$notify({
                        title: '删除成功',
                        type: 'success',
                        duration: 2500
                    })
                }).catch(err => {
                    this.delLoading = false
                    this.$refs[id].doClose()
                    console.log(err.response.data.message)
                })
            },
            add() {
                this.isAdd = true
                this.$refs.form.dialog = true
            },
            edit(data) {
                this.isAdd = false
                const _this = this.$refs.form
                _this.form = {
                    id: data.id,
                    medicine : {
                        id: data.medicine.id,
                        name: data.medicine.name
                    },
                    customer : {
                        id: data.customer.id,
                        name: data.customer.name
                    },
                    user : {
                        id: data.user.id,
                        name: data.user.name
                    },
                    saleCount: data.saleCount,
                    salePrice: data.salePrice,
                    sumPrice: data.sumPrice,
                    saleTime: parseTime(data.saleTime)
                }
                _this.dialog = true
            },
            // 导出
            download() {
                this.downloadLoading = true
                downloadSaleRecord().then(result => {
                    downloadFile(result, '销售记录', 'xlsx')
                    this.downloadLoading = false
                }).catch(() => {
                    this.downloadLoading = false
                })
            },
        }
    }
</script>

<style scoped>

</style>
