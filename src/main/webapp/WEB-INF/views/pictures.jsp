<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2018/6/11
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>Home</title>
    <%@include file="common/head.jsp" %>
    <link href="<%=path%>/resources/css/pictures.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<table class="table table-hover" id="img_table">
    <thead>
    <tr>
        <th>名称</th>
        <th>公开</th>
        <th>图片</th>
        <th>上传时间</th>
        <th><input type="button" value="登出" onclick="window.location.href='/logout'"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="picture" items="${pictures}">
        <tr>
            <td>${picture.name}</td>
            <td>
                <img class="Avatar AuthorInfo-avatar" width="150" height="100"
                     src="${picture.path}"
                     alt="图片获取失败">
            </td>
            <td> ${picture.guestVisible}</td>
            <td> ${picture.uploadTime}</td>
            <c:if test="${visitSelf}">
                <td>
                    <button type="submit" onclick="window.location.href=document.URL+'/${picture.id}'">修改
                    </button>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br/>
<c:if test="${visitSelf}">
    <input id="img_file" name="img_file" type="file" accept="image/*"/>
    <label for="img_file" id="img_label">选择文件</label>
    公开 <input type="checkbox" id="guestVisible" name="guestVisible" value="flase">
    <div id="preview_box"></div>
    名称：<input id="img_name" name="img_name" type="text"/>
    <button type="button" onclick="uploadToServer()">提交</button>
</c:if>

<%--预览图片功能js--%>
<script>
    $("#img_file").on("change", function (e) {
        var file = e.target.files[0]; //获取图片资源
        // 只选择图片文件
        if (!file.type.match('image.*')) {
            return false;
        }
        var reader = new FileReader();
        reader.readAsDataURL(file); // 读取文件
        // 渲染文件
        reader.onload = function (arg) {
            var img = '<img class="preview" src="' + arg.target.result + '" alt="preview"/>';
            $("#preview_box").empty().append(img);
        }
    });
</script>
<script src="http://apps.bdimg.com/libs/jquery.cookie/1.4.1/jquery.cookie.js"></script>
<script src="<%=path%>/resources/script/uploadPicture.js"></script>

</body>
</html>
