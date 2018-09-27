<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2018/7/6
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>详情</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<table class="table table-hover">
    <tr>
        <th>ID</th>
        <th>名称</th>
        <th>上传时间</th>
        <th>用户ID</th>
        <th>访客可见</th>
        <th>图片</th>
    </tr>
    <tr>
        <td id="img_id">${picture.id}</td>
        <td><input type="text" id="img_name" value="${picture.name}"></td>
        <td>${picture.uploadTime}</td>
        <td id="img_user_id">${picture.userId}</td>
        <td><select id="guest_visible">
            <c:choose>
                <c:when test="${picture.guestVisible}">
                    <option value="true" selected="selected">true</option>
                    <option value="false">false</option>
                </c:when>
                <c:otherwise>
                    <option value="true">true</option>
                    <option value="false" selected="selected">false</option>
                </c:otherwise>
            </c:choose>
        </select></td>
        <td>
            <img class="Avatar AuthorInfo-avatar" width="200" height="150"
                 src="${picture.path}"
                 alt="图片获取失败">
        </td>
    </tr>
</table>
<button type="button" onclick="updatePicture()">修改</button>
<button type="button" onclick="deletePicture()">删除</button>

<script src="http://apps.bdimg.com/libs/jquery.cookie/1.4.1/jquery.cookie.js"></script>
<script src="<%=path%>/resources/script/updatePicture.js"></script>
<script src="<%=path%>/resources/script/deletePicture.js"></script>
</body>
</html>
