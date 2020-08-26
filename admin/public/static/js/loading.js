Loading = {
  show: function () {
    $.blockUI({
      message: '                             <div class="sk-spinner sk-spinner-double-bounce">\n' +
          '                                <div class="sk-double-bounce1"></div>\n' +
          '                                <div class="sk-double-bounce2"></div>\n' +
          '                            </div> ',
      css: {
        backgroundColor: "rgba(255,204,51, 0.5)",
        padding: "10px",
        left: "50%",
        width: "80px",
        marginLeft: "-40px",
        border: 'none'
      }

    });
  },
  hide: function () {
    // 本地查询速度太快，loading显示一瞬间，故意做个延迟
    setTimeout(function () {
      $.unblockUI();
    }, 500)
  }
};