<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="common.jsp"%>
    <script type="text/javascript" src="${path}/static/js/auth.js"></script>
</head>
<body>
    <table id="auth-treegrid"></table>
    <div id="mm" class="easyui-menu" style="width:120px;">
        <div onclick="addOrUpdateAuthForm('添加')" data-options="iconCls:'icon-add'">增加</div>
        <div onclick="addOrUpdateAuthForm('编辑')" data-options="iconCls:'icon-edit'">编辑</div>
        <div onclick="refreshAuth()" data-options="iconCls:'icon-reload'">刷新</div>
    </div>
    <div id="win" data-options="footer:'#footer'">
        <form id="formAuth" method="post">
            <input type="hidden" id="dbid" name="dbid"/>
            <input type="hidden" id="parentId" name="parentId"/>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="parentName" style="width:100%" data-options="label:'上级节点:',readonly:true">
            </div>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="layer" name="layer" style="width:100%" data-options="label:'当前层次:',readonly:true">
            </div>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="authName" name="authName" style="width:100%" data-options="label:'权限名称:',required:true">
            </div>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="authCode" name="authCode" style="width:100%" data-options="label:'权限编码:'">
            </div>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="authURL" name="authURL" style="width:100%" data-options="label:'URL:'">
            </div>
            <div style="margin-bottom:5px">
                <input class="easyui-textbox" id="orders" name="orders" style="width:100%" data-options="label:'排序:'">
            </div>
            <div style="margin-bottom:5px">
                    <select class="easyui-combobox" panelHeight=auto data-options="editable:false" id="type" name="type" label="类型:" style="width:100%">
                        <option value="1">模块</option>
                        <option value="2">资源</option>
                    </select>
            </div>
            <div style="margin-bottom:5px">
                <select class="easyui-combobox" panelHeight=auto data-options="editable:false" id="valid" name="valid" label="是否有效:" style="width:100%">
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
</body>
</html>
