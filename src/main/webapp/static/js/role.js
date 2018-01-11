$(function () {
    initDataGrid();
});
function showGrantAuth2RoleWin(roleId) {
    console.log(roleId);
    $("#roleId").val(roleId);
    $("#tt").tree({
        url:path+'/auth/getAuthValid?roleId='+roleId,
        method:'get',
        animate:true,
        checkbox:true,
        cascadeCheck:false
    });
    $("#win1").window({
        modal:true,//灰色的隔层
        closed:true,//是否可关闭
        iconCls:'icon-edit',//图标
        width:"300px",
        height:"400px",
        title:"角色授权",
        left:'35%',
        top:'10%',
        collapsible:false,//折叠
        minimizable:false,
        maximizable:false,
        resizable:false
    }).window("open");
}
function submitForm1() {
    var nodes = $("#tt").tree("getChecked");
    if(nodes.length<1){
        $.messager.alert('提示','请选择需要的权限节点!','warning');
    }else{
        var authIds = [];
        var roleId = $("#roleId").val();
        for(var i=0;i<nodes.length;i++){
            authIds.push(nodes[i].id)
        }
        var formData = {
            roleId:roleId,
            authIds:authIds
        };
        $.ajax({
            url:path+"/role/grantAuth",
            methid:"get",
            data:formData,
            traditional:true,//解决传递数组参数时出现的问题
            success:function (res) {
                if(res.msg){
                    $("#win1").window("close");
                }
            },
            error:function (res) {}
        })
    }
}
function clearForm1() {
    $("#win1").window("close");
}
function add(res) {
    var param = res;
    if(param != "添加"){
        $("#dbid").val(res.dbid);
        $("#rname").textbox("setValue",res.roleName);
        $("#rcode").textbox("setValue",res.roleCode);
        $("#orders").textbox("setValue",res.orders);
        $("#val").combobox("setValue",res.valid);
        param='修改';
    }else {
        $("#dbid").val("");
    }
    $("#win").window({
        modal:true,//灰色的隔层
        closed:true,//是否可关闭
        iconCls:'icon-edit',//图标
        width:"260px",
        height:"220px",
        title:param+"角色",
        collapsible:false,
        minimizable:false,
        maximizable:false,
        resizable:false,
        onBeforeClose:function () {
            $("#formAuth").form("reset");
        }
    }).window("open");
}
function submitForm() {
    var formData = $("#formAuth").form().serialize();
    console.log(formData);
    console.log($("#rname").val());
    if($("#rname").val()!="") {
        $.ajax({
            url: path + '/role/insertRole',
            method: 'post',
            data: formData,
            success: function (res) {
                if (res.msg) {
                    $("#win").window("close");
                    $("#role-datagrid").datagrid("reload");
                }
            },
            error: function (res) {
            }
        })
    }else{
        $.messager.alert("警告","角色名称不能为空！","warning")
    }
}
function clearForm() {
    $("#win").window("close");
}
function searchRole() {
    $("#role-datagrid").datagrid("load",{
        roleName:$("#roleName").textbox("getValue"),
        roleCode:$("#roleCode").textbox("getValue"),
        valid:$("#valid").textbox("getValue")
    });
}
function initDataGrid() {
    $.ajax({
        url:path+'/role/queryRoleAuths',
        method:'post',
        success:function (res) {
            console.log("res.msg"+res.msg);
            if(res.msg){
                $('#role-datagrid').datagrid({
                    rownumbers:true,//显示行号
                    singleSelect:true,//单选
                    autoRowHeight:false,//自动行高
                    pagination:true,//是否分页
                    pageSize:10,//每页显示几条
                    fitColumns:true,
                    fit:true,
                    pageList:[3,5,10,20],
                    url:path+"/role/getRole",
                    method:'post',
                    columns:[[
                        {title:"角色名称",field:"roleName",width:"20%"},
                        {title:"角色编码",field:"roleCode",width:"20%"},
                        {title:"是否有效",field:"valid",formatter:function (value, row, index) {
                            if ( value == '1'){
                                return '有效'
                            }else {
                                return '<span style="color: red">无效</span>'
                            }},width:"20%"},
                        {title:"排序",field:"orders",width:"20%"},
                        {title:"授权",field:"dbid",formatter:function (value, row, index) {
                            return '<a href="javascript:void(0)" onclick="showGrantAuth2RoleWin('+value+')">授权</a>'
                        },width:"20%"}
                    ]],
                    onDblClickRow:function (index, row) {
                        add(row);
                    }
                });
            }else{
                $('#role-datagrid').datagrid({
                    rownumbers:true,//显示行号
                    singleSelect:true,//单选
                    autoRowHeight:false,//自动行高
                    pagination:true,//是否分页
                    pageSize:10,//每页显示几条
                    fitColumns:true,
                    fit:true,
                    pageList:[3,5,10,20],
                    url:path+"/role/getRole",
                    method:'post',
                    columns:[[
                        {title:"角色名称",field:"roleName",width:"20%"},
                        {title:"角色编码",field:"roleCode",width:"20%"},
                        {title:"是否有效",field:"valid",formatter:function (value, row, index) {
                            if ( value == '1'){
                                return '有效'
                            }else {
                                return '<span style="color: red">无效</span>'
                            }},width:"20%"},
                        {title:"排序",field:"orders",width:"20%"},
                        {title:"授权",field:"dbid",formatter:function (value, row, index) {
                            // return '<a href="javascript:void(0)" onclick="showGrantAuth2RoleWin('+value+')">授权</a>'
                        },width:"20%"}
                    ]],
                    onDblClickRow:function (index, row) {
                        // add(row);
                    }
                });
            }
        },
        error:function (res) {}
    })
    /*$('#role-datagrid').datagrid({
        rownumbers:true,//显示行号
        singleSelect:true,//单选
        autoRowHeight:false,//自动行高
        pagination:true,//是否分页
        pageSize:10,//每页显示几条
        fitColumns:true,
        fit:true,
        pageList:[3,5,10,20],
        url:path+"/role/getRole",
        method:'post',
        columns:[[
            {title:"角色名称",field:"roleName",width:"20%"},
            {title:"角色编码",field:"roleCode",width:"20%"},
            {title:"是否有效",field:"valid",formatter:function (value, row, index) {
                if ( value == '1'){
                    return '有效'
                }else {
                    return '<span style="color: red">无效</span>'
                }},width:"20%"},
            {title:"排序",field:"orders",width:"20%"},
            {title:"授权",field:"dbid",formatter:function (value, row, index) {
                return '<a href="javascript:void(0)" onclick="showGrantAuth2RoleWin('+value+')">授权</a>'
            },width:"20%"}
        ]],
        onDblClickRow:function (index, row) {
            // add(row);
        }
    });*/
}