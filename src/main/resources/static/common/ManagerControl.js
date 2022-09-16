function orderModify(id){
            var tmp = $("#selMod"+id).val();
            $.ajax({
                url : "/modifyOrder?id=" + id + "&nowState=" + tmp,
                type : "GET",
                success: function(result){
                    console.log("tmp:", tmp);
                    var tag = "";
                    tag += "<div id=\"divMod" + id + "\">";
                    if(tmp == 0){
                        tag += "<span>입금전</span>"
                    }
                    else if(tmp == 1){
                        tag += "<span>배송준비중</span>"
                    }
                    else if(tmp == 2){
                        tag += "<span>배송중</span>"
                    }
                    else if(tmp == 3){
                        tag += "<span>배송완료</span>"
                    }
                    tag += "</div>"

                    $("#divMod" + id).replaceWith(tag);
                },
                error: function(error){
                    console.log("에러발생");
                }
            });
        }

        function orderDelete(id){
            $.ajax({
                url : "/deleteOrder?id=" + id,
                type : "GET",
                success: function(result){
                    $('#divDel' + id).replaceWith("");
                },
                error: function(error){
                    console.log("에러발생");
                }
            });
        }