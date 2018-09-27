function deletePicture() {
    var img_id = document.getElementById("img_id").innerHTML;
    var data = {'id':img_id};

    var current_url = location.href;
    var suffix = current_url.substr(current_url.lastIndexOf("/") + 1);
    var redirect_url = current_url.substr(0,current_url.lastIndexOf("/"));
    $.ajax({
        type: "DELETE",
        // url: "/api/v1/picture/" + suffix,   不写url，直接提交到当前url
        dataType: "json",
        processData: false,
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(data),
        success: function (data) {
            if (data.success === true) {
                window.location.href=redirect_url;
            }
            else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert(data);
        }
    });
}