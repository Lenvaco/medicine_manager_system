(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-f6cab3b6"],{"33ce":function(e,t,o){"use strict";o.r(t);var r=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("el-dialog",{attrs:{"append-to-body":!0,"close-on-click-modal":!1,"before-close":e.cancel,visible:e.dialog,title:e.isAdd?"新增药品":"编辑药品",width:"500px"},on:{"update:visible":function(t){e.dialog=t}}},[o("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,size:"small","label-width":"105px"}},[e.isAdd?e._e():o("el-form-item",{attrs:{label:"药品编号",prop:"id"}},[o("el-input",{attrs:{disabled:""},model:{value:e.form.id,callback:function(t){e.$set(e.form,"id",e._n(t))},expression:"form.id"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"药品名",prop:"name"}},[o("el-input",{model:{value:e.form.name,callback:function(t){e.$set(e.form,"name","string"===typeof t?t.trim():t)},expression:"form.name"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"使用方式",prop:"mode"}},e._l(e.modeOptions,(function(t){return o("el-radio",{key:t.id,attrs:{label:t.key},model:{value:e.form.mode,callback:function(t){e.$set(e.form,"mode",t)},expression:"form.mode"}},[e._v(e._s(t.display_name))])})),1),e._v(" "),o("el-form-item",{attrs:{label:"使用量",prop:"dosage"}},[o("el-input",{attrs:{maxlength:"50"},model:{value:e.form.dosage,callback:function(t){e.$set(e.form,"dosage",t)},expression:"form.dosage"}})],1),e._v(" "),o("el-form-item",{staticStyle:{"margin-bottom":"0px"},attrs:{label:"使用功效"}},[o("el-input",{attrs:{type:"textarea",rows:2,maxlength:"90","show-word-limit":"",placeholder:"请输入使用功效",clearable:""},model:{value:e.form.efficacy,callback:function(t){e.$set(e.form,"efficacy",t)},expression:"form.efficacy"}})],1),e._v(" "),o("el-form-item",{staticStyle:{"margin-bottom":"0px"},attrs:{label:"组成成份"}},[o("el-input",{attrs:{type:"textarea",rows:2,maxlength:"90","show-word-limit":"",placeholder:"请输入组成成份",clearable:""},model:{value:e.form.description,callback:function(t){e.$set(e.form,"description",t)},expression:"form.description"}})],1),e._v(" "),o("el-form-item",{staticStyle:{"margin-bottom":"0px"},attrs:{label:"注意事项"}},[o("el-input",{attrs:{type:"textarea",rows:2,maxlength:"90","show-word-limit":"",placeholder:"请输入注意事项",clearable:""},model:{value:e.form.caution,callback:function(t){e.$set(e.form,"caution",t)},expression:"form.caution"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"库存量",prop:"inventory"}},[o("el-input-number",{model:{value:e.form.inventory,callback:function(t){e.$set(e.form,"inventory",e._n(t))},expression:"form.inventory"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"生产日期",prop:"productTime"}},[o("el-date-picker",{attrs:{type:"date","value-format":"yyyy-MM-dd",editable:!1,disabled:!e.isAdd,placeholder:"选择生产日期"},model:{value:e.form.productTime,callback:function(t){e.$set(e.form,"productTime",t)},expression:"form.productTime"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"过期日期",prop:"expireTime"}},[o("el-date-picker",{attrs:{type:"date","value-format":"yyyy-MM-dd",editable:!1,disabled:!e.isAdd,placeholder:"选择过期日期"},model:{value:e.form.expireTime,callback:function(t){e.$set(e.form,"expireTime",t)},expression:"form.expireTime"}})],1)],1),e._v(" "),o("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),e._v(" "),o("el-button",{attrs:{loading:e.loading,type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)},i=[],a=o("3bc2"),n=o("ca17"),l=o.n(n),s=(o("542c"),{components:{Treeselect:l.a},props:{isAdd:{type:Boolean,required:!0}},data:function(){return{loading:!1,dialog:!1,form:{id:"",name:"",mode:"0",dosage:"",efficacy:"",description:"",caution:"",inventory:0,productTime:"",expireTime:""},modeOptions:[{key:"0",display_name:"内服"},{key:"1",display_name:"外用"}],rules:{name:[{required:!0,message:"必须输入药品名",trigger:"blur"}],efficacy:[{required:!0,message:"必须输入使用功效",trigger:"blur"}],description:[{required:!0,message:"必须输入组成成份",trigger:"blur"}],caution:[{required:!0,message:"必须输入注意事项",trigger:"blur"}],productTime:[{required:!0,message:"必须输入生产日期",trigger:"blur"}],expireTime:[{required:!0,message:"必须输入过期日期",trigger:"blur"}]}}},methods:{cancel:function(){this.resetForm()},doSubmit:function(){var e=this;this.$refs["form"].validate((function(t){if(!t)return!1;e.isAdd?e.doAdd():e.doEdit()}))},doAdd:function(){var e=this;Object(a["a"])(this.form).then((function(t){e.resetForm(),e.$notify({title:"添加成功",type:"success",duration:2500}),e.loading=!1,e.$parent.init()})).catch((function(t){e.loading=!1,console.log(t.response.data.message)}))},doEdit:function(){var e=this;Object(a["d"])(this.form).then((function(t){e.resetForm(),e.$notify({title:"修改成功",type:"success",duration:2500}),e.loading=!1,e.$parent.init()})).catch((function(t){e.loading=!1,console.log(t.response.data.message)}))},resetForm:function(){this.dialog=!1,this.$refs["form"].resetFields(),this.form={id:"",name:"",mode:"0",dosage:"",efficacy:"",description:"",caution:"",inventory:0,productTime:"",expireTime:""}}}}),c=s,d=(o("c837"),o("2877")),m=Object(d["a"])(c,r,i,!1,null,"2c127ccd",null);t["default"]=m.exports},"3bc2":function(e,t,o){"use strict";o.d(t,"a",(function(){return i})),o.d(t,"c",(function(){return a})),o.d(t,"d",(function(){return n})),o.d(t,"b",(function(){return l}));var r=o("b775");function i(e){return Object(r["a"])({url:"api/medicine",method:"post",data:e})}function a(){return Object(r["a"])({url:"api/medicine/download",method:"get",responseType:"blob"})}function n(e){return console.log(e),Object(r["a"])({url:"api/medicine/"+e.id,method:"put",data:e})}function l(e){return Object(r["a"])({url:"api/medicine/"+e,method:"delete"})}},c837:function(e,t,o){"use strict";var r=o("cc26"),i=o.n(r);i.a},cc26:function(e,t,o){}}]);