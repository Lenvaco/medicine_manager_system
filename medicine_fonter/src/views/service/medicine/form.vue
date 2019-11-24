<template>
    <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增药品' : '编辑药品'" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="105px">
            <el-form-item v-if="!isAdd" label="药品编号" prop="id">
                <el-input disabled v-model.number="form.id"/>
            </el-form-item>
            <el-form-item label="药品名" prop="name">
                <el-input v-model.trim="form.name" />
            </el-form-item>
            <el-form-item label="使用方式" prop="mode">
                <el-radio v-for="item in modeOptions" :key="item.id" v-model="form.mode" :label="item.key" >{{item.display_name}}</el-radio>
            </el-form-item>
            <el-form-item label="使用功效"  prop="efficacy">
                <el-input v-model.number="form.efficacy"/>
            </el-form-item>
            <el-form-item label="组成成份" prop="description">
                <el-input v-model.number="form.description"/>
            </el-form-item>
            <el-form-item label="注意事项" prop="caution">
                <el-input v-model.number="form.caution"/>
            </el-form-item>
            <el-form-item label="库存量" prop="inventory">
            <el-input-number v-model.number="form.inventory"/>
        </el-form-item>
            <el-form-item label="生产日期" prop="productTime">
                <el-date-picker
                    v-model="form.productTime"
                    type="date"
                    value-format="yyyy-MM-dd"
                    :editable="false"
                    :disabled="!isAdd"
                    placeholder="选择生产日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="过期日期" prop="expireTime">
                <el-date-picker
                    v-model="form.expireTime"
                    type="date"
                    value-format="yyyy-MM-dd"
                    :editable="false"
                    :disabled="!isAdd"
                    placeholder="选择过期日期">
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
    import { add, edit } from '@/api/medicine'
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
                form: {id: '', name: '', mode: '0', efficacy: '', description: '',caution: '',inventory: 0, productTime: '',expireTime: ''},
                modeOptions: [
                    { key: '0', display_name: '内服' },
                    { key: '1', display_name: '外用' }
                ],
                rules: {
                    name : [
                        {required: true, message: '必须输入药品名' , trigger: 'blur'}
                    ],
                    efficacy : [
                        {required: true, message: '必须输入使用功效' , trigger: 'blur'}
                    ],
                    description : [
                        {required: true, message: '必须输入组成成份', trigger: 'blur'}
                    ],
                    caution : [
                        {required: true, message: '必须输入注意事项', trigger: 'blur'}
                    ],
                    productTime : [
                        {required: true, message: '必须输入生产日期', trigger: 'blur'}
                    ],
                    expireTime : [
                        {required: true, message: '必须输入过期日期', trigger: 'blur'}
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
                this.form = {id: '', name: '', mode: '0', efficacy: '', description: '',caution: '',inventory: 0, productTime: '',expireTime: ''}
            },
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    /deep/ .el-input-number .el-input__inner {
        text-align: left;
    }
</style>
