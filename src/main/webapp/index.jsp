<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" class="no-js">
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <%@include file="/WEB-INF/views/common.jsp"%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${path}/static/assets/css/reset.css">
    <link rel="stylesheet" href="${path}/static/assets/css/supersized.css">
    <link rel="stylesheet" href="${path}/static/assets/css/style.css">
    <script src="${path}/static/assets/js/html5.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#submit").click(function(){
                var username= $("#username").val()
                var password = $("#password").val()
                console.log(username)
                console.log(password)
                var fromData = {
                    userName:username,
                    password:password
                }
                $.ajax({
                    url:path+'/user/login',
                    data:fromData,
                    method:'post',
                    success:function (res) {
                        console.log(res.error)
                        console.log(res.success)
                        if(res.error != null){
                            alert("提示："+res.error);
//                            $.messager.alert("错误提示",res.error,"error");
                        }else if(res.success != null){
                            location = path+"/main";
                        }
                    },
                    error:function (error) {
                        alert(error)
                    }
                })
            });
        })
    </script>
</head>
<body  style="background-image: url('${path}/static/assets/img/1.jpg')">
<div class="page-container">
    <h1>欢迎登录</h1>
    <input type="text" name="username" id="username" placeholder="请输入您的用户名！">
    <input type="password" name="password" id="password" placeholder="请输入您的用户密码！">
    <button id="submit" type="submit" >登录</button>
</div>
<script src="${path}/static/assets/js/jquery-1.8.2.min.js" ></script>
<script src="${path}/static/assets/js/supersized.3.2.7.min.js" ></script>
<script src="${path}/static/assets/js/supersized-init.js" ></script>
<script src="${path}/static/assets/js/scripts.js" ></script>
</body>
</html>



