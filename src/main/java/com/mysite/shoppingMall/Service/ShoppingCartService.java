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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    // 유저의 쇼핑 카트 정보를 가져온다.
    public List<ShoppingCart> getCartList(Integer userId) {
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findByMallUserId(userId);
        return shoppingCartList;
    }

    // 쇼핑 카트에 목록을 추가한다.
    public void addCart(ProductBuyForm productBuyForm, HttpSession session) {
        IsLogined isLogined = Ut.isLogined(session);

        MallUser mallUser = userRepository.findById(isLogined.getUserId()).get();
        Product product = productRepository.findById((long) productBuyForm.getProductsId()).get();

        if (shoppingCartRepository.existsByProductIdAndCartColor(productBuyForm.getProductsId(), productBuyForm.getOrderColor())) {
            ShoppingCart shoppingCart = shoppingCartRepository.findByProductIdAndCartColor(productBuyForm.getProductsId(), productBuyForm.getOrderColor());
            shoppingCart.setCartCount(productBuyForm.getOrderCounter());
            shoppingCart.setCartTotalPrice(productBuyForm.getOrderTotalPrice());
            shoppingCart.setCartSize(productBuyForm.getOrderSize());
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
    
    // 오버로딩 기법 사용
    // 쇼핑 카트에서 목록을 삭제한다. (id로 삭제)
    public void deleteCart(long id) {
        ShoppingCart cart = shoppingCartRepository.findById(id).get();
        shoppingCartRepository.delete(cart);
    }

    // 쇼핑 카트에서 목록을 삭제한다. (객체를 이용하여 삭제)
    public void deleteCart(ShoppingCart shoppingCart) {
        shoppingCartRepository.delete(shoppingCart);
    }

    // 체크박스의 체크 여부를 변환한다.
    public void changeChecked(int check, long id) {
        ShoppingCart cart = shoppingCartRepository.findById(id).get();
        cart.setChecked(check);
        shoppingCartRepository.save(cart);
    }

    // 체크박스의 전체 체크 여부를 변환한다.
    public void changeChecked(int check, HttpSession session) {
        MallUser user = userService.getUser(session);
        List<ShoppingCart> carts = shoppingCartRepository.findByMallUserId(user.getId());
        for(ShoppingCart cart : carts){
            cart.setChecked(check);
            shoppingCartRepository.save(cart);
        }
    }

    // 장바구니 가격을 계산함 (체크박스를 한번 체크하면 계속 기억하기 때문에 사전에 가격 계산이 필요하다.)
    public List<Integer> getPriceList(Integer userId) {
        // list 0번째 : 상품금액 , 1번째 : 배송비, 2번째 : 총 비용
        List<ShoppingCart> cartList = getCheckedCartList(userId);
        List<Integer> prices = new ArrayList<>();
        int productPrice = 0;

        for (int i = 0; i < cartList.size(); i++) {
            productPrice += cartList.get(i).getCartTotalPrice();
        }

        prices.add(productPrice);

        if (productPrice >= 50000 || cartList.size() == 0) {
            prices.add(0);
            prices.add(productPrice);
        } else {
            prices.add(3000);
            prices.add(productPrice + 3000);
        }
        return prices;
    }

    public List<ShoppingCart> getCheckedCartList(Integer userId) {
        return shoppingCartRepository.findByMallUserIdAndChecked(userId, 1);
    }


}
