<template>
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <h4 class="lighter">
                    <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
                    <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
                </h4>
                <hr>
                <p>
                <router-link to="/business/course" class="btn btn-white btn-default btn-round">
                    返回课程
                </router-link>
                    <button v-on:click="add()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-edit"></i>
                        新增
                    </button>
                <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
                    <i class="ace-icon fa fa-refresh"></i>
                    刷新
                </button>
            </p>

                <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
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
                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <button v-on:click="toSection(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                                    小节
                                </button>&nbsp;
                                <button v-on:click="edit(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                                    编辑
                                </button>&nbsp;
                                <button v-on:click="del(chapter.id)" class="btn btn-white btn-xs btn-warning btn-round">
                                    删除
                                </button>
                            </div></td>
                    </tr>
                    </tbody>
                </table>
                <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">表单</h4>
                            </div>
                            <div class="modal-body">
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">名称</label>
                                        <div class="col-sm-10">
                                            <input v-model="chapter.name" class="form-control" placeholder="名称">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">讲师</label>
                                        <div class="col-sm-10">
                                            <select v-model="chapter.teacherId" class="form-control">
                                                <option v-for="o in teachers" v-bind:value="o.id">{{o.name}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->

            </div>
        </div>
</template>

<script>
    import Pagination from "../../components/pagination";
    export default {
        components: {Pagination},
        name: "chapter",
        data:function(){
            return{
                chapter:{},
                chapters:[],
                course: {},
                teachers: [],
            }

        },
        mounted() {

            let _this=this;
            _this.$refs.pagination.size = 5;
            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            if (Tool.isEmpty(course)) {
                _this.$router.push("/welcome");
            }
            _this.allTeacher();
            _this.course = course;
            this.list(1);
        },
        methods:{
            add(){
                let _this = this;
                _this.chapter = {};
                $("#form-modal").modal("show");
            },
            edit(chapter) {
                let _this = this;
                _this.chapter = $.extend({}, chapter);
                $("#form-modal").modal("show");
            },
            save() {
                let _this = this;

                // 保存校验
                if (!Validator.require(_this.chapter.name, "名称")||
                    !Validator.require(_this.chapter.teacherId, "老师")
                    || !Validator.length(_this.chapter.courseId, "课程ID", 1, 8)) {
                    return;
                }
                _this.chapter.courseId = _this.course.id;

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/save/', _this.chapter).then((response)=>{
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
                    _this.$ajax.delete( process.env.VUE_APP_SERVER+'/business/admin/chapter/delete/' + id).then((response)=>{
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1);
                            Toast.success("删除成功！");
                        }
                    })
                });
            },
            list(page){
                let _this=this;
                console.log(process.env.VUE_APP_SERVER+'/business/admin/chapter/list');
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/list',{
                        page: page,
                        size: _this.$refs.pagination.size,
                        courseId: _this.course.id
                }
            ).then((response)=>{
                    let resp = response.data.content;
                    _this.chapters=resp.list;
                    _this.$refs.pagination.render(page, resp.total);
                });

            },
            toSection(chapter) {
                let _this = this;
                SessionStorage.set(SESSION_KEY_CHAPTER, chapter);
                _this.$router.push("/business/section");
            },
            allTeacher() {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/all').then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    _this.teachers = resp.content;
                })
            },
        }
    }

</script>

<style scoped>

</style>