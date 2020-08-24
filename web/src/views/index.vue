<template>
    <div>

        <Header></Header>
        <!-- hero section -->
        <div class="bg-dark py-13 pt-lg-15 pb-lg-15">
            <div class="container">
                <div class="row">
                    <div class="offset-xl-1 col-xl-10 col-md-12 col-12">
                        <div class="text-center text-light mb-3">

                            <h1 class="text-white mb-6">
                                在线视频课程平台
                            </h1>
                            <p class="lead text-muted m-3">
                                知识付费时代刚刚起步，在这个领域有很多的发展机会。整个课程以实战为基础，手把手从零开始，一步一步搭建一个完整的企业级开发架构。不讲废话，只讲干货。
                            </p>
                            <p>
                                <router-link to="/list" class="btn btn-secondary btn-lg mb-5">点击进入所有课程</router-link>
                            </p>

                        </div>
                    </div>
                </div>
            </div>
        </div>



        <!-- about section -->
        <div class="py-lg-13 py-8">
            <div class="container">
                <div class="row">
                    <div class="offset-xl-1 col-xl-10 col-md-12 col-12">
                        <div class="mb-8">
                            <h2 class="mb-3">最新上线</h2>
                        </div>
                        <div class="row">
                            <div v-for="o in news" class="col-md-4">
                                <course v-bind:course="o"></course>
                            </div>
                            &nbsp;
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <Footer></Footer>


    </div>

</template>

<script>
    import Header from "../components/header";
    import Footer from "../components/footer";
    import Course from "../components/course";
    export default {
        name: "index",
        components: {Course, Footer, Header},
        data: function () {
            return {
                news: [],
            }
        },
        mounted() {
            let _this = this;
            _this.listNew();
        },
        methods: {
            /**
             * 查询新上好课
             */
            listNew() {
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/web/course/list-new').then((response)=>{
                    console.log("查询新上好课结果：", response);
                    let resp = response.data;
                    if (resp.success) {
                        _this.news = resp.content;
                    }
                }).catch((response)=>{
                    console.log("error：", response);
                })
            },
        }
    }
</script>

<style scoped>

</style>