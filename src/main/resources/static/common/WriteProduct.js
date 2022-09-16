function productSubmit() {
        productForm.action = "/product/doWrite";
        productForm.method = "post";
        productForm.submit();
    }

    function productModify() {
        productForm.action = "/product/doModify";
        productForm.method = "post";
        productForm.submit();
    }

    function cancel() {
        history.back();
    }