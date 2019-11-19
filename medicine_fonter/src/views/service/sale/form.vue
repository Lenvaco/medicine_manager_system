<template>
    <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增销售记录' : '编辑销售记录'" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="105px">
            <el-form-item label="药品编号" prop="medicine.id">
                <el-input v-model.number="form.medicine.id"/>
            </el-form-item>
            <el-form-item label="顾客编号" prop="customer.id">
                <el-input v-model.number="form.customer.id" />
            </el-form-item>
            <el-form-item label="销售人员编号"  prop="user.id">
                <el-input v-model.number="form.user.id"/>
            </el-form-item>
            <el-form-item label="数目" prop="saleCount">
                <el-input v-model.number="form.saleCount"/>
            </el-form-item>
            <el-form-item label="单价" prop="salePrice">
                <el-input v-model.number="form.salePrice"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="text" @click="cancel">取消</el-button>
            <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import { add, edit } from '@/api/supplier'
    import Treeselect from '@riophae/vue-treeselect'
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    export default {
        components: {Treeselect},
        props: {
            isAdd: {
                type: Boolean,
                required: true
            },
        },
        data() {
            return {
                loading: false, dialog: false,
                form: {id: '', medicine:{ id: '', name: ''}, customer:{ id: '', name: ''}, user:{ id: '', name: ''}, saleCount: null,  salePrice: null, saleTime: ''},
                rules: {
                    'medicine.id' : [
                        {required: true, message: '必须输入药品编号' , trigger: 'blur'}
                    ],
                    'customer.id' : [
                        {required: true, message: '必须输入顾客编号' , trigger: 'blur'}
                    ],
                    'user.id' : [
                        {required: true, message: '必须输入销售人员编号', trigger: 'blur'}
                    ],
                    saleCount : [
                        {required: true, message: '必须输入销售数目', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            cancel() {
                this.resetForm()
            },
            doSubmit() {
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        if (this.isAdd) {
                            this.doAdd()
                        } else this.doEdit()

                    } else {
                        return false
                    }
                })
            },
            doAdd() {
                add(this.form).then(res => {
                    this.resetForm()
                    this.$notify({
                        title: '添加成功',
                        type: 'success',
                        duration: 2500
                    })
                    this.loading = false
                    this.$parent.init()
                }).catch(err => {
                    this.loading = false
                    console.log(err.response.data.message)
                })
            },
            doEdit() {
                edit(this.form).then(res => {
                    this.resetForm()
                    this.$notify({
                        title: '修改成功',
                        type: 'success',
                        duration: 2500
                    })
                    this.loading = false
                    this.$parent.init()
                }).catch(err => {
                    this.loading = false
                    console.log(err.response.data.message)
                })
            },
            resetForm() {
                this.dialog = false
                this.$refs['form'].resetFields()
                this.form = {id: '', medicine:{ id: '', name: ''}, customer:{ id: '', name: ''}, user:{ id: '', name: ''}, saleCount: null,  salePrice: null, saleTime: ''}
            },
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    /deep/ .el-input-number .el-input__inner {
        text-align: left;
    }
</style>
