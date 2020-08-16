<template>
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <p>
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
                <div class="row">
                    <div  v-for="teacher in teachers" class="col-md-2">
                        <div class="ibox-content text-center">

                            <span class="profile-picture">
                                <img v-show="!teacher.image"  class="img-circle img-responsive img-thumbnail" src="/static/image/demo-course.jpg" v-bind:title="teacher.intro"/>
                                <img v-show="teacher.image"  class="img-circle img-responsive img-thumbnail"  v-bind:src="teacher.image" v-bind:title="teacher.intro" />
                            </span>

                            <p class="font-bold">{{teacher.name}}-{{teacher.nickname}}</p>
                                      <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
                                        <div class="inline position-relative">
                                          <a href="javascript:;" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                                            <i class="ace-icon fa fa-circle light-green"></i>
                                            &nbsp;
                                            <span class="white">{{teacher.position}}</span>
                                          </a>
                                        </div>

                        </div>


                            <p></p>
                            <div class="profile-social-links align-center">
                                <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>
                                &nbsp;
                                <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>
                            </div>


                        </div>
                    </div>
                </div>
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
                                                    <label class="col-sm-2 control-label">姓名</label>
                                                    <div class="col-sm-10">
                                                        <input v-model="teacher.name" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">昵称</label>
                                                    <div class="col-sm-10">
                                                        <input v-model="teacher.nickname" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">头像</label>
                                                    <div class="col-sm-10">
                                                        <Oss v-bind:input-id="'image-upload'"
                                                                  v-bind:text="'上传头像'"
                                                                  v-bind:suffixs="['jpg', 'jpeg', 'png']"
                                                                  v-bind:use="FILE_USE.TEACHER.key"
                                                                  v-bind:after-upload="afterUpload"></Oss>
                                                        <div v-show="teacher.image" class="row">
                                                            <div class="col-md-4">
                                                                <img v-bind:src="teacher.image" class="img-responsive">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">职位</label>
                                                    <div class="col-sm-10">
                                                        <input v-model="teacher.position" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">座右铭</label>
                                                    <div class="col-sm-10">
                                                        <input v-model="teacher.motto" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">简介</label>
                                                    <div class="col-sm-10">
                                                        <input v-model="teacher.intro" class="form-control">
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
    import Oss from "../../components/oss";


    export default {
        components: {Pagination,Oss},
        name: "business-Teacher",
        data:function(){
            return{
                 teacher:{},
                 teachers:[],
                 FILE_USE: FILE_USE,
            }

        },
        mounted() {
            let _this=this; _this.$refs.pagination.size = 5;
            this.list(1);
        },
        methods:{
            add(){
                let _this = this;
                _this. teacher = {};
                $("#form-modal").modal("show");
            },
            edit(teacher) {
                let _this = this;
                _this. teacher = $.extend({},  teacher);
                $("#form-modal").modal("show");
            },
            save() {
                let _this = this;

                // 保存校验
                if (1 != 1
                    || !Validator.require(_this.teacher.name, "姓名")
                    || !Validator.length(_this.teacher.name, "姓名", 1, 50)
                    || !Validator.length(_this.teacher.nickname, "昵称", 1, 50)
                    || !Validator.length(_this.teacher.image, "头像", 1, 100)
                    || !Validator.length(_this.teacher.position, "职位", 1, 50)
                    || !Validator.length(_this.teacher.motto, "座右铭", 1, 50)
                    || !Validator.length(_this.teacher.intro, "简介", 1, 500)
                ) {
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/teacher/save/', _this. teacher).then((response)=>{
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
                    _this.$ajax.delete( process.env.VUE_APP_SERVER+'/business/admin/teacher/delete/' + id).then((response)=>{
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
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/teacher/list',{
                        page: page,
                        size: _this.$refs.pagination.size,
                }
                ).then((response)=>{
                    let resp = response.data.content;
                    _this. teachers=resp.list;
                    _this.$refs.pagination.render(page, resp.total);
                });

            },
            afterUpload(resp) {
                let _this = this;
                let image = resp.content.path;
                _this.teacher.image =image;
                console.log(image);
            },
        }

    }

</script>

<style scoped>

</style>