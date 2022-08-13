package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.ProductBuyForm;
import com.mysite.shoppingMall.Repository.ProductRepository;
import com.mysite.shoppingMall.Repository.ShoppingCartRepository;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.IsLogined;
import com.mysite.shoppingMall.Vo.MallUser;
import com.mysite.shoppingMall.Vo.Product;
import com.mysite.shoppingMall.Vo.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ShoppingCart> getCartList(Integer userId) {
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findByMallUserId(userId);
        return shoppingCartList;
    }

    public void addCart(ProductBuyForm productBuyForm, HttpSession session) {
        IsLogined isLogined = Ut.isLogined(session);

        MallUser mallUser = userRepository.findById(isLogined.getUserId()).get();
        Product product = productRepository.findById((long)productBuyForm.getProductId()).get();

        if(shoppingCartRepository.existsByProductIdAndCartColor(productBuyForm.getProductId(), productBuyForm.getOrderColor())) {
            ShoppingCart shoppingCart = shoppingCartRepository.findByProductIdAndCartColor(productBuyForm.getProductId(), productBuyForm.getOrderColor());
            shoppingCart.setCartCount(productBuyForm.getOrderCounter());
            shoppingCartRepository.save(shoppingCart);
            return;
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setMallUser(mallUser);
        shoppingCart.setProduct(product);
        shoppingCart.setCartColor(productBuyForm.getOrderColor());
        shoppingCart.setCartSize(productBuyForm.getOrderSize());
        shoppingCart.setCartCount(2);
        shoppingCartRepository.save(shoppingCart);
    }
}
