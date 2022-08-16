package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.ProductBuyForm;
import com.mysite.shoppingMall.Repository.ProductRepository;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.MallUser;
import com.mysite.shoppingMall.Vo.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public Product findProduct(Long id) {
        Product product = productRepository.findById(id).get();
        return product;
    }

    public List<Product> findCategory(String category) {
        List<Product> productList = productRepository.findByCategory(category).orElseGet(null);
        return productList;
    }

    public void setRandomNum(ProductBuyForm productBuyForm) {
        int orderNumber = (int) (Math.random() * 10000000);
        productBuyForm.setOrderNumber(orderNumber);
    }

    public int calcPrice(Product product, Long id) {
        return (int) (product.getPrice() * (1 - (double) product.getDiscount() / 100));
    }

    public void readyForOrder(ProductBuyForm productBuyForm, MallUser mallUser) {
        String[] emailTemp = Ut.splitEmail(mallUser.getUserEmail());
        productBuyForm.setOrderEmail1(emailTemp[0].trim());
        productBuyForm.setOrderEmail2(emailTemp[1].trim());

        String[] cellPhone = Ut.splitCellPhone(mallUser.getCellphone());
        productBuyForm.setOrderCellPhone1(cellPhone[0].trim());
        productBuyForm.setOrderCellPhone2(cellPhone[1].trim());
        productBuyForm.setOrderCellPhone3(cellPhone[2].trim());

        String[] Address = Ut.splitAddress(mallUser.getHomeAddress());
        productBuyForm.setOrderAddress1(Address[3].trim()); //우편번호
        productBuyForm.setOrderAddress2(Address[0].trim()); //주소
        productBuyForm.setOrderAddress3(Address[1].trim()); //동
        productBuyForm.setOrderAddress4(Address[2].trim()); //상세주소
    }
}
