<template>
  <div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
      <div>

        <h1 class="logo-name">mooc</h1>

      </div>
      <h3>欢迎使用 IMooc</h3>


        <div class="form-group">
          <input v-model="user.loginName" type="text" class="form-control" placeholder="用户名" required="">
        </div>
        <div class="form-group">
          <input v-model="user.password" type="password" class="form-control" placeholder="密码" required="">
        </div>
        <div class="input-group">
          <input v-model="user.imageCode" type="text" class="form-control" placeholder="验证码">
          <span class="input-group-addon" id="basic-addon2">
                                <img v-on:click="loadImageCode()" id="image-code" alt="验证码"/>
                              </span>
        </div>
        <div class="clearfix">
          <label class="inline">
            <input v-model="remember" type="checkbox" class="ace"/>
            <span class="lbl">记住我</span>
          </label>
        </div>

          <button type="submit" class="btn btn-primary block full-width m-b" v-on:click="login()">登 录</button>


    </div>
  </div>
</template>

<script>
  export default {
    name: "login",
    data: function() {
      return {
        user: {},
        remember: true, // 默认勾选记住我
        imageCodeToken: ""
      }
    },
    mounted: function() {
      let _this = this;
      // 从缓存中获取记住的用户名密码，如果获取不到，说明上一次没有勾选“记住我”
      let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
      if (rememberUser) {
        _this.user = rememberUser;
      }

      // 初始时加载一次验证码图片
      _this.loadImageCode();
    },
    methods: {
      login() {
        let _this = this;

        // 将明文存储到缓存中
        // let passwordShow = _this.user.password;

        // 如果密码是从缓存带出来的，则不需要重新加密
        let md5 = hex_md5(_this.user.password);
        let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
        if (md5 !== rememberUser.md5) {
          _this.user.password = hex_md5(_this.user.password + KEY);
        }

        _this.user.imageCodeToken = _this.imageCodeToken;

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login', _this.user).then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            console.log("登录成功：", resp.content);
            let loginUser = resp.content;
            Tool.setLoginUser(resp.content);

            // 判断“记住我”
            if (_this.remember) {
              // 如果勾选记住我，则将用户名密码保存到本地缓存
              // 原：这里需要保存密码明文，否则登录时又会再加一层密
              // 新：这里保存密码密文，并保存密文md5，用于检测密码是否被重新输入过
              let md5 = hex_md5(_this.user.password);
              LocalStorage.set(LOCAL_KEY_REMEMBER_USER, {
                loginName: loginUser.loginName,
                // password: _this.user.passwordShow,
                password: _this.user.password,
                md5: md5
              });
            } else {
              // 没有勾选“记住我”时，要把本地缓存清空，否则按照mounted的逻辑，下次打开时会自动显示用户名密码
              LocalStorage.set(LOCAL_KEY_REMEMBER_USER, null);
            }
            _this.$router.push("/welcome")
          } else {
            Toast.warning(resp.message);
            _this.user.password = "";
            _this.loadImageCode();
          }
        });
      },

      /**
       * 加载图形验证码
       */
      loadImageCode: function () {
        let _this = this;
        _this.imageCodeToken = Tool.uuid(8);
        $('#image-code').attr('src', process.env.VUE_APP_SERVER + '/system/admin/kaptcha/image-code/' + _this.imageCodeToken);
      },
    }
  }
</script>
