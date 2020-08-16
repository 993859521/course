<template>
    <div>
        <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-upload"></i>
            {{text}}
        </button>
        <input class="hidden" type="file" ref="file" v-on:change="check()" v-bind:id="inputId+'-input'">
    </div>
</template>

<script>
    export default {
        name: 'oss-file',
        props: {
            text: {
                default: "上传文件"
            },
            inputId: {
                default: "file-upload"
            },
            suffixs: {
                default: []
            },
            use: {
                default: ""
            },
            afterUpload: {
                type: Function,
                default: null
            },
        },
        data: function () {
            return {
            }
        },
        methods: {
            uploadFile () {
                let _this = this;
                let formData = new window.FormData();
                let file = _this.$refs.file.files[0];

                // 判断文件格式
                let suffixs = _this.suffixs;
                let fileName = file.name;
                let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
                let validateSuffix = false;
                for (let i = 0; i < suffixs.length; i++) {
                    if (suffixs[i].toLowerCase() === suffix) {
                        validateSuffix = true;
                        break;
                    }
                }
                if (!validateSuffix) {
                    Toast.warning("文件格式不正确！只支持上传：" + suffixs.join(","));
                    $("#" + _this.inputId + "-input").val("");
                    return;
                }
                let key = hex_md5(file.name + file.size + file.type);
                let key10 = parseInt(key, 16);
                let key62 = Tool._10to62(key10);
                // key："file"必须和后端controller参数名一致
                formData.append('file', file);
                formData.append('use', _this.use);
                formData.append('key_md5', key62);
                formData.append('size', file.size);
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/file/oss-simple', formData).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    console.log("上传文件成功：", resp);
                    _this.afterUpload(resp);
                    $("#" + _this.inputId + "-input").val("");
                });
            },
            check(param) {
                let _this = this;
                let file = _this.$refs.file.files[0];
                let key = hex_md5(file.name + file.size + file.type);
                let key10 = parseInt(key, 16);
                let key62 = Tool._10to62(key10);
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/admin/file/check/' + key62).then((response) => {
                    let resp = response.data;
                    if (resp.success) {
                        let obj = resp.content;
                        if (!obj) {
                            console.log("没有找到文件记录，从分片1开始上传");
                            _this.uploadFile(param);
                        } else {
                            Toast.success("文件极速秒传成功！");
                            _this.afterUpload(resp);
                            $("#" + _this.inputId + "-input").val("");
                        }
                    } else {
                        Toast.warning("文件上传失败");
                        $("#" + _this.inputId + "-input").val("");
                    }
                })
            },

            selectFile () {
                let _this = this;
                $("#" + _this.inputId + "-input").trigger("click");
            }
        }
    }
</script>
