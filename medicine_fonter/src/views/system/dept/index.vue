<template>
    <div class="app-container">
        <!--工具栏-->
        <div class="head-container">
            <!-- 搜索 -->
            <el-input v-model="query.value" clearable placeholder="输入部门名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery"/>
            <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
            <!-- 新增 -->
            <div v-permission="['ADMIN','DEPT_ALL','DEPT_CREATE']" style="display: inline-block;margin: 0px 2px;">
                <el-button
                    class="filter-item"
                    size="mini"
                    type="primary"
                    icon="el-icon-plus"
                    @click="add">新增</el-button>
            </div>
            <div style="display: inline-block;">
                <el-button
                    class="filter-item"
                    size="mini"
                    type="warning"
                    icon="el-icon-more"
                    @click="changeExpand">{{ expand ? '折叠' : '展开' }}</el-button>
                <!--<deptForm ref="form" :is-add="true" :dicts="dicts"/>-->
            </div>
        </div>
        <!--表单组件-->
        <deptForm ref="form" :is-add="isAdd"/>
        <!--表格渲染-->
        <tree-table v-loading="loading" :expand-all="expand" :data="data" :columns="columns" size="small">

            <el-table-column prop="createTime" label="创建日期">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.createTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column v-if="checkPermission(['ADMIN','DEPT_ALL','DEPT_EDIT','DEPT_DELETE'])" label="操作" width="130px" align="center" fixed="right">
                <template slot-scope="scope">
                    <el-button v-permission="['ADMIN','DEPT_ALL','DEPT_EDIT']" size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"/>
                    <el-popover
                        v-permission="['ADMIN','DEPT_ALL','DEPT_DELETE']"
                        :ref="scope.row.id"
                        placement="top"
                        width="180">
                        <p>确定删除本条数据吗？</p>
                        <div style="text-align: right; margin: 0">
                            <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                            <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
                        </div>
                        <el-button slot="reference" :disabled="scope.row.id === 1" type="danger" icon="el-icon-delete" size="mini"/>
                    </el-popover>
                </template>
            </el-table-column>
        </tree-table>
    </div>
</template>

<script>
    import treeTable from '@/components/TreeTable'
    import checkPermission from '@/utils/permission'
    import initData from '@/mixin/initData'
    import { del } from '@/api/dept'
    import { parseTime } from '@/utils/index'
    import deptForm from './form'
    export default {
        name: 'Dept',
        components: { deptForm, treeTable },
        mixins: [initData],
        data() {
            return {
                columns: [
                    {
                        text: '名称',
                        value: 'name'
                    }
                ],
                delLoading: false, expand: true
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
                this.url = 'api/dept'
                this.params = { page: this.page, size: this.size,  }
                const query = this.query
                const value = query.value
                if (value) { this.params['name'] = value }
                return true
            },
            subDelete(id) {
                this.delLoading = true
                del(id).then(res => {
                    this.delLoading = false
                    this.$refs[id].doClose()
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
                const _this = this.$refs.form
                _this.getDepts()
                _this.dialog = true
            },
            changeExpand() {
                this.expand = !this.expand
                this.init()
            },
            edit(data) {
                this.isAdd = false
                const _this = this.$refs.form
                _this.getDepts()
                _this.form = {
                    id: data.id,
                    name: data.name,
                    parentId: data.parentId,
                    createTime: data.createTime,
                }
                _this.dialog = true
            }
        }
    }
</script>

<style scoped>

</style>
