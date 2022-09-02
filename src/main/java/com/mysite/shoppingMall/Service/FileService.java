package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Form.ProductWriteForm;
import com.mysite.shoppingMall.Entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    public void doUpload(ProductWriteForm productWriteForm, List<MultipartFile> fileList) {
        String root = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\product_image\\" + productWriteForm.getTitle();

        File fileCheck = new File(root);
        if (!fileCheck.exists()) fileCheck.mkdirs();

        try {
            for (int i = 0; i < fileList.size(); i++) {
                File uploadFile = new File(root + "\\" + fileList.get(i).getOriginalFilename());
                fileList.get(i).transferTo(uploadFile);
            }
        } catch (IllegalStateException | IOException e) {
            for (int i = 0; i < fileList.size(); i++) {
                new File(root + "\\" + fileList.get(i).getOriginalFilename()).delete();
            }
            e.printStackTrace();
        }
    }

    public void deleteFile(Product product) {
        String root = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\product_image\\" + product.getTitle();
        new File(root + "\\" + product.getMainImage()).delete();
        for (int i = 0; i < product.getProductImageList().size(); i++){
            new File(root + "\\" + product.getProductImageList().get(i).getImages()).delete();
        }
        new File(root).delete();
    }
}
