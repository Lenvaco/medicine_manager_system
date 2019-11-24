<template>
    <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增顾客' : '编辑顾客'" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="75px">
            <el-form-item v-if="!isAdd" label="顾客编号" prop="id">
                <el-input disabled v-model.number="form.id"/>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model.trim="form.name" />
            </el-form-item>
            <el-form-item label="性别" prop="sex">
                <el-radio v-for="item in sexOptions" :key="item.id" v-model="form.sex" :label="item.key" >{{item.display_name}}</el-radio>
            </el-form-item>
            <el-form-item label="年龄"  prop="age">
                <el-input-number v-model.number="form.age" :min="0" controls-position="right"/>
            </el-form-item>
            <el-form-item label="电话" prop="phone">
                <el-input v-model.number="form.phone"/>
            </el-form-item>
            <el-form-item style="margin-bottom: 0px;" label="地址">
                <el-input
                    type="textarea"
                    :rows="2"
                    maxlength="90"
                    show-word-limit
                    placeholder="具体地址"
                    v-model.trim="form.address"
                    clearable>
                </el-input>
            </el-form-item>
            <el-form-item style="margin-bottom: 0px;" label="症状">
                <el-input
                    type="textarea"
                    :rows="2"
                    maxlength="90"
                    show-word-limit
                    placeholder="详细症状"
                    v-model.trim="form.symptom"
                    clearable>
                </el-input>
            </el-form-item>
            <el-form-item style="margin-bottom: 0px;" label="备注">
                <el-input
                    type="textarea"
                    :rows="2"
                    maxlength="90"
                    show-word-limit
                    placeholder="备注信息"
                    v-model.trim="form.remark"
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
    import { add, edit } from '@/api/customer'
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
                form: {id: '', name: '', sex: '0', age: null, phone: null, address: '', symptom: '', remark: ''},
                sexOptions: [
                    { key: '0', display_name: '男' },
                    { key: '1', display_name: '女' }
                ],
                rules: {
                    name : [
                        {required: true, message: '必须输入姓名' , trigger: 'blur'}
                    ],
                    phone : [
                        {required: true, message: '必须输入组成成份', trigger: 'blur'},
                        {required: true, trigger: 'blur', validator: validPhone}
                    ],
                    age : [
                        {required: true, message: '必须输入年龄', trigger: 'blur'}
                    ],
                    symptom : [
                        {required: true, message: '必须输入症状', trigger: 'blur'}
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
                this.form = {id: '', name: '', sex: '0', age: null, phone: null, address: '', symptom: '', remark: ''}
            },
            isValidPhone(str) {
                const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
                return reg.test(str)
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    /deep/ .el-input-number .el-input__inner {
        text-align: left;
    }
</style>
