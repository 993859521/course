<template>
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <p>

                <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
                    <i class="ace-icon fa fa-refresh"></i>
                    刷新
                </button>
            </p>

                <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                                                <th>id</th>
                        <th>相对路径</th>
                        <th>文件名</th>
                        <th>后缀</th>
                        <th>大小</th>
                        <th>用途</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr v-for="file in files">
                        <td>{{file.id}}</td>
                        <td>{{file.path}}</td>
                        <td>{{file.name}}</td>
                        <td>{{file.suffix}}</td>
                        <td>{{file.size}}</td>
                        <td>{{FILE_USE | optionKV(file.useEnum)}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
</template>

<script>
    import Pagination from "../../components/pagination";
    export default {
        components: {Pagination},
        name: "business-File",
        data:function(){
            return{
                 file:{},
                 files:[],
                FILE_USE: FILE_USE,
            }

        },
        mounted() {
            let _this=this; _this.$refs.pagination.size = 5;
            this.list(1);
        },
        methods:{

            /**
             * 点击【删除】
             */
            list(page){
                let _this=this;
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/file/list',{
                        page: page,
                        size: _this.$refs.pagination.size,
                }
                ).then((response)=>{
                    let resp = response.data.content;
                    _this. files=resp.list;
                    console.info(resp);
                    _this.$refs.pagination.render(page, resp.total);
                });

            }
        }
    }

</script>

<style scoped>

</style>