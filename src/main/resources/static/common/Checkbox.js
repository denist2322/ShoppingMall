  function checkPrice(price, chId){
    let result = "";
    if($("#chk"+chId).is(":checked")){
        $.ajax({
            url : "/isChecked?check=1&id=" + chId,
            type : "GET",
            success : function(data){
                chTotalPrice += price;
                result = "<div class=\"pl-3\" id=\"totalPrice\">" + chTotalPrice +"</div>";
                $("#totalPrice").replaceWith(result);
                setPrice();
            }
        });
    }
    else{
        $.ajax({
            url : "/isChecked?check=0&id=" + chId,
            type : "GET",
            success : function(data){
                chTotalPrice -= price;
                result = "<div class=\"pl-3\" id=\"totalPrice\">" + chTotalPrice +"</div>";
                $("#totalPrice").replaceWith(result);
                setPrice();
            }
        });
    }
}

function setPrice(){
    let shippingCost = 0
    let result = "";
    if(chTotalPrice >= 50000 || chTotalPrice === 0) {
        result = "<div class=\"pl-3\" id=\"deliveryPrice\">0</div>";
        $("#deliveryPrice").replaceWith(result);
    }
    else{
        shippingCost = 3000;
        result = "<div class=\"pl-3\" id=\"deliveryPrice\">3000</div>";
        $("#deliveryPrice").replaceWith(result);

    }
    let finalTotalPrice = chTotalPrice + shippingCost;
    result = "<div class=\"pl-3\" id=\"finalTotalPrice\">" + finalTotalPrice + "</div>";
    $("#finalTotalPrice").replaceWith(result);
}


$(document).ready(function() {
    $("#cbx_chkAll").click(function() {
        if($("#cbx_chkAll").is(":checked")) {
        $("input[type=checkbox]").prop("checked", true);
        $.ajax({
                url : "/allChecked?check=1",
                type : "GET",
                success : function(data){
                    console.log(data)
                    chTotalPrice = data[0];
                    result = "<div class=\"pl-3\" id=\"totalPrice\">" + chTotalPrice +"</div>";
                    $("#totalPrice").replaceWith(result);
                    setPrice();
                }
            });
        }
        else {
        $("input[type=checkbox]").prop("checked", false);
        $.ajax({
                url : "/allChecked?check=0",
                type : "GET",
                success : function(data){
                    chTotalPrice = data[0];
                    result = "<div class=\"pl-3\" id=\"totalPrice\">" + chTotalPrice +"</div>";
                    $("#totalPrice").replaceWith(result);
                    setPrice();
                }
            });
        }
    });
});
