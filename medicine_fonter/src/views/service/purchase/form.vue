<template>
    <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增采购记录' : '编辑采购记录'" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="105px">
            <el-form-item label="药品编号" prop="medicine.id">
                <el-input v-model.trim="form.medicine.id"/>
            </el-form-item>
            <el-form-item label="供应商编号" prop="supplier.id">
                <el-input v-model.trim="form.supplier.id" />
            </el-form-item>
            <el-form-item label="销售人员编号"  prop="user.id">
                <el-input v-model.trim="form.user.id"/>
            </el-form-item>
            <el-form-item label="数目" prop="purchaseCount">
                <el-input-number :disabled="!isAdd" v-model.number="form.purchaseCount" :min="1" controls-position="right" />
            </el-form-item>
            <el-form-item label="采购单价" prop="purchasePrice">
                <el-input :disabled="!isAdd" v-model="form.purchasePrice"/>
            </el-form-item>
            <el-form-item label="采购日期" prop="purchaseTime">
                <el-date-picker
                    v-model="form.purchaseTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:SS"
                    :editable="false"
                    :disabled="!isAdd"
                    placeholder="选择采购日期">
                </el-date-picker>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="text" @click="cancel">取消</el-button>
            <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import { add, edit } from '@/api/purchase'
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
            const isPriceVlidator= (rule, value, callback) => {
                var pattern = /^\d{1,5}(?:\.\d{1,2})?$/;
                if (!pattern.test(value)) {
                    return callback(new Error('请输入正确金额,整数位不超过五位,小数点后最多只能输入两位'))
                }else return callback()
            }
            return {
                loading: false, dialog: false,
                form: {id: '', medicine:{ id: '', name: ''}, supplier:{ id: '', name: ''}, user:{ id: '', name: ''},  purchaseCount: null,  purchasePrice: '', purchaseTime: null},
                rules: {
                    'medicine.id' : [
                        {required: true, message: '必须输入药品编号' , trigger: 'blur'}
                    ],
                    'supplier.id' : [
                        {required: true, message: '必须输入供应商编号' , trigger: 'blur'}
                    ],
                    'user.id' : [
                        {required: true, message: '必须输入销售人员编号', trigger: 'blur'}
                    ],
                    purchaseCount : [
                        {required: true, message: '必须输入采购数目', trigger: 'blur'}
                    ],
                    purchasePrice : [
                        {required: true, message: '必须输入采购单价', trigger: 'blur'},
                        {required: true, trigger: 'blur', validator: isPriceVlidator}
                    ],
                    purchaseTime : [
                        {required: true, message: '必须选取采购时间', trigger: 'blur'}
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
                this.form = {id: '', medicine:{ id: '', name: ''}, supplier:{ id: '', name: ''}, user:{ id: '', name: ''},  purchaseCount: null,  purchasePrice: '', purchaseTime: null}
            },
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    /deep/ .el-input-number .el-input__inner {
        text-align: left;
    }
</style>
