
/**
 * 客户提交问卷
 * */
$(function () {

    $( "body" ).on( "click touchend", "#_submit", function () {
        _submit();

    } );

})



function _submit(){
    //获取所有试题div
   var $container=$("#content").children();

   //创建答案数组
    var answers=new Array($container.length);
    for(var i=0;i<$container.length;i++){
       //获取每个题目的id，同时也是题目div的id
      var qid=$( $container[i]).attr("id");
      var answer=new Object();
      answer.qid=qid;
      //标题
        answer.qtitle=$("#qtitle_"+qid).text();

      //答案
      answer.answer=($("input[name='name_"+qid+"']").val());

      //试卷编号
     answer.qno=$("#_hidden").val();

     answers[i]=answer;
    }

    //发送ajax请求
    sendData(JSON.stringify(answers));
}



/**
 * jquery发送请求
 * */
function sendData(datas){
    $(function(){
        //加载网络数据
        $.ajax({
            type:"get",
            url:"http://gsmzzk.com:8080/front/answer",
            data:{"answers":datas},
            async:true,
            dataType:"json",
            success:function(data){
                alert(data);
                //页面的跳转
                /*window.location.href="http://localhost:8080/findallques";*/
            }
        });
    })
}