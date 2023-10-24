package com.kidsart.library.service;

import com.kidsart.library.dto.AdminDto;
import com.kidsart.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);
    Admin save(AdminDto adminDto);
}
