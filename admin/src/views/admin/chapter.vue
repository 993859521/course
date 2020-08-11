<template>
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>课程</th>
                        <th>课程ID</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="chapter in chapters">
                        <td>{{chapter.id}}</td>
                        <td>{{chapter.name}}</td>
                        <td>{{chapter.courseId}}</td>
                        <td>        <div class="hidden-sm hidden-xs btn-group">
                            <button  class="btn btn-white btn-xs btn-info btn-round">
                                小节
                            </button>&nbsp;
                            <button  class="btn btn-white btn-xs btn-info btn-round">
                                编辑
                            </button>&nbsp;
                            <button  class="btn btn-white btn-xs btn-warning btn-round">
                                删除
                            </button>
                        </div></td>
                    </tr>
                    </tbody>
                </table>

            </div>
        </div>

</template>

<script>

    export default {
        name: "chapter",
        data:function(){
            return{
                chapters:[]
            }

        },
        mounted() {
            let _this=this;
            this.list();
        },
        methods:{
            list(){
                let _this=this;
                _this.$ajax.get('http://127.0.0.1:8040/business/admin/list').then((response)=>{
                    console.log("查询章列表结果：",response)
                    _this.chapters=response.data
                });

            }
        }
    }

</script>

<style scoped>

</style>