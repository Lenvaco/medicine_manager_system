<template>
    <div class="app-container">
        <!--工具栏-->
        <div class="head-container">
            <!-- 搜索 -->
            <el-input v-model="query.value" clearable placeholder="输入供应商名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
            <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
            <!-- 新增 -->
            <div v-permission="['ADMIN','SUPPLIER_ALL','SUPPLIER_CREATE']" style="display: inline-block;margin: 0px 2px;">
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
                    v-permission="['ADMIN','SUPPLIER_ALL','SUPPLIER_SELECT']"
                    :loading="downloadLoading"
                    size="mini"
                    class="filter-item"
                    type="warning"
                    icon="el-icon-download"
                    @click="download">导出</el-button>
            </div>
        </div>
        <!--表单组件-->
        <supplierForm ref="form" :is-add="isAdd"/>
        <!--表格渲染-->
        <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
            <el-table-column prop="id" width="160px" label="编号"/>
            <el-table-column prop="name" label="供应商名" />
            <el-table-column prop="phone"  label="电话" />
            <el-table-column :show-overflow-tooltip="true" prop="address" label="地址" />
            <el-table-column :show-overflow-tooltip="true" min-width="100px" prop="description" label="简介" />
            <el-table-column label="合作时间">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.cooperationTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column v-if="checkPermission(['ADMIN','SUPPLIER_ALL','SUPPLIER_EDIT','SUPPLIER_DELETE'])" label="操作" width="130px" align="center" fixed="right">
                <template slot-scope="scope">
                    <el-button v-permission="['ADMIN','SUPPLIER_ALL','SUPPLIER_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"/>
                    <el-popover
                        v-permission="['ADMIN','SUPPLIER_ALL','SUPPLIER_DELETE']"
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
    import { del, downloadSupplier } from '@/api/supplier'
    import { parseTime, downloadFile } from '@/utils/index'
    import supplierForm from './form'
    export default {
        name: 'Supplier',
        components: { supplierForm },
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
                this.url = 'api/supplier'
                // const sort = 'sort,asc'
                this.params = { page: this.page, size: this.size }
                const query = this.query
                const value = query.value
                // const enabled = query.enabled
                if (value) { this.params['name'] = value }
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
                    name: data.name,
                    phone: data.phone,
                    address: data.address,
                    description: data.description,
                    cooperationTime: parseTime(data.cooperationTime)
                }
                _this.dialog = true
            },
            // 导出
            download() {
                this.downloadLoading = true
                downloadSupplier().then(result => {
                    downloadFile(result, '供应商列表', 'xlsx')
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
