function delTopic(id){
    if(window.confirm("是否确认删除？")){
        window.location.href='topic.do?oper=delTopic&id='+id;
    }
}