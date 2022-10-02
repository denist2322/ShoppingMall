function order(){
            orderForm.action = "/product/orderSheetTmp";
            orderForm.method = "get";
            orderForm.submit();
        }

        function cart(){
            orderForm.action = "/addCart";
            orderForm.method = "get";
            orderForm.submit();
        }

        // 옷의 색상을 선택하면 화면에 보여줌.
         $(function() {
            $("#color").change(function() {
                $("#resultColor").css("background", $("option:selected").val().toLowerCase());
           });
         });

         // 선택한 사이즈 화면에 보여줌
         $(function() {
            $("#size").change(function() {
                let removeColor = $("#color").val();
                let result = $("option:selected").text().replace(removeColor,"");
                result = result.replace("색상을 선택하세요.","");
                if(result != "사이즈를 선택하세요."){
                    $("#resultSize").text(result);
                }
           });
         });
            let count = 1;
            let result = ""
            function minus() {
            if(count > 1) {
             count--;

             result = "<div id=\"count\">";
             result += "<input type=\"text\" id=\"inputCount\" name=\"orderCounter\" class=\"w-8 h-8 bg-white text-black text-bold font-xl text-center focus:outline-none\" value=\"" + count + "\" readonly>";
             result += "<div>";

             $("#count").replaceWith(result);
             replace();
              }
             }

             function plus() {
             count++;
             result = "<div id=\"count\">";
             result += "<input type=\"text\" id=\"inputCount\" name=\"orderCounter\" class=\"w-8 h-8 bg-white text-black text-bold font-xl text-center focus:outline-none\" value=\"" + count + "\" readonly>";
             result += "<div>";

             $("#count").replaceWith(result);
             replace();
             }

             function replace(){
             let count = $("#inputCount").val();
             let result = "<input type=\"text\" id=\"totalPrice\" name=\"orderTotalPrice\" class=\"flex px-auto py-2 box-border bg-gray-200 font-bold text-right focus:outline-none\" value=\"" + (count*price) + "\"readonly/>";
             $("#totalPrice").replaceWith(result);

             result =  "<input class=\"text-right text-xl text-yellow-600 focus:outline-none\" id=\"expect_price\" value=\"" + (count*price) + "\"readonly/>"
             $("#expect_price").replaceWith(result);
            }
