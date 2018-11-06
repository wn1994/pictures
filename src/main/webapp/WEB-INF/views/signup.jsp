<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2018/6/8
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>
</head>
<body>
<div>
    <p>SignUp</p>
    <form action="signup" method="post">
        <table>
            <tr>
                <td>phonenum:</td>
                <td><input type="text" name="phoneNum"></td>
            </tr>
            <tr>
                <td>username:</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>password:</td>
                <td><input type="text" name="password"></td>
            </tr>
            <tr>
                <td><input type="submit" value="注册"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
