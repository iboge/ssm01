<%@ page import="com.hwua.ssm.po.Auth" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>RBAC</title>
    <%@include file="common.jsp"%>
    <script type="text/javascript" src="${path}/static/js/main.js"></script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px;">
        <div style="font-size: 24px;float: left">RBAC人力资源管理系统</div>
        <div style="font-size: 14px;float: right">
            <c:choose>
                <c:when test="${sessionScope.user!=null}">
                    <a href="javascript:void(0)" onclick="updatepwd(${sessionScope.user.success})">欢迎${sessionScope.user.name}登录</a>
                    <a href="${path}/user/end">退出</a>
                </c:when>
                <c:otherwise>
                    <a href="javascript:void(0)">登录</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div data-options="region:'west',split:false,title:'功能模块'" style="width:150px;">
        <div class="easyui-accordion" data-options="fit:true">
            <c:forEach items="${sessionScope.auths}" var="auth">
                <c:set value="${auth}" var="auth"/>
                <div title="${auth.authName}">
                    <ul id="tree-${auth.dbid}"></ul>
                    <script type="text/javascript">
                        <%
                            Auth auth = (Auth) pageContext.getAttribute("auth");
                            String json = JSON.toJSONString(auth.getChildren());
                            pageContext.setAttribute("json",json);
                        %>
                        var treeData = '${json}';
                        treeData = JSON.parse(treeData);
                        console.log(treeData);
                        $("#tree-${auth.dbid}").tree({
                            data:treeData,
                            onClick:function (node) {
                                if(node.children.length==0){
                                    if($("#main-tab").tabs("exists",node.text)){
                                        $("#main-tab").tabs("select",node.text);
                                    }else{
                                        $("#main-tab").tabs("add",{
                                            title:node.text,
                                            content:"<iframe width='100%' height='100%' frameborder='0' src="+path+node.authURL+"/>",
                                            closable:true
                                        });
                                    }
                                }
                            }
                        })
                    </script>
                </div>
            </c:forEach>
        </div>
    </div>
    <div data-options="region:'center',title:'业务面板'">
        <div id="main-tab" class="easyui-tabs" data-options="fit:true"></div>
    </div>
    <div id="win" data-options="footer:'#footer'">
        <form id="pwdform" method="post">
            <input type="hidden" id="userId" name="userId" value="${sessionScope.user.success}"/>
            <input class="easyui-passwordbox" id="password" name="password" style="width:100%" data-options="label:'用户密码:'">
        </form>
        <div id="footer" style="text-align: center">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" data-options="iconCls:'icon-cancel'" style="width:80px">取消</a>
        </div>
    </div>
</body>
</html>
