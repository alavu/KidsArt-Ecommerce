package com.kidsart.library.service;

import com.kidsart.library.dto.BannerDto;
import com.kidsart.library.model.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {

    Banner save(BannerDto bannerDto, MultipartFile bannerImage);

    List<BannerDto> getAllBanners();

    BannerDto findById(long id);

    Banner update(BannerDto bannerDto,MultipartFile bannerImage);

    void disable(long id);

    void enable(long id);

    void deleteBanner(long id);
}
