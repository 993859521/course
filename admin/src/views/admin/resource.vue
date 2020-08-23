<template>
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <p>
                <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
                    <i class="ace-icon fa fa-refresh"></i>
                    刷新
                </button>
            </p>

                <div class="row">
                    <div class="col-md-6">
                        <textarea id="resource-textarea" class="form-control" v-model="resourceStr" name="resource" rows="10"></textarea>

                        <br>
                        <button id="save-btn" type="button" class="btn btn-primary" v-on:click="save()">
                            保存
                        </button>
                    </div>
                    <div class="col-md-6">
                        <ul id="tree" class="ztree"></ul>
                    </div>
                </div>
        </div>
        </div>
</template>

<script>
    import Pagination from "../../components/pagination";
    export default {
        components: {Pagination},
        name: "business-Resource",
        data:function(){
            return{
                resource: {},
                resources: [],
                resourceStr: "",
                tree: {},
            }

        },
        mounted() {
            this.list(1);
        },
        methods:{
            save() {
                let _this = this;

                // 保存校验
                if (Tool.isEmpty(_this.resourceStr)) {
                    Toast.warning("资源不能为空！");
                    return;
                }
                let json = JSON.parse(_this.resourceStr);

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resource/save', json).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        $("#form-modal").modal("hide");
                        _this.list(1);
                        Toast.success("保存成功！");
                    } else {
                        Toast.warning(resp.message)
                    }
                })
            },

            /**
             * 点击【删除】
             */
            del(id) {
                let _this = this;
                Confirm.show("删除大章后不可恢复，确认删除？", function () {
                    Loading.show();
                    _this.$ajax.delete( process.env.VUE_APP_SERVER+'/system/admin/resource/delete/' + id).then((response)=>{
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1);
                            Toast.success("删除成功！");
                        }
                    })
                });
            },
            list() {
                let _this = this;
                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/resource/load-tree').then((res)=>{
                    Loading.hide();
                    let response = res.data;
                    _this.resources = response.content;
                    // 初始化树
                    _this.initTree();
                })
            },
            initTree() {
                let _this = this;
                let setting = {
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "",
                            // enable: true
                        }
                    }
                };

                _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resources);
                _this.zTree.expandAll(true);
            },
        }
    }

</script>

<style scoped>

</style>