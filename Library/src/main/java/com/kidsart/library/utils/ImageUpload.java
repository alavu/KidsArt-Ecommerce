package com.kidsart.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Component
public class ImageUpload {
    private final String UPLOAD_FOLDER = "D:\\New Workout BRC\\Week11\\KidsArt-Ecommerce-Springboot\\KidsArt-Ecommerce-Springboot\\Admin\\src\\main\\resources\\static\\imgs\\image-product";
    String UPLOAD_FOLDER_CUSTOMER = "D:\\New Workout BRC\\Week11\\KidsArt-Ecommerce-Springboot\\KidsArt-Ecommerce-Springboot\\Customer\\src\\main\\resources\\static\\imgs\\image-banner";
    public boolean uploadFile(MultipartFile file) {
        boolean isUpload = false;
        try {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_FOLDER + File.separator + file.getOriginalFilename()) , StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExist(MultipartFile multipartFile){
        boolean isExist = false;
        try {
           File file = new File(UPLOAD_FOLDER +"\\" + multipartFile.getOriginalFilename());
           isExist = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExist;
    }
    public String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(UPLOAD_FOLDER);
        Path uploadPathCustomer = Paths.get(UPLOAD_FOLDER_CUSTOMER);


        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            Files.createDirectories(uploadPathCustomer);
        }

        try (InputStream inputStream = file.getInputStream()) {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            Path filePath = uploadPath.resolve(fileName);
            Path filePathCustomer = uploadPathCustomer.resolve(fileName);
            Files.write(filePath,buffer, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(filePathCustomer,buffer, StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);

            return fileName.toString();
        } catch (IOException e) {
            throw new IOException("Could not store file " + fileName, e);
        }
    }
}
