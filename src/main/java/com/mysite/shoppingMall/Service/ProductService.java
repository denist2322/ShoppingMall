package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Entity.*;
import com.mysite.shoppingMall.Form.JoinForm;
import com.mysite.shoppingMall.Form.OrderSheetForm;
import com.mysite.shoppingMall.Form.ProductBuyForm;
import com.mysite.shoppingMall.Form.ProductWriteForm;
import com.mysite.shoppingMall.Repository.*;
import com.mysite.shoppingMall.Ut.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;
    private final ProductImageRepository productImageRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductColorRepository productColorRepository;
    private final UserService userService;
    private final OrderSheetRepository orderSheetRepository;
    private final ShoppingCartService shoppingCartService;

    // id에 따른 단건 제품을 조회한다.
    public Product findProduct(Long id) {
        Product product = productRepository.findById(id).get();
        return product;
    }
    // 카테고리에 따른 제품을 조회한다.
    public List<Product> findCategory(String category) {
        List<Product> productList = productRepository.findByCategory(category).orElseGet(null);
        return productList;
    }
    // 실제로 주문 처리 과정 전에 필요한 정보를 설정한다.
    public void setOrderNum(ProductBuyForm productBuyForm) {
        int orderNumber = (int) (Math.random() * 10000000);
        productBuyForm.setOrderNumber(orderNumber);
        if (productBuyForm.getOrderTotalPrice() < 50000) {
            productBuyForm.setShippingCost(3000);
        }
    }
    // 할인율을 계산해 준다.
    public int calcPrice(Product product, Long id) {
        return (int) (product.getPrice() * (1 - (double) product.getDiscount() / 100));
    }
    // 주문서에 출력할 정보를 사전에 셋팅해준다.
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
    // 제품 검색 기능
    @Transactional
    public List<Product> searchTitleAndBody(String keyword) {
        List<Product> productList = productRepository.findByTitleAndBody(keyword);
        return productList;
    }
    // 제품을 실제로 작성한다.
    public void doWrite(List<MultipartFile> mainImage, List<MultipartFile> detailImage, ProductWriteForm productWriteForm) {
        Product product = new Product();

        product.setRegDate(LocalDateTime.now());
        product.setMainImage(mainImage.get(0).getOriginalFilename());
        fileService.doUpload(productWriteForm, mainImage); // 메인 이미지 업로드
        createAndModify(productWriteForm, product);
        productRepository.save(product);
        colorAndSize(productWriteForm, product);

        for (int i = 0; i < detailImage.size(); i++) {
            ProductImage productImage = new ProductImage();
            productImage.setImages(detailImage.get(i).getOriginalFilename());
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        }
        fileService.doUpload(productWriteForm, detailImage); // 디테일 이미지 업로드

    }
    // 제품 정보 수정을 진행한다.
    public void doModify(ProductWriteForm productWriteForm) {
        Product product = productRepository.findById(productWriteForm.getId()).orElse(null);

        String root = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\product_image\\";
        // 1. 원본 폴더
        File file = new File(root + product.getTitle());
        // 2. 새로운 폴더
        File newFile = new File(root + productWriteForm.getTitle());
        // 폴더 이름을 변경해줌.
        file.renameTo(newFile);

        createAndModify(productWriteForm, product);

        productRepository.save(product);
        for(int i = 0; i< product.getProductColorList().size(); i++){
            productColorRepository.delete(product.getProductColorList().get(i));
        }
        for(int i = 0; i< product.getProductSizeList().size(); i++){
            productSizeRepository.delete(product.getProductSizeList().get(i));
        }
        colorAndSize(productWriteForm, product);
    }
    // 중복으로 사용되는 제품 작성 및 수정 부분을 메소드로 따로 빼 줌.
    private void createAndModify(ProductWriteForm productWriteForm, Product product) {
        product.setTitle(productWriteForm.getTitle());
        product.setBody(productWriteForm.getBody());
        product.setPrice(productWriteForm.getPrice());
        product.setDiscount(productWriteForm.getDiscount());
        product.setCategory(productWriteForm.getCategory());
    }
    // 중복으로 사용되는 제품 색상 과 사이즈의 제품 작성 및 수정 부분을 메소드로 따로 빼 줌.
    private void colorAndSize(ProductWriteForm productWriteForm, Product product) {
        // == 색상 업로드 == (**을 통해 구분받는다.)
        String[] tmp = Ut.splitStar(productWriteForm.getColor());
        for (int i = 0; i < tmp.length; i++) {
            ProductColor productColor = new ProductColor();
            productColor.setProductColor(tmp[i].trim());
            productColor.setProduct(product);
            productColorRepository.save(productColor);
        }
        // == 사이즈 업로드 == (**을 통해 구분받는다.)
        tmp = Ut.splitStar(productWriteForm.getSize());
        for (int i = 0; i < tmp.length; i++) {
            ProductSize productSize = new ProductSize();
            productSize.setProductSize(tmp[i].trim());
            productSize.setProduct(product);
            productSizeRepository.save(productSize);
        }
    }
    // 제품 게시물이 존재하는지 확인한다.
    public boolean isExists(Long id) {
        if (productRepository.existsById(id)) {
            return true;
        }
        return false;
    }
    // 제품 게시물을 삭제한다.
    public void doDelete(Long id) {
        Product product = productRepository.findById(id).get();
        // 파일 삭제
        fileService.deleteFile(product);
        // 한번에 같이 삭제된다. OneToMany 에서 cascade = CascadeType.REMOVE 했기 때문
        productRepository.delete(product);

    }

    // 관리자 페이지에 상품리스트를 가져온다.
    public List<Product> getList() {
        return productRepository.findAll();
    }

    // 수정 부분에 출력해주기 위해 색상 문자열을 사전에 수정해준다. (색상은 **로 구분된다.)
    public String setColorString(List<ProductColor> productColorList) {
        String color = "";
        for (int i = 0; i < productColorList.size(); i++) {
            if (i == productColorList.size() - 1) {
                color += productColorList.get(i).getProductColor();
                break;
            }
            color += productColorList.get(i).getProductColor() + "**";
        }
        return color;
    }

    // 수정 부분에 출력해주기 위해 사이즈 문자열을 사전에 수정해준다. (사이즈는 **로 구분된다.)
    public String setSizeString(List<ProductSize> productSizeList) {
        String size = "";
        for (int i = 0; i < productSizeList.size(); i++) {
            if (i == productSizeList.size() - 1) {
                size += productSizeList.get(i).getProductSize();
                break;
            }
            size += productSizeList.get(i).getProductSize() + "**";
        }
        return size;
    }

    // 작성된 주문을 저장한다. (단건)
    public void saveOrder(OrderSheetForm orderSheetForm, HttpSession session) {
        OrderSheet orderSheet = new OrderSheet();
        setOrderForm(orderSheet,orderSheetForm);
        orderSheet.setSheetProductColor(orderSheetForm.getOrderSheetColor());
        orderSheet.setSheetProductSize(orderSheetForm.getOrderSheetSize());
        orderSheet.setSheetProductCount(orderSheetForm.getOrderSheetCount());
        orderSheet.setProductCost(orderSheetForm.getOrderSheetProductCost());

        MallUser malluser = userService.getUser(session);
        orderSheet.setMallUser(malluser);

        Product product = productRepository.findById(orderSheetForm.getProductsId()).get();
        orderSheet.setProduct(product);

        orderSheetRepository.save(orderSheet);

    }

    // 작성된 주문을 저장한다. (2건 이상)
    public void saveCartOrder(OrderSheetForm orderSheetForm, HttpSession session) {
        MallUser malluser = userService.getUser(session);
        List<ShoppingCart> shoppingCartList = shoppingCartService.getCheckedCartList(malluser.getId());

        for(int i = 0; i < shoppingCartList.size(); i++){
            OrderSheet orderSheet = new OrderSheet();
            setOrderForm(orderSheet, orderSheetForm);
            orderSheet.setSheetProductColor(shoppingCartList.get(i).getCartColor());
            orderSheet.setSheetProductSize(shoppingCartList.get(i).getCartSize());
            orderSheet.setSheetProductCount(shoppingCartList.get(i).getCartCount());
            orderSheet.setProductCost(shoppingCartList.get(i).getCartTotalPrice());
            orderSheet.setMallUser(malluser);
            orderSheet.setProduct(shoppingCartList.get(i).getProduct());

            orderSheetRepository.save(orderSheet);
            shoppingCartService.deleteCart(shoppingCartList.get(i));
        }
    }

    // 주문서 테이블에 정보를 추가한다.
    private void setOrderForm(OrderSheet orderSheet, OrderSheetForm orderSheetForm) {
        orderSheet.setSheetNumber(orderSheetForm.getOrderSheetNumber());
        String orderEmail = orderSheetForm.getOrderSheetEmail1() + "@" + orderSheetForm.getOrderSheetEmail2();
        orderSheet.setSheetOrdererEmail(orderEmail);
        orderSheet.setSheetOrdererName(orderSheetForm.getOrderSheetName());
        String orderPhone = orderSheetForm.getOrderSheetCellPhone1() + "-" + orderSheetForm.getOrderSheetCellPhone2() + "-" + orderSheetForm.getOrderSheetCellPhone3();
        orderSheet.setSheetOrdererPhone(orderPhone);
        orderSheet.setSheetReceiverName(orderSheetForm.getOrderSheetReceiver());
        String receiverPhone = orderSheetForm.getOrderSheetReceiverPhone1() + "-" + orderSheetForm.getOrderSheetReceiverPhone2() + "-" + orderSheetForm.getOrderSheetCellPhone3();
        orderSheet.setSheetReceiverPhone(receiverPhone);
        String address = orderSheetForm.getOrderSheetReceiverAddress2() + " " + orderSheetForm.getOrderSheetReceiverAddress3() + " " + orderSheetForm.getOrderSheetReceiverAddress4() + " " + orderSheetForm.getOrderSheetReceiverAddress1();
        orderSheet.setSheetReceiverAddress(address);
        orderSheet.setSheetOption(orderSheetForm.getOrderSheetReceiverOption());
        orderSheet.setShippingCost(orderSheetForm.getOrderSheetShippingCost());

        orderSheet.setTotalPrice(orderSheetForm.getOrderSheetTotalPrice());
        orderSheet.setRegDate(LocalDateTime.now());
        orderSheet.setNowState(1);
    }

    // 주문 정보를 가져온다.
    public List<OrderSheet> getOrderList(Integer userId) {
        return orderSheetRepository.findByMallUserId(userId);
    }

    // 주문서 리스트를 가져온다.
    public List<OrderSheet> getOrderList() {
        return orderSheetRepository.findAll();
    }

    // 제품 배송 상태를 가져온다. (마이페이지에 출력하기 위함.)
    public List<Integer> getShippingState(HttpSession session) {
        int userid = Ut.isLogined(session).getUserId();
        List<Integer> state = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            state.add(orderSheetRepository.findByNowStateAndMallUserId(i, userid).size());
        }
        return state;
    }

    // 주문 내역을 삭제한다.
    public void deleteOrder(long id) {
        OrderSheet orderSheet = orderSheetRepository.findById(id).get();
        orderSheetRepository.delete(orderSheet);
    }
    // 현재 배송 상태를 수정한다.
    public void modifyShippingOrder(long id, long nowState) {
        OrderSheet orderSheet = orderSheetRepository.findById(id).get();
        orderSheet.setNowState((int)nowState);
        orderSheetRepository.save(orderSheet);
    }
    // 쇼핑카트에서 나온 정보를 주문서에 넣기 전에 설정함.
    public void setOrderCart(OrderSheetForm orderSheetForm, ProductBuyForm productBuyForm, Integer id, List<ShoppingCart> shoppingCartList) {
        List<Integer> prices = shoppingCartService.getPriceList(id);
        productBuyForm.setOrderTotalPrice(prices.get(0));
        productBuyForm.setShippingCost(prices.get(1));
        productBuyForm.setOrderTitle(shoppingCartList.get(0).getProduct().getTitle());
    }

    // 회원가입때 작성된 주소, 핸드폰 번호를 템플릿 양식에 맞게 만들어줌.
    public void convertAdAndPhone(JoinForm joinForm, MallUser mallUser) {
        String[] addressTmp = Ut.splitStar(mallUser.getHomeAddress());
        joinForm.setAddress1(addressTmp[3].trim());
        joinForm.setAddress2(addressTmp[0].trim());
        joinForm.setAddress3(addressTmp[1].trim());
        joinForm.setAddress4(addressTmp[2].trim());
        String[] cellPhoneTmp = Ut.splitCellPhone(mallUser.getCellphone());
        joinForm.setCellphone2_1(cellPhoneTmp[0].trim());
        joinForm.setCellphone2_2(cellPhoneTmp[1].trim());
        joinForm.setCellphone2_3(cellPhoneTmp[2].trim());
    }

}
