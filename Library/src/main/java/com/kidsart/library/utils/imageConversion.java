/*package com.kidsart.library.utils;

import com.kidsart.library.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class imageConversion {
    public List<Image> convertMultipartFilesToImages(List<MultipartFile> imageFiles) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            // Convert MultipartFile to Image and add to the list
            Image image = new Image();
            image.setBytes(file.getBytes()); // Set the image bytes
            image.setContentType(file.getContentType()); // Set content type if needed
            // Other properties as needed
            images.add(image);
        }
        return images;
    }

}
*/