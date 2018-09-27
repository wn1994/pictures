function uploadToServer() {
    var form_data = new FormData();
    var file_data = $("#img_file").prop("files")[0];
    var file_name = $("#img_name").val();
    var guest_visible = $("#guestVisible").prop("checked");
// 把上传的数据放入form_data
    form_data.append("name", file_name);
    form_data.append("img_file", file_data);
    form_data.append("guestVisible", guest_visible);
    $.ajax({
        type: "POST", // 上传文件要用POST
        // url: "/picture",  不写url，直接提交到当前url
        dataType: "json",
        crossDomain: true, // 如果用到跨域，需要后台开启CORS
        processData: false,  // 注意：不要 process data
        contentType: false,  // 注意：不设置 contentType
        data: form_data,
        success: function (data) {
            if (data.success === true) {
                //刷新网页
                location.reload();
            }
            else {
                alert("照片插入出现异常!");
            }
        },
        error: function (data) {
            alert(data);
        }
    });
}