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

    // 파일 업로드를 진행한다.
    public void doUpload(ProductWriteForm productWriteForm, List<MultipartFile> fileList) {
        String root = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\product_image\\" + productWriteForm.getTitle();

        // 파일 경로에 폴더가 존재하지 않으면 생성한다.
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

    // 파일 삭제를 진행한다.
    public void deleteFile(Product product) {
        String root = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\product_image\\" + product.getTitle();
        new File(root + "\\" + product.getMainImage()).delete();
        for (int i = 0; i < product.getProductImageList().size(); i++) {
            new File(root + "\\" + product.getProductImageList().get(i).getImages()).delete();
        }
        new File(root).delete();
    }
}
