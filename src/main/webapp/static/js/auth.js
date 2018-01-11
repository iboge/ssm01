function submitForm() {
    var formData = $("#formAuth").form().serialize();
    console.log(formData);
    console.log($("#authName").val());
    if($("#authName").val()!="") {
        $.ajax({
            url: path + '/auth/addOrUpdate',
            method: 'post',
            data: formData,
            success: function (res) {
                if (res.msg) {
                    $("#win").window("close");
                    $("#auth-treegrid").treegrid("reload");
                }
            },
            error: function (res) {
            }
        })
    }else{
        $.messager.alert("警告","权限名称不能为空！","warning")
    }
}
function clearForm() {
    $("#win").window("close");
}
function onContextMenu(e,row){
    if (row){
        e.preventDefault();
        $(this).treegrid('select', row.dbid);
        $('#mm').menu('show',{
            left: e.pageX,
            top: e.pageY
        });
    }
}
function addOrUpdateAuthForm(param) {
    var row = $("#auth-treegrid").treegrid("getSelected");
    if(row.parentId!=-1){
        var parent = $("#auth-treegrid").treegrid("getParent",row.dbid);
        $("#parentName").textbox("setValue",parent.authName);
    }
    if(param=='添加'){
        $("#dbid").val("");
        $("#parentName").textbox("setValue",row.authName);
        $("#layer").textbox("setValue",row.layer+1);
        $("#parentId").val(row.dbid);
    }else if(param=='编辑'){
        $("#layer").textbox("setValue",row.layer);
        $("#authName").textbox("setValue",row.authName);
        $("#authCode").textbox("setValue",row.authCode);
        $("#authURL").textbox("setValue",row.authURL);
        $("#orders").textbox("setValue",row.orders);
        $("#type").combobox("setValue",row.type);
        $("#valid").combobox("setValue",row.valid);
        $("#dbid").val(row.dbid);
        $("#parentId").val(row.parentId);
    }
    $("#win").window({
        modal:true,//灰色的隔层
        closed:true,//是否可关闭
        iconCls:'icon-edit',//图标
        width:"260px",
        height:"340px",
        title:"权限"+param,
        collapsible:false,
        minimizable:false,
        maximizable:false,
        resizable:false,
        onBeforeClose:function () {
            //清空表单数据（表单重置）
            $("#formAuth").form("reset");
        }
    }).window("open");
}
function refreshAuth() {
    $('#auth-treegrid').treegrid("reload");
}
$(function () {
    $("#auth-treegrid").treegrid({
        url:path+"/auth/getAllAuth",
        method:"get",//请求方式
        fitColumns:true,
        fit:true,//填充
        rownumbers:true,//显示行号
        idField:"dbid",//唯一字段
        treeField:"authName",//需要显示为tree的字段
        onContextMenu: onContextMenu,
        columns:[[
            {title:"权限名称",field:"authName",width:"18%"},
            {title:"权限编码",field:"authCode",width:"15%"},
            {title:"URL",field:"authURL",width:"15%"},
            {title:"类型",field:"type",width:"13%",formatter:function (value, row, index) {
                if ( value == '1'){
                    return '模块'
                }else {
                    return '资源'
                }
            }},
            {title:"排序",field:"orders",width:"13%"},
            {title:"是否有效",field:"valid",width:"13%",formatter:function (value) {
                if ( value == '1'){
                    return '有效'
                }else {
                    return '<span style="color: red">无效</span>'
                }
            }},
            {title:"层级",field:"layer",width:"13%"}
        ]]
    });
});