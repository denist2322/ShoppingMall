package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Repository.ProductRepository;
import com.mysite.shoppingMall.Vo.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public Product findProduct(Long id) {
        Product product = productRepository.findById(id).get();
        return product;
    }
}
