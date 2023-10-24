package com.kidsart.library.service;

import com.kidsart.library.model.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    List<Image> findProductImages();
    List<Image> findAll();

}
