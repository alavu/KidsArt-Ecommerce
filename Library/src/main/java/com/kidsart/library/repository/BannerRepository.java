package com.kidsart.library.repository;

import com.kidsart.library.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {

    Banner findById(long id);
}
