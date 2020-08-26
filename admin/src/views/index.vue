<template>
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img alt="image" class="img-circle" src="../../public/static/image/profile_small.jpg" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">{{loginUser.name}}</strong></span>
                                <span class="text-muted text-xs block">超级管理员<b class="caret"></b></span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a class="J_menuItem" href="form_avatar.html">修改头像</a>
                                </li>
                                <li class="divider"></li>
                                <li><a v-on:click="logout()">安全退出</a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">Mooc
                        </div>
                    </li>
                    <li>
                        <router-link to="/welcome">
                            <i class="fa fa-home"></i>
                            <span class="J_menuItem">欢迎</span>
                        </router-link>



                    </li>
                    <li v-show="hasResource('01')">
                        <a href="#">
                            <i class="fa fa fa-bar-chart-o"></i>
                            <span class="nav-label">系统管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li v-show="hasResource('0101')">
                                <router-link to="/system/user">
                                    <span class="J_menuItem">用户管理</span>
                                </router-link>
                            </li>
                            <li v-show="hasResource('0102')">
                                <router-link to="/system/resource">
                                    <span class="J_menuItem">资源管理</span>
                                </router-link>
                            </li>
                            <li v-show="hasResource('0103')">
                                <router-link to="/system/role">
                                    <span class="J_menuItem">角色管理</span>
                                </router-link>
                            </li>

                        </ul>

                    </li>
                    <li v-show="hasResource('02')">
                        <a href="#">
                            <i class="fa fa-desktop"></i>
                            <span class="nav-label">业务管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li  v-show="hasResource('0201')">
                                <router-link to="/business/category">
                                    <span class="J_menuItem">分类管理</span>
                                </router-link>

                            </li>

                            <li  v-show="hasResource('0202')">
                                <router-link to="/business/course">
                                    <span class="J_menuItem">课程管理</span>
                                </router-link>

                            </li>
                            <li  v-show="hasResource('0203')">
                                <router-link to="/business/teacher">
                                    <span class="J_menuItem">教师管理</span>
                                </router-link>

                            </li>
                            <li  v-show="hasResource('0204')">
                                <router-link to="/business/member">
                                    <span class="J_menuItem">会员管理</span>
                                </router-link>

                            </li>



                        </ul>

                    </li>
                    <li v-show="hasResource('03')" >
                        <a href="#">
                            <i class="fa fa-desktop"></i>
                            <span class="nav-label">文件管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li v-show="hasResource('0301')">
                                <router-link to="/business/file">
                                    <span class="J_menuItem">文件管理</span>
                                </router-link>

                            </li>

                        </ul>
                    </li>

                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>

                    </div>
                    <ul class="nav navbar-top-links navbar-right">

                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i> <span class="label label-primary">8</span>
                            </a>
                            <ul class="dropdown-menu dropdown-alerts">
                                <li>
                                    <a href="mailbox.html">
                                        <div>
                                            <i class="fa fa-envelope fa-fw"></i> 您有16条未读消息
                                            <span class="pull-right text-muted small">4分钟前</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="profile.html">
                                        <div>
                                            <i class="fa fa-qq fa-fw"></i> 3条新回复
                                            <span class="pull-right text-muted small">12分钟钱</span>
                                        </div>
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" href="notifications.html">
                                            <strong>查看所有 </strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>

                    </ul>
                </nav>
            </div>
            <div class="row J_mainContent" id="content-main">
                <router-view></router-view>

            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2020-2099 mooc
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    $("body").attr("class","fixed-sidebar full-height-layout gray-bg")
    export default {
        name: "index",
        data: function() {
            return {
                loginUser: {},
            }
        },
        mounted: function() {
            let _this = this;
            _this.loginUser = Tool.getLoginUser();
            console.log(Tool.getLoginUser());
            if (!_this.hasResourceRouter(_this.$route.name)) {
                _this.$router.push("/login");
            }
        },
        methods: {
            logout () {
                let _this = this;
                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/user/logout/' + _this.loginUser.token).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        Tool.setLoginUser(null);
                        _this.$router.push("/login")
                    } else {
                        Toast.warning(resp.message)
                    }
                });
            },
            /**
             * 查找是否有权限
             * @param router
             */
            hasResourceRouter(router) {
                let _this = this;
                let resources = Tool.getLoginUser().resources;

                if (Tool.isEmpty(resources)) {
                    return false;
                }

                for (let i = 0; i < resources.length; i++) {
                    console.log("router:"+router+"   resources:"+resources[i].page);
                    if (router === resources[i].page) {
                        return true;
                    }
                }
                return false;
            },
            /**
             * 查找是否有权限
             * @param id
             */
            hasResource(id) {
                return Tool.hasResource(id);
            },

        }

    }

</script>

<style scoped>

</style>