package com.kidsart.library.service;


public interface SmsService {
    String generateOtp();
    void sendOtp(String otp);


}
