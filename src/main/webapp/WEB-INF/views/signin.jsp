<%--
  Created by IntelliJ IDEA.
  User: wangning
  Date: 18-6-11
  Time: 上午10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <p>SignIn</p>
    <form action="signin" method="post">
        <table>
            <tr>
                <td>phonenum:</td>
                <td><input type="text" name="phoneNum"></td>
            </tr>
            <tr>
                <td>password:</td>
                <td><input type="text" name="password"></td>
            </tr>
            <tr>
                <td><input type="submit" value="登录"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
