<template>
    <div >
        <div class="header fixed-top bg-dark" >
            <!-- navigation start -->
            <div class="container-lg">
                <nav class="navbar navbar-expand-lg navbar-default">

                    <button
                            class="navbar-toggler collapsed"
                            type="button"
                            data-toggle="collapse"
                            data-target="#navbar-default"
                            aria-controls="navbar-default"
                            aria-expanded="false"
                            aria-label="Toggle navigation"
                    >
                        <span class="icon-bar top-bar mt-0"></span>
                        <span class="icon-bar middle-bar"></span>
                        <span class="icon-bar bottom-bar"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbar-default">
                        <button
                                class="navbar-toggler collapsed"
                                type="button"
                                data-toggle="collapse"
                                data-target="#navbar-default"
                                aria-controls="navbar-default"
                                aria-expanded="false"
                                aria-label="Toggle navigation"
                        >
                            <i class="fas fa-times"></i>
                        </button>
                        <ul class="navbar-nav ml-auto mr-lg-3">
                            <li class="nav-item dropdown disabled">
                                <a class="nav-link d-lg-none" href="#!">
                                    Menu
                                </a>
                            </li>

                            <li class="nav-item dropdown ">
                                <a
                                        class="nav-link dropdown-toggle"
                                        href="#"
                                        id="menu-1"
                                        data-toggle="dropdown"
                                        aria-haspopup="true"
                                        aria-expanded="false"
                                        data-display="static"
                                >
                                    主页
                                </a>
                            </li>
                            <li class="nav-item dropdown ">
                                <router-link class="nav-link" to="/list">全部课程</router-link>


                            </li>
                            <li class="nav-item dropdown">
                                <a
                                        class="nav-link dropdown-toggle"
                                        href=""
                                        id="menu-3"
                                        data-toggle="dropdown"
                                        aria-haspopup="true"
                                        aria-expanded="false"
                                >
                                    更多
                                </a>
                                <ul class="dropdown-menu dropdown-menu-arrow dropdown-menu-right dropdown-menu-xl-left " aria-labelledby="menu-3">
                                    <li>
                                        <a class="dropdown-item" href="#">关于我们</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="#">渠道合作</a>
                                    </li>
                                    <li>
                                        <div class="dropdown-divider"></div>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="#">更多信息</a>
                                    </li>
                                </ul>
                            </li>

                        </ul>
                        <div class="header-btn ">
                            <span v-show="loginMember.id" class="text-white pr-3">您好：{{loginMember.name}}</span>
                            <a v-show="loginMember.id" v-on:click="logout()" class="btn btn-primary btn-sm">退出登录</a>
                            <a v-show="!loginMember.id" v-on:click="openLoginModal()" class="btn btn-primary btn-sm">登录/注册</a>
                        </div>
                    </div>
                </nav>
            </div>
            <!-- navigation close -->
        </div>

        <login ref="loginComponent"></login>


    </div>
</template>

<script>
    import Login from "./login";
    export default {
        name: "Header",
        components: {Login},
        data: function () {
            return {
                loginMember: {}
            }
        },
        mounted() {
            let _this = this;
            _this.loginMember = Tool.getLoginMember();
        },
        methods: {
            /**
             * 打开登录注册窗口
             */
            openLoginModal() {
                let _this = this;
                _this.$refs.loginComponent.openLoginModal();
            },
            setLoginMember(loginMember) {
                let _this = this;
                _this.loginMember = loginMember;
            },
            logout () {
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/web/member/logout/' + _this.loginMember.token).then((response)=>{
                    let resp = response.data;
                    if (resp.success) {
                        Tool.setLoginMember(null);
                        _this.loginMember = {};
                        Toast.success("退出登录成功");
                        _this.$router.push("/");
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },
        }
    }

</script>

<style scoped>

</style>