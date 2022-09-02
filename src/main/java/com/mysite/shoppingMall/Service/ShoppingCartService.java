package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.ProductBuyForm;
import com.mysite.shoppingMall.Repository.ProductRepository;
import com.mysite.shoppingMall.Repository.ShoppingCartRepository;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Ut.IsLogined;
import com.mysite.shoppingMall.Entity.MallUser;
import com.mysite.shoppingMall.Entity.Product;
import com.mysite.shoppingMall.Entity.ShoppingCart;
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
        System.out.println(productBuyForm.getOrderCounter());
        IsLogined isLogined = Ut.isLogined(session);

        MallUser mallUser = userRepository.findById(isLogined.getUserId()).get();
        Product product = productRepository.findById((long)productBuyForm.getProductsId()).get();

        if(shoppingCartRepository.existsByProductIdAndCartColor(productBuyForm.getProductsId(), productBuyForm.getOrderColor())) {
            ShoppingCart shoppingCart = shoppingCartRepository.findByProductIdAndCartColor(productBuyForm.getProductsId(), productBuyForm.getOrderColor());
            shoppingCart.setCartCount(productBuyForm.getOrderCounter());
            shoppingCartRepository.save(shoppingCart);
            return;
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setMallUser(mallUser);
        shoppingCart.setProduct(product);
        shoppingCart.setCartColor(productBuyForm.getOrderColor());
        shoppingCart.setCartSize(productBuyForm.getOrderSize());
        shoppingCart.setCartCount(productBuyForm.getOrderCounter());
        shoppingCart.setCartTotalPrice(productBuyForm.getOrderTotalPrice());
        shoppingCartRepository.save(shoppingCart);
    }

    public void deleteCart(long id) {
        ShoppingCart cart = shoppingCartRepository.findById(id).get();
        shoppingCartRepository.delete(cart);
    }
}
