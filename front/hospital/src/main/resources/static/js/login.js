$(function () {

    $("input[name='LoginButton']").click(function () {
        var username= $("#username").val();
        var pwd=$("#password").val();
        var quser=new Object();
        quser.username=username;
        quser.password=pwd;
        sendData(JSON.stringify(quser));
    });

});

//发送请求
/**
 * jquery发送请求
 * */
function sendData(datas){
    $(function(){
        //加载网络数据
        $.ajax({
            url:"login/submit",
            type:"post",
            data:{"quser":datas},
            async:true,
            dataType:"json",
            success:function(data){

               var code= data.resultCode;
               if(code>0){//登录成功
                   ///back/home
                   window.location.href="back/findallques";
               }else{
                   //登录失败
                   $("#_warn").text(data.msg);
               }
                //页面的跳转
                // window.location.href="http://localhost:8080/findallques";
            }
        });
    })
}