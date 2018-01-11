function updatepwd(id) {
    console.log(id);
    $("#win").window({
        modal: true,//灰色的隔层
        closed: true,//是否可关闭
        iconCls: 'icon-edit',//图标
        width: "260px",
        height: "125px",
        title: "更改密码",
        collapsible: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        onBeforeClose: function () {
            $("#pwdform").form("reset");
        }
    }).window("open");
}
function submitForm() {
    var userId = $("#userId").val();
    console.log(userId);
    var pwd = $("#password").val();
    console.log(pwd);
    var formdata = {
        userId:userId,
        password:pwd
    };
    if(pwd!=""){
        $.ajax({
            url:path+'/user/updatePassword',
            method:'post',
            data:formdata,
            success:function (res) {
                if(res.msg){
                    $("#win").window("close")
                }else{}
            },
            error:function (res) {}
        });
    }else{
        $.messager.alert("警告","密码输入框不能为空！","warning")
    }
}
function clearForm() {
    $("#win").window("close");
}