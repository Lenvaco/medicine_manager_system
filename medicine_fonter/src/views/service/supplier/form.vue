<template>
    <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增供应商' : '编辑供应商'" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
            <el-form-item v-if="!isAdd" label="编号" prop="id">
                <el-input  disabled v-model="form.id"/>
            </el-form-item>
            <el-form-item label="供应商名" prop="name">
                <el-input  v-model.trim="form.name"/>
            </el-form-item>
            <el-form-item label="联系电话" prop="phone">
                <el-input  v-model.number="form.phone" />
            </el-form-item>
            <el-form-item  label="合作时间" prop="cooperationTime">
                <el-date-picker
                    v-model="form.cooperationTime"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:SS"
                    :editable="false"
                    :disabled="!isAdd"
                    placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item style="margin-bottom: 0px;" label="地址">
                <el-input
                    type="textarea"
                    :rows="2"
                    maxlength="90"
                    show-word-limit
                    placeholder="请输入具体地址"
                    v-model.trim="form.address"
                    clearable>
                </el-input>
            </el-form-item>
            <el-form-item style="margin-bottom: 0px;" label="简介">
                <el-input
                    type="textarea"
                    :rows="4"
                    maxlength="120"
                    show-word-limit
                    show-overflow-tooltip
                    placeholder="在此输入供应商简介"
                    v-model.trim="form.description"
                    clearable>
                </el-input>
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
            const validPhone = (rule, value, callback) => {
                if (!value) {
                    callback(new Error('请输入电话号码'))
                } else if (!this.isValidPhone(value)) {
                    callback(new Error('请输入正确的11位手机号码'))
                } else {
                    callback()
                }
            }
            return {
                loading: false, dialog: false,
                form: {id: '', name: '', phone: null, address: '', description: '', cooperationTime: ''},
                rules: {
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'},
                        {min: 2, max: 14, message: '长度在 2 到 15 个字符', trigger: 'blur'}
                    ],
                    phone: [
                        {required: true, trigger: 'blur', validator: validPhone}
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
                this.form = {id: '', name: '', phone: null, address: '', description: '', cooperationTime: ''}
            },
            isValidPhone(str) {
                const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
                return reg.test(str)
            }
        }
    }
</script>

<style scoped>

</style>
