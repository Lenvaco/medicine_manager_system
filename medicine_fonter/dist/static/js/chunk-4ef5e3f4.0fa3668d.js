(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4ef5e3f4"],{7844:function(e,t,o){"use strict";o.r(t);var r=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("el-dialog",{attrs:{"append-to-body":!0,"close-on-click-modal":!1,"before-close":e.cancel,visible:e.dialog,title:e.isAdd?"新增供应商":"编辑供应商",width:"500px"},on:{"update:visible":function(t){e.dialog=t}}},[o("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,size:"small","label-width":"80px"}},[e.isAdd?e._e():o("el-form-item",{attrs:{label:"编号",prop:"id"}},[o("el-input",{attrs:{disabled:""},model:{value:e.form.id,callback:function(t){e.$set(e.form,"id",t)},expression:"form.id"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"供应商名",prop:"name"}},[o("el-input",{model:{value:e.form.name,callback:function(t){e.$set(e.form,"name","string"===typeof t?t.trim():t)},expression:"form.name"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"联系电话",prop:"phone"}},[o("el-input",{model:{value:e.form.phone,callback:function(t){e.$set(e.form,"phone",e._n(t))},expression:"form.phone"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"合作时间",prop:"cooperationTime"}},[o("el-date-picker",{attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:SS",editable:!1,disabled:!e.isAdd,placeholder:"选择日期"},model:{value:e.form.cooperationTime,callback:function(t){e.$set(e.form,"cooperationTime",t)},expression:"form.cooperationTime"}})],1),e._v(" "),o("el-form-item",{staticStyle:{"margin-bottom":"0px"},attrs:{label:"地址"}},[o("el-input",{attrs:{type:"textarea",rows:2,maxlength:"90","show-word-limit":"",placeholder:"请输入具体地址",clearable:""},model:{value:e.form.address,callback:function(t){e.$set(e.form,"address","string"===typeof t?t.trim():t)},expression:"form.address"}})],1),e._v(" "),o("el-form-item",{staticStyle:{"margin-bottom":"0px"},attrs:{label:"简介"}},[o("el-input",{attrs:{type:"textarea",rows:4,maxlength:"120","show-word-limit":"","show-overflow-tooltip":"",placeholder:"在此输入供应商简介",clearable:""},model:{value:e.form.description,callback:function(t){e.$set(e.form,"description","string"===typeof t?t.trim():t)},expression:"form.description"}})],1)],1),e._v(" "),o("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),e._v(" "),o("el-button",{attrs:{loading:e.loading,type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)},i=[],n=o("a6b9"),a=o("ca17"),l=o.n(a),s=(o("542c"),{components:{Treeselect:l.a},props:{isAdd:{type:Boolean,required:!0}},data:function(){var e=this,t=function(t,o,r){o?e.isValidPhone(o)?r():r(new Error("请输入正确的11位手机号码")):r(new Error("请输入电话号码"))};return{loading:!1,dialog:!1,form:{id:"",name:"",phone:null,address:"",description:"",cooperationTime:""},rules:{name:[{required:!0,message:"请输入名称",trigger:"blur"},{min:2,max:14,message:"长度在 2 到 15 个字符",trigger:"blur"}],phone:[{required:!0,trigger:"blur",validator:t}]}}},methods:{cancel:function(){this.resetForm()},doSubmit:function(){var e=this;this.$refs["form"].validate((function(t){if(!t)return!1;e.isAdd?e.doAdd():e.doEdit()}))},doAdd:function(){var e=this;Object(n["a"])(this.form).then((function(t){e.resetForm(),e.$notify({title:"添加成功",type:"success",duration:2500}),e.loading=!1,e.$parent.init()})).catch((function(t){e.loading=!1,console.log(t.response.data.message)}))},doEdit:function(){var e=this;Object(n["d"])(this.form).then((function(t){e.resetForm(),e.$notify({title:"修改成功",type:"success",duration:2500}),e.loading=!1,e.$parent.init()})).catch((function(t){e.loading=!1,console.log(t.response.data.message)}))},resetForm:function(){this.dialog=!1,this.$refs["form"].resetFields(),this.form={id:"",name:"",phone:null,address:"",description:"",cooperationTime:""}},isValidPhone:function(e){var t=/^1[3|4|5|7|8][0-9]\d{8}$/;return t.test(e)}}}),d=s,c=o("2877"),m=Object(c["a"])(d,r,i,!1,null,"6b55efda",null);t["default"]=m.exports},a6b9:function(e,t,o){"use strict";o.d(t,"a",(function(){return i})),o.d(t,"c",(function(){return n})),o.d(t,"d",(function(){return a})),o.d(t,"b",(function(){return l}));var r=o("b775");function i(e){return Object(r["a"])({url:"api/supplier",method:"post",data:e})}function n(){return Object(r["a"])({url:"api/supplier/download",method:"get",responseType:"blob"})}function a(e){return Object(r["a"])({url:"api/supplier/"+e.id,method:"put",data:e})}function l(e){return Object(r["a"])({url:"api/supplier/"+e,method:"delete"})}}}]);