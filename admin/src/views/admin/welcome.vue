<template>
    <div>
    <Oss v-bind:input-id="'video-upload'"
          v-bind:text="'上传VOD'"
          v-bind:suffixs="['jpg', 'jpeg', 'png']"
          v-bind:use="FILE_USE.COURSE.key"
          v-bind:after-upload="afterUpload"></Oss>
    <div v-show="course_image" class="row">
        <div v-show="course_image" class="row">
            <div class="col-md-6">
                <img v-bind:src="course_image" class="img-responsive">
            </div>
        </div>
    </div>
    </div>
</template>

<script>
    import Oss from "../../components/oss";
    export default {
        components: {Oss},
        name: "welcome",
        data:function(){
            return{
                course_image:"",
                FILE_USE: FILE_USE,
            }

        }
    ,
        methods:{
        afterUpload(resp) {
            let _this = this;
            let video = resp.content.path;
            console.log(video);
            _this.course_image = video;

        },
        }
    }
</script>

<style scoped>

</style>