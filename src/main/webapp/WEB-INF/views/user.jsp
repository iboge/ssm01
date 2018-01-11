<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="common.jsp"%>
    <script type="text/javascript" src="${path}/static/js/user.js"></script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',border:false" style="height:50px;padding:10px">
        <c:forEach items="${sessionScope.allAuths}" var="allAuth">
            <c:if test="${allAuth.dbid==14}">
                <input class="easyui-textbox" id="userName" data-options="prompt:'输入用户名'" style="width:15%">&nbsp;&nbsp;&nbsp;
                <input class="easyui-textbox" id="realName" data-options="prompt:'输入用户角色'" style="width:15%">&nbsp;&nbsp;&nbsp;
                <select class="easyui-combobox" id="valid" panelHeight="auto" data-options="editable:false" style="width:10%">
                    <option value="1">有效</option>
                    <option value="0">无效</option>
                </select>&nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchRole()" data-options="iconCls:'icon-search'">搜索</a>&nbsp;&nbsp;&nbsp;
            </c:if>
        </c:forEach>
        <c:forEach items="${sessionScope.allAuths}" var="allAuth">
            <c:if test="${allAuth.dbid==11}">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="add('添加')" data-options="iconCls:'icon-add'">添加</a>
            </c:if>
        </c:forEach>
    </div>
    <div id="win" data-options="footer:'#footer'">
        <form id="formAuth" method="post">
            <input type="hidden" id="dbid" name="dbid"/>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="uname" name="userName" style="width:100%" data-options="label:'用户名称:',required:true">
            </div>
            <div style="margin-bottom:5px">
                <input class="easyui-passwordbox" id="password" name="password" style="width:100%" data-options="label:'用户密码:'">
            </div>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="rname" name="realName" style="width:100%" data-options="label:'真实姓名:'">
            </div>
            <div style="margin-bottom:5px">
                <select class="easyui-combobox" panelHeight=auto data-options="editable:false" id="val" name="valid" label="用户状态:" style="width:100%">
                    <option value="1">有效</option>
                    <option value="0">无效</option>
                </select>
            </div>
        </form>
        <div id="footer" style="text-align:center;padding:5px 0">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" data-options="iconCls:'icon-ok'" style="width:80px">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" data-options="iconCls:'icon-cancel'" style="width:80px">取消</a>
        </div>
    </div>
    <div id="win1" class="easyui-window" title="授予角色" data-options="modal:true,closed:true,iconCls:'icon-edit',footer:'#footer1'" style="width:100%;padding:20px;">
        <input type="hidden" id="userId" name="userId"/>
        <table id="dg" class="easyui-datagrid"></table>
        <div id="footer1" style="text-align:center;padding:5px 0">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm1()" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm1()" data-options="iconCls:'icon-cancel'" style="width:80px">取消</a>
        </div>
    </div>
    <div data-options="region:'center'">
    <c:forEach items="${sessionScope.allAuths}" var="allAuth">
        <c:if test="${allAuth.dbid==14}">
            <table id="user-datagrid"></table>
        </c:if>
    </c:forEach>
    </div>
</body>
</html>
