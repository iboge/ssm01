<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="common.jsp"%>
    <script type="text/javascript" src="${path}/static/js/role.js"></script>
</head>
<body class="easyui-layout">
    <div id="search" data-options="region:'north',border:false" style="height:50px;padding:10px">
        <c:forEach items="${sessionScope.allAuths}" var="allAuth">
            <c:if test="${allAuth.dbid==45}">
                <input class="easyui-textbox" id="roleName" data-options="prompt:'输入权限名称'" style="width:15%">&nbsp;&nbsp;&nbsp;
                <input class="easyui-textbox" id="roleCode" data-options="prompt:'输入权限编码'" style="width:15%">&nbsp;&nbsp;&nbsp;
                <select class="easyui-combobox" id="valid" panelHeight="auto" data-options="editable:false" style="width:10%">
                    <option value="1">有效</option>
                    <option value="0">无效</option>
                </select>&nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchRole()" data-options="iconCls:'icon-search'">搜索</a>&nbsp;&nbsp;&nbsp;
            </c:if>
        </c:forEach>
        <c:forEach items="${sessionScope.allAuths}" var="allAuth">
            <c:if test="${allAuth.dbid==44}">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="add('添加')" data-options="iconCls:'icon-add'">添加</a>
            </c:if>
        </c:forEach>
    </div>
    <div id="win" data-options="footer:'#footer'">
        <form id="formAuth" method="post">
            <input type="hidden" id="dbid" name="dbid"/>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="rname" name="roleName" style="width:100%" data-options="label:'角色名称:'">
            </div>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="rcode" name="roleCode" style="width:100%" data-options="label:'角色编码:'">
            </div>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="orders" name="orders" style="width:100%" data-options="label:'排序:'">
            </div>
            <div style="margin-bottom:5px">
                <select class="easyui-combobox" panelHeight=auto data-options="editable:false" id="val" name="valid" label="是否有效:" style="width:100%">
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
    <div id="win1" class="easyui-window" title="授权" data-options="modal:true,closed:true,iconCls:'icon-edit',footer:'#footer1'" style="width:100%;padding:20px;">
        <div class="easyui-panel" style="padding:5px">
            <input type="hidden" id="roleId" name="roleId"/>
            <ul id="tt" class="easyui-tree"></ul>
        </div>
        <div id="footer1" style="text-align:center;padding:5px 0">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm1()" data-options="iconCls:'icon-ok'" style="width:80px">授权</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm1()" data-options="iconCls:'icon-cancel'" style="width:80px">取消</a>
        </div>
    </div>
    <div data-options="region:'center'">
    <c:forEach items="${sessionScope.allAuths}" var="allAuth">
        <c:if test="${allAuth.dbid==45}">
            <table id="role-datagrid"></table>
        </c:if>
    </c:forEach>
    </div>
</body>
</html>
