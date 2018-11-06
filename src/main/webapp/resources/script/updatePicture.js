function updatePicture() {
    var img_id = document.getElementById("img_id").innerHTML;
    var img_name = $("#img_name").val();
    var img_user_id = document.getElementById("img_user_id").innerHTML;
    var guest_visible =$("#guest_visible option:selected").val();

    var data = {'id':img_id,'name':img_name,'userId':img_user_id,'guestVisible':guest_visible};

    var current_url = location.href;
    var suffix = current_url.substr(current_url.lastIndexOf("/") + 1);
    var redirect_url = current_url.substr(0,current_url.lastIndexOf("/"));
    $.ajax({
        type: "PUT",
        // url: "/picture/" + suffix,   不写url，直接提交到当前url
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