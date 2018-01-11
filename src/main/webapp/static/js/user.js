$(function () {
    initDataGrid();
});
function showGrantAuth2RoleWin(userId) {
    console.log(userId);
    $("#userId").val(userId);
    $("#dg").datagrid({
        url:path+'/role/getRoleValid?userId='+userId,
        method:'get',
        animate:true,
        rownumbers:true,
        onLoadSuccess:function () {
            console.log($(this).datagrid("getData"));
            var rows = $(this).datagrid("getData").rows;
            for(var i = 0; i < rows.length; i++){
                if(rows[i].checked){
                    $(this).datagrid("selectRow",i);
                }
            }
        },
        columns:[[
            {checkbox:true,field:"id"},
            {title:"角色名称",field:"rname"},
            {title:"角色编码",field:"rcode"}
        ]]
    });
    //延时
    /*setTimeout(function () {
        console.log("-----");
    },500);*/
    $("#win1").window({
        modal:true,//灰色的隔层
        closed:true,//是否可关闭
        iconCls:'icon-edit',//图标
        width:"300px",
        height:"400px",
        title:"授予角色",
        left:'35%',
        top:'10%',
        collapsible:false,//折叠
        minimizable:false,
        maximizable:false,
        resizable:false
    }).window("open");
}
function submitForm1() {
    var nodes = $("#dg").datagrid("getSelections");
    console.log(nodes);
    // return;
    if(nodes.length<1){
        $.messager.alert('提示','请选择需要的角色!','warning');
    }else{
        var userIds = [];
        var userId = $("#userId").val();
        for(var i=0;i<nodes.length;i++){
            userIds.push(nodes[i].id)
        }
        var formData = {
            userId:userId,
            userIds:userIds
        };
        $.ajax({
            url:path+"/user/grantRole",
            method:"get",
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
        $("#uname").textbox("setValue",res.userName);
        $("#password").textbox("setValue","");
        $("#rname").textbox("setValue",res.realName);
        $("#val").combobox("setValue",res.valid);
        param='修改';
    }else {
        $("#dbid").val("");
    }
    var cs = param;
    $("#win").window({
        modal:true,//灰色的隔层
        closed:true,//是否可关闭
        iconCls:'icon-edit',//图标
        width:"260px",
        height:"220px",
        title:param+"用户",
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
    console.log($("#uname").val());
    if($("#uname").val()!=""){
        $.ajax({
            url:path+'/user/insertUser',
            method:'post',
            data:formData,
            success:function (res) {
                if(res.msg){
                    $("#win").window("close");
                    $("#user-datagrid").datagrid("reload");
                }
            },
            error:function (res) {}
        })
    }else{
        $.messager.alert("警告","用户名称不能为空！","warning")
    }
}
function clearForm() {
    $("#win").window("close");
}
function searchRole() {
    $("#user-datagrid").datagrid("load",{
        userName:$("#userName").textbox("getValue"),
        realName:$("#realName").textbox("getValue"),
        valid:$("#valid").textbox("getValue")
    });
}
function initDataGrid() {
    $.ajax({
        url:path+'/auth/queryUserAuths',
        method:'post',
        success:function (res) {
            console.log("res.msg"+res.msg);
            if(res.msg){
                $('#user-datagrid').datagrid({
                    rownumbers:true,//显示行号
                    singleSelect:true,//单选
                    autoRowHeight:false,//自动行高
                    pagination:true,//是否分页
                    pageSize:10,//每页显示几条
                    fit:true,
                    fitColumns:true,
                    pageList:[3,5,10,20],
                    url:path+"/user/getUser",
                    method:'post',
                    columns:[[
                        {title:"用户名称",field:"userName",width:30},
                        {title:"用户角色",field:"realName",width:30},
                        {title:"是否有效",field:"valid",width:30,formatter:function (value, row, index) {
                            if ( value == '1'){
                                return '有效'
                            }else {
                                return '<span style="color: red">无效</span>'
                            }}},
                        {title:"授予角色",field:"dbid",formatter:function (value, row, index) {
                            return '<a href="javascript:void(0)" onclick="showGrantAuth2RoleWin('+value+')">授予角色</a>'
                        },width:30},
                    ]],
                    onDblClickRow:function (index, row) {
                        add(row);
                    }
                });
            }else{
                $('#user-datagrid').datagrid({
                    rownumbers:true,//显示行号
                    singleSelect:true,//单选
                    autoRowHeight:false,//自动行高
                    pagination:true,//是否分页
                    pageSize:10,//每页显示几条
                    fit:true,
                    fitColumns:true,
                    pageList:[3,5,10,20],
                    url:path+"/user/getUser",
                    method:'post',
                    columns:[[
                        {title:"用户名称",field:"userName",width:30},
                        {title:"用户角色",field:"realName",width:30},
                        {title:"是否有效",field:"valid",width:30,formatter:function (value, row, index) {
                            if ( value == '1'){
                                return '有效'
                            }else {
                                return '<span style="color: red">无效</span>'
                            }}},
                        {title:"授予角色",field:"dbid",formatter:function (value, row, index) {
                            // return '<a href="javascript:void(0)" onclick="showGrantAuth2RoleWin('+value+')">授予角色</a>'
                        },width:30},
                    ]],
                    onDblClickRow:function (index, row) {
                        // add(row);
                    }
                });
            }
        },
        error:function (res) {}
    });

    /*$('#user-datagrid').datagrid({
        rownumbers:true,//显示行号
        singleSelect:true,//单选
        autoRowHeight:false,//自动行高
        pagination:true,//是否分页
        pageSize:10,//每页显示几条
        fit:true,
        fitColumns:true,
        pageList:[3,5,10,20],
        url:path+"/user/getUser",
        method:'post',
        columns:[[
            {title:"用户名称",field:"userName",width:30},
            {title:"用户角色",field:"realName",width:30},
            {title:"是否有效",field:"valid",width:30,formatter:function (value, row, index) {
                if ( value == '1'){
                    return '有效'
                }else {
                    return '<span style="color: red">无效</span>'
                }}},
            {title:"授予角色",field:"dbid",formatter:function (value, row, index) {
                return '<a href="javascript:void(0)" onclick="showGrantAuth2RoleWin('+value+')">授予角色</a>'
            },width:30},
        ]],
        onDblClickRow:function (index, row) {
            $.ajax({
                url:path+'/auth/queryUserAuths',
                method:'post',
                success:function (res) {
                    console.log("res.msg"+res.msg);
                    if(res.msg){
                        add(row);
                    }
                },
                error:function (res) {}
            })
            // add(row);
        }
    });*/
}