package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.OrderSheetForm;
import com.mysite.shoppingMall.Form.ProductBuyForm;
import com.mysite.shoppingMall.Form.ProductWriteForm;
import com.mysite.shoppingMall.Repository.ProductColorRepository;
import com.mysite.shoppingMall.Repository.ProductImageRepository;
import com.mysite.shoppingMall.Repository.ProductRepository;
import com.mysite.shoppingMall.Repository.ProductSizeRepository;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;
    private final ProductImageRepository productImageRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductColorRepository productColorRepository;


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

    public void readyForOrder(OrderSheetForm orderSheetForm, MallUser mallUser) {
        String[] emailTemp = Ut.splitEmail(mallUser.getUserEmail());
        orderSheetForm.setOrderSheetEmail1(emailTemp[0].trim());
        orderSheetForm.setOrderSheetEmail2(emailTemp[1].trim());

        String[] cellPhone = Ut.splitCellPhone(mallUser.getCellphone());
        orderSheetForm.setOrderSheetCellPhone1(cellPhone[0].trim());
        orderSheetForm.setOrderSheetCellPhone2(cellPhone[1].trim());
        orderSheetForm.setOrderSheetCellPhone3(cellPhone[2].trim());

        String[] Address = Ut.splitStar(mallUser.getHomeAddress());
        orderSheetForm.setOrderSheetReceiverAddress1(Address[3].trim()); //우편번호
        orderSheetForm.setOrderSheetReceiverAddress2(Address[0].trim()); //주소
        orderSheetForm.setOrderSheetReceiverAddress3(Address[1].trim()); //동
        orderSheetForm.setOrderSheetReceiverAddress4(Address[2].trim()); //상세주소
    }

    @Transactional
    public List<Product> searchTitleAndBody(String keyword) {
        List<Product> productList = productRepository.findByTitleAndBody(keyword);
        return productList;
    }

    public void doWrite(List<MultipartFile> mainImage, List<MultipartFile> detailImage, ProductWriteForm productWriteForm) {
        Product product = new Product();
        product.setRegDate(LocalDateTime.now());
        product.setTitle(productWriteForm.getTitle());
        product.setBody(productWriteForm.getBody());
        product.setPrice(productWriteForm.getPrice());
        product.setDiscount(productWriteForm.getDiscount());
        product.setCategory(productWriteForm.getCategory());
        product.setMainImage(mainImage.get(0).getOriginalFilename());
        fileService.doUpload(productWriteForm, mainImage); // 메인 이미지 업로드
        productRepository.save(product);

        for (int i = 0; i < detailImage.size(); i++) {
            ProductImage productImage = new ProductImage();
            productImage.setImages(detailImage.get(i).getOriginalFilename());
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        }
        fileService.doUpload(productWriteForm, detailImage); // 디테일 이미지 업로드

        String[] tmp = Ut.splitStar(productWriteForm.getColor());
        for (int i = 0; i < tmp.length; i++) {
            ProductColor productColor = new ProductColor();
            productColor.setProductColor(tmp[i].trim());
            productColor.setProduct(product);
            productColorRepository.save(productColor);
        }

        tmp = Ut.splitStar(productWriteForm.getSize());
        for (int i = 0; i < tmp.length; i++) {
            ProductSize productSize = new ProductSize();
            productSize.setProductSize(tmp[i].trim());
            productSize.setProduct(product);
            productSizeRepository.save(productSize);
        }

    }

    public boolean isExists(Long id) {
        if (productRepository.existsById(id)) {
            return true;
        }
        return false;
    }

    public void doDelete(Long id) {
        Product product = productRepository.findById(id).get();
        // 파일 삭제
        fileService.deleteFile(product);
        // 한번에 같이 삭제된다. OneToMany 에서 cascade = CascadeType.REMOVE 했기 때문
        productRepository.delete(product);

    }

    public List<Product> getList() {
        return productRepository.findAll();
    }

    public String setColorString(List<ProductColor> productColorList) {
        String color = "";
        for(int i = 0 ; i < productColorList.size(); i++){
            if(i == productColorList.size()-1){
                color += productColorList.get(i).getProductColor();
                break;
            }
            color += productColorList.get(i).getProductColor() + "**";
        }
        return color;
    }

    public String setSizeString(List<ProductSize> productSizeList) {
        String size = "";
        for(int i = 0 ; i < productSizeList.size(); i++){
            if(i == productSizeList.size()-1){
                size += productSizeList.get(i).getProductSize();
                break;
            }
            size += productSizeList.get(i).getProductSize() + "**";
        }
        return size;
    }
}
