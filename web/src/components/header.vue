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
                                <a
                                        class="nav-link dropdown-toggle"
                                        href="#"
                                        id="menu-2"
                                        data-toggle="dropdown"
                                        aria-haspopup="true"
                                        aria-expanded="false"
                                        data-display="static"
                                >
                                    全部课程
                                </a>

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
                                <ul
                                        class="dropdown-menu dropdown-menu-arrow dropdown-menu-right dropdown-menu-xl-left "
                                        aria-labelledby="menu-3"
                                >
                                    <li>
                                        <a class="dropdown-item" href="./pages/blog.html">
                                            Blog
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                                class="dropdown-item"
                                                href="./pages/blog-author.html"
                                        >
                                            Blog Author
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                                class="dropdown-item"
                                                href="./pages/blog-category.html"
                                        >
                                            Blog Category
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                                class="dropdown-item"
                                                href="./pages/blog-classic.html"
                                        >
                                            Blog Classic
                                        </a>
                                    </li>
                                    <li>
                                        <a
                                                class="dropdown-item"
                                                href="./pages/blog-single.html"
                                        >
                                            Blog Single
                                        </a>
                                    </li>
                                </ul>
                            </li>

                        </ul>
                        <div class="header-btn ">
                            <a v-on:click="openLoginModal()" class="btn btn-primary btn-sm">登录/注册</a
                            >
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
        methods: {
            /**
             * 打开登录注册窗口
             */
            openLoginModal() {
                let _this = this;
                _this.$refs.loginComponent.openLoginModal();
            },
        }
    }

</script>

<style scoped>

</style>